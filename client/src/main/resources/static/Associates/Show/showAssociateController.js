angular.module('rhmsApp').controller('showAssociateController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog','$http', '$stateParams', '$state', '$rootScope', '$mdToast', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $stateParams, $state, $rootScope, $mdToast) {
	
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
            $state.go('home.associates');
        };

        var onError = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Error occured").position('top right'));
        };

        $http.delete('/api/associates/associates/'+ $scope.associate.associateId)
        	.success(onSuccess)
        	.error(onError);

    };
    
     $http.get("/api/associates/associates/"+$stateParams.associateId).then(function(response) {

         $scope.associate = response.data;
         $scope.associate.moveInString = $scope.associate.moveInDate.month+" "+$scope.associate.moveInDate.dayOfMonth+", "+$scope.associate.moveInDate.year+" "+$scope.associate.moveInDate.hour+":"+$scope.associate.moveInDate.minute;
         $scope.associate.housingAgreedString = $scope.associate.housingAgreed != null ? "Signed on " + $scope.associate.housingAgreed.month+" "+$scope.associate.housingAgreed.dayOfMonth+", "+$scope.associate.housingAgreed.year : "Not Signed";
     });
     
     $scope.showEditResidentForm = function(ev){
    	
    	 $mdDialog.show({
    		 controller: 'editAssociateController',
    		 templateUrl: '/../../Associates/Edit/edit.html',
    		 parent: angular.element(document.body),
    		 targetEvent: ev,
    		 clickOutsideToClose: true,
    		 fullscreen: $scope.customFullScreen
    	 });
     };
}]);