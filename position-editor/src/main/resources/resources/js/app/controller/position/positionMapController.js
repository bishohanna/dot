/*Register Branch Controller*/
dotApp.controller('positionMapController', function ($scope, $rootScope) {

    $scope.drawMap = function () {
        $scope.map = {
            center: {latitude: '50.249647', longitude: '13.646518'},
            zoom: 3,
            control: {},
            bounds: {
                northeast: {latitude: '83.972721', longitude: '121.279031'},
                southwest: {latitude: '-58.027740', longitude: '-157.023081'}
            }
        };

        $scope.mapOptions = {
            minZoom: 3,
            maxZoom: 16
        }
    };

    $rootScope.drawDraggablePosition = function (position, onCoordsChanged) {

        if ($rootScope.expandEdit) {
            $scope.editPositionMarker = {
                id: position.goEuroId,
                coords: {
                    latitude: position.latitude,
                    longitude: position.longitude
                },
                options: {
                    draggable: true,
                    icon: position.iconUrl
                },
                events: {
                    dragend: onCoordsChanged
                }
            };

            $scope.map.control.refresh({latitude: position.latitude, longitude: position.longitude});
            $scope.map.control.getGMap().setZoom(14);
        }
    };

    $rootScope.redrawBouncingPositions = function (positions) {

        $rootScope.clearAllBouncingPositions();

        var index;
        for (index = 0; index < positions.length; index++) {
            $rootScope.drawBouncingPosition(positions[index]);
        }

    };

    $rootScope.clearAllBouncingPositions = function () {
        $rootScope.goeuroPositionsMarkers = [];
    };

    $rootScope.drawBouncingPosition = function (position) {
        var bouncingPosition = {
            goEuroId: position.goEuroId,
            latitude: position.latitude,
            longitude: position.longitude,
            iconUrl: position.iconUrl,
            options: {
                animation: google.maps.Animation.BOUNCE
            }

        };

        $rootScope.goeuroPositionsMarkers.push(bouncingPosition);

    };

    $rootScope.removeBouncingPosition = function (position) {
        var index;
        for (index = 0; index < $rootScope.goeuroPositionsMarkers.length; index++) {
            if (position.goEuroId == $rootScope.goeuroPositionsMarkers[index].goEuroId) {
                $rootScope.goeuroPositionsMarkers.splice(index, 1);
                break;
            }
        }
    };


    $scope.$on('$viewContentLoaded', function () {
        //initialize vars
        $rootScope.goeuroPositionsMarkers = [];
        //draw map
        $scope.drawMap();
    });

})
;