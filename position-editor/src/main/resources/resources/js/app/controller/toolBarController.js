/*Register Branch Controller*/
dotApp.controller('toolBarController', function ($scope, $rootScope, $state) {

    $rootScope.toggleExpandEdit = function () {
        $rootScope.expandEdit = !$rootScope.expandEdit;
    };

    $scope.changeToBranchesState = function () {
        $rootScope.editBranch = false;
        $state.go('chooseBranchState', {}, {reload: true});
    }

    $scope.$on('$viewContentLoaded', function () {
        $rootScope.expandEdit = false;

    });

    $scope.$on('$viewContentLoaded', function () {

    });
});