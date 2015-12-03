var app = angular.module("et", ["ngResource", "ngRoute", "ngTable"]);

app.config(function($routeProvider, $locationProvider) {
	$routeProvider
		.when("/", {
			redirectTo: "/order"
		})
		.when("/order", {
			templateUrl: "/assets/html/order.html",
			controller: "OrderController"
		})
		.otherwise({
			redirectTo: "/order"
		});
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});
});