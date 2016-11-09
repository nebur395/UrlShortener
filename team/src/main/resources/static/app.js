angular.module('urlShortener', ['ui.router'])

    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider

            //starter screen
            .state('starter', {
                url: "/starter",
                templateUrl: "templates/starter.html",
                controller: "starterCtrl"
                /*onEnter: function ($state, auth) {
                    if (auth.isAuthenticated()) {
                        $state.go('home');
                    }
                }*/
            });

        $urlRouterProvider.otherwise('starter');
    });
