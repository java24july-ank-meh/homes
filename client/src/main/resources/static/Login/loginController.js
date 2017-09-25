angular.module('rhmsApp').controller('loginController', ['$scope', '$http', '$rootScope', '$location', function($scope, $http, $rootScope, $location){
/*    $http.get("/HousingOnlineManagementSystem/api/login").then(function(response) {
        $scope.code = response.data;
    });*/
	
    if(!$rootScope.rootUser == undefined){
    	$state.go("home.dashboard");
    }
    
    var paramValue = $location.search().code;
	$http.post("/api/slack/manager/scopes/basic",{code : paramValue}).then(function(response) {
		
		//$scope.user = resmponse.data;
		//$location.path("/home/dashboard");
		if(response.data.scope === "basic"){
	        	$window.location.href = "https://slack.com/oauth/authorize?scope=channels:write,Channels:read,Chat:write,Users:read,Groups:write,Im:write,mpim:write";	
    	}else if(response.data.scope === "client"){
				$window.location.href = "https://slack.com/oauth/authorize?scope=client";
    	}  else if(response.data.scope === "all"){
    		console.log("you in bro!");
    		$location.path("/home/dashboard");
    	}
    });
    
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
