	
	/* Section switch */
		var profileDiv = $('#profileSection');
		var listingDiv = $('#listingSection');
		$(document).ready(function(){
			listingDiv.hide();
			
			function showModal(modal){
				$(modal).modal(options);
			}
		});
		$('#profile').click(function(){
			listingDiv.hide();
			profileDiv.show();
		});
		$('#listing').click(function(){
			profileDiv.hide();
			listingDiv.show();
		});
	/******************************/
        /*******  Form validation **********/
	/*******  Add house form validation *********/
            $("#add-house-form").validate({
                    rules: {
                        name: {
                            required: true
                        },
                        houseType: {
                            required:true
                        },
                        size: {
                            required:true,
                            min: 0,
                            number: true
                        },
                        leasingType: {
                            required:true
                        },
                        price: {
                            required:true,
                            min: 0,
                            number: true
                        },
                        availability: {
                            required: true,
                            min: 0,
                            number: true
                        },
                        addressLine1: {
                            required:true
                        },
                        city: {
                            required:true
                        },
                        state: {
                            required:true
                        },
                        zipCode: {
                            required:true,
                            number: true
                        }
                        
                    }
            });
            
            $("#edit-profile-form").validate({
                rules: {
                    occupation: "required",
                    providerType: "required",
                    addressLine1: "required",
                    city: "required",
                    states: "required",
                    zipCode: {
                        required: true,
                        number: true
                    }
                }
            });
        
            
            
            
	/********************************************/