var dotApp = angular.module('dotApp', ['ui.router', 'ngMaterial', 'uiGmapgoogle-maps', 'ui.bootstrap']);


dotApp.config(function ($stateProvider, $urlRouterProvider, uiGmapGoogleMapApiProvider) {
    //
    // For any unmatched url, redirect to /chooseBranch -> first screen
    $urlRouterProvider.otherwise("/chooseBranch");
    //
    // Now set up the states
    $stateProvider
        .state('chooseBranchState', {
            url: "/chooseBranch",
            views: {
                "mainView": {
                    templateUrl: "/resources/templates/chooseBranch.html",
                    controller: 'branchController'
                }
            }
        })
        .state('resumeWorkState', {
            url: "/resumeWork",
            views: {
                "toolBarView": {
                    templateUrl: "/resources/templates/toolBar.html",
                    controller: 'toolBarController'
                },
                "leftView": {
                    templateUrl: "/resources/templates/position/positionGoeuroFilter.html",
                    controller: 'positionGoeuroFilterController'
                },
                "middleView": {
                    templateUrl: "/resources/templates/position/positionMapView.html",
                    controller: 'positionMapController'
                },
                "rightView": {
                    templateUrl: "/resources/templates/position/positionEdit.html",
                    controller: 'positionEditController'
                }
            }
        });

    uiGmapGoogleMapApiProvider.configure({
        key: 'AIzaSyDOWUrh64_e3szJY8DQSjztuddgGQ10HB8',
        v: '3.20', //defaults to latest 3.X anyhow
        libraries: 'weather,geometry,visualization'
    });
});

dotApp.run(function ($rootScope, $state, $mdDialog, $uibModal) {

    $rootScope.editBranch = false;
    $rootScope.expandEdit = false;

    /*UTIL METHODS*/
    $rootScope.showAlert = function (message, title) {
        $mdDialog.show(
            $mdDialog.alert()
                .clickOutsideToClose(true)
                .title(title)
                .textContent(message)
                .ok('OK !')
        );
    }

    $rootScope.showConfirm = function (title, message, doOnConfirm) {
        var confirm = $mdDialog.confirm()
            .title(title)
            .textContent(message)
            .ok('YES')
            .cancel('NO');
        $mdDialog.show(confirm).then(doOnConfirm, function () {
            //do nothing on cancel
        });
    }

    $rootScope.showLoading = function () {
        $mdDialog.show({
            templateUrl: '/resources/templates/loading.html',
            parent: angular.element(document.body),
            clickOutsideToClose: false,
            fullscreen: false
        })
    }

    $rootScope.hideLoading = function () {
        $mdDialog.cancel();
    }

    $rootScope.onServerError = function () {
        $rootScope.showAlert('Service Unavailable , Please contact your administrator.', 'Error !');
    }


    $rootScope.openSingleEditWindow = function (editItem, templateUrl, scope, controller, doOnSave) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: templateUrl,
            controller: controller,
            scope: scope,
            keyboard: false,
            backdrop: false,
            resolve: {
                editItem: function () {
                    return editItem;
                }
            }
        });

        modalInstance.result.then(doOnSave);

    };

    /**
     * Reloads the original state on page refresh
     */
    $rootScope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {

            if (toState.url != "/chooseBranch" && (typeof $rootScope.selectedBranch == 'undefined' || $rootScope.selectedBranch == null)) {
                event.preventDefault();
                $state.go('chooseBranchState');
            }
        })
});