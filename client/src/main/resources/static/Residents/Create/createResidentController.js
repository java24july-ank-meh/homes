angular.module('rhmsApp').controller('createResidentController', ['$scope', '$http', '$mdDialog','$state', '$stateParams', '$mdToast', function($scope, $http, $mdDialog, $state, $stateParams, $mdToast) {


    $http.get("/api/Residents/")
    .then(function(response) {
        $scope.residents = response.data;
    });
    /*$http.get("/api/Apartments/1").then(function(response) {
    	$scope.unnassignedApartment = response.data; //assign to the unnassigned complex
    	console.log($scope.unnassignedApartment)
    });*/
	
	
    $scope.createResidentFormSubmit = function () {

        var onSuccess = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Resident Created").position('top right'));
            $state.go('home.showResident', { residentId: data});
            $scope.hide();
            
        };

        var onError = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }
        
        //$scope.resident.apartment = $scope.unnassignedApartment;
        //console.log($scope.resident);
        $http.post('/api/Residents/Create', $scope.resident)
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
        $scope.resident = "";
    };
}]);
