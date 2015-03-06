'use strict';

angular.module('listings').controller('ListingsController', ['$scope', '$state', 'Listings', 'Search',
    function($scope, $state, Listings, Search) {

        $scope.searchString = Search.query;

        $scope.$watch('searchString', function() {
           Search.query = $scope.searchString;
        });

        $scope.listings = Listings.all();

    }
]);
