var customerId = 2;

$(document).ready(function () {

	$.ajax({
			url: "/api/shopping-cart/customer/" + customerId,
			type: 'GET',
			contentType: 'application/json',
			success: function (carts) {
			
			let btn = '<a style="color: red;" href="#!" role="button" class="float-right" data-toggle="modal" data-target="#modalDelete" id="' + carts[0].id + '" onclick="deleteShoppingCart(this.id)"><i class="fas fa-times"></i></a>';
			$('div#div_id').append(btn);
			
				$('#price').append("Shopping cart");
				
				$('#body_table').empty();
				if (carts[0].totalPrice != null && carts[0].totalPrice != "") {
					let row = $('<tr><td>Total price: ' + carts[0].totalPrice.toFixed(2) +  ' </td></tr>');	
					$('#body_table').append(row);			
				}
				if (carts[0].timestamp != null && carts[0].timestamp != "") {
					let row = $('<tr><td>Creation date and time: ' + carts[0].timestamp.split("T")[0] + "  " + carts[0].timestamp.split("T")[1].substring(0, 5) +  ' </td></tr>');				
					$('#body_table').append(row);			
				}
				
				
				
				$.ajax({
			url: "/api/product-buy/shopping-cart/" + carts[0].id,
			type: 'GET',
			contentType: 'application/json',
			success: function (products) {
			
				for(let p of products){
					
					$.ajax({
					url: "/api/product//" + p.productId,
					type: 'GET',
					contentType: 'application/json',
					success: function (product) {
						
						let row = $('<tr><td>' + product.name +  ' </td><td>' + product.price +  ' </td><td>' + 'x' + p.quantity +  ' </td></tr>');	
						$('#body_table').append(row);
						
					},
					error: function () {
						console.log('error getting products to buy');
					}
				});	
					
				}
			},
			error: function () {
				console.log('error getting products to buy');
			}
		});	
				
			},
			error: function () {
				console.log('error getting shopping cart');
			}
		});	
		
	
});

function deleteShoppingCart(id){

	$('a#yes_delete').click(function(event){
		
		event.preventDefault();
	
	
		$.ajax({
				type:"PUT", 
				url: "/api/shopping-cart/" + id + "/delete",
				contentType: "application/json",
				success:function(){				
					location.reload();
					return;
					
				},
				error:function(){
				console.log('error deleting shopping cart');
			}
			});
	});
};