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
        .state('mailinglist', {
            url: '/mailinglist',
            templateUrl: 'views/mailinglist.html',
            controller: 'mailingListController'
        })
        .state('composemail', {
            url: '/composemail',
            templateUrl: 'views/composemail.html',
            controller: 'composeMailController'
        })
        .state('importmailing', {
            url: '/importmailing',
            templateUrl: 'views/importmailing.html',
        })
});