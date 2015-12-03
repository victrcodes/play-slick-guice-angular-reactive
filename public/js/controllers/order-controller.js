app.controller("OrderController", ($scope, $http, $resource, $routeParams, NgTableParams) => {

	var Order = $resource("/data/order", {}, { get: { isArray: true } });

	$scope.orders = [];

	$scope.currentOrderID = null;
	$scope.orderEdit = null;

	$scope.orderTable = new NgTableParams(
		{
			page: 1,
			count: 0
		},
		{
			total: 0,
			/*counts: [5, 10, 15, 20],*/
			getData: params => $scope.orders = Order.get()
		}
	);

	$scope.upsertOrder = row => {
		$http.put("/data/order", row).success(() => {
			$scope.orderTable.reload();
			$scope.orderEdit = null;
		});
	};

	$scope.newOrder = () => {
		$scope.orders.push({});
		$scope.orderEdit = $scope.orders.length - 1;
	};

});