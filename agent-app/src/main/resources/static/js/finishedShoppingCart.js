checkUserRole("ROLE_CUSTOMER");
var username = getUsernameFromToken();

$(document).ready(function () {

	$.ajax({
			url: "/api/shopping-cart/customer/finished/" + username,
			type: 'GET',
			contentType: 'application/json',
			success: function (carts) {
				
				if(carts.length == 0){
					$('#main_container').attr("hidden",true);
					return;
				}
			
				$('#price').append("My orders");
				
				$('#body_table').empty();
				for(let c of carts){
			
					if (c.totalPrice != null && c.totalPrice != "" && c.timestamp != null && c.timestamp != "") {
						let row = $('<tr><td>Total price: ' + c.totalPrice.toFixed(2) +  ' </td><td>Creation date and time: ' + c.timestamp.split("T")[0] + "  " + c.timestamp.split("T")[1].substring(0, 5) +  ' </td></tr>');	
						$('#body_table').append(row);			
					}
				
				}
				
			},
			error: function () {
				console.log('error getting shopping cart');
			}
		});	
		
	
});