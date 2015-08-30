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
    $urlRouterProvider.when('/api/groups/:groupId/semesters/:semesterId/gradebooks/:gradebookId', function ($rootScope, $location) {
        var userRole = $rootScope.globals.currentUser.userRole;
        if (userRole === 'lecturer') {
            return $location.url() + '/tasks';
        } else if (userRole === 'student') {
            return $location.url() + '/student';
        }
    });

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
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
    });

    $stateProvider.state('gradebook', {
        url: '/api/groups/:groupId/semesters/:semesterId/gradebooks/:gradebookId',
        abstract: true,
        templateUrl: 'views/gradebook.html'
    });
                
    $stateProvider.state('gradebook-student', {
        abstract: true,
        templateUrl: 'views/gradebook-student.html'     
    });
    
    $stateProvider.state('gradebook-student.views', {
        url: '/api/groups/:groupId/semesters/:semesterId/gradebooks/:gradebookId/student',
        views: {
            'tasks': {
                templateUrl: 'views/gradebook.tasks.html',
                controller: 'GradebookTasksCtrl'
            },
            'attendance': {
                templateUrl: 'views/gradebook.attendance.html',
                controller: 'GradebookAttendanceCtrl'
            }
        }
    });
    
    $stateProvider.state('gradebook.tasks', {
        url: '/tasks',
        templateUrl: 'views/gradebook.tasks.html',
        controller: 'GradebookTasksCtrl'
    });

    $stateProvider.state('gradebook.attendance', {
        url: '/attendance',
        templateUrl: 'views/gradebook.attendance.html',
        controller: 'GradebookAttendanceCtrl'
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
