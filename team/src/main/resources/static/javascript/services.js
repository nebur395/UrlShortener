angular.module('urlShortener')

    // 'auth' service manage the authentication function of the page with the server
    .factory('auth', function ($state, $http, $httpParamSerializer) {

        var session = undefined,
            _authenticated = false;

        return {
            //return true if the user is authenticated
            isAuthenticated: function () {
                if (_authenticated) {
                    return _authenticated;
                } else {
                    var tmp = angular.fromJson(localStorage.sessionJWT);
                    if (tmp !== undefined) {
                        this.authenticate(tmp);
                        return _authenticated;
                    } else {
                        return false;
                    }
                }
            },

            //authenticate the [identity] user
            authenticate: function (jwt) {
                session = jwt;
                _authenticated = jwt !== undefined;
                localStorage.sessionJWT = angular.toJson(session);
            },

            getSession: function () {
                return session;
            },

            //logout function
            logout: function () {
                session = undefined;
                _authenticated = false;
                localStorage.removeItem("sessionJWT");
                $state.go('starter');
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
                }).success(function (data, status, headers) {
                    that.authenticate(headers().authorization);
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
                    callbackSuccess(data);
                }).error(function (data) {
                    callbackError('ERROR');
                });
            }
        };
    })

    .factory('unsafePage', function ($state, $http) {
        return {

            //send the register info to the server
            getInformationPage: function (callbackSuccess,callbackError) {
                $http({
                    method: 'GET',
                    url: '/unsafePage',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) { //Object Matches in data
                    callbackSuccess(data);
                }).error(function (data) {
                    callbackError('error getting information of unsafe page');
                });
            }
        };
    })


    // 'viewStatistics' service manage the view statistics functionallity
    .factory('viewStatistics', function ($state, auth, $stomp) {

        $stomp.setDebug(function (args) {
            console.log(args);
        });

        return {

            //get the statistics of the system
            getStats: function (addResponse) {
                $stomp.connect('/urlShortener', {}).then(
                    function (frame) {
                        console.log('Connected: ' + frame);
                        $stomp.subscribe('/viewStats/standardStats', function (payload, headers, res) {
                            console.log(payload);
                            addResponse(payload);
                        }, {})
                    }, function(error) {
                        console.error(error);
                    }
                );
            },

            //get the ADMIN statistics of the system
            getAdminStats: function (updateStats, updateVisibility) {
                $stomp.connect('/urlShortener', {}).then(
                    function (frame) {
                        console.log('Connected: ' + frame);
                        $stomp.subscribe('/viewStats/adminStats', function (payload, headers, res) {
                            console.log(payload);
                            updateStats(payload);
                        }, {});
                        $stomp.subscribe('/viewStats/visibilityStats', function (payload, headers, res) {
                            console.log(payload);
                            updateVisibility(payload);
                        }, {});
                        var visibility = {
                            firstStats: true
                        };
                        $stomp.send('/app/changeVisibility',visibility, {});
                    }, function(error) {
                        console.error(error);
                    }
                );
            },

            disconnectWebsockets: function () {
                $stomp.disconnect();
                console.log("Disconnected");
            },

            // send the stats visibility of the system
            sendVisibility: function (request, callbackSuccess) {
                $stomp.send('/app/changeVisibility',request, {}).then(
                    function (frame) {
                        callbackSuccess('Stats visibility succesfully changed');
                    }, function(error) {
                        console.error(error);
                    }
                );
            }
        };
    })


    // 'restrictAccess' service manage blocking access depending on location functionallity
    .factory('restrictAccess', function ($state, $http, $httpParamSerializer) {

        return {

            //get countries
            getListOfCountries: function (callbackSuccess,callbackError) {
                $http({
                    method: 'GET',
                    url: '/restrictAccess',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) {
                    callbackSuccess(data);
                }).error(function (data) {
                    callbackError('Error to administrate restrictions about location');
                });
            },

            //block access from a country
           blockCountry: function (unblockCountry,callbackSuccess,callbackError) {
                $http({
                    method: 'POST',
                    url: '/restrictAccess/blockCountry',
                    data: $httpParamSerializer(unblockCountry),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) {
                    callbackSuccess(data);
                }).error(function (data) {
                    callbackError('Error to administrate restrictions about location');
                });
            },

            //unblock access from a country
            unblockCountry: function (blockCountry,callbackSuccess,callbackError) {
                $http({
                    method: 'POST',
                    url: '/restrictAccess/unblockCountry',
                    data: $httpParamSerializer(blockCountry),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (data) {
                    callbackSuccess(data);
                }).error(function (data) {
                    callbackError('Error to administrate restrictions about location');
                });
            }
        };
    });
