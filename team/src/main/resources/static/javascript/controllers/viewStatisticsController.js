angular.module('urlShortener')

    .controller('viewStatisticsCtrl', ['$scope', '$state', 'auth', 'viewStatistics', function ($scope, $state, auth, viewStatistics) {

        $scope.statistics = {};
        $scope.visibility = {};
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

        $scope.getStats = function () {
            if ($scope.logged()) {
                viewStatistics.getAdminStats(function (stats) {
                    $scope.statistics = stats;
                    $scope.$apply();
                }, function (statsVisibility) {
                    $scope.visibility = statsVisibility;
                    $scope.$apply();
                });
            } else {
                viewStatistics.getStats(function (stats) {
                    $scope.statistics = stats;
                    $scope.$apply();
                });
            }
        };
        $scope.getStats();

        $scope.sendVisibility = function () {
            viewStatistics.sendVisibility($scope.visibility, showSuccess);
        };
    }]);
