var params = (new URL(window.location.href)).searchParams;
var productId = params.get("id");

checkUserRole("ROLE_AGENT");
var username = getUsernameFromToken();

$(document).ready(function () {
	
	$.ajax({
        url: "/api/product/" + productId,
		type: 'GET',
		contentType: 'application/json',
        success: function (product) {
		
			$('#name').append(" " + product.name);
			
			$('#body_table').empty();
			if (product.price != null && product.price != "") {
				let row = $('<tr><td>Price: ' + product.price +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (product.availableQuantity != null && product.availableQuantity != "") {
				let row = $('<tr><td>Quantity: ' + product.availableQuantity +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if(product.isDeleted == false){
				let btn_delete = $('<tr><td>' + '<button name="deleteButton" type = "button" data-toggle="modal" data-target="#modalDelete" class="btn btn-danger float-right" id="' + product.id + '" onclick="deleteProduct(this.id)">Delete</button>' + '</td></tr>');
				$('#body_table').append(btn_delete);
			}
			
			let btn_edit = $('<tr><td>' + '<button name="editButton" type = "button" data-toggle="modal" data-target="#modalEdit" class="btn btn-warning float-right" id="' + product.id + '" onclick="editProduct(this.id)">Edit</button>' + '</td></tr>');
			$('#body_table').append(btn_edit);
			
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
						
					
			
        },
        error: function () {
            alert('error getting product');
        }
    });	
    
    $('#getLink').click(function(){
	
		let link = "https://localhost:8091/html/product.html?id=" + productId;
		$('#productLink').val(link);
	});
	
});

function addProduct(path) {
	
	let image_div = $('<div class="column">' +
        '<img height="520px" width="640px"   src="' + path + '">' +
        '</div>');
    $('div#product_image').append(image_div);
};

function deleteProduct(id){
	
	$('a#yes_delete').click(function(event){
		
		event.preventDefault();
	
	
		$.ajax({
				type:"PUT", 
				url: "/api/product/" + id + "/delete",
				contentType: "application/json",
				success:function(){				
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful delete product!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
						window.setTimeout(function(){
								window.location.href = "../html/myProducts.html";
								return;
						},1000);
					
				},
				error:function(){
				console.log('error deleting product');
			}
			});
	});
	
};

function editProduct(id){
	
	$.ajax({
        url: "/api/product/" + productId,
		type: 'GET',
		contentType: 'application/json',
        success: function (product) {
			$('#newPrice').val(product.price);
			$('#newQuantity').val(product.availableQuantity);
						
			
        },
        error: function () {
            alert('error getting product');
        }
    });	
    
    $('form#editProduct').submit(function (event) {

		event.preventDefault();
		
		let newPrice = $('#newPrice').val();
		let newQuantity = $('#newQuantity').val();
		
		var productDTO = {
			"id": id,
			"price": newPrice,
			"availableQuantity": newQuantity
		};
		
		$.ajax({
			url: "/api/product",
			type: 'PUT',
			contentType: 'application/json',
			data: JSON.stringify(productDTO),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful edit product!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
						window.setTimeout(function(){
								location.reload();
								return;
						},1000);
			},
			error: function () {
				console.log('error editing product');
			}
		});		
		
	});
};