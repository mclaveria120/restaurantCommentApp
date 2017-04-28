/**
 * 
 */
angular.module('app')
.constant('AUTH_EVENTS', {
	loginSuccess: 'auth-login-success',
	logoutSuccess: 'auth-logout-success',
	loginFailed:'auth-login-falied',

})
.constant('ROLES', {
	admin : 'ADMIN',
	owner : 'OWNER',
	customer:'CUSTOMER',
	guest : 'PUBLIC',
})
.constant('APP_CONSTANT',{
		REMOTE_HOST:'http://localhost:8080/finalProjectBackend',
});
