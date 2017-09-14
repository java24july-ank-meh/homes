angular.module('rhmsApp').controller('sidenavController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', '$rootScope', '$state', '$location', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $rootScope, $state, $location){
   
	

	$scope.toggleSidenav = function(menuId) {
        $mdSidenav(menuId).toggle();
    };
    $scope.menu = [
        {
            link : '.dashboard',
            title: 'Dashboard',
            icon: 'dashboard'
        },
        {
            link : '.complexes',
            title: 'Complexes',
            icon: 'business'
        },
        {
            link : '.residents',
            title: 'Residents',
            icon: 'group'
        },
        {
            link : 'home.maintenance()',
            title: 'Maintenance',
            icon: 'build'
        },
    ];
    $scope.residentMenu = [
		{
            link : '.dashboard',
            title: 'Dashboard',
            icon: 'dashboard'
        },
        {
            link : '.showApartment({apartmentId: rootResident.apartment})',
            title: 'Apartment',
            icon: 'home'
        },
        {
            link : '.showResident({residentId: rootResident.residentId})',
            title: 'Profile',
            icon: 'account_box'
        },
        {
            link : 'home.createMaintenance()',
            title: 'Maintenance',
            icon: 'build'
        },
        {
            link : 'home.resources()',
            title: 'Resources',
            icon: 'bookmark'
        }
    ];
    $scope.admin = [
        {
            link : '',
            title: 'Trash',
            icon: 'delete'
        },
        {
            link : '',
            title: 'Settings',
            icon: 'settings'
        }
    ];
    $scope.commonMenu = [{
            link : 'logout',
            title: 'Logout',
            icon: 'power_settings_new'
        } ];
  
    
    //if($rootScope.rootUser == undefined){
    	
	    $http.get("/api/sidenav").then(function(response) {
	    	
	        $rootScope.rootUser = response.data;
	        console.log($rootScope.rootUser);
	        console.log(!$rootScope.rootUser);
	        if(!$rootScope.rootUser.id){ // check if empty unknown user
		    	$state.go("logout");
		    }
	
	        $scope.isManager = $rootScope.rootUser.isManager ? "Manager" : "Resident";
	        if(!$rootScope.rootUser.isManager) {
	        	$http.get("/api/Residents/email/"+$rootScope.rootUser.email).then(function(response) {
	                $rootScope.rootResident = response.data;
	                if(!$rootScope.rootResident.apartment) {
	                	//$scope.residentMenu[1].link = '';
	                	//$scope.residentMenu[1].title = 'Apartment - unassigned';
	                }
	            });
	        }
	        
	    });
	    
//	    

    //}
    
    $scope.logout = function() {
    	console.log("hi");
    	delete $rootScope.rootUser;
    	$state.go("login");
    }
}]);
