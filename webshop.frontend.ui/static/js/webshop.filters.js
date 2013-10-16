define(['angular'], function() {
    angular.module('webshop.filters', []).
        filter('yesNoFilter', function(){
            return function(input){
                if(input == "true" || input == true) {
                    return "yes";
                } else {
                    return "no";
                }
            }
        }).
        filter('newLineFilter', function(){
            return function(input){
            	if(input == undefined) {
            		return '';
            	}
                return input.replace(new RegExp('\n', 'g'), "<br>");
            }
        });
});