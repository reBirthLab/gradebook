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

function pad(num, size) {
            var s = num + "";
            while (s.length < size)
                s = "0" + s;
            return s;
        };

controllers.controller('MainCtrl', function ($scope, $mdSidenav, $location, AuthenticationService, UserGradebooks) {

    $scope.index = 0;
    $scope.toggleSidenav = function (menuId) {
        $mdSidenav(menuId).toggle();
    };
    
    $scope.logout = function (){
        AuthenticationService.ClearCredentials();
        $location.path('/login');
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
controllers.controller('GradebookTasksCtrl', function ($scope, $state, $stateParams, $mdDialog, $mdToast, GradebookTasks) {
    $scope.groupGrades = GradebookTasks.query({
        groupId: $stateParams.groupId,
        semesterId: $stateParams.semesterId,
        gradebookId: $stateParams.gradebookId
    });
    
    $scope.calcTaskDates = function (date, taskLength){
        var startDate = new Date(date);
        startDate.setDate(startDate.getDate() - startDate.getDay() + 1);
        
        var endDate = new Date(startDate);
        endDate.setDate(endDate.getDate() + (taskLength * 7) - 1);
        
        return pad(startDate.getDate(), 2) + '/' + pad(startDate.getMonth(), 2) + ' - ' + 
                pad(endDate.getDate(), 2) + '/' + pad(endDate.getMonth(), 2);
    }

    $scope.showAddTaskForm = function (ev) {
        $mdDialog.show({
            controller: 'AddTaskDialogController',
            templateUrl: 'views/partitials/dialog.task-form.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            locals: {
                gradebookId: $stateParams.gradebookId
            }
        }).then(function () {
            $state.go($state.current, {}, {reload: true});
            showSuccessToast();
        });
    };
    
     var showSuccessToast = function () {
        $mdToast.show(
                $mdToast.simple()
                .content('New task was successfully added to gradebook!')
                .position("top right")
                .hideDelay(3000)
                );
    };
    
     $scope.showTaskDetails = function (ev, scope) {
        $mdDialog.show({
            controller: 'TaskDetailsDialogController',
            templateUrl: 'views/partitials/dialog.task-details.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            locals: {
                taskId: scope.value[0].taskId
            }
        });
    };
});

controllers.controller('AddTaskDialogController', function ($scope, $mdDialog, $mdToast, Task, gradebookId) {
    
    $scope.submit = function () {
        $scope.dataLoading = true;
        
        var newTask = new Task($scope.task);
        newTask.gradebookId = parseInt(gradebookId);
        newTask.$save({
        }, function () {
            $mdDialog.hide();
        }, function () {
            showErrorToast();
            $scope.dataLoading = false;
        });
    };
    
    var showErrorToast = function () {
        $mdToast.show(
                $mdToast.simple()
                .content('Something went wrong! Please try again!')
                .position("top right")
                .hideDelay(3000)
                );
    };

    $scope.cancel = function () {
        $mdDialog.cancel();
    };
});

controllers.controller('TaskDetailsDialogController', function ($scope, $mdDialog, taskId, Task) {
    $scope.task = Task.get({taskId: taskId}, function (task) {
        
        var startDate = new Date(task.startDate);
        startDate.setDate(startDate.getDate() - startDate.getDay() + 1);
        $scope.startDate = pad(startDate.getDate(), 2) + '/' +
                pad(startDate.getMonth(), 2) + '/' +
                startDate.getFullYear();

        var endDate = new Date(startDate);
        endDate.setDate(endDate.getDate() + (task.taskLength * 7) - 1);
        $scope.endDate = pad(endDate.getDate(), 2) + '/' +
                pad(endDate.getMonth(), 2) + '/' +
                endDate.getFullYear();
        
        var onCourseDays = [];
        if (task.onCourseMon) onCourseDays.push("Monday");
        if (task.onCourseTue) onCourseDays.push("Tuesday");
        if (task.onCourseWed) onCourseDays.push("Wednesday");
        if (task.onCourseThu) onCourseDays.push("Thursday");
        if (task.onCourseFri) onCourseDays.push("Friday");
        
        $scope.onCourseDays = onCourseDays;
    });

    $scope.hide = function () {
        $mdDialog.hide();
    };

    $scope.cancel = function () {
        $mdDialog.cancel();
    };
});


controllers.controller('GradebookAttendanceCtrl', function ($scope, $state, $stateParams, Gradebook) {
    $scope.groupGrades = Gradebook.query({
        groupId: $stateParams.groupId,
        semesterId: $stateParams.semesterId,
        gradebookId: $stateParams.gradebookId
    });
});
