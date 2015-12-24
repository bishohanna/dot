/*Register Branch Controller*/
dotApp.controller('positionEditController', function ($scope, $rootScope, positionEditService) {

    $scope.reloadBranchPositions = function (startIndex) {

        $scope.loadingBranchPositions = true;

        positionEditService.loadPositionsByBranch($rootScope.selectedBranch, $scope.positionNameFilter, startIndex).success(function (data) {

            if (data.success) {
                $scope.savedPositions = data.value;
                $scope.totalPositionCount = data.totalItems;
                $scope.currentPositionPage = data.pageIndex;
                $scope.loadingBranchPositions = false;
            } else {
                $rootScope.showAlert('Error loading this branch positions .', 'Error !');
            }

        }).error(function (error) {
            $rootScope.onServerError();
        });

    };


    $scope.removeFromBranch = function (position) {

        positionEditService.removePositionFromBranch(position, $rootScope.selectedBranch).success(function (data) {

            if (data.success && data.value) {

                //reload positions
                $scope.reloadBranchPositions(1);
            } else {
                $rootScope.showAlert('Error removing position from this branch', 'Error !');
            }


        }).error(function (error) {
            $rootScope.onServerError();
        });

    };

    $rootScope.updateAndReplacePosition = function (position) {
        positionEditService.updatePosition(position).success(function (data) {
            if (data.success) {

                //replcae position
                $scope.replacePosition(data.value);
            } else {
                $rootScope.showAlert('Error updating position, please contact your administrator', 'Error !');
            }

        }).error(function (error) {
            $rootScope.onServerError();
        });
    };

    $scope.replacePosition = function (position) {

        var index;
        for (index = 0; index < $scope.savedPositions.length; index++) {
            if (position.goEuroId == $scope.savedPositions[index].goEuroId) {

                $scope.savedPositions.splice(index, 1, position);
                break;
            }
        }
    };

    $rootScope.saveNewPositions = function (positions) {

        if (positions == null || positions.length == 0) {
            $rootScope.showAlert('Please select at least one position to add to your branch !', 'Empty Positions');
        }

        else {

            var positionsWrapper = new Object();
            positionsWrapper.positions = positions;
            positionsWrapper.branch = $rootScope.selectedBranch;

            positionEditService.saveNewPositions(positionsWrapper).success(function (data) {

                if (data.success) {

                    //reload positions again
                    $scope.reloadBranchPositions(1);
                } else {
                    $rootScope.showAlert('Error Adding positions to branch :' + error);
                }

            }).error(function (error) {
                $rootScope.onServerError();
            });

        }
    };


    $scope.openEditPositionWindow = function (selectedPosition) {

        $rootScope.openSingleEditWindow(selectedPosition,
            '../resources/templates/position/dialogs/editPositionDialog.html',
            $scope,
            'editDetailsModalController',
            function (selectedPosition) {
                //on pressing ok , will update and replace the selected position
                $rootScope.updateAndReplacePosition(selectedPosition);
            });

    };

    var doOnMarkerPositionChange = function (marker, eventName, args) {

        var lat = marker.getPosition().lat();
        var lon = marker.getPosition().lng();

        $scope.selectedPosition.latitude = lat.toPrecision(7);
        $scope.selectedPosition.longitude = lon.toPrecision(7);

        $rootScope.updateAndReplacePosition($scope.selectedPosition);
    };

    $scope.showPositionOnMap = function () {
        $rootScope.drawDraggablePosition($scope.selectedPosition, doOnMarkerPositionChange);
    };


    $scope.$on('$viewContentLoaded', function () {
        $scope.positionNameFilter = '';
        $scope.reloadBranchPositions(1);
    });


});


dotApp.controller('editDetailsModalController', function ($scope, $uibModalInstance, editItem) {
    $scope.selectedPosition = editItem;

    $scope.ok = function () {
        $uibModalInstance.close($scope.selectedPosition);
    };

});


