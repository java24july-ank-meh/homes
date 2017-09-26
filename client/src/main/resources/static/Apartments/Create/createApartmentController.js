angular.module('rhmsApp').controller('createApartmentController', ['$scope', '$http', '$mdDialog','$state', '$stateParams', '$mdToast', function($scope, $http, $mdDialog, $state, $stateParams, $mdToast) {

	$scope.unit = {};
	
    $scope.newApartmentFormSubmit = function () {
    

        var onSuccess = function (data, status, headers, config) {
        	let unitAbrev = $scope.unit.buildingNumber + "-" + $scope.unit.unitNumber;
        	$http.post('/api/slack/complex/create', {complex: $scope.unit.complex.complexName, unit:unitAbrev});  
        	$mdToast.show($mdToast.simple().textContent("Apartment Created").position('top right'));
            $state.go('home.showApartment', { apartmentId: data});
            $scope.hide();
            
        };

        var onError = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }
        $scope.unit.complex = {};
        $scope.unit.complex.complexId = $stateParams.complexId;
        $http.get("/api/complex/complex/"+$stateParams.complexId).then(function(){
        	$scope.unit.complex.complexName = response.data.name;	
        });
        
        $http.post('/api/complex/unit', $scope.unit)
            .success(onSuccess)
            .error(onError);

    };
    
    $scope.hide = function() {
        $mdDialog.hide();
      };

      $scope.cancel = function() {
        $mdDialog.cancel();
      };


    //6. create resetForm() function. This will be called on Reset button click.
    $scope.resetForm = function () {
        $scope.unit = "";
    };
}]);
