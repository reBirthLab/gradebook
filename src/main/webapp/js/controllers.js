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

var controllers = angular.module('GradebookControllers', []);

controllers.controller('MainCtrl', function ($scope, $mdSidenav, $parse, UserGradebooks) {

    $scope.index = 0;
    $scope.toggleSidenav = function (menuId) {
        $mdSidenav(menuId).toggle();
    };

    var userGradebooks = UserGradebooks.query(function () {
        $scope.user = {
            firstName: userGradebooks[0].firstName,
            lastName: userGradebooks[0].lastName
        };
    });

    $scope.userGradebooks = userGradebooks;

    function getByGradebookId(arr, id) {
        for (var d = 0, len = arr.length; d < len; d += 1) {
            if (arr[d].gradebookId === id) {
                return arr[d];
            }
        }
    }

    $scope.get = function (gradebookId) {
        var currentGradebook = getByGradebookId(userGradebooks, gradebookId);
        $scope.gradebook = {
            group: currentGradebook.number + " |",
            subject: currentGradebook.subject + " |",
            year: currentGradebook.academicYear,
            semester: currentGradebook.name
        };
    };
});

controllers.controller('LoginCtrl', function ($scope, $mdToast, $location, AuthenticationService) {
    // reset login status
    AuthenticationService.ClearCredentials();

    $scope.login = function () {
        $scope.dataLoading = true;
        AuthenticationService.Login($scope.username, $scope.password, function (response) {
            if (response.status === 200) {
                AuthenticationService.SetCredentials($scope.username, $scope.password);
                $location.path('/');
            } else {
                showErrorToast();
                $scope.dataLoading = false;
            }
        });
    };
    var showErrorToast = function () {
        $mdToast.show(
                $mdToast.simple()
                .content('Wrong username or password. Please try again!')
                .position("top right")
                .hideDelay(3000)
                );
        $scope.password = "";
    };
});

// TEMPORARY
controllers.controller('GradebookTasksCtrl', function ($scope, $state, $stateParams, Gradebook) {
    $scope.groupGrades = Gradebook.query({
        groupId: $stateParams.groupId,
        semesterId: $stateParams.semesterId,
        gradebookId: $stateParams.gradebookId
    });
});

controllers.controller('GradebookAttendanceCtrl', function ($scope, $state, $stateParams, Gradebook) {
    $scope.groupGrades = Gradebook.query({
        groupId: $stateParams.groupId,
        semesterId: $stateParams.semesterId,
        gradebookId: $stateParams.gradebookId
    });
});
