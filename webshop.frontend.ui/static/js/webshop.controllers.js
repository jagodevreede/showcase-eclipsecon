define(['angular', 'webshop.services'], function(angular) {

	var ctrlModule = angular.module('webshop.controllers',[]);

	ctrlModule.controller('ProductsCtrl', ['$scope', '$http', '$routeParams', 'basket', function($scope, $http, $routeParams, basket) {
		$scope.category = $routeParams.category;

		$http.get('/products/' + $scope.category).success(function(data) {
			$scope.products = data;
		});

		$scope.inBasket = function(product) {
			basket.addProduct(product);
		}

		$scope.basket = basket;

		$scope.$watch('search', function(search) {
			if(search != undefined && search.length > 0) {
				$http.get('/productsearch?query=' + search).success(function(data) {
					$scope.products = data;
				});		
			}
		});
	}]);

	ctrlModule.controller('CategoriesCtrl', ['$scope', '$http', 'basket', function($scope, $http, basket) {
		$http.get('/products/categories').success(function(data) {
			$scope.categories = data;
		});

		$scope.basket = basket;
	}]);

	ctrlModule.controller('BasketCtrl', ['$scope', '$http', 'basket', function($scope, $http, basket) {
		$scope.basket = basket;

		$scope.removeItem = function(product) {
			basket.removeItem(product);
		}
	}]);

	ctrlModule.controller('CheckoutCtrl', ['$scope', '$http', 'basket', '$location', function($scope, $http, basket, $location) {
		//Check if the user is allready logged in.
		$http.get('/login/me').success(function(customer) {
			$scope.loggedInUser = customer;
		});

		$scope.basket = basket;

		$scope.newUser = {};

		$scope.createUser = function() {

			if($scope.validate() == true) {
				$http.post('/customers', $scope.newUser).success(function() {
					$http.post('/login', {email: $scope.newUser.email, password: $scope.newUser.password}).success(function() {
						$scope.loggedInUser = $scope.newUser;
					}).error(function() {
						$scope.errormessage = "Failed to login";
					});
				}).error(function() {
					$scope.righterrormessage = "Failed to create user";
				});
			}
		}

		$scope.validate = function() {
			if($scope.newUser.password != $scope.repeatPassword) {
				return false;
			}

			if($scope.newUser.email == undefined || $scope.newUser.email.lenght == 0) {
				return false;
			}

			return true;
		}

		$scope.logindetails = {};

		$scope.login = function() {
			$http.post('/login', {email: $scope.logindetails.email, password: $scope.logindetails.password}).success(function(customer) {
				$scope.loggedInUser = customer;
			}).error(function() {
				$scope.lefterrormessage = "Failed to login";
			});

			
		}

		$scope.logout = function() {
			$http.post('/login/logout').success(function() {	
				$scope.loggedInUser = undefined;
			});		
		}

		$scope.placeOrder = function() {

			var order = {
				products: $scope.basket.products
			};

			$http.post("/orders", order).success(function() {
				basket.empty();
				$location.path('ordercompleted');
			});
		}
	}]);

	ctrlModule.controller('OrdersCtrl', ['$scope', '$http', 'basket', '$location', function($scope, $http) {
		//Check if the user is allready logged in.
		$http.get('/login/me').success(function(customer) {
			$scope.loggedInUser = customer;

			$http.get('/orders/mine').success(function(orders) {
				$scope.orders = orders;
			});
		});
	}]);	

	return ctrlModule;
});
