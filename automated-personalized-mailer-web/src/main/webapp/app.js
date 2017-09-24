//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-24 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

var routerApp = angular.module('routerApp', ['ui.router']);

routerApp.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/addmail');

    $stateProvider
        .state('addmail', {
            url: '/addmail',
            templateUrl: 'views/addmail.html',
            controller: 'addMailController'
        })
});



