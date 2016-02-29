'use strict';

angular.module('listings').controller('ListingsController', ['$scope', '$state', 'Listings', 'Search',
    function($scope, $state, Listings, Search) {

      $scope.searchString = Search.query;

        $scope.$watch('searchString', function(val) {
           Search.query = val;
        });

      /*if ($state.params.query && (!Listings.query || Listings.query === '')) {
        Listings.query = angular.copy($state.params.query);
      }*/




      Listings.get({}, function(listings){
        //console.log(listings.advertnodes);

        /*angular.forEach(listings.advertnodes, function(val, key){
          listings.advertnodes[key].name = null;
        });*/

        $scope.listings = listings.advertnodes;
      });

        $scope.$on('AUTH_EVENTS.logoutSuccess', function(){
          $scope.searchString = '';

        });
    }
]).filter('filterListings', function() {
  return function( items, searchString) {
    var filtered = [];
    if (searchString) {
      angular.forEach(items, function(item) {
        if(item.name && item.name !== null && (item.name.toLowerCase().indexOf(searchString.toLowerCase()) != -1)) {
          item.score = 2 + (item.isRootNode ? 1 : 0);
          filtered.push(item);
        } else if(item.owner && item.owner !== null && (item.owner.toLowerCase().indexOf(searchString.toLowerCase()) != -1)) {
          item.score = 1;
          filtered.push(item);
        } else if(item.summary && item.summary !== null && (item.summary.toLowerCase().indexOf(searchString.toLowerCase()) != -1)) {
          item.score = 1;
          filtered.push(item);
        } else if (item.tags && item.tags instanceof Array && item.tags.length > 0) {
          for(var i = 0;i < item.tags.length; i++){
            if (item.tags[i].toLowerCase() === searchString.toLowerCase()){
              item.score = 1;
              filtered.push(item);
              i = item.tags.length;
            }
          }
        }
      });

      filtered.sort(function(a,b){
        return a.score < b.score;
      });

        return filtered;
    } else {
      return items;
    }


  };
});
