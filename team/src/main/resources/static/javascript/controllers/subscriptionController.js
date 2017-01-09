angular.module('urlShortener')

    .controller('subscriptionCtrl', ['$scope', '$state', 'auth', 'subscription', function ($scope, $state, auth, subscription) {

        $scope.urlList = {};
        $scope.myurl = "";
        $scope.myurlu = "";
        $scope.myurlr = "";
        $scope.subscribed = "";
        $scope.removed = "";

        $scope.logged = function () {
            return auth.isAuthenticated();
        };

        // feedback handling variables
        $scope.error = false;
        $scope.success = false;
        $scope.successMsg = "";
        $scope.errorMsg = "";

        $scope.getListOfSubscribedUrls = function () {

            subscription.listSubscribedUrlsAdmin(function (myList) {
                $scope.urlList = myList;   //dos listas
            },showError);
        };

        $scope.addSubscription = function () {

            var subscribed = {
                subscribed: $scope.myurl
            };

            subscription.addSubscription(subscribed, function (urlSubscribed) {
                var index = $scope.urlList.subscribedUrls.indexOf($scope.myurl);
                $scope.urlList.unsubscribedUrls.splice(index,1);
                $scope.urlList.subscribedUrls.push($scope.myurl);
            });
        };

        $scope.removeSubscription = function () {
            var unsubscribed = {
                unsubscribed: $scope.myurlu
            };
            subscription.removeSubscription(unsubscribed, function (urlUnsubscribed) {
                var index = $scope.urlList.unsubscribedUrls.indexOf($scope.myurlu);
                $scope.urlList.subscribedUrls.splice(index,1);
                $scope.urlList.unsubscribedUrls.push($scope.myurlu);
            });
        };

        $scope.removeUrl = function () {
            var removed = {
                removed: $scope.myurlr
            };
            subscription.removeUrl(removed, function (urlRemoved) {
                var index = $scope.urlList.unsubscribedUrls.indexOf($scope.myurlr);
                $scope.urlList.subscribedUrls.splice(index,1);
                //$scope.urlList.unsubscribedUrls.push($scope.myurlr);
            });
        };


        // hide the error mensage
        $scope.hideError = function () {
            $scope.errorMsg = "";
            $scope.error = false;
        };

        // show the error mensage
        var showError = function (error) {
            $scope.errorMsg = error;
            $scope.error = true;
        };

        // show the success mensage
        var showSuccess = function (message) {
            $scope.successMsg = message;
            $scope.success = true;
        };

        // hide the success mensage
        $scope.hideSuccess = function () {
            $scope.success = false;
            $scope.successMsg = "";
        };

        $scope.getListOfSubscribedUrls();
    }]);
