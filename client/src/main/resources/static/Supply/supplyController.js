angular.module('rhmsApp').controller('supplyController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http','$mdToast',  function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $mdToast) {

    $scope.deleteRowCallback = function(rows){
        $mdToast.show(
            $mdToast.simple()
                .content('Deleted row id(s): '+rows)
                .hideDelay(3000)
        );
    };
	
	$scope.error = false;
	
     $http.get("/api/request/supply").then(function(response) {
         $scope.supplyRequests = response.data;

     }, function(response){
        $scope.error = true;
     });
     
     $scope.resolveSupply = function (id) {

	      var onSuccess = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent("Supply Delivered").position('top right'));
	          $scope.hide();
	          $state.reload();
	      };

	      var onError = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent(data));
	      };

	      $http.post('/api/request/supply/'+id+'/complete')
	      	.success(onSuccess)
	      	.error(onSuccess);

	  };
	  
	    $scope.getAssociate = function(id){
	         var associate =_.find($scope.supplyRequests, function(item){
	             return item.submittedBy === id;
	          }).associate;
	         
	         if(associate != null)
	        	 return associate.firstName + " " + associate.lastName;
	         else
	        	 return null;
	      };
	      
	      $scope.getUnit = function(id){
	    	   	  
	          var unit =_.find($scope.supplyRequests, function(item){
	        	  if(item.unit)
	        		  return item.unit.unitId === id;
	        	  
	           }).unit;
	   
	          return unit.unitNumber + " - " + unit.complex.name;
	          
	       };
	       
	 	  $scope.isResolved = function(id){
			
	          return _.find($scope.supplyRequests, function(item){
	             return item.suppliesId == id;
	          });
	      };
	      

}]);