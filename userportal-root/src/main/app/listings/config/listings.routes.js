'use strict';

// Setting up route
angular.module('listings').config(['$stateProvider',
  function($stateProvider) {

    $stateProvider.
      state('listings', {
        url: '/listings',
        templateUrl: 'listings/views/listings.view.html',
        controller: 'ListingsController',
        data: {
          authorizedRoles: ['public']
        }
      }).
      state('listing', {
        url: '/listings/:listingId',
        templateUrl: 'listings/views/listing.view.html',
        controller: 'ListingController',
        data: {
          authorizedRoles: ['public']
        }
      });
  }
]);
