angular.module('urlShortener')

    .controller('unsafePageCtrl', ['$scope', '$state', 'auth', function ($scope, $state, auth) {

        $scope.object; //Objeto Matches con toda informacion en JSON
        $scope.safe;

        // feedback handling variables
        $scope.error = false;
        $scope.success = false;
        $scope.successMsg = "";
        $scope.errorMsg = "";
        $scope.safe = true;

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



        $scope.getInformationPage = function () {
            var object = {
                object : $scope.object
            };
            urlShortener.getInformationPage(url, showSuccess, showError);
        }
    }]);
