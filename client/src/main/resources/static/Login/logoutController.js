angular.module('rhmsApp').controller('logoutController', ['$scope', '$state', '$rootScope', '$http', function($scope, $state, $rootScope, $http){
	delete $rootScope.rootUser;
	delete $rootScope.rootResident;

	$state.go("login");


}]);

