//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-25 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

routerApp.controller('mailingListController', function($scope, $http) {
    var url = 'rest/recipient/findall/';
    $http.get(url).success( function(response) {
        $scope.recipients = response;
    });
});