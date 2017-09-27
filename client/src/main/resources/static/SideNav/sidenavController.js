angular.module('rhmsApp').controller('sidenavController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http', '$rootScope', '$state', '$location', 'localStorageService', function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $rootScope, $state, $location, localStorageService){

	$rootScope.rootUser = localStorageService.get("rootUser");
	$rootScope.rootAssociate = localStorageService.get("rootAssociate");
	if($rootScope.rootUser)
		$scope.isManager = $rootScope.rootUser.isManager ? "Manager" : "Resident";
	
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
            link : '.associates',
            title: 'Associates',
            icon: 'group'
        },
        {
            link : 'home.maintenance()',
            title: 'Maintenance',
            icon: 'build'
        },
        {
            link : 'home.supply()',
            title: 'Supplies',
            icon: 'shopping_cart'
        },
    ];
    $scope.residentMenu = [
		{
            link : '.dashboard',
            title: 'Dashboard',
            icon: 'dashboard'
        },
        {
            link : 'home.resources()',
            title: 'Resources',
            icon: 'bookmark'
        }
    ];
    
    $scope.assignedMenu = [
    	{
            link : '.dashboard',
            title: 'Dashboard',
            icon: 'dashboard'
        },
    	 {
             link : '.showApartment({apartmentId: rootAssociate.unitId})',
             title: 'Apartment',
             icon: 'home'
         },
         {
             link : '.showAssociate({associateId: rootAssociate.associateId})',
             title: 'Profile',
             icon: 'account_box'
         },
         {
             link : 'home.createMaintenance()',
             title: 'Maintenance',
             icon: 'build'
         },
         {
             link : 'home.createSupply()',
             title: 'Supplies',
             icon: 'shopping_cart'
         },
         {
             link : 'home.resources()',
             title: 'Resources',
             icon: 'bookmark'
         }
    ];

    $scope.commonMenu = [{
            link : 'logout',
            title: 'Logout',
            icon: 'power_settings_new'
        } ];

   if(!$rootScope.rootUser)
    	$state.go("logout");
   else
    $http.post("/api/slack/resident/admin",{email : $rootScope.rootUser.email}).then(function(response){
    	$rootScope.rootUser.isManager = response.data;
    	$scope.isManager = $rootScope.rootUser.isManager ? "Manager" : "Resident";
    	localServiceStorage.set("rootUser", $rootScope.rootUser);
    	
        if(!$rootScope.rootUser.isManager)
        	$http.get("/api/associates/associates/"+$rootScope.rootUser.email+"/email").then(function(response){
        		$rootScope.rootAssociate = response.data;
        		localServiceStorage.set("rootAssociate", rootAssociate);
        	    if($rootScope.rootAssociate!= null && $rootScope.rootAsscociate.unitId != null)
        	    	$scope.residentMenu = $scope.assignedMenu;
        });
    });
   
   
   
    $scope.logout = function() {
    	$state.go("logout");
    }
}]);
