
var addRestaurantAdminModule = angular.module("addRestaurantAdminModule", []);
addRestaurantAdminModule.controller('addRestaurantAdminController', function($rootScope,$location, $scope, $uibModal, addRestaurantService, utilService) {
	$rootScope.messageError=false;
	var addRestaurantAdmintCtrl = this;

	utilService.checkUser('ADMIN');
	
	addRestaurantAdmintCtrl.restaurant = {
		name: "",
		address : "",
		description : "",
		maxCapacity : "",
	};

	addRestaurantAdmintCtrl.error = false;
	addRestaurantAdmintCtrl.message = "";
	
	addRestaurantAdmintCtrl.cancel = function() {
		$location.path('/admin');
	}

	addRestaurantAdmintCtrl.addRestaurant = function() {
		addRestaurantService.addRestaurant(addRestaurantAdmintCtrl.restaurant,
			function(data, headers) { 
					utilService.showModal('Restaurant added');
					$location.path('/admin');
			}, 
			function(data, headers) {
					addRestaurantAdmintCtrl.message = data.message;
					addRestaurantAdmintCtrl.error = true;
			});
	}
});

addRestaurantAdminModule.factory('addRestaurantService', function($http,APP_CONSTANT) {
	var addRestaurantService = {};

	addRestaurantService.addRestaurant = function(data, onSuccess, onError) {
			$http.post(APP_CONSTANT.REMOTE_HOST + '/api/restaurants/', data)
			.success(function(data, status, headers, config) {
				onSuccess(data, headers);
			}).error(function(data, status, headers, config) {
				onError(data, headers);
			});
	};
	return addRestaurantService;
});