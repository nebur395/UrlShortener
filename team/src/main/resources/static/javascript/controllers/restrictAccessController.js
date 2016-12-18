angular.module('urlShortener')

    .controller('restrictAccessCtrl', ['$scope', '$state', 'auth', 'restrictAccess', function ($scope, $state, auth, restrictAccess) {

        $scope.countryList = {};
        $scope.result = false;
        $scope.resultUpdated = false;
        $scope.unblocked = "";
        $scope.blocked = "";
        $scope.myCountry = "";
        $scope.myCountryU = "";
        $scope.updatedCountry = "";
        $scope.request = "";
        $scope.time = "";
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

        $scope.updateFrequency = function () {
            var updatedCountry = {
                updatedCountry: $scope.updatedCountry,
                request: $scope.request,
                time: $scope.time
            };
            restrictAccess.updateFrequency(updatedCountry, function (resultUpdated) {
                $scope.resultUpdated = resultUpdated;
                if(resultUpdated){
                    showSuccess("Update frequency of "+$scope.updatedCountry)
                }else{
                    showError("Unable update frequency of "+$scope.updatedCountry)
                }
                $scope.updatedCountry = "";
                $scope.request = "";
                $scope.time = "";
            },showError);
        };

        $scope.getListOfCountries();
    }]);
