//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-24 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

routerApp.controller('addMailController', function($scope, $http, $timeout) {
    $scope.recipient = {};
    $scope.recipients = {};
    $scope.mailAdded = false;

    $scope.submitForm = function() {
        $http({
            method  : 'POST',
            url     : 'rest/recipient/create/',
            headers : {'Content-Type':'application/json'},
            data    : $scope.recipient
        })
            .then(function(data) {
                if (data.errors) {
                    $scope.errorName = data.errors.name;
                    $scope.errorMail = data.errors.mail;
                    $scope.errorCategory = data.errors.category;
                } else {
                    $scope.message = data.message;
                }
            });

        $scope.mailAdded=true;
        $timeout(function() {
            $scope.mailAdded = false;
        }, 430);
        $scope.recipient={};
    };
});


