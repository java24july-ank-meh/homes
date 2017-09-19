angular.module('rhmsApp').controller('createMaintenanceController', ['$scope', '$http', '$mdDialog','$state','$mdToast', '$rootScope', function($scope, $http, $mdDialog, $state, $mdToast, $rootScope) {

	$scope.problems = [
		'Electrical',
		'Heating',
		'Air Conditioning',
		'Plumbing',
		'Carpentry',
		'Locksmith',
		'Refrigerator',
		'Washer / Dryer',
		'Other'
	];
	
	$scope.locations = [
		'Bathroom',
		'Bedroom',
		'Kitchen',
		'Dining Area',
		'Living Room',
		'Other'
	];
	$scope.maintenance = {};
	$scope.maintenance.date = new Date();
	
	
    $scope.newMaintenanceFormSubmit = function () {

        var onSuccess = function (data, status, headers, config) {
            
        	 $mdToast.show($mdToast.simple().textContent("Maintenance Created").position('top right'));
            $state.go('home.showApartment', { apartmentId: $rootScope.rootResident.apartment});
            
        };

        var onError = function (data, status, headers, config) {
        	 $mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }

        $http.post('/api/Apartments/'+$rootScope.rootResident.apartment +'/Maintenance/create', $scope.maintenance )
            .success(onSuccess)
            .error(onError);

    };

    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.complex = "";
    };
}]);
