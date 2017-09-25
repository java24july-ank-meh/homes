var app = angular.module('rhmsApp', ['ngMaterial', 'ngMdIcons', 'ui.router', 'ngTable', 'mdDataTable']);

app.run(function($rootScope) {
    $rootScope.rootTest = 'test';
});

app.config(function($stateProvider, $urlRouterProvider, $mdThemingProvider) {
    $urlRouterProvider.otherwise('/home/dashboard');

    $stateProvider
		.state('login', {
			url: '/login',
			templateUrl: 'Login/login.html',
			controller: 'loginController'
		})
		.state('logout', {
			url: '/logout',
			templateUrl: 'Login/logout.html',
			controller: 'logoutController'
		})
		.state('oauth', {
			url: '/login/oauth',
			templateUrl: 'Login/oauth.html',
			controller: 'oauthController'
		})
        .state('home', {
            url: '/home',
            templateUrl: 'SideNav/sidenav.html',
            controller: 'sidenavController'
        })
        .state('home.dashboard',{
            url:'/dashboard',
            templateUrl: 'Dashboard/dashboard.html',
            controller: 'dashboardController'
        })
        .state('home.complexes', {
            url: '/complexes',
            templateUrl: 'Complexes/complexes.html',
            controller: 'complexesController'
        })
        .state('home.associates',{
            url:'/associates',
            templateUrl: 'Associates/associates.html',
            controller: 'associatesController'
        })
        .state('home.showAssociate',{
            url:'/associates/:associateId',
            templateUrl: 'Associates/Show/ShowAssociate.html',
            controller: 'showAssociateController'
        })
        .state('home.editAssociate',{
        	url:'/associates/edit/:associateId',
        	templateUrl:'Associates/Edit/edit.html',
        	controller: 'editAssociateController'
        })
        .state('home.importAssociate',{
        	url:'/associates/import',
        	templateUrl:'Associates/Import/importAssociate.html',
        	controller: 'importAssociateController'
        })
        .state('home.createComplex', {
            url: '/complexes/create',
            templateUrl: 'Complexes/Create/create.html',
            controller: 'createComplexController'
        })
        .state('home.editComplex',{
            url:'/complexes/edit/:complexId',
            templateUrl: 'Complexes/Edit/edit.html',
            controller: 'editComplexController',
            params: {
                complexId: {
                    value: null
                }
            }
        })
        .state('home.showComplex',{
            url:'/complexes/:complexId',
            templateUrl: 'Complexes/Show/show.html',
            controller: 'showComplexController',
            params: {
                complexId: {
                    value: null
                }
            }
        })
        .state('home.showApartment',{
            url:'/apartments/:apartmentId',
            templateUrl: 'Apartments/Show/show.html',
            controller: 'showApartmentController',
            params: {
                complexId: {
                    value: null
                }
            }
        })
        .state('home.resources',{
            url:'/resources',
            templateUrl: 'Resources/resources.html'
        })
       .state('home.createMaintenance', {
            url: '/maintenance/create',
            templateUrl: 'Maintenance/Create/create.html',
            controller: 'createMaintenanceController'
        })
        .state('home.maintenance', {
            url: '/maintenance/',
            templateUrl: 'Maintenance/maintenance.html',
            controller: 'maintenanceController'
        })
    	.state('home.createSupply', {
	        url: '/supply/create',
	        templateUrl: 'Supply/Create/create.html',
	        controller: 'createSupplyController'
    	})
    	.state('home.supply', {
            url: '/supply/',
            templateUrl: 'Supply/supply.html',
            controller: 'supplyController'
        });

    var customBlueMap =$mdThemingProvider.extendPalette('deep-orange', {
        'contrastDefaultColor': 'light',
        'contrastDarkColors': ['50'],
        '50': 'ffffff'
    });
    $mdThemingProvider.definePalette('customBlue', customBlueMap);
    $mdThemingProvider.theme('default')
        .primaryPalette('customBlue', {
            'default': '500',
            'hue-1': '50'
        })
        .accentPalette('pink');
    $mdThemingProvider.theme('input', 'default')
        .primaryPalette('grey')
});
