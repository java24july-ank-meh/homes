angular.module('rhmsApp').controller('associatesController', ['$scope', '$mdBottomSheet','$http', '$mdDialog','$stateParams','$state' ,'$mdToast', '$filter', 'NgTableParams', function($scope, $mdBottomSheet,$http, $mdDialog, $stateParams, $state, $mdToast, $filter, NgTableParams) {
	
	$scope.error = false;
	
	$http.get("api/resident-composite/residentcomposite/residentinfo/withRoomDetails").then(function(response) {
		$scope.associatedata = response.data;
		$scope.residentsTable = new NgTableParams({}, { dataset: $scope.associatedata});
    }, function(response){
   	 $scope.error = true;
	 $mdToast.show($mdToast.simple().textContent("An Error Occured. Error " + response.status).position('top right'));
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
