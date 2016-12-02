angular.module('urlShortener', ['ui.router', 'base64'])

    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider


            //starter screen
            .state('starter', {
                url: "/starter",
                templateUrl: "templates/starter.html",
                controller: "starterCtrl"
            })

            //sign up screen
            .state('signUp', {
                url: "/signUp",
                templateUrl: "templates/signUp.html",
                controller: "signUpCtrl"
            })

            //sign in screen
            .state('signIn', {
                url: "/signIn",
                templateUrl: "templates/signIn.html",
                controller: "signInCtrl"
            })

            //starter screen
            .state('viewStatistics', {
                url: "/viewStatistics",
                templateUrl: "templates/viewStatistics.html",
                controller: "viewStatisticsCtrl"
            })

            //unsafe information
            .state('unsafePage', {
                url: "/unsafePage",
                templateUrl: "templates/unsafePage.html",
                controller: "unsafePageCtrl"
            });

        $urlRouterProvider.otherwise('starter');
    });
