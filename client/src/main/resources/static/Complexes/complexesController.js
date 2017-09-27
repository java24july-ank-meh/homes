angular.module('rhmsApp').controller('complexesController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', '$rootScope', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $rootScope) {

	$scope.error = false;
	
	$scope.selected = {};
	
	$scope.isManager = $rootScope.rootUser.isManager;
	
	$http.get('/api/complex/office')
	.success(function(data){
		$scope.offices = data;
		$scope.filtered = $scope.offices;
	});

     $http.get("/api/complex-composite/complexcomposite").then(function(response) {
         $scope.complexes = response.data.complexes;
         
         if($scope.complexes === '')
        	 $scope.error = true;
     },function(response){
    	 $scope.error = true;
     });

}]);