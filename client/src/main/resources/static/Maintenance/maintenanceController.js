angular.module('rhmsApp').controller('maintenanceController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http) {

	$scope.error = false;
	
     $http.get("/api/Maintenance").then(function(response) {
         $scope.maintenanceRequests = response.data;
         
         if($scope.maintenanceRequests === '')
        	 $scope.error = true;
     });
     
 	 $scope.resolveMaintenance = function (maintenance) {

	      var onSuccess = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent("Maintenance Completed").position('top right'));
	          $scope.hide();
	         $state.go('home.showMaintenance');
	      };

	      var onError = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent(data));
	      };

	      $http.post('/api/Maintenance/'+maintenance.maintenanceId+'/complete')
	      	.success(onSuccess)
	      	.error(onSuccess);

	  };

}]);