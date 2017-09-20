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
    	
    	$scope.maintenance.unitId = $rootScope.rootAssociate.unitId;
    	$scope.maintenance.submittedBy = $rootScope.rootAssociate.associateId;

        var onSuccess = function (data, status, headers, config) {
            
        	 $mdToast.show($mdToast.simple().textContent("Maintenance Created").position('top right'));
            $state.go('home.showApartment', { apartmentId: $rootScope.rootAssociate.unitId});
            
        };

        var onError = function (data, status, headers, config) {
        	 $mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }

        $http.post('/api/request/units/'+$rootScope.rootAssociate.unitId+'/maintenance', $scope.maintenance )
            .success(onSuccess)
            .error(onError);

    };

    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.complex = "";
    };
}]);
