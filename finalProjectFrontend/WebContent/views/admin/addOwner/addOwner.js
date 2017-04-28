
var addOwnerModule = angular.module("addOwnerModule", []);
addOwnerModule.controller('addOwnerController', function($location, $routeParams,$rootScope, addOwnerService, utilService) {
	$rootScope.messageError=false;
	var addOwnerCtrl = this;

	utilService.checkUser('ADMIN');
	
	addOwnerCtrl.owner = {
			name: "",
			surname : "",
			email : "",
			password : "",
			phone:""
		};

	
	addOwnerCtrl.cancel = function() {
		$location.path('/admin');
	}

	addOwnerCtrl.addOwner = function() {
		var data={
				owner:addOwnerCtrl.owner,
				restaurantId: $routeParams.id
		};
		
		addOwnerService.addOwner(data,
			function(data, headers) { 
					utilService.showModal('User added');
					$location.path('/admin');
			}, 
			function(data, headers) {
					console.log(data);
					utilService.showErrorMessage(data);
			});
	}
});

addOwnerModule.factory('addOwnerService', function($http,APP_CONSTANT) {
	var addOwnerModule = {};
	
	addOwnerModule.addOwner = function(data, onSuccess, onError) {
			$http.put(APP_CONSTANT.REMOTE_HOST + '/api/restaurants/'+data.restaurantId, data.owner)
			.success(function(data, status, headers, config) {
				onSuccess(data, headers);
			}).error(function(data, status, headers, config) {
				onError(data, headers);
			});
	};
	return addOwnerModule;
});