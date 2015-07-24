/* 
 * Copyright (C) 2015 Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

var gradebookApp = angular.module('gradebookApp', ['ui.router']);

gradebookApp.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise("/");

    $stateProvider

            .state('login', {
                url: "/",
                templateUrl: "views/login.html"
            })
            .state('gradebook-lecturer', {
                url: "/gradebook",
                templateUrl: "views/gradebook-lecturer.html"
            });
});

//var gradebookApp = angular.module('gradebookApp', ['ngRoute']);
//
//gradebookApp.config(['$routeProvider', function ($routeProvider) {
//
//        $routeProvider
//                .when('/', {
//                    templateUrl: 'views/test.html'
//                })
//                .when('/gradebook', {
//                    templateUrl: 'views/gradebook-lecturer.html'
//                })
//                .otherwise({redirectTo: '/'});
//
//    }]);
