
var utilModule = angular.module("utilModule", []);

utilModule.factory('utilService', function($rootScope,$uibModal,$http,APP_CONSTANT,$location,$rootScope,$cookieStore,$http,AUTH_EVENTS) {
	var utilModule= {};
	
	$rootScope.messageError=false;
	$rootScope.message="";
	
	utilModule.showModal = function(msgToDisplay) {
		var modalInstance = $uibModal.open({
			animation : true,
			component : 'successComponent',
			resolve : {
				msg : function() {
					return msgToDisplay;
				}
			}
		});
	};
	
	utilModule.checkUser=function(controlllerRole){
		if($rootScope.globals.userSession.role!=controlllerRole){
			$location.path('/logout');
		}
	};
	
	utilModule.showComment = function(msgToDisplay) {
		var modalInstance = $uibModal.open({
			animation : true,
			component : 'commentComponet',
			resolve : {
				msg : function() {
					return msgToDisplay;
				}
			}
		});
	};
	
	
	utilModule.showErrorMessage=function(data){
		if(data==null){
			$rootScope.messageError=true;
			$rootScope.message="error";
			$location.path('/logout');
		}else{
			console.log(data);
			$rootScope.messageError=true;
			$rootScope.message=data.message==null?"Error":data.message;
			if(data.status==401){
				$location.path('/logout');
			}	
		}
	}
	
	utilModule.clearCredentials = function () {
        $rootScope.globals = {};
        $cookieStore.remove('globals');
        $http.defaults.headers.common.Authorization = 'Basic ';
        $rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
};
	return utilModule;
});