/*Register Branch Controller*/
dotApp.controller('branchController', function ($scope, $rootScope, $state, branchService) {

    $scope.getAllBranches = function () {
        branchService.getBranches().success(function (data) {
            $scope.branches = data.value;
        }).error(function (error) {
            $rootScope.onServerError();
        });
    }

    $scope.createBranch = function (branch) {
        branchService.createBranch(branch).success(function (data) {
            if (data.success) {
                $scope.createdBranch = data.value;
                //reload all branches
                $scope.getAllBranches();
            }
        }).error(function (error) {
            $rootScope.onServerError();
        });
    }

    $scope.deleteBranch = function (branch) {

        $rootScope.showConfirm('Are you sure you want to delete branch ' + branch.name + '?',
            'This branch and all of its dependencies will be permanently lost , Are you sure?', function () {
                branchService.deleteBranch(branch).success(function (data) {
                    if (data.value == true) {
                        //reload branches
                        $scope.getAllBranches();
                    }
                }).error(function (error) {
                    $rootScope.onServerError();
                });
            });
    }

    $scope.resumeWork = function () {
        if ($scope.selectedBranch != null) {
            $rootScope.selectedBranch = $scope.selectedBranch;
            $rootScope.editBranch = true;
            $state.go("resumeWorkState");
        } else {
            $rootScope.showAlert('Please select one branch !', 'Select a branch');
        }
    }

    $scope.$on('$viewContentLoaded', function () {
        //get All Branches
        $scope.getAllBranches();
    });

});