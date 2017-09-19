angular.module('rhmsApp').controller('logoutController', ['$scope', '$state', '$rootScope', '$http', function($scope, $state, $rootScope, $http){
	delete $rootScope.rootUser;
	delete $rootScope.rootResident;

	$http.get('api/logout1').then(function(response){
	$state.go("login", {}, {reload: true});
	});

}]);

