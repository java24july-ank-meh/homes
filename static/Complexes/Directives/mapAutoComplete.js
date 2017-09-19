angular.module('rhmsApp').directive('mapAutocomplete', ['$document', function($document) {
    return function(scope, element, attr) {


      var placeSearch, autocomplete;
      var componentForm = {
    	        street_number: 'short_name',
    	        route: 'long_name',
    	        locality: 'long_name',
    	        administrative_area_level_1: 'short_name',
    	        country: 'long_name',
    	        postal_code: 'short_name'
    	      };

      function initialize() {
        // Create the autocomplete object, restricting the search
        // to geographical location types.
        autocomplete = new google.maps.places.Autocomplete(
        		
            /** @type {HTMLInputElement} */
          (document.getElementById('name')), { types: ['establishment', 'geocode'] });
        // When the user selects an address from the dropdown,
        // populate the address fields in the form.
        google.maps.event.addListener(autocomplete, 'place_changed', function() {
          scope.$apply(function() {
            fillInAddress();
          });
        });
      }

      // [START region_fillform]
      function fillInAddress() {
        // Get the place details from the autocomplete object.
        var place = autocomplete.getPlace();
        
        scope.complex.name =place.name;
        scope.complex.phone =place.formatted_phone_number;
        scope.complex.address =place.formatted_address;
        scope.complex.photo = place.photos[0].getUrl({'maxWidth': 1000, 'maxHeight': 1000});
        scope.complex.website = place.website;
        
        for (var component in componentForm) {
          document.getElementById(component).value = '';
          document.getElementById(component).disabled = false;
        }

        // Get each component of the address from the place details
        // and fill the corresponding field on the form.
        for (var i = 0; i < place.address_components.length; i++) {
          var addressType = place.address_components[i].types[0];
          if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
          }
        }
      }
      // [END region_fillform]

      // [START region_geolocation]
      // Bias the autocomplete object to the user's geographical location,
      // as supplied by the browser's 'navigator.geolocation' object.
      function geolocate() {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            var geolocation = new google.maps.LatLng(
                position.coords.latitude, position.coords.longitude);
            var circle = new google.maps.Circle({
              center: geolocation,
              radius: position.coords.accuracy
            });
            autocomplete.setBounds(circle.getBounds());
          });
        }
      }

      document.getElementById('address').onfocus=function(){
        geolocate();
      };

      // [END region_geolocation]
      //
      $document.onload = initialize();
    };
  }]);