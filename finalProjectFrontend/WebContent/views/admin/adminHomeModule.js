
var adminHomeModule = angular.module("adminHomeModule");

adminHomeModule.controller('adminHomeController', function($scope,$rootScope,$location,adminHomeService,utilService) {
	$rootScope.messageError=false;
	
	$scope.sortType     = 'id'; 
	$scope.sortReverse  = false; 
	
	utilService.checkUser('ADMIN');
	
	$scope.init = function () {
			adminHomeService.listAllRestaurants(
					function(data,headers) {
						$scope.restaurants = data;
					}
					,function(data,headers) {
						utilService.showErrorMessage(data);
					}
		    );
	};
	
	$scope.createRestaurant = function () {
		$location.path('/admin/addRestaurant');
	};
	
	$scope.enabledRestaurant=function(data){
		adminHomeService.enableRestaurant(data.id,
				function(data,headers) {
					$scope.init();
				}
				,function(data,headers) {
					utilService.showErrorMessage(data);
				}
	    );
	};
	
	$scope.disableRestaurant=function(data){
		adminHomeService.disableRestaurant(data.id,
				function(data,headers) {
					$scope.init();
				}
				,function(data,headers) {
					utilService.showErrorMessage(data);
				}
	    );
	};
	
	$scope.viewUser=function(data){
		$location.path('/admin/users/'+data.user_id);
	};
	
	$scope.viewUsers=function(){
		$location.path('/admin/users/');
	};
	
	$scope.addOwner=function(data){
		$location.path('/admin/addOwner/'+data.id);
	};
	
	$scope.viewReviews=function(data){
		$location.path('/admin/reviews/'+data.id);
	};
});


adminHomeModule.service('adminHomeService', function($http,$timeout,APP_CONSTANT) {
	var adminHomeService = {};
	
	adminHomeService.listAllRestaurants =  function(callback, callbackError) {
				$http.get(APP_CONSTANT.REMOTE_HOST + '/api/restaurants/all')
				.success(function(data, status, headers, config) {
					callback(data, headers);
				}).error(function(data, status, headers, config) {
					callbackError(data, headers);
				});
			}
	
	adminHomeService.enableRestaurant =  function(id,callback, callbackError) {
		$http.post(APP_CONSTANT.REMOTE_HOST + '/api/restaurants/'+id+"/enable")
		.success(function(data, status, headers, config) {
			callback(data, headers);
		}).error(function(data, status, headers, config) {
			callbackError(data, headers);
		});
	}
	
	adminHomeService.disableRestaurant =  function(id,callback, callbackError) {
		$http.post(APP_CONSTANT.REMOTE_HOST + '/api/restaurants/'+id+"/disable")
		.success(function(data, status, headers, config) {
			callback(data, headers);
		}).error(function(data, status, headers, config) {
			callbackError(data, headers);
		});
	}

	return adminHomeService;
})
