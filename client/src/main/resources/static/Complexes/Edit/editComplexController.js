angular.module('rhmsApp').controller('editComplexController', ['$scope', '$http', '$stateParams','$mdDialog','$state','$mdToast', function($scope, $http, $stateParams, $mdDialog, $state, $mdToast ) {


    $http.get("/api/complex/complex/"+$stateParams.complexId)
        .success(function(data) {
            $scope.complex = data;

        })
        .error(function(){
        	 $mdToast.show($mdToast.simple().textContent("Complex Not Found").position('top right'));
        });


    $scope.editComplexFormSubmit = function () {

        var onSuccess = function (data, status, headers, config) {
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
