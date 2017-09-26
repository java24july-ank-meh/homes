angular.module('rhmsApp').controller('importAssociateController', ['$scope', '$http', '$mdDialog','$state', '$stateParams', '$mdToast', function($scope, $http, $mdDialog, $state, $stateParams, $mdToast) {
	$scope.formData = {};

    /*$http.get("/api/associates/associates/")
    .then(function(response) {
        $scope.associates = response.data;
    });*/
	
	
    $scope.importResidentFormSubmit = function (formData) {

        var onSuccess = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Associates Created").position('top right'));
            $state.go('home.associates');
            $scope.hide();
            
        };

        var onError = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }
        
        //$scope.resident.apartment = $scope.unnassignedApartment;
        //console.log($scope.resident);
        let fd = new FormData();
        fd.append("file", fileInputElement.files[0]);
        console.log(formData);
        console.log(fd);
        $http.post('/api/associates/import/excel/', formData)
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
        $scope.file = "";
    };
}]);
