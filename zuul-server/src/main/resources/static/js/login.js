var userToken = null;

var entityMap = {
	'&': '&amp;',
	'<': '&lt;',
	'>': '&gt;',
	'"': '&quot;',
	"'": '&#39;',
	'/': '&#x2F;',
	'`': '&#x60;',
	'=': '&#x3D;'
};

$(document).ready(function () {	
	
	$("#code").on('input',function(){
		let code = $('#code').val();
		
		if(code === "" || code == null){
			$('#codeDescription').text("This is required field.");
			$('#codeDescription').css("color","red");
		}else{
			$('#codeDescription').text("");
		}
	});
	
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

		let username = escapeHtml($('#username').val());
		let password = escapeHtml($('#password').val());

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
					userToken = token;
					
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Success!A code has been sent to your email.'
					+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					
					$('#container').attr("hidden",true);
					$('#submitCodeDiv').attr("hidden",false);
					
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
	
	/*Submit code*/
	$('form#submitCodeForm').submit(function (event) {

		event.preventDefault();
	
		let code = escapeHtml($('#code').val());
		
		if ((code == "") || (code == "")) {
			return;
		}
		
		alert(userToken);
		
		// POSLATI AJAX POZIV
		
		//Ako je kod dobar pozvati sledeci kod:
		//localStorage.setItem('token', userToken);
		//redirectUser(userToken);
		
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

function sendNewLink() {	
	let username = $('#username').val();
	
	if ((username == "")) {
		let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Enter your username!'
			+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
		$('#div_alert').append(alert);
		return;
	} 
	$.ajax({
		url: "/api/auth/profile/new-activation-link",
		type: 'POST',
		contentType: 'application/json',
		data: username,
		success: function () {
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Success!'
			+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		},
		error: function (jqXHR) {
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">ERROR. ' + jqXHR.responseText 
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
	});		
};

function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};


