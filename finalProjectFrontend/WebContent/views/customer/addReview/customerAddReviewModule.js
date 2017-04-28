
var customerAddReviewModule = angular.module("customerAddReviewModule", []);
customerAddReviewModule.controller('customerAddReviewController', function($rootScope,$location, $routeParams,$scope, customerAddReviewService, utilService) {
	$rootScope.messageError=false;
	$scope.review = {
			description: "",
			stars : 3
		};
	
	utilService.checkUser('CUSTOMER');
	
	$scope.cancel = function() {
		$location.path('/customer');
	}

	$scope.addReview = function() {
		var data={
				review:$scope.review,
				restaurantId: $routeParams.id
		};
		
		customerAddReviewService.addReview(data,
			function(data, headers) { 
					utilService.showModal('Review added');
					$location.path('/customer');
			}, 
			function(data, headers) {
					utilService.showErrorMessage(data);
			});
	}
});

customerAddReviewModule.factory('customerAddReviewService', function($http,APP_CONSTANT,$rootScope) {
	var customerAddReviewService = {};
	
	customerAddReviewService.addReview =  function(data,callback, callbackError) {
		$http.post(APP_CONSTANT.REMOTE_HOST + '/api/users/'+$rootScope.userSession.id+'/restaurants/'+data.restaurantId+'/review',
				data.review)
		.success(function(data, status, headers, config) {
			callback(data, headers);
		}).error(function(data, status, headers, config) {
			callbackError(data, headers);
		});
	}
	
	return customerAddReviewService;
	
});