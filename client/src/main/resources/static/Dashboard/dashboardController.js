angular.module('rhmsApp').controller('dashboardController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', '$rootScope','$q', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $rootScope, $q) {
	
	$scope.error = false;
	
	
	///api/complex/unit  <--- units without residents
	///api/complex-composite/complexcomposite/units  <--- units with residents
	
	/*
	$http.get("/api/complex/unit").then(function(response) {
         $scope.units = response.data;
     }, function(response){
    	 $scope.error = true;
     });
     $http.get("/api/associates").then(function(response) {
         $scope.residents = response.data;
     });
	 */
     
     $q.all({
    	 units: $http.get("/api/complex/unit"),
    	 associates: $http.get("/api/associates/associates")
     }).then(function(results) {
    	 $scope.units = results.units.data;
    	 $scope.associates = results.associates.data;
    	 
    	 for(var i = 0; i < $scope.units.length; i++){
    		 $scope.units[i].associates = [];
    		 
    		 for(var j = 0; j < $scope.associates.length; j++){
    			 if($scope.units[i].unitId == $scope.associates[j].unitId){
    				 $scope.units[i].associates.push($scope.associates[j])
    			 }
    		 }
    	 }
     });
}]);