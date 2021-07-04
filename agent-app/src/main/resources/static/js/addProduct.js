var agentId=1

checkUserRole("ROLE_AGENT");
var username = getUsernameFromToken();

$(document).ready(function () {

	/*Add product*/
	$('form#addProduct').submit(function (event) {

		event.preventDefault();
	
		$('#div_alert').empty();

		let price = escapeHtml($('#price').val());
		let availableQuantity = escapeHtml($('#quantity').val());
		let name = $('#name').val();
		
		var productDTO = {
			"agentId": agentId,
			"price": price,
			"availableQuantity": availableQuantity,
			"name": name
		};
		
		if($('#file').val() == "" || $('#file').val() == null){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Choose file!' + 
			'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}

		
		$.ajax({
			url: "/api/product",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(productDTO),
			success: function (productId) {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful created product!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				
				window.setTimeout(function(){
					var actionPath = "/api/product/upload?productId=" + productId;
					$('#form_image').attr('action', actionPath)
					$('#form_image').submit();
				},1000);
				return;
			},
			error: function (xhr) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});		
	});

});

function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};