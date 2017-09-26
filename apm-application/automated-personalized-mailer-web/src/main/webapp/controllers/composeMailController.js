//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-26 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

routerApp.controller('composeMailController', function($scope, $http, $timeout) {
    // create a blank object to handle form data.
    $scope.maildata = {};

    $scope.isDisabled = false;

    $scope.sendForm = function() {
        $scope.isDisabled = true;
        $scope.message1 = "Preparing group mail.";
        $http({
            method  : 'POST',
            url     : 'rest/mail/message/',
            data    : $scope.maildata,
            headers : {'Content-Type': 'application/json'}
        })
            .success(function(data) {
                if (data.errors) {
                    $scope.errorCategory = data.errors.category;
                    $scope.errorSender = data.errors.sender;
                    $scope.errorSubject = data.errors.subject;
                    $scope.errorMailMessage = data.errors.message;
                } else {
                    $scope.message1 = "";
                    $scope.message2 = "Group mail sent!";
                    $scope.isDisabled = false;
                    $timeout(function() {
                        $scope.message2 = "";
                    }, 1800);
                }
            });
    };

    var url = 'rest/recipient/findall/';
    $http.get(url).success( function(response) {
        $scope.recipients = response;
    });
});

