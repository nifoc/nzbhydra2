angular
    .module('nzbhydraApp')
    .factory('UpdateService', UpdateService);

function UpdateService($http, growl, blockUI, RestartService, RequestsErrorHandler, $uibModal) {

    var currentVersion;
    var latestVersion;
    var updateAvailable;
    var latestVersionIgnored;
    var versionHistory;


    return {
        update: update,
        showChanges: showChanges,
        getInfos: getInfos,
        getVersionHistory: getVersionHistory,
        ignore: ignore
    };

    function getInfos() {
        return RequestsErrorHandler.specificallyHandled(function () {
            return $http.get("internalapi/updates/infos").then(
                function (data) {
                    currentVersion = data.data.currentVersion;
                    latestVersion = data.data.latestVersion;
                    updateAvailable = data.data.updateAvailable;
                    latestVersionIgnored = data.data.latestVersionIgnored;
                    return data;
                }
            );
        });
    }

    function ignore(version) {
        return $http.put("internalapi/updates/ignore?version=" + version).then(function (data) {
            return data;
        });
    }

    function getVersionHistory() {
        return $http.get("internalapi/updates/versionHistory").then(function (data) {
            versionHistory = data.data;
            return data;
        });
    }

    function showChanges() {
        return $http.get("internalapi/updates/changesSince").then(function (response) {
            var params = {
                size: "lg",
                templateUrl: "static/html/changelog-modal.html",
                resolve: {
                    versionHistory: function () {
                        return response.data;
                    }
                },
                controller: function ($scope, $sce, $uibModalInstance, versionHistory) {
                    $scope.versionHistory = versionHistory;

                    $scope.ok = function () {
                        $uibModalInstance.dismiss();
                    };
                }
            };

            var modalInstance = $uibModal.open(params);
            modalInstance.result.then();
        });
    }


    function update() {
        blockUI.start("Downloading update. Please stand by...");
        $http.put("internalapi/updates/installUpdate").then(function () {
                //Handle like restart, ping application and wait
                //Perhaps save the version to which we want to update, ask later and see if they're equal. If not updating apparently failed...
                RestartService.startCountdown("Downloaded update. Shutting down Hydra for wrapper to execute update.");
            },
            function () {
                blockUI.reset();
                growl.info("An error occurred while updating. Please check the logs.");
            });
    }
}