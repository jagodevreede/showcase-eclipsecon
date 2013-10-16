define(['angular'], function() {
  var srvModule = angular.module('webshop.services', []);
  srvModule.factory('basket', function() {
      var products = [];
      if( localStorage['basket'] != undefined) {
        products = JSON.parse(localStorage['basket']);
      } else {
        localStorage['basket'] = '[]';
      }

      return {
        products: products,
        addProduct: function(product) {
          var updated = false;

          angular.forEach(products, function(productInBasket) {
            if(product._id == productInBasket._id) {
              product.amount++;          
              updated = true;
            }
          });

          if(!updated) {
            product.amount = 1;
            products.push(product);
          }

          localStorage['basket'] = JSON.stringify(products);
        },

        removeItem: function(product) {
          var idxToRemove = -1;
          angular.forEach(products, function(productInBasket, idx) {
            if(product._id == productInBasket._id) {
              if(product.amount > 1) {
                product.amount--;
              } else {
                idxToRemove = idx;
              }          
            }
          });

          if(idxToRemove != -1) {
            products.splice(idxToRemove, 1);
          }

          localStorage['basket'] = JSON.stringify(products);
        },

        size: function() {
          var total = 0;
          angular.forEach(products, function(product) {
            total += product.amount;
          });

          return total;
        },

        total: function() {
          var total = 0;
          angular.forEach(products, function(product) {
            total += (product.amount * product.price);
          });

          return total;
        },

        empty: function() {
          products = [];
          localStorage['basket'] = JSON.stringify(products);
        }
      };
    });

  return srvModule;
});