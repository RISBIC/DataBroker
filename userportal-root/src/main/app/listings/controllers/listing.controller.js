'use strict';

angular.module('listings').controller('ListingController', ['$scope', '$state', 'Listings', 'Search', '$window',
    function($scope, $state, Listings, Search, $window) {

        $scope.searchString = Search.query;

        Listings.get({advertID: $state.params.listingId}, function(advert){
          $scope.currentListing = advert;
        });

      console.log($state);

      //$scope.fields = [{"label":"Name","value":"Outstation Service","id":"name","formFieldType":"text"},{"label":"Nodeclass","value":"DataService","id":"nodeClass","formFieldType":"text"},{"label":"Description","value":"Speed Management Network Data Outstation Service","id":"description","formFieldType":"textarea"},{"label":"Details","value":"","id":"details","formFieldType":"text"},{"label":"Childnodeids","value":["http://newcastle.gov.uk/SMN#Outstations"],"id":"childNodeIds","formFieldType":"text"},{"label":"Owner","value":"Admin User","id":"owner","formFieldType":"text"},{"label":"Datecreated","value":"2015-03-11T09:00:00+00:00","id":"dateCreated","formFieldType":"text"},{"label":"Dateupdated","value":"2015-03-11T09:00:00+00:00","id":"dateUpdated","formFieldType":"text"},{"label":"Access","value":["user","public"],"id":"access","formFieldType":"text"}];
      $scope.fields = [];

      function toTitleCase(str) {
        return str.replace(/\w\S*/g, function (txt) {
          return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        });
      }

      $scope.updateFields = function(d) {
        $scope.fields = [];

        var excludedProperties = ['_children', 'children', 'depth', 'id', 'parent', 'x', 'x0', 'y', 'y0', 'isRootNode', 'metadataPath', 'metadataId', 'requesterId', 'serviceURL', 'childNodeIds', 'dateCreated', 'dateUpdated', 'access', 'nodeClass', '$$hashKey'];

        for (var key in d) {

          if (excludedProperties.indexOf(key) === -1) {
            console.log(toTitleCase(key));
            var field = {
              label: toTitleCase(key),
              //label: key,
              value: d[key],
              id: key,
              formFieldType: ((typeof d[key] !== 'string' || (typeof d[key] === 'string' && d[key].length < 35) ? 'text' : 'textarea'))
            };

            $scope.fields.push(field);

            jQuery('textarea').each(function () {
              this.style.height = this.scrollHeight + 'px'
            });

          }
        }

        console.log($scope.fields);
        $scope.$apply();
      }
;
      function getListing(id) {
        return Listings.one(id);
      }

      var root = getListing($state.params.listingId);



      function recurse(node) {
        if (node.childNodeIds){
          node.children = [];
          $window._.each(node.childNodeIds, function(nodeid) {
            var thisNode = getListing(nodeid);
            if (thisNode) {
              node.children.push(thisNode);
              recurse(thisNode);
            }
          });


        }
      }

      recurse(root);

      console.log(root);

      $scope.treeData = root;



    }
]);

