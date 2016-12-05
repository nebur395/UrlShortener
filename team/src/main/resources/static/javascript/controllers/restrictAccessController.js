angular.module('urlShortener')

    .controller('restrictAccessCtrl', ['$scope', '$state', 'auth', 'restrictAccess', function ($scope, $state, auth, restrictAccess) {

        $scope.countryList = {};
        $scope.result = false;
        $scope.unblocked = "";
        $scope.blocked = "";
        $scope.myCountry = "";
        $scope.myCountryU = "";
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
            var unblocked = {
                unblocked: $scope.myCountry
            };
            restrictAccess.blockCountry(unblocked, function (blocked) {
                var countryIndex = $scope.countryList.unblockList.indexOf($scope.myCountry);
                $scope.countryList.unblockList.splice(countryIndex,1);
                $scope.countryList.blockList.push($scope.myCountry);
            },showError);
        };

        $scope.unblockCountry = function () {
            var blocked = {
                blocked: $scope.myCountryU
            };
            restrictAccess.unblockCountry(blocked, function (unblocked) {
                var countryIndex = $scope.countryList.blockList.indexOf($scope.myCountryU);
                $scope.countryList.blockList.splice(countryIndex,1);
                $scope.countryList.unblockList.push($scope.myCountryU);
            },showError);
        };

        $scope.getListOfCountries();
    }]);
