'use strict';

// Setting up route
angular.module('listings').config(['$stateProvider',
  function($stateProvider) {

    $stateProvider.
      state('listings', {
        url: '/listings/search/:query',
        templateUrl: 'listings/views/listings.view.html',
        controller: 'ListingsController',
        data: {
          authorizedRoles: ['public']
        }
      }).
      state('listing', {
        url: '/listings/:listingId/:metadataPath',
        templateUrl: 'listings/views/listing.view.html',
        controller: 'ListingController',
        data: {
          authorizedRoles: ['public']
        }
      });
  }
]);
