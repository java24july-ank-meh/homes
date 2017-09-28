angular.module('rhmsApp').controller('editComplexController', ['$scope', '$http', '$stateParams','$mdDialog','$state','$mdToast', '$rootScope', function($scope, $http, $stateParams, $mdDialog, $state, $mdToast, $rootScope ) {

	let oldComplex;
    $http.get("/api/complex/complex/"+$stateParams.complexId)
        .success(function(data) {
            $scope.complex = data;
            oldComplex = data;

        })
        .error(function(){
        	 $mdToast.show($mdToast.simple().textContent("Complex Not Found").position('top right'));
        });


    $scope.editComplexFormSubmit = function () {
    	
    	let oldChannelName = "";
    	
        var onSuccess = function (data, status, headers, config) {
        	
        	$http.get('/api/slack/complex/channelName/' + oldComplex.name)
    		.success(function(data){
    			oldChannelName = data; 
    			
    			$http.post('/api/slack/complex/update', {oldName: $scope.oldChannelName, 
            		newName: $scope.complex.name, token:$rootScope.rootUser.token});
    			
    		});
        	
        	 $mdToast.show($mdToast.simple().textContent("Complex Updated").position('top right'));
            $state.go('home.showComplex', { complexId: data });
        };

        var onError = function (data, status, headers, config) {
        	 $mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        };
        
        $http.put('/api/complex/complex/'+$stateParams.complexId, $scope.complex )
        	.success(onSuccess)
        	.error(onError);

    };

    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.complex = "";
    };
}]);
