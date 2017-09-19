angular.module('rhmsApp').controller('createApartmentController', ['$scope', '$http', '$mdDialog','$state', '$stateParams', '$mdToast', function($scope, $http, $mdDialog, $state, $stateParams, $mdToast) {


    $scope.newApartmentFormSubmit = function () {

        var onSuccess = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Apartment Created").position('top right'));
            $state.go('home.showApartment', { apartmentId: data});
            $scope.hide();
            
        };

        var onError = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }
        
        $scope.apartment.complexId = $stateParams.complexId;

        $http.post('/api/ApartmentComplexes/'+$stateParams.complexId+'/Apartments/create', $scope.apartment)
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
