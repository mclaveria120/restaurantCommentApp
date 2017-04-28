/**
 * 
 */
'use strict';
 angular.module("loginModule",[]);
 angular.module("registrationModule",[]);
 angular.module("utilModule", []);
 //admin
 angular.module("adminHomeModule", []);
 angular.module("addRestaurantAdminModule", []);
 angular.module("addOwnerModule", []);
 angular.module("adminUsersModule",[]);
 angular.module("adminReviewsModule",[]);
 //owner
 angular.module("ownerHomeModule", []);
 angular.module("ownerRestaurantModule", []);
 angular.module("ownerCommentModule", []);
 angular.module("ownerAddCommentModule", []);
 
 //customer
 angular.module("customerHomeModule", []);
 angular.module("customerAddReviewModule", []);
 //guest
 angular.module("welcomeModule", []);
 angular.module("addReviewModule", []);
 
 
 angular
 .module('appCoreModule', [
	 'ngRoute',
     'ngCookies',
     'ui.bootstrap'
 ]);
//Step 2: Register App
angular.module("app", 	
			['appCoreModule',
			
			'registrationModule',
			'loginModule',
			'utilModule',
			//admin
			'adminHomeModule',
			'addOwnerModule',
			'addRestaurantAdminModule',
			'adminUsersModule',
			'adminReviewsModule',
			//owner
			'ownerHomeModule',
			'ownerRestaurantModule',
			'ownerCommentModule',
			'ownerAddCommentModule',
			//customer
			'customerHomeModule',
			'customerAddReviewModule',
			//guest
			'welcomeModule',
			'addReviewModule'
		 ]);



