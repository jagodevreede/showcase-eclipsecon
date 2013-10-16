define(['angular'], function(angular) {
	angular.module('webshop.directives', [])
  		.directive('navbar', function() {
	  		return {
	  			restrict: 'E',
	  			controller: 'CategoriesCtrl',
	  			templateUrl: '/module/partials/navbar.html'
	  		}
  		}
  	);
 })