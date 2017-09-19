angular.module('rhmsApp').controller('editApartmentController', ['$scope', '$http', '$mdDialog','$state', '$stateParams','$mdToast', function($scope, $http, $mdDialog, $state, $stateParams, $mdToast) {

    $http.get("/api/Apartments/"+$stateParams.apartmentId)
    .success(function(data) {
        $scope.apartment = data;

    })
    .error(function(){
    	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
    });

	
    $scope.editApartmentFormSubmit = function () {

        var onSuccess = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Apartment Updated").position('top right'));
            $state.reload();
            $scope.hide();
            
        };

        var onError = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }
        
        $scope.apartment.complex = null;
        
        $http.put('/api/Apartments/'+$stateParams.apartmentId, $scope.apartment)
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
        $scope.apartment = "";
    };
}]);
