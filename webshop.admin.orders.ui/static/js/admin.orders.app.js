angular.module('orderadmin', ['components']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/orders', {templateUrl: 'partials/orders.html', controller: OrdersCtrl}).
      otherwise({redirectTo: '/orders'});
}]);


