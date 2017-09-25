/**
 * 
 */

angular.module("app", []).controller("homepage", function($http){
	var self = this;
	$http.get("/user").success(function(data){
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
	
	self.logout = function(){
		$http.post('/logout', {}).success(function(){
			self.authenticated = false;
		}).error(function(data){
			console.log("Logout failed");
			self.authenticated = false;
		});
	};
	
	/*Slack returns the user info object as a string. This method retrieves the value of the 
	name attribute (between the string 'name=' and the following comma) */
	let getNameFromString = function(input){
		
		let splitOnName = input.split('name=');
		let splitOnComma = splitOnName[1].split(',');
		return splitOnComma[0];
	}
});