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
                controller: "signUpCtrl",
                onEnter: function($state, auth){
                    if(!auth.isAuthenticated()){
                        $state.go('starter');
                    }
                }
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
                onExit: function(viewStatistics){
                    viewStatistics.disconnectWebsockets();
                }
            })

            //unsafe information
            .state('unsafePage', {
                url: "/unsafePage",
                templateUrl: "templates/unsafePage.html",
                controller: "unsafePageCtrl"
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
            })

            //subscribe urls
            .state('subscription', {
                url: "/subscription",
                templateUrl: "templates/subscription.html",
                controller: "subscriptionCtrl",
                onEnter: function($state, auth){
                    if(!auth.isAuthenticated()){
                        $state.go('starter');
                    }
                }
            })

            //forbidden access
            .state('forbiddenAccess', {
                url: "/forbiddenAccess",
                templateUrl: "templates/forbiddenAccess.html"
            });

        $urlRouterProvider.otherwise('starter');
    });
