angular.module('rhmsApp').controller('assignResidentController', ['$scope', '$http', '$mdDialog','$state', '$stateParams', '$mdToast', function($scope, $http, $mdDialog, $state, $stateParams, $mdToast) {

    $http.get("/api/associates/associates")
    .then(function(response) {
        $scope.associates = response.data;
    });
    
    $scope.hide = function() {
        $mdDialog.hide();
      };

      $scope.cancel = function() {
        $mdDialog.cancel();
      };
      
 	 $scope.assignAssociate = function (associateId) {

 	      var onSuccess = function (data, status, headers, config) {
 	    	  	$http.get("/api/complex/unit/" + $stateParams.apartmentId).then(function(response){
 	 	    	  	let unit = response.data;
 	    	  		let complexName = unit.complex.name;
 	    	  		let unitNumber = unit.unitNumber;
 	    	  		let buildingNumber = unit.buildingNumber;
 	    	  		
 	 	    		$http.get("/api/associates/associates/"+$stateParams.associateId).then(function(response){
 	 	    			let residentEmail = response.data.email;
 	 	    			$http.post("/api/slack/complexInvite",{email:residentEmail,complex:complexName,token:$rootScope.rootUser.token});
 	 	    			$http.post("/api/slack/unitInvite",{email:residentEmail,complex:complexName,unit:buildingNumber+"-"+unitNumber,token:$rootScope.rootUser.token});
 	 	    		});
 	 	    		
 	    	  	});
 	    	  
 	    	  $mdToast.show($mdToast.simple().textContent("Associate Assigned").position('top right'));
 	          $scope.hide();
 	         $state.reload();
 	      };

 	      var onError = function (data, status, headers, config) {
 	    	  $mdToast.show($mdToast.simple().textContent(data));
 	      };

 	      $http.post('api/associates/associates/'+associateId+'/assign/'+$stateParams.apartmentId)
 	      	.success(onSuccess)
 	      	.error(onSuccess);
 	  };

    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.apartment = "";
    };
}]);
