angular.module('urlShortener')

    .controller('starterCtrl', ['$scope', '$state', 'urlShortener', 'qrGenerator', function ($scope, $state, urlShortener, qrGenerator) {

        $scope.url = "";
        $scope.qr = "";
        $scope.avaiableQR = false;

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
        };

        $scope.getQR = function () {
            qrGenerator.generateQR($scope.successMsg, function (urlQR) {
                $scope.qr = urlQR;
                $scope.avaiableQR = true;
            });
        }

    }]);
