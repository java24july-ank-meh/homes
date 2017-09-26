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
  

    if($rootScope.rootUser == undefined)
    	$state.go("logout");
//    $rootScope.rootUser={"name":"Huriel Hernandez","id":"U6TCFPESD","email":"huriel.hdz@gmail.com","image_24":"https://avatars.slack-edge.com/2017-08-30/235332720711_a5d536632ed16b8858d4_24.jpg","image_32":"https://avatars.slack-edge.com/2017-08-30/235332720711_a5d536632ed16b8858d4_32.jpg","image_48":"https://avatars.slack-edge.com/2017-08-30/235332720711_a5d536632ed16b8858d4_48.jpg","image_72":"https://avatars.slack-edge.com/2017-08-30/235332720711_a5d536632ed16b8858d4_72.jpg","image_192":"https://avatars.slack-edge.com/2017-08-30/235332720711_a5d536632ed16b8858d4_192.jpg","image_512":"https://avatars.slack-edge.com/2017-08-30/235332720711_a5d536632ed16b8858d4_512.jpg","image_1024":"https://avatars.slack-edge.com/2017-08-30/235332720711_a5d536632ed16b8858d4_1024.jpg","isManager":false};
//    $rootScope.rootAssociate = {"associateId":1,"firstName":"Huriel","lastName":"Hernandez","phone":"(956)313-3128","about":null,"email":"huriel.hdz@gmail.com","slackId":"U6TCFPESD","unitId":104, "moveInDate": null};
//    $rootScope.rootUser={"name":"Admin Admin","id":"U6WKV27MF","email":"admin@golemico.com","image_24":"https://secure.gravatar.com/avatar/1b80f93f766652f955f4fa210a6ebae3.jpg?s=24&d=https%3A%2F%2Fcfr.slack-edge.com%2F0180%2Fimg%2Favatars%2Fava_0021-24.png","image_32":"https://secure.gravatar.com/avatar/1b80f93f766652f955f4fa210a6ebae3.jpg?s=32&d=https%3A%2F%2Fcfr.slack-edge.com%2F66f9%2Fimg%2Favatars%2Fava_0021-32.png","image_48":"https://secure.gravatar.com/avatar/1b80f93f766652f955f4fa210a6ebae3.jpg?s=48&d=https%3A%2F%2Fcfr.slack-edge.com%2F3654%2Fimg%2Favatars%2Fava_0021-48.png","image_72":"https://secure.gravatar.com/avatar/1b80f93f766652f955f4fa210a6ebae3.jpg?s=72&d=https%3A%2F%2Fcfr.slack-edge.com%2F66f9%2Fimg%2Favatars%2Fava_0021-72.png","image_192":"https://secure.gravatar.com/avatar/1b80f93f766652f955f4fa210a6ebae3.jpg?s=192&d=https%3A%2F%2Fcfr.slack-edge.com%2F7fa9%2Fimg%2Favatars%2Fava_0021-192.png","image_512":"https://secure.gravatar.com/avatar/1b80f93f766652f955f4fa210a6ebae3.jpg?s=512&d=https%3A%2F%2Fcfr.slack-edge.com%2F7fa9%2Fimg%2Favatars%2Fava_0021-512.png","isManager":true};

    
    $http.post("/api/slack/resident/admin",{email : $rootScope.rootUser.email}).then(function(response){
    	$rootScope.rootUser.isManager = response.data;
    	$scope.isManager = $rootScope.rootUser.isManager ? "Manager" : "Resident";
    	
    });
    
    if($rootScope.rootAssociate!= null && $rootScope.rootAsscociate.unitId != null)
    	$scope.residentMenu = $scope.assignedMenu;
    

    $scope.logout = function() {
    	console.log("hi");
    	delete $rootScope.rootUser;
    	$state.go("login");
    }
}]);
