'use strict';

angular.module('listings').controller('ListingController', ['$scope', '$state', 'Listings', 'Search',
    function($scope, $state, Listings, Search) {

        $scope.searchString = Search.query;

        $scope.currentListing = Listings.one($state.params.listingId);

    }
]);

