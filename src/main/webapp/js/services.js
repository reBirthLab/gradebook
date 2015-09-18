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

var services = angular.module('GradebookServices', []);

services.factory('UserGradebooks', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/gradebooks');
});

services.factory('Gradebook', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/gradebooks/:gradebookId');
});

services.factory('Groups', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/groups');
});

services.factory('Semesters', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/semesters');
});

services.factory('Lecturers', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/lecturers');
});

services.factory('GradebookTasks', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/groups/:groupId/semesters/:semesterId/gradebooks/:gradebookId/tasks');
});

services.factory('GradebookAttendance', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/groups/:groupId/semesters/:semesterId/gradebooks/:gradebookId/attendance');
});

services.factory('Task', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/tasks/:taskId', null, {
        update: { method: 'PUT' }
    });
});

services.factory('Grade', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/students/:studentId/tasks/:taskId/grade', null, {
        update: { method: 'PUT' }
    });
});

services.factory('Attendance', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/attendances/:attendanceId', null, {
        update: { method: 'PUT' }
    });
});

services.factory('Faculty', function ($resource) {
    return $resource('http://gradebook-anastasius.rhcloud.com/api/v1/faculties/:facultyId', null, {
        update: { method: 'PUT' }
    });
});

services.factory('MessageService', function ($mdToast) {
    return {
        showSuccessToast: function (message) {
            $mdToast.show(
                    $mdToast.simple()
                    .content(message)
                    .position("top right")
                    .hideDelay(3000)
                    );
        },
        showErrorToast: function () {
            $mdToast.show(
                    $mdToast.simple()
                    .content('Something went wrong! Please try again!')
                    .position("top right")
                    .hideDelay(3000)
                    );
        }
    };
});

services.factory('AuthenticationService', function (Base64, $http, $cookieStore, $rootScope, $timeout) {
    var service = {};

    service.Login = function (username, password, callback) {

        var authdata = Base64.encode(username + ':' + password);

        $http.get('http://gradebook-anastasius.rhcloud.com/api/v1/users/check', {
            headers: {'Authorization': 'Basic ' + authdata}
        }).then(function (response) {
            callback(response);
        }, function (response) {
            callback(response);
        });
    };

    service.SetCredentials = function (username, password, role) {
        var authdata = Base64.encode(username + ':' + password);

        $rootScope.globals = {
            currentUser: {
                username: username,
                authdata: authdata,
                userRole: role
            }
        };

        $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata;
        $cookieStore.put('globals', $rootScope.globals);
    };

    service.ClearCredentials = function () {
        $rootScope.globals = {};
        $cookieStore.remove('globals');
        $http.defaults.headers.common.Authorization = 'Basic ';
    };

    return service;
});

services.factory('Base64', function () {

    var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';

    return {
        encode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            do {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);

                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;

                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }

                output = output +
                        keyStr.charAt(enc1) +
                        keyStr.charAt(enc2) +
                        keyStr.charAt(enc3) +
                        keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            } while (i < input.length);

            return output;
        },
        decode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
            var base64test = /[^A-Za-z0-9\+\/\=]/g;
            if (base64test.exec(input)) {
                window.alert("There were invalid base64 characters in the input text.\n" +
                        "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                        "Expect errors in decoding.");
            }
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

            do {
                enc1 = keyStr.indexOf(input.charAt(i++));
                enc2 = keyStr.indexOf(input.charAt(i++));
                enc3 = keyStr.indexOf(input.charAt(i++));
                enc4 = keyStr.indexOf(input.charAt(i++));

                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;

                output = output + String.fromCharCode(chr1);

                if (enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }

                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";

            } while (i < input.length);

            return output;
        }
    };
});