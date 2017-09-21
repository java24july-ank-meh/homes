angular.module('rhmsApp').controller('associatesController', ['$scope', '$mdBottomSheet','$http', '$mdDialog','$stateParams','$state' ,'$mdToast', '$filter', 'NgTableParams', function($scope, $mdBottomSheet,$http, $mdDialog, $stateParams, $state, $mdToast, $filter, NgTableParams) {
	
	$scope.error = false;
	
	$http.get("/api/associates/associates").then(function(response) {
		$scope.associates = response.data;
    }, function(response){
   	 $scope.error = true;
	 $mdToast.show($mdToast.simple().textContent("An Error Occured. Error " + response.status).position('top right'));
    });

	$http.get("/api/complex/complex").then(function(response) {
		
		$scope.residentData = [];
		response.data.forEach(function(complex) {
			if(complex.units){
				complex.units.forEach(function(unit) {
					if(unit.residents){ 
						unit.residents.forEach(function(resident) {
							$scope.residents.forEach(function(trueResident) {
								if (trueResident.apartmentNumber === undefined){
									trueResident.apartmentNumber = 0;
									trueResident.complexName = "none";
								}
									
								if (trueResident.residentId === resident.residentId) {
									trueResident.apartmentNumber = apartment.apartmentNumber;
									trueResident.complexName = complex.name;
								}
							});
						});
					}
				});
			}
		});
		console.log($scope.complexes)
		$scope.residentsTable = new NgTableParams({}, { dataset: $scope.residents});	
		console.log($scope.residentData);
	});
	
	$scope.showCreateResidentForm = function(ev) {
		  
		  $mdDialog.show({
			  controller: 'createAssociateController',
			  templateUrl: '/../../Associates/Create/createAssociate.html',
			  parent: angular.element(document.body),
			  targetEvent: ev,
			  clickOutsideToClose:true,
			  fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
		  });
			  
	  };
	
}]);
