angular.module('rhmsApp').controller('createComplexController', ['$scope', '$http', '$mdDialog','$state','$mdToast', function($scope, $http, $mdDialog, $state, $mdToast) {

	$scope.place = {};
	$scope.complex = {};
	
    $scope.newComplexFormSubmit = function () {

        var onSuccess = function (data, status, headers, config) {
            
        	 $mdToast.show($mdToast.simple().textContent("Complex Created").position('top right'));
            $state.go('home.showComplex', { complexId: data});
            
        };

        var onError = function (data, status, headers, config) {
        	 $mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }

        $http.post('/api/ApartmentComplexes/create', $scope.complex )
            .success(onSuccess)
            .error(onError);

    };

    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.complex = "";
    };
}]);
