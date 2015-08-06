var ngMaterialMenu = angular.module("ngMenuSidenav", []);
ngMaterialMenu.factory("serviceContent", function () {
    var a = [];
    return {
        register: function (e, b, d) {
            var c = {id: e, show: b, state: d};
            a.push(c);
        }, hideAll: function () {
            for (var b = 0; b < a.length; b++) {
                if (a[b].state === true) {
                    a[b].show(false);
                    a[b].state = false;
                }
            }
        }, generateID: function () {
            return a.length;
        }, getByID: function (c) {
            for (var b = 0; b < a.length; b++) {
                if (a[b].id === c) {
                    return a[b];
                }
            }
            return undefined;
        }
    };
});
ngMaterialMenu.directive("mdMenuSidenav", ["serviceContent", function (a) {
    return {
        transclude: true, link: function () {
        }, template: "<div flex layout='row'><div flex ng-transclude=''></div></div>"
    };
}]);
ngMaterialMenu.directive("mdToogleMenu", ["serviceContent", function (a) {
    return {
        link: function (c, b) {
            var d = a.generateID();
            b.on("click", function () {
                var e = a.getByID(d);
                if (e) {
                    if (e.state) {
                        //a.hideAll();
                        e.show(false);
                        e.state = false;
                    } else {
                       // a.hideAll();
                        e.show(true);
                        e.state = true;
                    }
                }
            });
        }
    };
}]);
ngMaterialMenu.directive("mdMenuSidenavItem", [function () {
    return {
        transclude: true, link: function (b, c, a) {
        }, template: function (c, a) {
            var b = "<div flex layout='column' ng-transclude=''></div>";
            return b;
        }
    };
}]);
ngMaterialMenu.directive("mdMenuSidenavContent", ["serviceContent", function (a) {
    return {
        transclude: true, link: function (d, e, b) {
            var c = function (f) {
                if (!f) {
                    e.css("max-height", "0px");
                } else {
                    e.css("max-height", e[0].scrollHeight + "px");
                }
            };
            c(false);
            a.register(a.generateID(), c, false);
        }, template: function () {
            return "<div layout='column' ng-transclude></div>";
        }
    };
}]);
ngMaterialMenu.directive("mdMenuSidenavTitle", function () {
    return {
        transclude: true, link: function (b, a) {
        }, template: function (c, a) {
            var b = "<div flex layout='row' ng-transclude></div>";
            return b;
        }
    };
});
ngMaterialMenu.directive("mdMenuSidenavSubitem", function () {
    return {
        transclude: true, link: function (b, a) {
        }, template: function (b, a) {
            return "<div layout='row'><div flex ng-transclude></div></div>";
        }
    };
});