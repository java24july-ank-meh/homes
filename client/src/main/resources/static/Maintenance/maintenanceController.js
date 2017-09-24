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
	   
     $http.get("/api/request-composite/requestcomposite/maintenances").then(function(response) {
         $scope.maintenanceRequests = response.data.requests;
         
         if($scope.maintenanceRequests === '')
        	 $scope.error = true;
     }, function(response){
    	 $scope.error = true;
    	 $mdToast.show($mdToast.simple().textContent("An Error Occured. Error " + response.status).position('top right'));
     });
     
 	 $scope.resolveMaintenance = function (id) {

 		 console.log("MAINTENANCE" + id);
	      var onSuccess = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent("Maintenance Resolved").position('top right'));
	          $scope.hide();
	          $state.reload();
	      };

	      var onError = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent(data));
	      };

	      $http.post('/api/request/maintenance/'+id+'/complete')
	      	.success(onSuccess)
	      	.error(onSuccess);

	  };
	  
	  $scope.getMaintenanceWithId = function(id){
		  return $scope.maintenanceRequests;
		
          return _.find($scope.maintenanceRequests, function(item){
             return item.maintenanceId === id;
          })
      };
      
      $scope.getAssociate = function(id){
		 
         var associate =_.find($scope.maintenanceRequests, function(item){
             return item.submittedBy === id;
          }).associate;
         
         if(associate != null)
        	 return associate.firstName + " " + associate.lastName;
         else
        	 return null;
      };
      
      $scope.getUnit = function(id){
    	   	  
          var unit =_.find($scope.maintenanceRequests, function(item){
        	  if(item.unit)
        		  return item.unit.unitId === id;
        	  
           }).unit;
   
          return unit.unitNumber + " - " + unit.complex.name;
          
       };
       
 	  $scope.isResolved = function(id){
		
          return _.find($scope.maintenanceRequests, function(item){
             return item.maintenanceId === id;
          });
      };
      


}]);