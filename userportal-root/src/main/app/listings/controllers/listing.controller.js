'use strict';

angular.module('listings').controller('ListingController', ['$scope', '$state', 'Listings', 'Search', '$window',
    function($scope, $state, Listings, Search, $window) {

      var advertNodes;

        $scope.searchString = Search.query;

      function getListing(nodeid) {
        return $window._.find(advertNodes, function (listing) {
          return (listing.id === nodeid );
        });
      }

      function guid() {
        function s4() {
          return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
        }
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
          s4() + '-' + s4() + s4() + s4();
      }

      Listings.get({advertID: $state.params.listingId}, function(advert){
        $scope.currentListing = $window._.find(advert.advertnodes, function (listing) {
          return (listing.metadataId === $state.params.listingId && listing.metadataPath === $state.params.metadataPath );
        });

        advertNodes = advert.advertnodes;

        var root = $scope.currentListing;

        function getParent(node) {

          if (node === undefined) {
            return false
          } else {
            return $window._.find(advert.advertnodes, function (listing) {
              return (listing.childNodeIds.indexOf(node.id) !== -1);
            });
          }


        }

        function createBreadCrumb() {

          $scope.breadcrumbs = [root];
          var parent = getParent(root);

          while(parent) {
            $scope.breadcrumbs.unshift(parent);
            parent = getParent(parent);
          }
        }



        function recurse(node) {
          if (node.childNodeIds){
            node.children = [];
            $window._.each(node.childNodeIds, function(nodeid) {

              var thisNode = $window._.clone(getListing(nodeid));

              if (thisNode) {
                thisNode.id = guid();
                node.children.push(thisNode);
                recurse(thisNode);
              }
            });


          }
        }

        recurse(root);
        createBreadCrumb(root);


        $scope.treeData = root;

      });

     /* Listings.get({}, function(advert){
        $scope.currentListing = $window._.find(advert.advertnodes, function (listing) {
          return listing.metadataId === $state.params.listingId;
        });




      });*/

      //$scope.fields = [{"label":"Name","value":"Outstation Service","id":"name","formFieldType":"text"},{"label":"Nodeclass","value":"DataService","id":"nodeClass","formFieldType":"text"},{"label":"Description","value":"Speed Management Network Data Outstation Service","id":"description","formFieldType":"textarea"},{"label":"Details","value":"","id":"details","formFieldType":"text"},{"label":"Childnodeids","value":["http://newcastle.gov.uk/SMN#Outstations"],"id":"childNodeIds","formFieldType":"text"},{"label":"Owner","value":"Admin User","id":"owner","formFieldType":"text"},{"label":"Datecreated","value":"2015-03-11T09:00:00+00:00","id":"dateCreated","formFieldType":"text"},{"label":"Dateupdated","value":"2015-03-11T09:00:00+00:00","id":"dateUpdated","formFieldType":"text"},{"label":"Access","value":["user","public"],"id":"access","formFieldType":"text"}];
      $scope.fields = [];

      function toTitleCase(str) {
        return str.replace(/\w\S*/g, function (txt) {
          return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        });
      }

      function calcType(key, value) {
        if (key === 'metadataPath' || key === 'metadataId') {
          return 'hidden';
        } else if (value instanceof Array){
          return 'nglist';
        } else {
          return 'textarea';
        }
      }

      $scope.updateFields = function(d) {
        $scope.fields = [];
        var fields = [],
            coreFields = {
              name: 1,
              summary: 2,
              description: 3,
              tags: 4
            };

        console.log(d);

        var excludedProperties = ['_children', 'children', 'depth', 'id', 'parent', 'x', 'x0', 'y', 'y0', 'isRootNode', /*'metadataPath', 'metadataId',*/ 'requesterId', 'serviceURL', 'childNodeIds', 'dateCreated', 'dateUpdate', 'dateUpdated', 'access', 'nodeClass', '$$hashKey'];

        for (var key in d) {

          if (excludedProperties.indexOf(key) === -1) {
            console.log(toTitleCase(key));
            var field = {
              label: toTitleCase(key),
              key: key,
              value: d[key],
              id: key,
              formFieldType: calcType(key, d[key]),
              isArray: d[key] instanceof Array
            };

            fields.push(field);


          }
        }

        fields.sort(function(a,b){

          if (a.key in coreFields && b.key in coreFields) {
            return coreFields[a.key] - coreFields[b.key];
          }

          if (a.key in coreFields && b.key in coreFields === false) {
            return -1;
          }

          if (a.key in coreFields === false && b.key in coreFields) {
            return 1;
          }

          return 0;
        });

        $scope.fields = fields;

        $scope.$apply();

        jQuery('textarea').each(function () {
          this.style.height = this.scrollHeight + 'px'
        });
      };

      $scope.submitFields = function($event) {
        $event.preventDefault();

        console.log(
          JSON.stringify(
            $window._.map($scope.fields, function(d){
              return {
                key: d.key,
                value: d.value
              }
            })
          )
        );
      }





    }
]);

