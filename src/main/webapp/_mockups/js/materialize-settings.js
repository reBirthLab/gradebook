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

$(document).ready(function () {
    // Modal initialization
    $('.modal-trigger').leanModal();

    // Sidebar collapse button initialization
    $(".button-collapse").sideNav();

    // Arrordion initialization
    $('#main-menu').tendina();

    // Dropdown initialization
    $('select').material_select();

    // Tabs initialization
    $('ul.tabs').tabs();

    // Date picker initialization
    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        //selectYears: 15 // Creates a dropdown of 15 years to control year
    });
});
