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
			
				if(products.length == 0){
					
					$.ajax({
						type:"PUT", 
						url: "/api/shopping-cart/" + carts[0].id + "/delete",
						contentType: "application/json",
						success:function(){				
					location.reload();
					return;
					
					},
					error:function(){
					console.log('error deleting shopping cart');
					}
					});
					
				}
			
				for(let p of products){
					
					$.ajax({
					url: "/api/product/" + p.productId,
					type: 'GET',
					contentType: 'application/json',
					success: function (product) {
						
						let row = $('<tr><td>' + product.name +  ' </td><td>' + product.price +  ' </td><td>' + 'x' + p.quantity +  ' </td><td><a style="color: red;" href="#!" role="button" class="float-right"  id="' + p.id + '" onclick="deleteProductToBuy(this.id,' + carts[0].id + ',' + product.price + ')"><i class="fas fa-times"></i></a></td></tr>');	
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

function deleteProductToBuy(id, idCart, price){

	let new_price = price - price*2;

		$.ajax({
				type:"PUT", 
				url: "/api/product-buy/" + id + "/delete",
				contentType: "application/json",
				success:function(){		
				
					$.ajax({
							url: "/api/shopping-cart/" + idCart + "/" + new_price,
							type: 'PUT',
							contentType: 'application/json',
							success: function () {
									location.reload();
									return;
							},
							error: function () {
							console.log('error editing total price');
							}
					});		
					
				},
				error:function(){
				console.log('error deleting product to buy');
			}
			});

};