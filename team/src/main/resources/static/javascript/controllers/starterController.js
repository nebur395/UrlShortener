angular.module('urlShortener')

    .controller('starterCtrl', ['$scope', '$state', 'urlShortener', function ($scope, $state, urlShortener, checkRegion) {

        var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}

        $scope.url = "";    // initial url input form
        $scope.qr = "https://66.media.tumblr.com/44e89309ea155b3be1213e64cc872f2a/tumblr_n0wqfhEW9K1sghdp8o1_400.gif";
        $scope.avaiableQR = false;
        $scope.regionAvaiable = true;
        $scope.wantQr = false;

        // variables for vcard/qr generator
        $scope.qrFName = "";
        $scope.qrLName = "";
        $scope.qrEmail = "";
        $scope.qrPhone = "";
        $scope.qrCompany = "";
        $scope.qrStreet = "";
        $scope.qrZip = "";
        $scope.qrCity = "";
        $scope.qrCountry = "";
        $scope.qrLevel = "L";
        $scope.qrColour = "Black";
        $scope.qrLogo = "";

        // FEEDBACK MESSAGES

        // feedback handling variables
        $scope.error = false;
        $scope.success = false;
        $scope.successMsg = "";
        $scope.errorMsg = "";
        $scope.safe = true;

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

        $scope.hideSafe = function () {
            $scope.safe = true;
        };

        // show the success mensage
        var showSuccess = function (message) {
            $scope.successMsg = message.uri;
            $scope.success = true;
            $scope.safe = message.safe;

            if($scope.wantQr.toString() == 'true'){
                $scope.qr = "data:image/png;base64," + message.qrCode;
            }
            $scope.avaiableQR = true;
        };

        // hide the success mensage
        $scope.hideSuccess = function () {
            $scope.success = false;
            $scope.successMsg = "";
        };

        // hide the generated Qr
        $scope.hideImage = function () {
            $scope.avaiableQR = false;
            $scope.qr = "";
        };

        $scope.shortURL = function () {
            urlShortener.checkRegion(function (resultRegion){
                $scope.regionAvaiable = resultRegion;
                if ($scope.regionAvaiable == 'true'){
                    if ($scope.wantQr.toString() == 'false') {
                        var url = {
                            url: $scope.url,
                            safe: $scope.safe,
                            wantQr: 'false'
                        };

                        urlShortener.shortURL(url, showSuccess, showError);
                    }else{
                        var url = {
                            url: $scope.url,
                            safe: $scope.safe,
                            wantQr: 'true',
                            fName:  "" + $scope.qrFName,
                            lName: "" + $scope.qrLName,
                            Email:  "" + $scope.qrEmail,
                            Phone: "" + $scope.qrPhone,
                            Company: "" + $scope.qrCompany,
                            Street: "" + $scope.qrStreet,
                            Zip: "" + $scope.qrZip,
                            City: "" + $scope.qrCity,
                            Country: "" + $scope.qrCountry,
                            Level: "" + $scope.qrLevel,
                            Colour: "" + $scope.qrColour,
                            Logo: "" + $scope.qrLogo
                        };

                        urlShortener.shortURL(url, showSuccess, showError);
                    }


                }
            });
        };

    }]);
