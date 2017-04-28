
var customerHomeModule = angular.module("customerHomeModule");

customerHomeModule.controller('customerHomeController', function($scope,$rootScope,$location,customerHomeService,utilService) {
	
	utilService.checkUser('CUSTOMER');
	
	$scope.init = function () {
		customerHomeService.listAllRestaurants(
					function(data,headers) {
						$scope.restaurants = data;
					}
					,function(data,headers) {
						utilService.showErrorMessage(data);
					}
		    );
	};
	
	$scope.addReview=function(restaurant){
		$location.path('/customer/addReview/'+restaurant.id);
	};
	
	$scope.search=function(value){
		if(value==undefined){
			utilService.showErrorMessage({message:"Add a value"});
		}else{
			customerHomeService.searchRestaurant(value
					,function(data, headers){
						$scope.restaurants=data;
					}	
					,function(data,headers){
						utilService.showErrorMessage(data);
					});
		}
	};
	
	$scope.getTimes=function(n){
	     return new Array(n);
	   };
});


customerHomeModule.service('customerHomeService', function($http,APP_CONSTANT,$rootScope) {
	var customerHomeService = {};
	
	customerHomeService.listAllRestaurants =  function(callback, callbackError) {
				$http.get(APP_CONSTANT.REMOTE_HOST + '/api/restaurants')
				.success(function(data, status, headers, config) {
					callback(data, headers);
				}).error(function(data, status, headers, config) {
					callbackError(data, headers);
				});
			};
			
	customerHomeService.searchRestaurant =  function(query,callback, callbackError) {
		$http.get(APP_CONSTANT.REMOTE_HOST + '/api/restaurants/'+query)
		.success(function(data, status, headers, config) {
			callback(data, headers);
		}).error(function(data, status, headers, config) {
			callbackError(data, headers);
		});
	};
	
	return customerHomeService;
})
