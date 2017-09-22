/**
 * 
 */

angular.module("app", []).controller("resident", function($http){
	
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
	
	/*Slack returns the user info object as a string. This method retrieves the value of the 
	name attribute (between the string 'name=' and the following comma) */
	let getNameFromString = function(input){
		
		let splitOnName = input.split('name=');
		let splitOnComma = splitOnName[1].split(',');
		return splitOnComma[0];
	};
	
	self.sendMessage = function(){
		let url = "/resident/message";
		$http.post(url, {complex:self.messageComplex, unit:self.messageUnit, message:self.messageToSend})
				.success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
	};
	
	self.messageUser = function(){
		let url = "/resident/message/users";
		$http.post(url, {complex:self.messageComplex, unit:self.messageUnit, message:self.messageToSend, ids:self.ids})
				.success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
	};
	
	self.listUsers = function(){
		let url = "/resident/message/listusers?complex="+self.complexForList+"&unit="+self.unitForList;
		$http.get(url)
				.success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
	};
	
	self.inviteUser = function(){
		alert("u done messed up");
		let url = "/resident/complexInvite";
		$http.post(url, {complex:self.complexForInvite, email:self.emailForInvite})
				.success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
		
		self.inviteUserUnit = function(){
			alert("u done messed up");
			let url = "/resident/unitInvite";
			$http.post(url, {complex:self.complexForInviteUnit, email:self.emailForInviteUnit, unit:self.unitForInvite})
					.success(function(response){
						console.log(response);
					}).error(function(response){
						console.log(response);
					});
	};
	
	
	
});