angular.module('rhmsApp').controller('loginController', ['$scope', '$http', '$rootScope', function($scope, $http, $rootScope){
/*    $http.get("/HousingOnlineManagementSystem/api/login").then(function(response) {
        $scope.code = response.data;
    });*/
	
    if(!$rootScope.rootUser == undefined){
    	$state.go("home.dashboard");
    }
    
    $http.get("api/slack/user").success(function(data){
		console.log(data);
		let username = getNameFromString(data.name);
	    self.user = username;
		self.authenticated = true;
	}).error(function(response){
		console.log("error");
		console.log(response);
		self.user = "N/A";
		self.authenticated = false;
	});

}]);

