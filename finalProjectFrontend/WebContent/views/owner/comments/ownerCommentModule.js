
var ownerCommentModule = angular.module("ownerCommentModule");

ownerCommentModule.controller('ownerCommentController', function($scope,$rootScope,$location,ownerCommentService,utilService,$routeParams) {
	$rootScope.messageError=false;
	$scope.restaurant=null;
	
	utilService.checkUser('OWNER');
	
	$scope.init = function () {
		ownerCommentService.getRestaurant($routeParams.restaurantId,
					function(data,headers) {
						$scope.restaurant = data;
					}
					,function(data,headers) {
						utilService.showErrorMessage(data);
					}
		    );
	};

	$scope.addComment=function(data){
		$location.path('/owner/addComment/'+data.id);
	};
	
	$scope.getTimes=function(n){
	     return new Array(n);
	   };
});


ownerCommentModule.service('ownerCommentService', function($http,APP_CONSTANT, $rootScope) {
	var ownerCommentService = {};
	
	ownerCommentService.getRestaurant =  function(restaurantId,callback, callbackError) {
				$http.get(APP_CONSTANT.REMOTE_HOST + '/api/users/'+$rootScope.userSession.id+'/restaurants/'+restaurantId)
				.success(function(data, status, headers, config) {
					callback(data, headers);
				}).error(function(data, status, headers, config) {
					callbackError(data, headers);
				});
			}
	

	return ownerCommentService;
})
