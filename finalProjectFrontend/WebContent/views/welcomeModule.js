var welcomeModule = angular.module("welcomeModule");
welcomeModule.controller('welcomeController', function($scope,$rootScope,$location,utilService,welcomeService) {
	$rootScope.messageError=false;
	$scope.search=function(value){
		if(value==undefined || value.length==0){
			utilService.showErrorMessage({message:"Add a value"});
		}else{
			welcomeService.searchRestaurant(value
					,function(data, headers){
						$scope.restaurants=data;
					}	
					,function(data,headers){
						console.log(data);
						utilService.showErrorMessage(data);
					});
		}
	};
	
	$scope.init=function(){
		welcomeService.getLastInsertedRestaurants(
				function(data, headers){
					$scope.restaurants=data;
				}	
				,function(data,headers){
					utilService.showErrorMessage(data);
				});

	};
	
	$scope.addReview=function(data){
		$location.path('/addReview/'+data.id);
	};
	
});

welcomeModule.factory('welcomeService', function( $http, APP_CONSTANT) {
	var welcomeService = {};

	welcomeService.searchRestaurant = function(queryValue, onSuccess, onError) {
			$http.get(APP_CONSTANT.REMOTE_HOST + '/guest/restaurants/'+queryValue)
			.success(function(data, status, headers, config) {
				onSuccess(data, headers);
			}).error(function(data, status, headers, config) {
				onError(data, headers);
			});
	};
	welcomeService.getLastInsertedRestaurants = function(onSuccess, onError) {
		$http.get(APP_CONSTANT.REMOTE_HOST + '/guest/restaurants/last')
		.success(function(data, status, headers, config) {
			onSuccess(data, headers);
		}).error(function(data, status, headers, config) {
			onError(data, headers);
		});
};
	return welcomeService;
});