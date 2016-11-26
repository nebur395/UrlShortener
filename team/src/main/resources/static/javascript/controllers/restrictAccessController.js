angular.module('urlShortener')

    .controller('restrictAccessCtrl', ['$scope', '$state', 'auth', 'restrictAccess', function ($scope, $state, auth, restrictAccess) {

        $scope.countryList = {};
        $scope.result = false;
        $scope.unblocked = "";
        $scope.blocked = "";
        $scope.myCountry = "";
        //    $scope.visibility = {};
        $scope.logged = function () {
            return auth.isAuthenticated();
        };

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

        $scope.getListOfCountries = function () {
            /*if ($scope.logged()) {
                $scope.blockCountryF = document.getElementById('blockCountry').value;
            } else {
                showError('not logged');
            }*/
            restrictAccess.getListOfCountries(function (countriesLists) {
                $scope.countryList = countriesLists;
            },showError);
        };

        $scope.blockCountry = function () {
            $scope.unblocked = document.getElementById('blocking').value;
            restrictAccess.blockCountry($scope.unblocked, function (blocked) {
                $scope.result = blocked;
            },showError);
            $scope.getListOfCountries();
        };

        $scope.unblockCountry = function () {
            $scope.blocked = document.getElementById('unblocking').value;
            restrictAccess.unblockCountry($scope.blocked, function (unblocked) {
                $scope.result = unblocked;
            },showError);
            $scope.getListOfCountries();
        };

        $scope.getListOfCountries();

        /*$scope.sendVisibility = function () {
            viewStatistics.sendVisibility($scope.visibility, showSuccess, showError);
        }*/
    }]);
