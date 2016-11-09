angular.module('urlShortener')

    .controller('starterCtrl', ['$scope', '$state', 'urlShortener', function ($scope, $state,urlShortener) {

        $scope.url = "";

        // FEEDBACK MESSAGES

        // feedback handling variables
        $scope.error = false;
        $scope.success = false;
        $scope.successMsg = "";
        $scope.errorMsg = "";

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

        $scope.shortURL = function () {
            var url = {
                url: $scope.url
            };
            urlShortener.shortURL(url,showSuccess,showError);
        }

    }]);
