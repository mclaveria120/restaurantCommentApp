/**
 * 
 */
var configModule = angular.module('app'); 

configModule.component('successComponent', {
	  templateUrl: 'util/popup/success-ok.html',
	  bindings: {
	    resolve: '<',
	    close: '&',
	    dismiss: '&'
	  },
	  controller: function () {
	    var $ctrl = this;
	    
	    $ctrl.$onInit = function () {
	        $ctrl.msg = $ctrl.resolve.msg;
	      };

	    $ctrl.ok = function () {
	      $ctrl.close();
	    };

	    $ctrl.cancel = function () {
	      $ctrl.dismiss({$value: 'cancel'});
	    };
	  }
	})

configModule.component('commentComponet', {
	  templateUrl: 'util/popup/comment.html',
	  bindings: {
	    resolve: '<',
	    close: '&',
	    dismiss: '&'
	  },
	  controller: function () {
	    var $ctrl = this;
	    
	    $ctrl.$onInit = function () {
	        $ctrl.msg = $ctrl.resolve.msg;
	      };

	    $ctrl.ok = function () {
	      $ctrl.close();
	    };

	    $ctrl.cancel = function () {
	      $ctrl.dismiss({$value: 'cancel'});
	    };
	  }
	})
	