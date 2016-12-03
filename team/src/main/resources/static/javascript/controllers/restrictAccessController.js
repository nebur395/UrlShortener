angular.module('urlShortener')

    .controller('restrictAccessCtrl', ['$scope', '$state', 'auth', 'restrictAccess', function ($scope, $state, auth, restrictAccess) {

        $scope.countryList = {};
        $scope.result = false;
        $scope.unblocked = "";
        $scope.blocked = "";
        $scope.myCountry = "";
        $scope.logged = function () {
            return auth.isAuthenticated();
        };

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

        $scope.getListOfCountries = function () {
            restrictAccess.getListOfCountries(function (countriesLists) {
                $scope.countryList = countriesLists;
            },showError);
        };

        $scope.blockCountry = function () {
            $scope.unblocked = document.getElementById('blocking').value;
            var unblocked = {
                unblocked: $scope.unblocked
            };
            restrictAccess.blockCountry(unblocked, function (blocked) {
                $scope.result = blocked;
            },showError);
            $scope.getListOfCountries();
        };

        $scope.unblockCountry = function () {
            $scope.blocked = document.getElementById('unblocking').value;
            var blocked = {
                blocked: $scope.blocked
            };
            restrictAccess.unblockCountry(blocked, function (unblocked) {
                $scope.result = unblocked;
            },showError);
            $scope.getListOfCountries();
        };

        $scope.getListOfCountries();
    }]);
