angular.module('rhmsApp').controller('dashboardController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', '$rootScope', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $rootScope) {
	
	$scope.error = false;
	
     $http.get("/api/complex/units").then(function(response) {
         $scope.units = response.data;
         
         
         if($scope.complexes === '')
        	 $scope.error = true;
     });
     

     $http.get("/api/associates/associates").then(function(response) {
         $scope.residents = response.data;
         
         
         if($scope.complexes === '')
        	 $scope.error = true;
     });

}]);