checkUserRole("ROLE_AGENT");
var username = getUsernameFromToken();


$(document).ready(function () {		

	
$.ajax({
		type:"GET", 
		url: "/api/agent/api-token/" + username,
		contentType: "application/json",
		success:function(hasToken){					
			if(hasToken){
				$('#container').attr("hidden",true);
				let div = '</br><h4>You have API token already.</h4>';
				$('div#api_container').append(div);
			}else{
				
			}							
		},
		error:function(){
		}
});	

	$('form#checkToken').submit(function (event) {

		event.preventDefault();
		
		let token = $('#token').val();
		
		
		$.ajax({
			url: "/api/agent/check-token/" + token,
			type: 'GET',
			contentType: 'application/json',
		success: function (hasToken) {
			
			if(hasToken=="true"){
				$.ajax({
					url: "/api/agent/has-token/" + username + "/" + hasToken,
					type: 'PUT',
					contentType: 'application/json',
					success: function (hasToken) {
					
					nistagramAgent = getUsernameFromApiToken(token);
						
						$.ajax({
							url: "/api/agent/token/" + username + "/" + token,
							type: 'PUT',
							contentType: 'application/json',
							success: function () {
							let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful enter API token!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
						window.setTimeout(function(){
								window.location.href = "../html/createCampaign.html";
								return;
						},2000);
								
						},
						error: function () {
								console.log('error setting api token');
							}
						});		
				},
				error: function () {
						console.log('error getting api token');
					}
				});		
				
			}else{
			
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Invalid API token!' + 
			'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
			}
			
				
		},
		error: function () {
				console.log('error checking api token');
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Error!' + 
			'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
			}
		});		
		
		
	});

});