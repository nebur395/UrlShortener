angular.module('urlShortener')

    .controller('starterCtrl', ['$scope', '$state', 'urlShortener', 'qrGenerator', function ($scope, $state, urlShortener, qrGenerator, checkRegion) {

        $scope.url = "";    // initial url input form
        $scope.qr = "";
        $scope.avaiableQR = false;
        $scope.wantVcard = false;
        $scope.regionAvaiable = true;

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

        // show the vcard panel
        $scope.showVcard = function () {
            $scope.qr = "https://66.media.tumblr.com/44e89309ea155b3be1213e64cc872f2a/tumblr_n0wqfhEW9K1sghdp8o1_400.gif";
            $scope.wantVcard = true;
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

        // hide the Vcard panel
        $scope.hideVcard = function () {
            $scope.wantVcard = false;
        };

        $scope.shortURL = function () {
            urlShortener.checkRegion(function (resultRegion){
                $scope.regionAvaiable = resultRegion;
                if ($scope.regionAvaiable == 'true'){
                    var url = {
                        url: $scope.url
                    };
                    urlShortener.shortURL(url, showSuccess, showError);
                }
            });
        };

        $scope.vCardForm = function ()  {
            if ($scope.generateQRandVcard) {
                $scope.getQR();
            } else {
                $scope.download();
            }
        };

        // read values from the textFields and generate Qr
        $scope.getQR = function () {
            qrGenerator.generateQR($scope.successMsg,
                $scope.qrFName,
                $scope.qrLName,
                $scope.qrEmail,
                $scope.qrPhone,
                $scope.qrCompany,
                $scope.qrStreet,
                $scope.qrZip,
                $scope.qrCity,
                $scope.qrCountry,
                $scope.qrLevel, function (urlQR) {
                    $scope.qr = urlQR;
                    $scope.avaiableQR = true;
            });
        };

        $scope.download = function () {
            if ($scope.avaiableQR == true) {
                var link = document.createElement('a');
                link.href = $scope.qr;
                link.download = 'qrCode.jpg';
                document.body.appendChild(link);
                link.click();
            }
        };

    }]);
