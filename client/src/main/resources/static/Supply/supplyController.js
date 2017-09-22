angular.module('rhmsApp').controller('supplyController', ['$scope', '$mdBottomSheet','$mdSidenav', '$mdDialog', '$http','$mdToast',  function($scope, $mdBottomSheet, $mdSidenav, $mdDialog, $http, $mdToast) {

    $scope.deleteRowCallback = function(rows){
        $mdToast.show(
            $mdToast.simple()
                .content('Deleted row id(s): '+rows)
                .hideDelay(3000)
        );
    };
    $scope.nutritionList = [
    	  {
    		    "suppliesId": 41,
    		    "name": "Toilet Paper",
    		    "submitDate": "2017-09-18",
    		    "resolved": true,
    		    "resolveDate": null,
    		    "unitId": 10,
    		    "submittedBy": 5
    		  },
    		  {
    		    "suppliesId": 42,
    		    "name": "Trash Bags",
    		    "submitDate": "2017-09-18",
    		    "resolved": false,
    		    "resolveDate": null,
    		    "unitId": 11,
    		    "submittedBy": 6
    		  },
    ];
	
	$scope.error = false;
	
     $http.get("/api/Maintenance").then(function(response) {
         $scope.maintenanceRequests = response.data;
         
         if($scope.maintenanceRequests === '')
        	 $scope.error = true;
     });
     
 	 $scope.resolveMaintenance = function (maintenance) {

	      var onSuccess = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent("Maintenance Completed").position('top right'));
	          $scope.hide();
	         $state.go('home.showMaintenance');
	      };

	      var onError = function (data, status, headers, config) {
	    	  $mdToast.show($mdToast.simple().textContent(data));
	      };

	      $http.post('/api/Maintenance/'+maintenance.maintenanceId+'/complete')
	      	.success(onSuccess)
	      	.error(onSuccess);

	  };

}]);