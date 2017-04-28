
var addReviewModule = angular.module("addReviewModule", []);
addReviewModule.controller('addReviewController', function($rootScope,$location, $routeParams,$scope, addReviewService, utilService) {
	
	$rootScope.messageError=false;
	
	$scope.review = {
			description: "",
			stars : 3
		};
	
	$scope.cancel = function() {
		$location.path('/#');
	}

	$scope.addReview = function() {
		var data={
				review:$scope.review,
				restaurantId: $routeParams.restaurantId
		};
		
		addReviewService.addReview(data,
			function(data, headers) { 
					utilService.showModal('Review added');
					$location.path('/#');
			}, 
			function(data, headers) {
					utilService.showErrorMessage(data);
			});
	}
});

addReviewModule.factory('addReviewService', function($http,APP_CONSTANT,$rootScope) {
	var addReviewService = {};
	
	addReviewService.addReview =  function(data,callback, callbackError) {
		$http.post(APP_CONSTANT.REMOTE_HOST + '/guest/restaurants/'+data.restaurantId+'/review',
				data.review)
		.success(function(data, status, headers, config) {
			callback(data, headers);
		}).error(function(data, status, headers, config) {
			callbackError(data, headers);
		});
	}
	
	return addReviewService;
	
});