
var ownerHomeModule = angular.module("ownerHomeModule");

ownerHomeModule.controller('ownerHomeController', function($scope,$rootScope,$location,ownerHomeService,utilService) {
	$scope.restaurants=null;
	$rootScope.messageError=false;
	
	$scope.sortType     = 'id'; 
	$scope.sortReverse  = false; 
	
	utilService.checkUser('OWNER');
	
	$scope.init = function () {
		ownerHomeService.listAllRestaurants(
					function(data,headers) {
						$scope.restaurants = data;
					}
					,function(data,headers) {
						utilService.showErrorMessage(data);
					}
		    );
	};

	$scope.viewReviews=function(data){
		$location.path('/owner/reviews/'+data.id);
	};
	
	$scope.updateRestaurant=function(data){
		$location.path('/owner/editRestaurant/'+data.id);
	};
	
	$scope.createRestaurant=function(){
		$location.path('/owner/addRestaurant');
	};
	
});


ownerHomeModule.service('ownerHomeService', function($http,APP_CONSTANT, $rootScope) {
	var ownerHomeService = {};
	
	ownerHomeService.listAllRestaurants =  function(callback, callbackError) {
				$http.get(APP_CONSTANT.REMOTE_HOST + '/api/users/'+$rootScope.userSession.id+'/restaurants')
				.success(function(data, status, headers, config) {
					callback(data, headers);
				}).error(function(data, status, headers, config) {
					callbackError(data, headers);
				});
			}
	

	return ownerHomeService;
})
