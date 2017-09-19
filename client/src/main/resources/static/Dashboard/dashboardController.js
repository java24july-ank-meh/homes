angular.module('rhmsApp').controller('dashboardController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', '$rootScope', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $rootScope) {
	
	$scope.error = false;
	
     $http.get("/api/Apartments").then(function(response) {
         $scope.apartments = response.data;
         
         
         if($scope.complexes === '')
        	 $scope.error = true;
     });
     

     $http.get("/api/Residents").then(function(response) {
         $scope.residents = response.data;
         
         
         if($scope.complexes === '')
        	 $scope.error = true;
     });

}]);