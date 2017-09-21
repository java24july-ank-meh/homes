/**
 * 
 */

angular.module("app", []).controller("home", function($http,$window){
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
			
			 self.onload = function() {
				 $http.get("/manager/scopes/basic").then(function(response){
			        	if(response.data.scope === "basic"){
			        		$http.post('/logout', {}).then(function(){
								self.authenticated = false;
				        		alert("basic call");
					        	$window.location.href = "/channelwrite/slack";
			        		}).error(function(data){
								console.log("Logout failed");
								self.authenticated = false;
							});
			        	
			        	}else if(response.data.scope === "client"){
			        		$http.post('/logout', {}).then(function(){
								self.authenticated = false;
								alert("client call");
								$window.location.href = "/client/slack";
							}).error(function(data){
								console.log("Logout failed");
								self.authenticated = false;
							});
					      
			        	}  else {
			        		console.log("you in bro!");
			        	}
			        })
			 }
			
			self.logout = function(){
				$http.post('/logout', {}).success(function(){
					self.authenticated = false;
				}).error(function(data){
					console.log("Logout failed");
					self.authenticated = false;
				});
			};
			
			self.showInfo = function(){
				$http.get("/user").success(function(data){
					console.log(data);
				}).error(function(data){
					console.log(data);
				});
			}
			
			self.createComplex = function(){
				console.log("user typed: " + self.complexName);
				$http.post('/complex/create', {name:self.complexName}).success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
			};
			
			self.deleteComplex = function(){
				console.log("user typed: " + self.toDelete);
				$http.post('/complex/delete', {name:self.toDelete}).success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
			};
			
			self.updateComplex = function(){
				console.log("user typed: " + self.newComplex);
				$http.post('/complex/update', {oldName:self.oldComplex, newName:self.newComplex}).success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
			};
			
			self.createUnit = function(){
				$http.post('/unit/create', {unit:self.unitName, complex:self.addComplexCreate}).success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
			};
			
			self.deleteUnit = function(){
				$http.post('/unit/delete', {unit:self.unitToDelete, complex:self.addComplexDelete}).success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
			};
			
			self.updateUnit = function(){
				$http.post('/unit/update', {unit:self.oldUnit, complex:self.oldComplexUpdate, newUnit:self.newUnit, newComplex:self.newComplexUpdate}).success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
				});
			};
			
			self.checkAdmin = function(){
				$http.post('/resident/admin', {isAdmin:self.isAdmin}).success(function(response){
					console.log(response);
				}).error(function(response){
					console.log(response);
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