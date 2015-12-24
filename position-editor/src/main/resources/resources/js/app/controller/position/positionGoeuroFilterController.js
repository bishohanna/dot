/*Register Branch Controller*/
dotApp.controller('positionGoeuroFilterController', function ($scope, $rootScope, $state, goeuroPositionFilterService) {

    $scope.findPositionsByName = function (goeuroPositionName) {

        if (typeof goeuroPositionName == 'undefined' ||
            goeuroPositionName.length === 0 || !goeuroPositionName.trim()) {
            $rootScope.showAlert('Please Enter a valid position name, or its first letter(s) if you are not sure !', 'Validation !');
        }


        else {

            $rootScope.loadingGoeuroPositions = true;

            goeuroPositionFilterService.findPositionsByName(goeuroPositionName, $rootScope.selectedBranch)
                .success(function (data) {
                    $rootScope.goeuroPositions = data.value;
                    $rootScope.loadingGoeuroPositions = false;
                })
                .error(function (error) {
                    $rootScope.showAlert('Error loading goeuro positions, Please check your database connection', 'Error !');
                    $rootScope.loadingGoeuroPositions = false;
                });

            $scope.allGoeuroPositions = false;//remove select all
            $rootScope.clearAllBouncingPositions(); //remove previous positions from map
        }
    };

    $scope.addSelectedPositionsToBranch = function () {

        if ($rootScope.goeuroPositions == null || $rootScope.goeuroPositions.length == 0) {
            $rootScope.showAlert('Please select at least one position to add to your branch !', 'Empty Positions');
        } else {

            var selectedPositions = [];
            var index;
            for (index = 0; index < $rootScope.goeuroPositions.length; index++) {
                if ($rootScope.goeuroPositions[index].selected) {

                    var selectedPosition = $rootScope.goeuroPositions[index];
                    selectedPositions.push(selectedPosition);

                    //remove it from view
                    $rootScope.goeuroPositions.splice(index, 1);
                    index--;
                }
            }


            $rootScope.saveNewPositions(selectedPositions);
            $rootScope.clearAllBouncingPositions();
        }
    };

    $scope.handleGoeuroPositionSelection = function (position) {
        if (position.selected) {
            $rootScope.drawBouncingPosition(position);
        } else {
            $rootScope.removeBouncingPosition(position);
        }
    };

    $scope.selectAllPositions = function (value) {
        var index;
        for (index = 0; index < $rootScope.goeuroPositions.length; index++) {
            $rootScope.goeuroPositions[index].selected = value;
        }

        if (value) {
            $rootScope.redrawBouncingPositions($rootScope.goeuroPositions);
        } else {
            $rootScope.clearAllBouncingPositions();
        }
    };

    $scope.$on('$viewContentLoaded', function () {
        $scope.allGoeuroPositions = false;//disable select all
    });

});