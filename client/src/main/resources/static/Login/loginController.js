angular.module('rhmsApp').controller('loginController', ['$scope', '$http', '$rootScope', '$location', function($scope, $http, $rootScope, $location){

	
    var paramValue = $location.search().code;
    if(!paramValue == '')
		$http.post("/api/slack/manager/scopes/basic",{code : paramValue}).then(function(response) {
			
			//$scope.user = resmponse.data;
			//$location.path("/home/dashboard");
			if(response.data.scope === "basic"){
		        	$window.location.href = "https://slack.com/oauth/authorize?scope=channels:write,Channels:read,Chat:write,Users:read,Groups:write,Im:write,mpim:write";	
	    	}else if(response.data.scope === "client"){
					$window.location.href = "https://slack.com/oauth/authorize?scope=client";
	    	}else if(response.data.scope === ""){
	    		
	    		
	    		
	    		
	    	}else{
	    		$rootScope.rootUser = response.data;
	    		alert(response.data);
	    		console.log(response.data);
	    		$location.path("/home/dashboard");
	    	}
	    });

}]);
