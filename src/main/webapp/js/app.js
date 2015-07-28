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

angular.module('GradebookApp', [
    'ui.router', 
    'ngResource', 
    'angular.filter', 
    'GradebookControllers']);

angular.module('GradebookApp')
        .config(function ($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise("/");

            $stateProvider
                    .state('login', {
                        url: '/login',
                        templateUrl: 'views/login.html',
                        
                        
                    })
                    .state('gradebook', {
                        url: '/gradebook',
                        templateUrl: 'views/gradebook.html',
                        controller: 'StudentsGradesCtrl'
                    });
        });
