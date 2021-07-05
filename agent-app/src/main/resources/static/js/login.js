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
					localStorage.setItem('token', userToken);
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful login!'
					+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					window.setTimeout(function(){redirectUser(token);},1000);
					return;
				},
				error: function (xhr) {
					if(xhr.responseText == "")
						xhr.responseText = "Bad credentials!";
					let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText
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
	let role = decodeToken(token).role;
	if(role == "ROLE_AGENT")
			window.location.href = "addProduct.html";
	else if(role == "ROLE_CUSTOMER")
		window.location.href = "products.html";
		
}

function decodeToken(token) {
   var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

function getUsernameFromToken(token) {
	try{
		return decodeToken(token).sub;
	}
    catch(err){
		return null;
	}
}


function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};


