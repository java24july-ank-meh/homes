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
    	}  else if(response.data.scope === ""){
    	}else{
    		$rootScope.user = response.data;
    		$location.path("/home/dashboard");
    	}
    });

}]);
