'use strict';

/**
 * Configure RequireJS
 */

require
        .config({
            paths : {
                'domReady' : 'lib/require-domReady',
                'jquery' : 'lib/jquery-1.9.1.min',
                'angular' : 'lib/angular',
                'angular.sanatize' : 'lib/angular-sanatize.min'  
            },

            /**
             * for libs that either do not support AMD out of the box, or
             * require some fine tuning to dependency mgmt
             */
            shim : {
                'jquery' : {
                    exports : 'jquery'
                },

                'angular' : {
                    exports : 'angular',
                    deps : [ 'jquery' ]
                }, 

                'angular.sanatize' : {
                    deps : ['angular']
                }
            }
        });

require([ 'webshop.bootstrap' ], function() {
});