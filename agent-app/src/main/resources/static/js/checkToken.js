var agentId=1

$(document).ready(function () {

	$('form#checkToken').submit(function (event) {

		event.preventDefault();
		
		let token = $('#token').val();
		
		$.ajax({
			url: "/api/agent/check-token/" + token,
			type: 'GET',
			contentType: 'application/json',
		success: function (hasToken) {
			$.ajax({
				url: "/api/agent/has-token/" + agentId + "/" + hasToken,
				type: 'PUT',
				contentType: 'application/json',
				success: function () {
					location.reload();
					return;
				},
			error: function () {
				console.log('error setting has api token');
			}
			});	
		},
		error: function () {
				console.log('error checking api token');
			}
		});		

	});

});