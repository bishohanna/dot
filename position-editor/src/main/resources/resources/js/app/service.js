/*Branch service*/
dotApp.factory('branchService', function ($http) {
    return {
        createBranch: function (branch) {
            return $http.post('/branch/create', branch);
        },
        loadBranch: function (branchCode) {
            return $http.get('/branch/getById?branchId=' + branchCode);
        },
        getBranches: function () {
            return $http.get('/branch/getAll');
        },
        deleteBranch: function (branch) {
            return $http.post('/branch/delete', branch);
        }
    }
});

/*Goeuro position filter service*/
dotApp.factory('goeuroPositionFilterService', function ($http) {
    return {
        findPositionsByName: function (name, branch) {
            return $http.get('/goeuroPosition/findByName?name=' + name + '&branchId=' + branch.id);
        }
    }
});

dotApp.factory('positionEditService', function ($http) {
    return {
        saveNewPositions: function (positionsWrapper) {
            return $http.post('/position/saveNewPositions', positionsWrapper);
        },
        loadPositionsByBranch: function (branch, positionName, startIndex) {
            return $http.get('/position/loadByBranchPaged?branchId=' + branch.id + '&name=' + positionName + '&startIndex=' + startIndex);
        },
        removePositionFromBranch: function (position, branch) {
            return $http.get('/position/remove?positionId=' + position.id + '&branchId=' + branch.id);
        },
        updatePosition: function (position) {
            return $http.post('/position/update', position);
        }
    }
});