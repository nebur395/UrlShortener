angular.module('urlShortener', ['ui.router', 'base64', 'ngStomp'])

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
                controller: "viewStatisticsCtrl",
                onExit: function($state, viewStatistics){
                    viewStatistics.disconnectEliza();
                }
            })

            //restrict access
            .state('restrictAccess', {
                url: "/restrictAccess",
                templateUrl: "templates/restrictAccess.html",
                controller: "restrictAccessCtrl",
                onEnter: function($state, auth){
                    if(!auth.isAuthenticated()){
                        $state.go('starter');
                    }
                }
            });

        $urlRouterProvider.otherwise('starter');
    });
