var configModule = angular.module('app');

configModule.controller("applicationContoller", function($rootScope,$scope,$window,$location,AUTH_EVENTS, utilService,applicationService) {
	
	$rootScope.userSession;

	$scope.init = function () {
		$scope.parentmethod();
	}

	$rootScope.$on("CallParentMethod", function(){
        $scope.parentmethod();
     });

	$rootScope.home=function(){
		$rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
	}
	
     $scope.parentmethod = function() {
    	 	var globals = JSON.parse($window.localStorage.getItem("globals"));
    	 	if(globals){
    	 		$('div#guest').hide();
    	 		$('div#logout').show();
    	 		$rootScope.userSession = globals.userSession;
    	 	}else{
    	 		$('div#guest').show();
    	 		$('div#logout').hide();
    	 	}
     }
     $scope.getPath=function(){
    	 return $location.$$path; 
     }
     		
     $scope.logout = function () {
    	 applicationService.logout(
    			 function(data, headers) { 
    				 utilService.clearCredentials();
    				 $location.path('/login');
    	 		}, 
				function(data, headers) {
    	 			 	utilService.clearCredentials();
						utilService.showErrorMessage(data);
				});
 	};
     
});

configModule.factory('applicationService', function($http,APP_CONSTANT) {
	var applicationService = {};
	
	applicationService.logout = function(onSuccess, onError) {
			$http.get(APP_CONSTANT.REMOTE_HOST + '/logout')
			.success(function(data, status, headers, config) {
				onSuccess(data, headers);
			}).error(function(data, status, headers, config) {
				onError(data, headers);
			});
	};
	return applicationService;
});



