angular.module('productadmin', ['components']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/products', {templateUrl: 'partials/products.html', controller: ProductsCtrl}).
      when('/products/add', {templateUrl: 'partials/editproduct.html', controller: EditProductsCtrl}).
      when('/products/edit/:productId', {templateUrl: 'partials/editproduct.html', controller: EditProductsCtrl}).
      otherwise({redirectTo: '/products'});
}]);


