var OrdersCtrl = ['$scope', '$http', function($scope, $http) {
	

	$scope.getOrders = function() {
		$http.get('/orders').success(function(orders) {
			$scope.orders = orders;
		});	
	}

	$scope.getOrders();


	$scope.updateStatus = function(orderId, status) {
		$http.post('/orders/' + orderId + '/events', status).success(function() {
			$scope.getOrders();
		});
	}

	$scope.showCancel = function(order) {
		var show = true;
		angular.forEach(order.eventLog, function(event) {
			if(event.type == 'ORDER_SENT' || event.type == 'ORDER_CANCELED') {
				show = false;
				return false;
			}
		});

		return show;
	}

	$scope.showPaymentReceived = function(order) {
		var show = true;
		angular.forEach(order.eventLog, function(event) {
			if(event.type == 'ORDER_SENT' || event.type == 'ORDER_CANCELED' || event.type == 'PAYMENT_RECEIVED') {
				show = false;
				return false;
			}
		});

		return show;
	}

	$scope.showSent = function(order) {
		var show = true;
		angular.forEach(order.eventLog, function(event) {
			if(event.type == 'ORDER_SENT' || event.type == 'ORDER_CANCELED' ) {
				show = false;
				return false;
			}
		});

		return show;
	}
}];	
