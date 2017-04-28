
var adminUsersModule = angular.module("adminUsersModule");

adminUsersModule.controller('adminUsersController', function($scope, $routeParams,$rootScope,$location,adminUsersService,utilService) {
	$rootScope.messageError=false;
	
	$scope.sortType     = 'id'; 
	$scope.sortReverse  = false; 
	
	utilService.checkUser('ADMIN');
	
	$scope.init = function () {
		adminUsersService.getAllusers(
					function(data,headers) {
						$scope.users = data;
					}
					,function(data,headers) {
						utilService.showErrorMessage(data);
					}
		    );
	};
	$scope.selectUser=function(id){
		return $routeParams.id==id ? "success":"";
	};
});


adminUsersModule.service('adminUsersService', function($http,$timeout,APP_CONSTANT) {
	var adminUsersService = {};
	
	adminUsersService.getAllusers =  function(callback, callbackError) {
				$http.get(APP_CONSTANT.REMOTE_HOST + '/api/users')
				.success(function(data, status, headers, config) {
					callback(data, headers);
				}).error(function(data, status, headers, config) {
					callbackError(data, headers);
				});
			}
	
	return adminUsersService;
})
