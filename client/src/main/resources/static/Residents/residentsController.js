angular.module('rhmsApp').controller('residentsController', ['$scope', '$mdBottomSheet','$http', '$mdDialog','$stateParams','$state' ,'$mdToast', '$filter', 'NgTableParams', function($scope, $mdBottomSheet,$http, $mdDialog, $stateParams, $state, $mdToast, $filter, NgTableParams) {
	
	$http.get("/api/Residents").then(function(response) {
		$scope.residents = response.data;
    });

	$http.get("/api/ApartmentComplexes").then(function(response) {
		
		
		$scope.residentData = [];
		response.data.forEach(function(complex) {
			if(complex.apartments){
				complex.apartments.forEach(function(apartment) {
					if(apartment.residents){ 
						apartment.residents.forEach(function(resident) {
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
			  controller: 'createResidentController',
			  templateUrl: '/../../Residents/Create/createResident.html',
			  parent: angular.element(document.body),
			  targetEvent: ev,
			  clickOutsideToClose:true,
			  fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
		  });
			  
	  };
	
}]);
