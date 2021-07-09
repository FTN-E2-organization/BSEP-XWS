//checkUserRole("ROLE_CUSTOMER");
var username = getUsernameFromToken();

var params = (new URL(window.location.href)).searchParams;
var productId = params.get("id");

var campaignId = 1;
var available = 0;

$(document).ready(function () {
	
	$.ajax({
        url: "/api/product/" + productId,
		type: 'GET',
		contentType: 'application/json',
        success: function (product) {
	
			if(product.isDeleted){
				$('#main_container').attr("hidden",true);
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Product is deleted!' + 
				'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				window.setTimeout(function(){
					window.history.back();
				},1000);
			}else{
				$('#name').append(" " + product.name);
			
			$('#body_table').empty();
			if (product.price != null && product.price != "") {
				let row = $('<tr><td>Price: ' + product.price +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (product.availableQuantity != null && product.availableQuantity != "") {
				let row = $('<tr><td>Quantity: ' + product.availableQuantity +  ' </td></tr>');	
				$('#body_table').append(row);		
				available = product.availableQuantity;
			}
			if(product.availableQuantity > 0){
				let btn_add_to_cart = $('<tr><td>' + '<button name="addCartButton" type = "button" data-toggle="modal" data-target="#modalAddToCart" class="btn btn-info float-right" id="' + product.id + '" onclick="addToCartProduct(this.id)">Add to cart</button>' + '</td></tr>');
				$('#body_table').append(btn_add_to_cart);
			}
			
		        	let grouped={}
		        	$('#product_image').empty();
		  				if(grouped[product.id]){
		  				grouped[product.id].push(product)
		  				}      	else{
		  				grouped[product.id]=[product]
		  				}
		        	
		        	for (let p in grouped) {
		
		                fetch('/api/product/files/' +grouped[p][0].path)
		                    .then(resp => resp.blob())
		                    .then(blob => {
		                        const url = window.URL.createObjectURL(blob);
		                        addProduct(url);
		                    })
		                    .catch(() => alert('oh no!'));
					}
			}

        },
        error: function (jqXHR) {
            alert('Error ' + jqXHR.responseText);
        }
    });	
	
});

function addProduct(path) {
	
	let image_div = $('<div class="column">' +
        '<img height="520px" width="640px"   src="' + path + '">' +
        '</div>');
    $('div#product_image').append(image_div);
};

function addToCartProduct(id){
	
	$('form#addToCart').submit(function (event) {
	
	event.preventDefault();
	
	if(username == null){
		window.location.href = "../html/login.html";
	}
	

		let quantity = $('#quantity').val();
		
	
		if(quantity > available){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">We do not have that number of products in stock!'
                + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
            
            $('#addToCart').find(":submit").prop('disabled', false);
            $('#div_alert').append(alert);
            return;
		}
		
		var cartDTO = {
			"campaignId": campaignId,
			"customerUsername": username
		};
		
		$.ajax({
			url: "/api/shopping-cart/customer/" + username,
			type: 'GET',
			contentType: 'application/json',
			success: function (carts) {
				
				if(carts.length==0){
					
					$.ajax({
						url: "/api/shopping-cart",
						type: 'POST',
						contentType: 'application/json',
						data: JSON.stringify(cartDTO),
						success: function () {
						
						$.ajax({
							url: "/api/shopping-cart/customer/" + username,
							type: 'GET',
							contentType: 'application/json',
							success: function (carts) {
								
								var productDTO = {
								"quantity": quantity,
								"productId": id,
								"shoppingCartId": carts[0].id
								};
								
									$.ajax({
									url: "/api/product-buy",
									type: 'POST',
									contentType: 'application/json',
									data: JSON.stringify(productDTO),
									success: function () {
										$.ajax({
      										  url: "/api/product/" + id,
											type: 'GET',
											contentType: 'application/json',
    									    success: function (product) {
    									    
    									    let quantity_new = $('#quantity').val();   									   								
    									    let price_update = quantity_new * product.price;
												
												$.ajax({
													url: "/api/shopping-cart/" + carts[0].id + "/" + price_update,
													type: 'PUT',
													contentType: 'application/json',
												success: function () {
													location.reload();
													return;
												},
												error: function () {
														console.log('error editing product');
													}
												});		
												
											},
												error: function () {
												console.log('error getting product to buy');
											}
										});	
								},
									error: function () {
									console.log('error getting product to buy');
									}
								});	
								
								
							},
							error: function () {
							console.log('error getting shopping cart');
						}
						});	
				
					},
					error: function () {
						console.log('error creating shopping cart');
					}
					});		
					
				}else {
				
					var productDTO = {
						"quantity": quantity,
						"productId": id,
						"shoppingCartId": carts[0].id
					};
								
								$.ajax({
									url: "/api/product-buy",
									type: 'POST',
									contentType: 'application/json',
									data: JSON.stringify(productDTO),
									success: function () {
										$.ajax({
      										  url: "/api/product/" + id,
											type: 'GET',
											contentType: 'application/json',
    									    success: function (product) {
    									    
    									    let quantity_new = $('#quantity').val();  									
    									    let price_update = quantity_new * product.price;
												
												$.ajax({
													url: "/api/shopping-cart/" + carts[0].id + "/" + price_update,
													type: 'PUT',
													contentType: 'application/json',
												success: function () {
													location.reload();
													return;
												},
												error: function () {
														console.log('error editing product');
													}
												});		
												
											},
												error: function () {
												console.log('error getting product to buy');
											}
										});	
								},
									error: function () {
									console.log('error getting product to buy');
									}
								});	
				
				}
			},
			error: function () {
				console.log('error getting shopping cart');
			}
		});	
		
		
	
	});
	
};