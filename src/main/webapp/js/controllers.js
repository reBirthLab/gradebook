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
    
    $scope.closeSidenav = function (menuId) {
        $mdSidenav(menuId).close();
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
controllers.controller('GradebookTasksCtrl', function ($scope, $state, $stateParams, $mdDialog, MessageService, GradebookTasks) {
    $scope.groupGrades = GradebookTasks.query({
        groupId: $stateParams.groupId,
        semesterId: $stateParams.semesterId,
        gradebookId: $stateParams.gradebookId
    });
    
    $scope.orderByName = function(student) {
      return student[0].firstName;
    };
    
    $scope.calcTaskDates = function (date, taskLength){
        var startDate = new Date(date);
        startDate.setDate(startDate.getDate() - startDate.getDay() + 1);
        
        var endDate = new Date(startDate);
        endDate.setDate(endDate.getDate() + (taskLength * 7) - 1);
        
        return pad(startDate.getDate(), 2) + '/' + pad(startDate.getMonth() + 1, 2) + ' - ' + 
                pad(endDate.getDate(), 2) + '/' + pad(endDate.getMonth() + 1, 2);
    };

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
            var message = 'New task was successfully added to gradebook!';
            MessageService.showSuccessToast(message);
        });
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
    
    $scope.showEditGradeDialog = function (ev, scope) {
        $mdDialog.show({
            controller: 'EditGradeDialogController',
            templateUrl: 'views/partitials/dialog.grade.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            locals: {
                task: scope.task
            }
        }).then(function () {
            $state.go($state.current, {}, {reload: true});
            var message = 'The Grade was successfully updated!';
            MessageService.showSuccessToast(message);
        });
    }; 
});

controllers.controller('AddTaskDialogController', function ($scope, $mdDialog, MessageService, Task, gradebookId) {
    $scope.mode = 'Add';
    $scope.submitButton = 'Submit';
    
    $scope.submit = function () {
        $scope.dataLoading = true;
        
        var newTask = new Task($scope.task);
        newTask.gradebookId = parseInt(gradebookId);
        newTask.$save({
        }, function () {
            $mdDialog.hide();
        }, function () {
            MessageService.showErrorToast();
            $scope.dataLoading = false;
        });
    };

    $scope.cancel = function () {
        $mdDialog.cancel();
    };
});

controllers.controller('TaskDetailsDialogController', function ($scope, $state, $mdDialog, MessageService, taskId, Task) {
    $scope.task = Task.get({taskId: taskId}, function (task) {
        
        var startDate = new Date(task.startDate);
        startDate.setDate(startDate.getDate() - startDate.getDay() + 1);
        $scope.startDate = pad(startDate.getDate(), 2) + '/' +
                pad(startDate.getMonth() + 1, 2) + '/' +
                startDate.getFullYear();

        var endDate = new Date(startDate);
        endDate.setDate(endDate.getDate() + (task.taskLength * 7) - 1);
        $scope.endDate = pad(endDate.getDate(), 2) + '/' +
                pad(endDate.getMonth() + 1, 2) + '/' +
                endDate.getFullYear();
        
        var onCourseDays = [];
        if (task.onCourseMon) onCourseDays.push("Monday");
        if (task.onCourseTue) onCourseDays.push("Tuesday");
        if (task.onCourseWed) onCourseDays.push("Wednesday");
        if (task.onCourseThu) onCourseDays.push("Thursday");
        if (task.onCourseFri) onCourseDays.push("Friday");
        
        $scope.onCourseDays = onCourseDays;
    });
    
    $scope.showEditTaskForm = function (ev) {
        $mdDialog.show({
            controller: 'EditTaskDialogController',
            templateUrl: 'views/partitials/dialog.task-form.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            locals: {
                task: $scope.task
            }
        }).then(function () {
            $state.go($state.current, {}, {reload: true});
            var message = 'The task was successfully updated!';
            MessageService.showSuccessToast(message);
        });
    };

    $scope.hide = function () {
        $mdDialog.hide();
    };

    $scope.cancel = function () {
        $mdDialog.cancel();
    };
});

controllers.controller('EditTaskDialogController', function ($scope, $mdDialog, MessageService, task, Task) {
    $scope.mode = 'Edit';
    $scope.submitButton = 'Update';
    
    $scope.task = task;
    $scope.task.startDate = new Date (task.startDate);
    
    $scope.submit = function () {
        $scope.dataLoading = true;

        Task.update({
           taskId: task.taskId
        }, $scope.task,
        function () {
            $mdDialog.hide();
        }, function () {
            MessageService.showErrorToast();
            $scope.dataLoading = false;
        });
    };

    $scope.cancel = function () {
        $mdDialog.cancel();
    };
});

