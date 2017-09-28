angular.module('rhmsApp').controller('dashboardController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', '$rootScope','$q', '$mdToast', '$state', '$filter', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $rootScope, $q, $mdToast, $state, $filter) {
	
	$scope.error = false;
	
	$scope.associate = $rootScope.rootAssociate;

	$scope.moveInDate;
     
	if(!$rootScope.rootAssociate)
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
     }, function(){
    	 $scope.error = true;
     });
     
     $scope.newAssociateFormSubmit = function(moveInDate){
    	 var date = $filter('date')(moveInDate,'yyyy-MM-dd'); 
    	 
    	 var onSuccess = function (data, status, headers, config) {
    		  $http.post('/api/associates/'+$scope.associate.associateId+ "/moveInDate/", date);
    		  
         	$mdToast.show($mdToast.simple().textContent("Associate Updated").position('top right'));
             $state.reload();
             
         };

         var onError = function (data, status, headers, config) {
         	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
         }

         $http.post('/api/associates/associates/createOrUpdate/', $rootScope.rootAssociate)
             .success(onSuccess)
             .error(onError);
     };
}]);