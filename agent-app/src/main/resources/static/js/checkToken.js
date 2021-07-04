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
			if(hasToken){
			//dodaj permisije za rad sa kampanjama
			}
		},
		error: function () {
				console.log('error checking api token');
			}
		});		

	});

});