angular.module('rhmsApp').controller('showApartmentController', ['$scope', '$mdBottomSheet','$http', '$mdDialog','$stateParams','$state' ,'$mdToast', '$rootScope', function($scope, $mdBottomSheet,$http, $mdDialog, $stateParams, $state, $mdToast, $rootScope ) {

	$scope.error = false;
	$scope.aptannouncement = ' ';
	
     $http.get("/api/Apartments/"+$stateParams.apartmentId).then(function(response) {
         $scope.apartment = response.data;
         
         if($scope.apartment === ''){

        	 $mdToast.show($mdToast.simple().textContent("Apartment not found").position('top right'));
        	 $scope.error = true;
         }
         else
        	$http.get("/api/ApartmentComplexes/"+$scope.apartment.complex).then(function(response) {
        	 $scope.complex = response.data;
        	 
        	 $http.get("/api/Apartments/"+$scope.apartment.apartmentId +'/Maintenance').then(function(response) {
            	 $scope.maintenanceRequests = response.data;
        	 });
        	 
        	 
             if($scope.complex === ''){
            	 $mdToast.show($mdToast.simple().textContent("Complex Not Found").position('top right'));
            	 $scope.error = true;
             } else {
            	 
            	 var parsedAddress = "https://www.google.com/maps/embed/v1/directions?key=AIzaSyC9rOv9rx7A2EL0oOZGXkhuvkJYIVfkqGA&origin="+$scope.complex.address.split(' ').join('+')+"&destination=11730+Plaza+America+Drive,+Reston,+VA&avoid=tolls|highways";
            	 document.getElementById('complexMap').src = parsedAddress;
             }
         });
         
     },function (response){
    	   $mdToast.show($mdToast.simple().textContent(response));
     });
     
	  $scope.showConfirm = function(deleteApartment) {

		    var confirm = $mdDialog.confirm()
		          .title('Do you really want to delete the Apartment?')
		          .targetEvent(event)
		          .ok('Delete')
		          .cancel('Cancel');

		    $mdDialog.show(confirm).then(function() {
		      $scope.deleteApartment();
		    });
		  };

  $scope.deleteApartment = function () {

      var onSuccess = function (data, status, headers, config) {
    	  $mdToast.show($mdToast.simple().textContent("Apartment Deleted").position('top right'));
          $state.go('home.showComplex', { complexId: $scope.apartment.complex});
      };

      var onError = function (data, status, headers, config) {
    	  $mdToast.show($mdToast.simple().textContent(data));
      };

      $http.delete('/api/Apartments/'+$stateParams.apartmentId)
      	.success(onSuccess)
      	.error(onSuccess);

  };
  
  $scope.showEditApartmentForm = function(ev) {
	  
	    $mdDialog.show({
	      controller: 'editApartmentController',
	      templateUrl: '/../../Apartments/Edit/edit.html',
	      parent: angular.element(document.body),
	      targetEvent: ev,
	      clickOutsideToClose:true,
	      fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
	    });
  };
  
  $scope.showRemoveResidentConfirm = function(residentId, removeResident) {

	    var confirm = $mdDialog.confirm()
	          .title('Do you really want to remove the Resident?')
	          .targetEvent(event)
	          .ok('Remove')
	          .cancel('Cancel');

	    $mdDialog.show(confirm).then(function() {
	      $scope.removeResident(residentId);
	    });
	  };
  
  
	 $scope.removeResident = function (residentId) {

	      var onSuccess = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent("Resident Removed").position('top right'));
	          $state.go('home.showApartment', { apartmentId: $scope.apartment.apartmentId});
	          $state.reload();
	      };

	      var onError = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent(data));
	      };

	      $http.delete('/api/Residents/'+residentId+'/Apartment')
	      	.success(onSuccess)
	      	.error(onSuccess);

	  };
	  
  $scope.showAssignResidentForm = function(ev) {
		  
		  $mdDialog.show({
			  controller: 'assignResidentController',
			  templateUrl: '/../../Apartments/Assign/assign.html',
			  parent: angular.element(document.body),
			  targetEvent: ev,
			  clickOutsideToClose:true,
			  fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
		  });
			  
	  };
	  
$scope.sendAnnouncementFormSubmit = function(event){
		  
	  var onSuccess = function (data, status, headers, config) {
          
     	 $mdToast.show($mdToast.simple().textContent("Message sent!").position('top right'));
         
     };

     var onError = function (data, status, headers, config) {
     	 $mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
     }

     $http.post('/api/Apartments/message/'+$stateParams.apartmentId, $scope.aptannouncement )
         .success(onSuccess)
         .error(onError);

		  
	  };
	  
  

}]);