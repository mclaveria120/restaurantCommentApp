var ownerRestaurantModule = angular.module("ownerRestaurantModule", []);

ownerRestaurantModule.controller('ownerRestaurantController', function($location, $scope,$rootScope,$routeParams, ownerRestaurantService, utilService) {
	$rootScope.messageError=false;
	$scope.restaurant = {
		name: "",
		address : "",
		description : "",
		maxCapacity : "",
	};

	utilService.checkUser('OWNER');
	
	$scope.cancel = function() {
		$location.path('/owner');
	};

	$scope.addRestaurant = function() {
		ownerRestaurantService.addRestaurant($scope.restaurant,
			function(data, headers) { 
					utilService.showModal('Restaurant added');
					$location.path('/owner');
			}, 
			function(data, headers) {
					utilService.showErrorMessage(data);
			});
	};
	
	$scope.init=function(){
		ownerRestaurantService.getRestaurant($routeParams.restaurantId,
				function(data,headers) {
					$scope.restaurant = data;
				}
				,function(data,headers) {
					utilService.showErrorMessage(data);
				}
	    );
	};
	
	$scope.updateRestaurant = function() {
		ownerRestaurantService.updateRestaurant($scope.restaurant,
			function(data, headers) { 
					utilService.showModal('Restaurant updated');
					$location.path('/owner');
			}, 
			function(data, headers) {
					utilService.showErrorMessage(data);
			});
	};
	
});

ownerRestaurantModule.factory('ownerRestaurantService', function($http,APP_CONSTANT,$rootScope) {
	var ownerRestaurantService = {};

	ownerRestaurantService.addRestaurant = function(data, onSuccess, onError) {
			$http.post(APP_CONSTANT.REMOTE_HOST + '/api/users/'+$rootScope.userSession.id+'/restaurants/', data)
			.success(function(data, status, headers, config) {
				onSuccess(data, headers);
			}).error(function(data, status, headers, config) {
				onError(data, headers);
			});
	}
	
	ownerRestaurantService.updateRestaurant = function(data, onSuccess, onError) {
		$http.put(APP_CONSTANT.REMOTE_HOST + '/api/users/'+$rootScope.userSession.id+'/restaurants/', data)
		.success(function(data, status, headers, config) {
			onSuccess(data, headers);
		}).error(function(data, status, headers, config) {
			onError(data, headers);
		});
	}
	
	ownerRestaurantService.getRestaurant =  function(restaurantId,callback, callbackError) {
		$http.get(APP_CONSTANT.REMOTE_HOST + '/api/users/'+$rootScope.userSession.id+'/restaurants/'+restaurantId)
		.success(function(data, status, headers, config) {
			callback(data, headers);
		}).error(function(data, status, headers, config) {
			callbackError(data, headers);
		});
	}
	
	return ownerRestaurantService;
});

