angular.module('rhmsApp').controller('complexesController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http) {

	$scope.error = false;
	
     $http.get("/api/complex/complex").then(function(response) {
         $scope.complexes = response.data;
         
         if($scope.complexes === '')
        	 $scope.error = true;
     });

}]);