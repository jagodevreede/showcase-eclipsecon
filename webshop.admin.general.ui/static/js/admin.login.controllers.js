var LoginCtrl = ['$scope', '$http', function($scope, $http) {
	$scope.logindetails = {};

	$scope.login = function() {
		$http.post('/adminlogin', $scope.logindetails).success(function() {
			window.location = '/admin/orders/index.html';
		}).error(function() {
			$scope.errormessage = 'Username password combination incorrect';
		});
	}
}];	
