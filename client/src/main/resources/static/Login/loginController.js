angular.module('rhmsApp').controller('loginController', ['$scope', '$http', '$rootScope', function($scope, $http, $rootScope){
/*    $http.get("/HousingOnlineManagementSystem/api/login").then(function(response) {
        $scope.code = response.data;
    });*/
	
    if(!$rootScope.rootUser == undefined){
    	$state.go("home.dashboard");
    }

}]);

