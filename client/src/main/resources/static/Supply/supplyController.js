angular.module('rhmsApp').controller('supplyController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http','$mdToast', '$rootScope', '$state', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $mdToast, $rootScope, $state) {
	
	if(!$rootScope.rootUser.isManager)
		$state.go('home.dashboard');
	
    $scope.deleteRowCallback = function(rows){
        $mdToast.show(
            $mdToast.simple()
                .content('Deleted row id(s): '+rows)
                .hideDelay(3000)
        );
    };
	
	$scope.error = false;
	
     $http.get("/api/request-composite/requestcomposite/supplies").then(function(response) {
         $scope.supplyRequests = response.data.requests;

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
	    	  $mdToast.show($mdToast.simple().textContent("Error " + data.status));
	      };

	      $http.post('/api/request/supply/'+id+'/complete')
	      	.success(onSuccess)
	      	.error(onError);

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
	             return item.supplyId == id;
	          });
	      };
}]);