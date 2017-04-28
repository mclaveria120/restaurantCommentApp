var configModule = angular.module('app') // Please dont not use [], dependency 
.config(function($routeProvider) {	
//    $urlRouterProvider.otherwise('/login');
	$routeProvider
	.when('/', {
		 templateUrl : 'views/welcome.html',
	     controller  : 'welcomeController',
	     controllerAs: 'welcomeCtrl'
	})
	.when('/addReview/:restaurantId', {
		 templateUrl : 'views/addReview.html',
	     controller  : 'addReviewController',
	})
	.when('/login', {
		 templateUrl : 'views/login/login.html',
	     controller  : 'loginController',
	     controllerAs: 'loginCtrl'
	})
	.when('/registration', {
		 templateUrl : 'views/register/registration.html',
	     controller  : 'registrationController',
	     controllerAs: 'regCtrl'
	})
	.when('/logout', {
		redirectTo: '/'
	})
   .when('/admin', {
        templateUrl : 'views/admin/adminHome.html',
        controller  : 'adminHomeController'
    })
    .when('/admin/addRestaurant', {
        templateUrl : 'views/admin/addRestaurant/addRestaurant.html',
        controller  : 'addRestaurantAdminController',
        controllerAs: 'addRestaurantAdmintCtrl'
    })
    .when('/admin/users', {
        templateUrl : 'views/admin/users/adminUsers.html',
        controller  : 'adminUsersController',
    })
    .when('/admin/users/:id', {
        templateUrl : 'views/admin/users/adminUsers.html',
        controller  : 'adminUsersController',
    })
    .when('/admin/reviews/:id', {
        templateUrl : 'views/admin/reviews/adminReviews.html',
        controller  : 'adminReviewsController',
    })
    .when('/admin/addOwner/:id', {
        templateUrl : 'views/admin/addOwner/addOwner.html',
        controller  : 'addOwnerController',
        controllerAs: 'addOwnerCtrl'
    })
    .when('/owner', {
        templateUrl : 'views/owner/ownerHome.html',
        controller  : 'ownerHomeController'
    })
    .when('/owner/addRestaurant', {
        templateUrl : 'views/owner/restaurant/addRestaurant.html',
        controller  : 'ownerRestaurantController',
    })
     .when('/owner/editRestaurant/:restaurantId', {
        templateUrl : 'views/owner/restaurant/editRestaurant.html',
        controller  : 'ownerRestaurantController',
    })
    .when('/owner/reviews/:restaurantId', {
        templateUrl : 'views/owner/comments/ownerComment.html',
        controller  : 'ownerCommentController',
    })
      .when('/owner/addComment/:reviewId', {
        templateUrl : 'views/owner/addComment/ownerAddComment.html',
        controller  : 'ownerAddCommentController',
    })
    .when('/customer', {
        templateUrl : 'views/customer/customerHome.html',
        controller  : 'customerHomeController'
    })
    .when('/customer/addReview/:id', {
        templateUrl : 'views/customer/addReview/customerAddReview.html',
        controller  : 'customerAddReviewController'
        	  
    })
    .otherwise({ redirectTo: '/' });
})

.run(function ($rootScope, $location, $cookieStore,$window, $http,AUTH_EVENTS,ROLES) {
    	$rootScope.$on('$locationChangeStart', function (event, next, current) {
            if ( !($location.path() == '/' || $location.path() == '/registration' || $location.path() == '/login' || $location.path().includes("/addReview/")) && !$rootScope.globals.userSession) {
                $location.path('/');
            }else if($location.path() == '/logout'){
            	$rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
            }else{
            }
        });
    	
	$rootScope.$on(AUTH_EVENTS.loginFailed, function(event, next){
    		console.log('Login failed');
    		$location.path('#/');
    });
	
	$rootScope.$on(AUTH_EVENTS.logoutSuccess, function(event, next){
		$window.localStorage.removeItem("globals");
		$rootScope.userSession=null;
	    $rootScope.$emit("CallParentMethod", {});
	});
    
    $rootScope.$on(AUTH_EVENTS.loginSuccess, function(event, next){
	    $window.localStorage.setItem("globals", angular.toJson($rootScope.globals));
	    $rootScope.userSession = JSON.parse($window.localStorage.getItem("globals")).userSession;
	    $rootScope.$emit("CallParentMethod", {});
		$location.path('/'+$rootScope.globals.userSession.role.toLowerCase());
    });
    
    $rootScope.globals = $cookieStore.get('globals') || {};
    if ($rootScope.globals.userSession) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.userSession.authdata; // jshint ignore:line
        $http.defaults.headers.common['AUTH_KEY'] = $rootScope.globals.userSession.authKey; // jshint ignore:line
	    $window.localStorage.setItem("globals", angular.toJson($rootScope.globals));
	    $rootScope.userSession = JSON.parse($window.localStorage.getItem("globals")).userSession;

    }

})



