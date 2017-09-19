angular.module('rhmsApp').controller('showResidentController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog','$http', '$stateParams', '$state', '$rootScope', '$mdToast', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $stateParams, $state, $rootScope, $mdToast) {
	
	
	  $scope.showConfirm = function(deleteResident) {

		    var confirm = $mdDialog.confirm()
		          .title('Do you really want to delete the Resident?')
		          .targetEvent(event)
		          .ok('Delete')
		          .cancel('Cancel');

		    $mdDialog.show(confirm).then(function() {
		      $scope.deleteResident();
		    });
		  };

	
    $scope.deleteResident = function () {

        var onSuccess = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Resident Deleted").position('top right'));
            $state.go('home.residents');
        };

        var onError = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Error occured").position('top right'));
        };

        $http.delete('/api/Residents/'+ $scope.resident.residentId)
        	.success(onSuccess)
        	.error(onError);

    };
    

     $http.get("/api/Residents/"+$stateParams.residentId).then(function(response) {

         $scope.resident = response.data;
         console.log(response.data);
     });
     
     
     $scope.showEditResidentForm = function(ev){
    	
    	 $mdDialog.show({
    		 controller: 'editResidentController',
    		 templateUrl: '/../../Residents/Edit/edit.html',
    		 parent: angular.element(document.body),
    		 targetEvent: ev,
    		 clickOutsideToClose: true,
    		 fullscreen: $scope.customFullScreen
    	 });
    	 
     };

}]);