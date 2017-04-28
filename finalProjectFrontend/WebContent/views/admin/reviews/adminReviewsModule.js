
var adminReviewsModule = angular.module("adminReviewsModule");

adminReviewsModule.controller('adminReviewsController', function($scope, $routeParams,$rootScope,$location,adminReviewsService,utilService) {
	$rootScope.messageError=false;
	
	utilService.checkUser('ADMIN');
	
	$scope.init = function () {
		adminReviewsService.getAllReviews($routeParams.id,
					function(data,headers) {
						$scope.reviews = data;
					}
					,function(data,headers) {
						utilService.showErrorMessage(data);
					}
		    );
	};
	
	$scope.getTimes=function(n){
	     return new Array(n);
	   };
	   
   $scope.viewUser=function(data){
		$location.path('/admin/users/'+data.user_id);
	};   
	$scope.viewComment=function(comment){
		utilService.showComment(comment.text);
	};
});


adminReviewsModule.service('adminReviewsService', function($http,$timeout,APP_CONSTANT) {
	var adminReviewsService = {};
	
	adminReviewsService.getAllReviews =  function(id,callback, callbackError) {
				$http.get(APP_CONSTANT.REMOTE_HOST + '/api/restaurants/'+id+'/reviews')
				.success(function(data, status, headers, config) {
					callback(data, headers);
				}).error(function(data, status, headers, config) {
					callbackError(data, headers);
				});
			}
	
	return adminReviewsService;
})
