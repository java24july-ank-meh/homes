angular.module('rhmsApp').controller('editApartmentController', ['$scope', '$http', '$mdDialog','$state', '$stateParams','$mdToast', function($scope, $http, $mdDialog, $state, $stateParams, $mdToast) {

    $http.get("/api/complex/unit/"+$stateParams.apartmentId)
    .success(function(data) {
        $scope.unit = data;

    })
    .error(function(){
    	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
    });

	
    $scope.editApartmentFormSubmit = function () {

        var onSuccess = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("Unit Updated").position('top right'));
            $state.reload();
            $scope.hide();
            
        };

        var onError = function (data, status, headers, config) {
        	$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
        }
        
        $http.put('/api/complex/unit/'+$stateParams.apartmentId, $scope.unit)
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
