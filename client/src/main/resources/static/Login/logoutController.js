angular.module('rhmsApp').controller('logoutController', ['$scope', '$state', '$rootScope', '$http', 'localStorageService', function($scope, $state, $rootScope, $http, localStorageService){
	delete $rootScope.rootUser;
	delete $rootScope.rootAssociate;
	
	localStorageService.clearAll();
	$state.go("login");
}]);

