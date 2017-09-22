angular.module('rhmsApp').controller('maintenanceController', ['$rootScope', '$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', '$mdToast', function($rootScope, $scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $mdToast) {

	$scope.error = false;
	
	$scope.showTable = true;
	
	$scope.toggleView = function(){
		$scope.showTable = !$scope.showTable;
	}
	
	 $scope.deleteRowCallback = function(rows){
	        $mdToast.show(
	            $mdToast.simple()
	                .content('Deleted row id(s): '+rows)
	                .hideDelay(3000)
	        );
	    };
	   
     $http.get("/api/request/maintenance").then(function(response) {
         $scope.maintenanceRequests = response.data;
         
         if($scope.maintenanceRequests === '')
        	 $scope.error = true;
     }, function(response){
    	 $scope.error = true;
    	 $mdToast.show($mdToast.simple().textContent("An Error Occured. Error " + response.status).position('top right'));
     });
     
 	 $scope.resolveMaintenance = function (maintenance) {
 		 
 		 $scope.maintenanceRequest.unitId = $ro

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
	  
	  $scope.getMaintenanceWithId = function(id){
		  console.log(id);
		  return $scope.maintenanceRequests;
		
          return _.find($scope.maintenanceRequests, function(item){
             return item.maintenanceId === id;
          })
      };

}]);