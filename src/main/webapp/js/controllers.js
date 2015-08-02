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

var controllers = angular.module('GradebookControllers', ['ngMaterial', 'ngResource']);

controllers.controller('MainCtrl', function ($rootScope, $scope, $mdSidenav) {

    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
        if (toState.name === 'login') {
            $rootScope.isLogin = true;
        } else {
            $rootScope.isLogin = false;
        }
    });

    $scope.toggleSidenav = function (menuId) {
        $mdSidenav(menuId).toggle();
    };
});

controllers.controller('LoginCtrl', function ($scope, $http, $rootScope, $location) {

//    var authenticate = function (credentials, callback) {
//
//        var headers = credentials ? {authorization: "Basic "
//                    + btoa(credentials.username + ":" + credentials.password)
//        } : {};
//
//        $http.get('/api/v1/gradebooks', {headers: headers}).success(function (data) {
//            if (data.name) {
//                $rootScope.authenticated = true;
//            } else {
//                $rootScope.authenticated = false;
//            }
//            callback && callback();
//        }).error(function () {
//            $rootScope.authenticated = false;
//            callback && callback();
//        });
//    };
//    authenticate();
//
//     $scope.credentials = {};
//  $scope.login = function() {
//      authenticate($scope.credentials, function() {
//        if ($rootScope.authenticated) {
//          $location.path("/");
//          $scope.error = false;
//        } else {
//          $location.path("/login");
//          $scope.error = true;
//        }
//      });
//  };
});

// TEMPORARY
controllers.controller('GroupGradesCtrl', function ($scope, $state, $stateParams, Gradebook) {
    $scope.groupGrades = Gradebook.query({
        groupId: $stateParams.groupId,
        semesterId: $stateParams.semesterId,
        gradebookId: $stateParams.gradebookId
    });

});
