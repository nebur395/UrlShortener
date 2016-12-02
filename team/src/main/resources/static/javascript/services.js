angular.module('urlShortener')

    // 'auth' service manage the authentication function of the page with the server
    .factory('auth', function ($state, $http, $httpParamSerializer, $base64) {

        return {
            //return true if the user is authenticated
            isAuthenticated: function () {
                return false;
            },

            //logout function
            logout: function () {
                $http({
                    method: 'POST',
                    url: '/logout',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) {
                }).error(function (data) {
                });
            },

            //send the login info to the server
            signIn: function (user, password, callback) {
                var that = this;
                $http({
                    method: 'GET',
                    url: 'signIn',
                    headers: {
                        'user': user,
                        'pass': password
                    }
                }).success(function (data) {
                    $state.go('starter');

                }).error(function (data) {
                    callback(data);
                });
            },

            //send the register info to the server
            signUp: function (userObject, callbackSuccess, callbackError) {
                $http({
                    method: 'POST',
                    url: '/signUp',
                    data: $httpParamSerializer(userObject),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) {
                    callbackSuccess(data);
                }).error(function (data) {
                    callbackError(data);
                });
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
                    callbackSuccess(data.uri, data.safe);
                }).error(function (data) {
                    callbackError('ERROR');
                });
            }
        };
    })

    //Indicar que vaya a unsafeController?
    .factory('getInformationPage', function ($state, $http, $httpParamSerializer) {
        return {

            //send the register info to the server
            getInformationPage: function (url, callbackSuccess,callbackError) {
                $http({
                    method: 'GET',
                    url: '/unsafePage',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) { //Objeto Matches en data
                    callbackSuccess(data);
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
            generateQR: function (qrUrl, qrFname, qrLname, qrEmail, qrPhone, qrCompany, qrStreet, qrZip, qrCity, qrCountry, callbackSuccess) {
                $http({
                    method: 'GET',
                    url: '/qr',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'qrUrl': qrUrl,
                        'qrFname': qrFname,
                        'qrLname': qrLname,
                        'qrEmail': qrEmail,
                        'qrPhone': qrPhone,
                        'qrCompany': qrCompany,
                        'qrStreet': qrStreet,
                        'qrZip': qrZip,
                        'qrCity': qrCity,
                        'qrCountry': qrCountry
                    }
                }).success(function (data) {
                    callbackSuccess(data);
                }).error(function (data) {
                });
            }
        };
    })

    // 'viewStatistics' service manage the view statistics functionallity
    .factory('viewStatistics', function ($state, $http, $httpParamSerializer) {

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
            },

            //get the statistics of the system
            getAdminStats: function (callbackSuccess,callbackError) {
                $http({
                    method: 'GET',
                    url: '/viewStatistics/admin',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) {
                    callbackSuccess(data, data.statsVisibility);
                }).error(function (data) {
                    callbackError('Error to get the system admin statistics. You are not an administrator.');
                });
            },

            // send the stats visibility of the system
            sendVisibility: function (statsVisibility, callbackSuccess,callbackError) {
                $http({
                    method: 'POST',
                    url: '/viewStatistics',
                    data: $httpParamSerializer(statsVisibility),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) {
                    callbackSuccess(data);
                }).error(function (data) {
                    callbackError(data);
                });
            }
        };
    });
