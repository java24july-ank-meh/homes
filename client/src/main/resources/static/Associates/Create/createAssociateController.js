angular.module('rhmsApp').controller('createAssociateController', ['$scope', '$http', '$mdDialog','$state', '$stateParams', '$mdToast', function($scope, $http, $mdDialog, $state, $stateParams, $mdToast) {

	$scope.selected = {};
    $http.get("/api/associates/associates/")
    .then(function(response) {
        $scope.associates = response.data;
    });
   
	$http.get('/api/complex/office')
	.then(function(response){
		$scope.offices = response.data;
	}, function(response){
		$mdToast.show($mdToast.simple().textContent("An Error Occured. Error " + response.status).position('top right'));
	});
	
    $scope.createResidentFormSubmit = function () {
    	

    	$scope.associate.officeId = JSON.parse($scope.selected).officeId;

        var onSuccess = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Associate Created").position('top right'));
            $state.go('home.showAssociate', { residentId: data});
            $scope.hide();
            
        };

        var onError = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }

        $http.post('/api/associates/associates/createOrUpdate/', $scope.associate)
            .success(onSuccess)
            .error(onError);

    };
    
    $scope.hide = function() {
        $mdDialog.hide();
      };
 
      $scope.cancel = function() {
        $mdDialog.cancel();
      };
      
      
 	 /*$scope.assignResident = function (residentId) {

 	      var onSuccess = function (data, status, headers, config) {
 	    	  $mdToast.show($mdToast.simple().textContent("Resident Assigned").position('top right'));
 	          $scope.hide();
 	         $state.reload();
 	      };

 	      var onError = function (data, status, headers, config) {
 	    	  $mdToast.show($mdToast.simple().textContent(data));
 	      };

 	      $http.post('/api/Apartments/'+$stateParams.apartmentId+'/Resident/'+residentId)
 	      	.success(onSuccess)
 	      	.error(onSuccess);

 	  };*/


    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.associate = "";
    };
}]);
