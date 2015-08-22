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
'use strict';

var app = angular.module('GradebookApp', [
    'ngMaterial',
    'ngResource',
    'ngCookies',
    'ngMessages',
    'ui.router',
    'angular.filter',
    'vAccordion',
    'GradebookControllers',
    'GradebookServices']);

app.config(function ($httpProvider, $stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise("/login");
    $urlRouterProvider.when('/api/groups/:groupId/semesters/:semesterId/gradebooks/:gradebookId',
            '/api/groups/:groupId/semesters/:semesterId/gradebooks/:gradebookId/tasks');

    $stateProvider.state('login', {
        url: '/login',
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        onExit: function ($window, $rootScope) {
            $window.location.reload();
            $rootScope.isLogin = false;
        },
        onEnter: function ($rootScope) {
            $rootScope.isLogin = true;
        }
    });

    $stateProvider.state('main', {
        url: '/',
        //templateUrl: 'views/main.html',
        controller: 'MainCtrl'
    });

    $stateProvider.state('gradebook', {
        url: '/api/groups/:groupId/semesters/:semesterId/gradebooks/:gradebookId',
        abstract: true,
        templateUrl: 'views/gradebook.html',
    });

    $stateProvider.state('gradebook.tasks', {
        url: '/tasks',
        templateUrl: 'views/gradebook.tasks.html',
        controller: 'GradebookTasksCtrl'
    });

    $stateProvider.state('gradebook.attendance', {
        url: '/attendance',
        templateUrl: 'views/gradebook.attendance.html',
        controller: 'GradebookAttendanceCtrl',
    });
});

app.config(['$resourceProvider', function ($resourceProvider) {
        $resourceProvider.defaults.stripTrailingSlashes = false;
    }]);

app.run(function ($rootScope, $location, $cookieStore, $http) {
    // keep user logged in after page refresh
    $rootScope.globals = $cookieStore.get('globals') || {};

    if ($rootScope.globals.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic '
                + $rootScope.globals.currentUser.authdata;
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        // redirect to login page if not logged in
        if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
            $location.path('/login');
        }
    });
});

app.filter('orderObjectBy', function () {
    return function (items, field, reverse) {
        var filtered = [];
        angular.forEach(items, function (item) {
            filtered.push(item);
        });
        function index(obj, i) {
            return obj[i];
        }
        filtered.sort(function (a, b) {
            var comparator;
            var reducedA = field.split('.').reduce(index, a);
            var reducedB = field.split('.').reduce(index, b);
            if (reducedA === reducedB) {
                comparator = 0;
            } else {
                comparator = (reducedA > reducedB ? 1 : -1);
            }
            return comparator;
        });
        if (reverse) {
            filtered.reverse();
        }
        return filtered;
    };
});