controllers.controller('EditGradeDialogController', function ($scope, $mdDialog, MessageService, task, Grade) {
    
    $scope.task = task;
    
    var studentGrade = {};
    studentGrade.grade = task.grade;
    
    $scope.studentGrade = studentGrade;
    
    $scope.update = function () {
        $scope.dataLoading = true;

        Grade.update({
           studentId: task.studentId,
           taskId: task.taskId
        }, $scope.studentGrade,
        function () {
            $mdDialog.hide();
        }, function () {
            MessageService.showErrorToast();
            $scope.dataLoading = false;
        });
    };

    $scope.cancel = function () {
        $mdDialog.cancel();
    };
});


controllers.controller('GradebookAttendanceCtrl', function ($scope, $state, $stateParams, $mdDialog, GradebookAttendance, MessageService) {
    $scope.groupAttendance = GradebookAttendance.query({
        groupId: $stateParams.groupId,
        semesterId: $stateParams.semesterId,
        gradebookId: $stateParams.gradebookId
    });
    
    $scope.orderByName = function(student) {
      return student[0].firstName;
    };
    
    $scope.getDayOfWeek = function(d){
        var date = new Date(d);
        
        var weekday = new Array(7);
        weekday[0] = "Sun";
        weekday[1] = "Mon";
        weekday[2] = "Tue";
        weekday[3] = "Wed";
        weekday[4] = "Thu";
        weekday[5] = "Fri";
        weekday[6] = "Sat";
        
        return weekday[date.getUTCDay()];
    };
    
    $scope.formatClassDate = function (d){
        var date = new Date(d);         
        return pad(date.getUTCDate(), 2) + '/' + pad(date.getUTCMonth() + 1, 2);
    };
    
    $scope.attendanceStatus = function (scope) {
        var attendance = scope.attendance;
        if (attendance.present) {
            scope.present = true;
            return 'check_box';
        } else if (attendance.absent) {
            scope.absent = true;
            return 'indeterminate_check_box';
        } else if (attendance.absentWithReason) {
            scope.absentWithReason = true;
            return 'indeterminate_check_box';
        } else {
            return 'check_box_outline_blank';
        }
    };
    
    $scope.showEditAttendanceDialog = function (ev, scope) {
        $mdDialog.show({
            controller: 'EditAttendanceDialogController',
            templateUrl: 'views/partitials/dialog.attendance.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            locals: {
                attendance: scope.attendance
            }
        }).then(function () {
            $state.go($state.current, {}, {reload: true});
            var message = 'The Attendance was successfully updated!';
            MessageService.showSuccessToast(message);
        });
    };
});

controllers.controller('EditAttendanceDialogController', function ($scope, $mdDialog, MessageService, attendance, Attendance) {
    
    $scope.attendance = attendance;
    
    var classDate = new Date(attendance.classDate);
    $scope.classDate = pad(classDate.getDate(), 2) + '/' +
            pad(classDate.getMonth() + 1, 2) + '/' +
            classDate.getFullYear();
    
    if (attendance.present) {
        $scope.status = 'present';
    } else if (attendance.absent) {
        $scope.status = 'absent';
    } else if (attendance.absentWithReason) {
        $scope.status = 'absent-with-reason';
    };
   
    $scope.update = function () {
        $scope.dataLoading = true;

        var attendance = {};
        attendance.attendanceId = $scope.attendance.attendanceId;
        attendance.studentId = $scope.attendance.studentId;
        attendance.taskId = $scope.attendance.taskId;
        attendance.classDate = $scope.attendance.classDate;
        attendance.present = false;
        attendance.absent = false;
        attendance.absentWithReason = false;
        
        if($scope.status === 'present') attendance.present = true;
        if($scope.status === 'absent') attendance.absent = true;
        if($scope.status === 'absent-with-reason') attendance.absentWithReason = true;
        
        Attendance.update({
           attendanceId: attendance.attendanceId,
        }, attendance,
        function () {
            $mdDialog.hide();
        }, function () {
            MessageService.showErrorToast();
            $scope.dataLoading = false;
        });
    };

    $scope.cancel = function () {
        $mdDialog.cancel();
    };
});