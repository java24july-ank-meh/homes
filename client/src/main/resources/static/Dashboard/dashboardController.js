angular.module('rhmsApp').controller('dashboardController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', '$rootScope','$q', '$mdToast', '$state', '$filter', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $rootScope, $q, $mdToast, $state, $filter) {
	
	$scope.error = false;
	
	$scope.associate = $rootScope.rootAssociate;

	$scope.moveInDate;
     
	if($rootScope.rootUser.isManager)
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
     
     $scope.newAssociateFormSubmit = function(moveInDate){
    	 $scope.associate.moveInDateString =  $filter('date')(moveInDate,'yyyy-MM-dd'); 
    	 
    	 console.log($scope.associate.moveInDate, $scope.moveInDate);
    	 
    	 var onSuccess = function (data, status, headers, config) {
    		 alert($scope.associate);
         	$mdToast.show($mdToast.simple().textContent("Associate Updated").position('top right'));
             $state.reload();
             
         };

         var onError = function (data, status, headers, config) {
         	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
         }

         $http.post('/api/associates/associates/createOrUpdate/', $scope.associate)
             .success(onSuccess)
             .error(onError);
     };
}]);