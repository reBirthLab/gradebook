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
    'GradebookServices'
]);

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
        controller: 'GradebookTasksCtrl',
        onEnter: function ($rootScope) {
            $rootScope.$broadcast('selectTab', 0);
        }
    });

    $stateProvider.state('gradebook.attendance', {
        url: '/attendance',
        templateUrl: 'views/gradebook.attendance.html',
        controller: 'GradebookAttendanceCtrl',
        onEnter: function ($rootScope) {
            $rootScope.$broadcast('selectTab', 1);
        }
    });

    $stateProvider.state('faculties', {
        url: '/faculties',
        templateUrl: 'views/faculties.html',
        controller: 'FacultyCtrl'
    });

    $stateProvider.state('departments', {
        url: '/departments',
        templateUrl: 'views/departments.html',
        controller: 'DepartmentCtrl'
    });

    $stateProvider.state('semesters', {
        url: '/semesters',
        templateUrl: 'views/semesters.html',
        controller: 'SemesterCtrl'
    });

    $stateProvider.state('administrators', {
        url: '/administrators',
        templateUrl: 'views/administrators.html',
        controller: 'AdministratorCtrl'
    });

    $stateProvider.state('lecturers', {
        url: '/lecturers',
        templateUrl: 'views/lecturers.html',
        controller: 'LecturerCtrl'
    });

    $stateProvider.state('students', {
        url: '/students',
        templateUrl: 'views/students.html',
        controller: 'StudentCtrl'
    });

    $stateProvider.state('groups', {
        url: '/groups',
        templateUrl: 'views/groups.html',
        controller: 'GroupCtrl'
    });

    $stateProvider.state('gradebooks', {
        url: '/gradebooks',
        templateUrl: 'views/gradebooks.html',
        controller: 'GradebookCtrl'
    });

    $stateProvider.state('tasks', {
        url: '/tasks',
        templateUrl: 'views/tasks.html',
        controller: 'TaskCtrl'
    });
});

app.config(['$resourceProvider', function ($resourceProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;
}]);

app.run(function ($rootScope, $location, $cookieStore, $http, $window) {
    // keep user logged in after page refresh
    $rootScope.globals = $cookieStore.get('globals') || {};

    if ($rootScope.globals.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Bearer ' +
            $rootScope.globals.currentUser.token;
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        // redirect to login page if not logged in
        if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
            $location.path('/login');
            return;
        }
        if ($rootScope.globals.currentUser && isTokenExpired($rootScope.globals.currentUser.token)) {
            $cookieStore.remove('globals');
            $location.path('/login');
        }
    });

    function isTokenExpired(token) {
        var base64String = token.split('.')[1].replace('-', '+').replace('_', '/');
        var exp = JSON.parse($window.atob(base64String)).exp;
        return new Date().getTime() / 1000 > exp;
    }
});