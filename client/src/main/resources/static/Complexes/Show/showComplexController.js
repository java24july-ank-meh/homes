angular.module('rhmsApp').controller('showComplexController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog','$http', '$stateParams', '$state','$mdToast', "$rootScope", function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $stateParams, $state, $mdToast, $rootScope) {
	$scope.error = false;
	$scope.announcement  = '';
	$scope.isManager = $rootScope.rootUser.isManager;
	
	  $scope.showConfirm = function(deleteComplex) {

		    var confirm = $mdDialog.confirm()
		          .title('Do you really want to delete the Apartment Complex?')
		          .targetEvent(event)
		          .ok('Delete')
		          .cancel('Cancel');

		    $mdDialog.show(confirm).then(function() {
		      $scope.deleteComplex();
		    });
		  };

    $scope.deleteComplex = function () {

        var onSuccess = function (data, status, headers, config) {
        	
            $http.get('/api/slack/complex/channelName/' + $scope.complex.name)
    		  .success(function(data){
    			 $scope.channelName = data; 
    		  
    	        	$http.post('/api/slack/complex/delete', {channelName: $scope.channelName, token:$rootScope.rootUser.token});
    			 
    		  });
        	$mdToast.show($mdToast.simple().textContent("Complex Deleted").position('top right'));
            $state.go('home.complexes');
        };

        var onError = function (data, status, headers, config) {
        	 $mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        };
        

        $http.delete('/api/complex/complex/'+$stateParams.complexId)
        	.success(onSuccess)
        	.error(onSuccess);

    };
    
     $http.get("/api/complex/complex/"+$stateParams.complexId).then(function(response) {

         $scope.complex = response.data;
       
         $http.get("/api/complex/complex/"+$stateParams.complexId+"/units").then(function(response) {
        	 
        	 $scope.complex.units = response.data;
        	 
        	 $http.get("/api/associates/associates").then(function(response) {
        		 $scope.associates = response.data;
        		 
        		 for(var i = 0; i < $scope.complex.units.length; i++){
            		 $scope.complex.units[i].associates = [];
            		 
            		 for(var j = 0; j < $scope.associates.length; j++){
            			 if($scope.complex.units[i].unitId == $scope.associates[j].unitId){
            				 $scope.complex.units[i].associates.push($scope.associates[j])
            			 }
            		 }
            	 }
        	 });
         });
     }, function(){
    	 $scope.error="true";
     });
     
	  $scope.showCreateApartmentForm = function(ev) {
		  
		  $mdDialog.show({
			  controller: 'createApartmentController',
			  templateUrl: '/../../Apartments/Create/create.html',
			  parent: angular.element(document.body),
			  targetEvent: ev,
			  clickOutsideToClose:true,
			  fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
		  });
			  
	  };
	  
	  $scope.sendAnnouncementFormSubmit = function(event){
		  
		  var onSuccess = function (data, status, headers, config) {
	            
	        	 $mdToast.show($mdToast.simple().textContent("Message sent!").position('top right'));
	            
	        };

	        var onError = function (data, status, headers, config) {
	        	 $mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
	        }

	        $http.post('/api/slack/resident/message',{complex:$scope.complex.name, message:$scope.announcement, token:$rootScope.rootUser.token} )
	            .success(onSuccess)
	            .error(onError);

	  };

}]);