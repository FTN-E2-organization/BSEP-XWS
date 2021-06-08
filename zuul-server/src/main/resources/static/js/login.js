$(document).ready(function () {	
	
	
	$("#username").on('input',function(){
		let username = $('#username').val();
	
		if(username === "" || username == null){
			$('#usernameDescription').text("This is required field.");
			$('#usernameDescription').css("color","red");
		}else{
			$('#usernameDescription').text("");
		}
	});
	
	$("#password").on('input',function(){
		let password = $('#password').val();
	
		if(password === "" || password == null){
			$('#pswDescription').text("This is required field.");
			$('#pswDescription').css("color","red");
		}else{
			$('#pswDescription').text("");
		}
	});
	

	/*Login patient on submit*/
	$('form#logging_in').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();

		let username = $('#username').val();
		let password = $('#password').val();

		var userInfoDTO = {
			"username": username,
			"password": password,
		};

		if ((username == "") || (password == "")) {
			return;
		}
		else {
			$("form#logging_in").removeClass("unsuccessful");
			$('#login').attr("disabled",true);
		
			
			$.ajax({
				url: "/api/auth/login",
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(userInfoDTO),
				success: function (token) {
					localStorage.setItem('token', token);
					redirectUser(token);
					return;
				},
				error: function () {
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Invalid username or password.'
						+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					$('#login').attr("disabled",false);
					return;
				}
			});
				
		}
	});
});

function redirectUser(token){
	let roles = decodeToken(token).roles;
	for(let role of roles){
		if(role == "ROLE_REGULAR")
			window.location.href = "index.html";
	}
}

function decodeToken(token) {
   var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

