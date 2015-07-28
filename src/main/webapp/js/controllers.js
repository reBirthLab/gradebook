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

angular.module('GradebookControllers', ['ngMaterial', 'ngResource'])

        .controller('SidenavCtrl', function ($scope, $mdSidenav)
        {
            $scope.toggleSidenav = function (menuId) {
                $mdSidenav(menuId).toggle();
            };
        })

// TEMPORARY
        .controller('StudentsGradesCtrl', function ($scope, $http, $location) {
            $http.get('http://gradebook-anastasius.rhcloud.com/api/groups/1/semesters/5/gradebooks/2/tasks').
                    success(function (data) {

                        $scope.studentGrades = angular.fromJson(data);
                    });
        })

        .controller('NavCtrl', function ($scope, $location) {

            if ($location.path() === '/login') {
                return $scope.notLogin = false;
            } else {
                return $scope.notLogin = true;
            }
            ;
        })

        .controller('LoginController', function () {
            
        });
