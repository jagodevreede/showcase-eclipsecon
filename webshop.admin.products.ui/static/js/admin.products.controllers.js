var ProductsCtrl = ['$scope', '$http', function($scope, $http) {
	if($scope.category == undefined) {
		$scope.category = localStorage.category;
	}


	$http.get('/products/categories').success(function(categories) {
		$scope.categories = categories;
	});

	$scope.$watch('category', function(newVal, oldVal) {
		if(newVal != undefined) {
			localStorage.category = newVal;

			$http.get('/products/' + $scope.category).success(function(products) {
				$scope.products = products;
			});		
		}
	});
}];	

var EditProductsCtrl = ['$scope', '$http', '$routeParams', '$location', function($scope, $http, $routeParams, $location) {
	
	if($routeParams.productId != undefined) {
		$http.get('/products/all/' + $routeParams.productId).success(function(product) {
			$scope.product = product;
			$scope.existingCategory = $scope.product.category;
		});	
	}

	$http.get('/products/categories').success(function(categories) {
		$scope.categories = categories;
	});

	$scope.save = function() {
		if($scope.existingCategory != undefined && $scope.existingCategory != "") {
			$scope.product.category = $scope.existingCategory;
		} else {
			$scope.product.category = $scope.newCategory;
		}

		$http.post('/products', $scope.product).success(function() {
			$location.path('/products');
		});	
	}

	$scope.delete = function() {
		$http.delete('/products/all/' + $routeParams.productId).success(function() {
			$location.path('/products');
		});
	}
}];