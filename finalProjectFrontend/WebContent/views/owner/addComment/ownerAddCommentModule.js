
var ownerAddCommentModule = angular.module("ownerAddCommentModule", []);
ownerAddCommentModule.controller('ownerAddCommentController', function($location,$rootScope, $routeParams,$scope, ownerAddCommentService, utilService) {
	$rootScope.messageError=false;
	$scope.comment="";
	
	utilService.checkUser('OWNER');
	
	$scope.cancel = function() {
		$location.path('/owner/');
	}

	$scope.addComment = function() {
		var data={
				comment:$scope.comment,
				reviewId: $routeParams.reviewId
		};
		
		ownerAddCommentService.addComment(data,
			function(data, headers) { 
					utilService.showModal('Comment added');
					$location.path('/owner');
			}, 
			function(data, headers) {
					utilService.showErrorMessage(data);
			});
	}
});

ownerAddCommentModule.factory('ownerAddCommentService', function($http,APP_CONSTANT,$rootScope) {
	var ownerAddCommentService = {};
	ownerAddCommentService.addComment =  function(data,callback, callbackError) {
		$http.post(APP_CONSTANT.REMOTE_HOST + '/api/users/'+$rootScope.userSession.id+'/reviews/'+data.reviewId+'/comments',
				data.comment)
		.success(function(data, status, headers, config) {
			callback(data, headers);
		}).error(function(data, status, headers, config) {
			callbackError(data, headers);
		});
	}
	
	return ownerAddCommentService;
	
});