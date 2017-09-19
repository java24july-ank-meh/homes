angular.module('rhmsApp').controller('createComplexController', ['$scope', '$http', '$mdDialog','$state','$mdToast', function($scope, $http, $mdDialog, $state, $mdToast) {

	$scope.place = {};
	$scope.complex = {};
	$scope.selected = {};
	
	$http.get('/api/complex/office')
		.success(function(data){
			$scope.offices = data;
		});
	
    $scope.newComplexFormSubmit = function () {
    	
        var onSuccess = function (data, status, headers, config) {
            $scope.complex.office = {};
        	$scope.complex.office.id = $scope.selected.id;
        	$mdToast.show($mdToast.simple().textContent("Complex Created").position('top right'));
            $state.go('home.showComplex', { complexId: data});
            
        };

        var onError = function (data, status, headers, config) {
        	 $mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }

        $http.post('/api/complex/complex/', $scope.complex )
            .success(onSuccess)
            .error(onError);

    };

    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.complex = "";
    };
}]);
