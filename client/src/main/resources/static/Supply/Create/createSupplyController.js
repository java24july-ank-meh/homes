angular.module('rhmsApp').controller('createSupplyController', ['$scope', '$http', '$mdDialog','$state','$mdToast', '$rootScope', function($scope, $http, $mdDialog, $state, $mdToast, $rootScope) {

	$scope.supply = [
		{
			'name': 'Detergent Pods',
			'selected': false
		},	
		{
			'name': 'Dish Soap',
			'selected': false
		},
		{
			'name': 'Paper Towels',
			'selected': false
		},
		{
			'name': 'Sponge',
			'selected': false
		},

		{
			'name': 'Trash Bags',
			'selected': false
		},
		{
			'name': 'Toilet Paper',
			'selected': false
		}
	];

	
    $scope.newSupplyFormSubmit = function () {
    	
    	//Get selected Supplies
    	var requestedSupplies = [];

	    angular.forEach($scope.supply, function (item, value) {
	        if (item.selected){
	        		item.unitId = $rootScope.rootAssociate.unitId;
	        		item.submittedBy = $rootScope.rootAssociate.associateId;
	        		requestedSupplies.push(item);
	        	}
	    });
	    
        var onSuccess = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Supply Request Created").position('top right'));
            $state.go('home.showApartment', { apartmentId: $rootScope.rootAssociate.unitId});
        };

        var onError = function (data, status, headers, config) {
        	 $mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }

        $http.post('/api/request/units/'+$rootScope.rootAssociate.unitId+'/supply', requestedSupplies)
            .success(onSuccess)
            .error(onError);
    };

    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.supplies = "";
    };
}]);
