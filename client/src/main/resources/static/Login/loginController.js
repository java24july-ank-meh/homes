angular.module('rhmsApp').controller('loginController', ['$scope', '$http', '$rootScope', '$location','$window','localStorageService', function($scope, $http, $rootScope, $location,$window, localStorageService){
/*    $http.get("/HousingOnlineManagementSystem/api/login").then(function(response) {
        $scope.code = response.data;
    });*/
    var paramValue = $location.search().code;
    localStorageService.set("slack", paramValue);
	$http.post("/api/slack/manager/scopes/basic",{code : paramValue}).then(function(response) {

		if(response.data.scope === "basic"){
	        	$window.location.href = "https://slack.com/oauth/authorize?scope=channels:write,channels:read,chat:write,users:read,users:read.email,groups:write,im:write,mpim:write&client_id=237895291120.238685216005";	
    	}else if(response.data.scope === "client"){
				$window.location.href = "https://slack.com/oauth/authorize?scope=client&client_id=237895291120.238685216005";
    	}  else if(response.data.scope === ""){
    	}else{
    		$rootScope.rootUser = response.data;
			localStorageService.set("rootUser", $rootScope.rootUser);
			$location.path("/home/dashboard");
    	}
    });
}]);
