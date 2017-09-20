angular.module('rhmsApp').controller('editAssociateController', ['$scope', '$http', '$mdDialog', '$state', '$stateParams', '$mdToast', function($scope, $http, $mdDialog, $state, $stateParams, $mdToast){
	
	$http.get("api/associates/associates/"+$stateParams.associateId)
	.success(function(data){
		$scope.associate = data;
	})
	.error(function(){
		$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
	});
	
	$scope.editResidentFormSubmit = function(){
		
		var onSuccess = function(data, status, headers, config) {
			$mdToast.show($mdToast.simple().textContent("Associate Updated").position('top right'));
			$state.reload();
			$scope.hide();
		};
		
		var onError = function(data, status, headers, config) {
			$mdToast.show($mdToast.simple().textContent("An Error Occured").position('top right'));
		};

		$http.post('/api/associates/associates/createOrUpdate/', $scope.associate)
			.success(onSuccess)
			.error(onError);
		
	};
	
	$scope.hide = function() {
		$mdDialog.hide();
	};
	
	$scope.cancel = function(){
		$mdDialog.cancel();
	};
	
	$scope.resetForm = function() {
		$scope.associate = "";
	};
}]);
