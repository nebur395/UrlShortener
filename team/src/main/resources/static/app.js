angular.module('urlShortener', ['ui.router'])

    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider


            //starter screen
            .state('starter', {
                url: "/starter",
                templateUrl: "templates/starter.html",
                controller: "starterCtrl"
            })

            //starter screen
            .state('viewStatistics', {
                url: "/viewStatistics",
                templateUrl: "templates/viewStatistics.html",
                controller: "viewStatisticsCtrl"
            });

        $urlRouterProvider.otherwise('starter');
    });
