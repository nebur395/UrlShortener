angular.module('urlShortener')

    // 'auth' service manage the authentication function of the page with the server
    .factory('auth', function ($state, $http) {

        return {
            //return true if the user is authenticated
            isAuthenticated: function () {
                return false;
            },

            //logout function
            logout: function () {
            }
        };
    })

    // 'urlShortener' service manage the url shortener controller in common
    .factory('urlShortener', function ($state, $http, $httpParamSerializer) {

        return {

            //send the register info to the server
            shortURL: function (url, callbackSuccess,callbackError) {
                $http({
                    method: 'POST',
                    url: '/link',
                    data: $httpParamSerializer(url),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) {
                    callbackSuccess(data.uri);
                }).error(function (data) {
                    callbackError('ERROR');
                });
            }
        };
    })


    // 'qrGenerator' service manage the QR generator
    .factory('qrGenerator', function ($state, $http) {

        return {

            //send the register info to the server
            generateQR: function (url, callbackSuccess) {
                $http({
                    method: 'GET',
                    url: '/qr',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'url': url
                    }
                }).success(function (data) {
                    callbackSuccess(data);
                }).error(function (data) {
                });
            }
        };
    })


    // 'viewStatistics' service manage the view statistics functionallity
    .factory('viewStatistics', function ($state, $http) {

        return {

            //get the statistics of the system
            getStats: function (callbackSuccess,callbackError) {
                $http({
                    method: 'GET',
                    url: '/viewStatistics',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) {
                    callbackSuccess(data);
                }).error(function (data) {
                    callbackError('Error to get the system statistics');
                });
            }
        };
    });
