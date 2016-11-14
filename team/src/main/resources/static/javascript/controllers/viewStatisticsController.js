angular.module('urlShortener')

    .controller('viewStatisticsCtrl', ['$scope', '$state', 'viewStatistics', function ($scope, $state, viewStatistics) {

        $scope.statistics = {};

        // FEEDBACK MESSAGES

        // feedback handling variables
        $scope.error = false;
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

        $scope.getStats = function () {
            viewStatistics.getStats(function (stats) {
                $scope.statistics = stats;
            },showError);
        };
        $scope.getStats();

    }]);
