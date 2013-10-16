define(['angular', 'webshop.controllers', 'webshop.filters', 'webshop.services', 'webshop.directives'], function(angular) {

  angular.module('webshop.app', ['webshop.controllers', 'webshop.services', 'webshop.directives', 'webshop.filters']).config(['$routeProvider', function($routeProvider) {
    $routeProvider.
    	when('/products', {templateUrl: 'partials/products.html',   controller: 'ProductsCtrl'}).
        when('/products/:category', {templateUrl: 'partials/products.html',   controller: 'ProductsCtrl'}).
        when('/basket', {templateUrl: 'partials/basket.html',   controller: 'BasketCtrl'}).
        when('/checkout', {templateUrl: 'partials/checkout.html',   controller: 'CheckoutCtrl'}).
        when('/ordercompleted', {templateUrl: 'partials/ordercompleted.html'}).
        when('/orders', {templateUrl: 'partials/orders.html', controller: 'OrdersCtrl'}).
        otherwise({redirectTo: '/products'});
  }]);
});
