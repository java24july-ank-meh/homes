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
 	    	  $mdToast.show($mdToast.simple().textContent("Associate Assigned").position('top right'));
 	          $scope.hide();
 	         $state.reload();
 	      };

 	      var onError = function (data, status, headers, config) {
 	    	  $mdToast.show($mdToast.simple().textContent(data));
 	      };

 	      $http.post('api/complex-composite/complexcomposite/'+$stateParams.apartmentId +'/assign/'+associateId)
 	      	.success(onSuccess)
 	      	.error(onSuccess);
 	  };

    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.apartment = "";
    };
}]);
