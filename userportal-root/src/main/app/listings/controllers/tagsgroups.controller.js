'use strict';

angular.module('listings').factory('tagsGroupsService',
    [
       '$http',
        function($http)
        {
            var tagsGroupsService = {};

            tagsGroupsService.tagsGroups = [];

            tagsGroupsService.reload =
                function(caller, callback)
                {
                    $http({ method: 'GET', url: 'config/tags-groups.json'})
                        .then(
                            function successCallback(responce)
                            {
                                tagsGroupsService.tagsGroups = responce.data;
                                callback(caller, tagsGroupsService.tagsGroups);
                            },
                            function errorCallback(responce)
                            {
                                tagsGroupsService.tagsGroups = [];
                                callback(caller, tagsGroupsService.tagsGroups);
                            }
                        );
                }

            return tagsGroupsService;
        }
    ]
);

angular.module('listings').controller('TagsGroupsController',
    [
        '$scope',
        '$location',
        '$log',
        'tagsGroupsService',
        function($scope, $location, $log, tagsGroupsService)
        {
            this.tagsGroups = tagsGroupsService.tagsGroups;

            this.onTagsGroupsChange =
                function (object, tagsGroups)
                {
                    object.tagsGroups = tagsGroups.slice(0, 6);
                }

            this.doTagsGroupsReload =
                function()
                {
                    tagsGroupsService.reload(this, this.onTagsGroupsChange);
                }

            this.doSelectTags =
                function(tags)
                {
                    $scope.navToSearch(tags);
                }

            this.tagsGroups = tagsGroupsService.reload(this, this.onTagsGroupsChange);
        }
    ]
);

