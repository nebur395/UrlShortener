angular.module('urlShortener')

    .controller('viewStatisticsCtrl', ['$scope', '$state', 'viewStatistics', function ($scope, $state, viewStatistics) {

        $scope.statistics = {};

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

        $scope.getStats = function () {
            viewStatistics.getStats(function (stats) {
                $scope.statistics = stats;
            },showError);
        };
        $scope.getStats();

    }]);
