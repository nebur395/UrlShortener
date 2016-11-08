angular.module('urlShortener')

    // 'urlShortener' service manage the url shortener controller in common
    .factory('urlShortener', function ($state, $http) {

        return {

            //send the register info to the server
            shortURL: function (url, callbackSuccess,callbackError) {
                console.log(JSON.stringify(url));
                $http({
                    method: 'POST',
                    url: '/link',
                    data: JSON.stringify(url),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).success(function (data) {
                    callbackSuccess(data.uri);
                }).error(function (data) {
                    callbackError('ERROR');
                });
            }
        };
    });
