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

	$("#oldPassword").on('input',function(){
		let oldPassword = $('#oldPassword').val();
		
		if(oldPassword === "" || oldPassword == null){
			$('#oldPasswordDescription').text("This is required field.");
			$('#oldPasswordDescription').css("color","red");
		}else{
			$('#oldPasswordDescription').text("");
		}
	});
	
	$("#password").on('input',function(){
 		let password = $('#password').val();
		let passwordRepeat = $('#passwordRepeat').val();
	
	  	let numCharacter = /[0-9]+/i
		let lowercaseCharacter = /[a-z]+/g
		let uppercaseCharacter = /[A-Z]+/g
		let specialSymbol = /[?|!@#.$%/]+/i
		let pswLength = $('#password').val().length;
		
		let strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{10,})");
		let mediumRegex = new RegExp("^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{10,})");

		if(password === "" || password == null){
			$('#pswDescription').text("This is required field.");
			$('#pswDescription').css("color","red");
			
			$('#numCharacter').css("color","red");
			$('#lowercaseCharacter').css("color","red");
			$('#uppercaseCharacter').css("color","red");
			$('#specialSymbol').css("color","red");
			$('#pswLength').css("color","red");
		}else{
			
		  	if(numCharacter.test(password))
				$('#numCharacter').css("color","green");
			else
				$('#numCharacter').css("color","red");
			
			if(lowercaseCharacter.test(password))
				$('#lowercaseCharacter').css("color","green");
			else
				$('#lowercaseCharacter').css("color","red");
				
			if(uppercaseCharacter.test(password))
				$('#uppercaseCharacter').css("color","green");
			else
				$('#uppercaseCharacter').css("color","red");
				
			if(specialSymbol.test(password))
				$('#specialSymbol').css("color","green");
			else
				$('#specialSymbol').css("color","red");
			
			if(pswLength >= 10 && pswLength <= 32)
				$('#pswLength').css("color","green");
			else
				$('#pswLength').css("color","red");
				
			if(strongRegex.test(password)){
				$('#pswDescription').text("Strong password");
				$('#pswDescription').css("color","green");
			}
			else if(mediumRegex.test(password)){
				$('#pswDescription').text("Medium password");
				$('#pswDescription').css("color","orange");
			}
			else{
				$('#pswDescription').text("Weak password");
				$('#pswDescription').css("color","red");
			}
			
			if(passwordRepeat != '' && password != passwordRepeat){
				$('#pswRepeatDescription').text("Passwords do not match");
				$('#pswRepeatDescription').css("color","red");
			}else{
				$('#pswRepeatDescription').text("");
			}
		}
		
			
	});
	
	
	$("#passwordRepeat").on('input',function(){
		let password = $('#password').val();
		let passwordRepeat = $('#passwordRepeat').val();
		
		if(passwordRepeat === "" || passwordRepeat == null){
			$('#pswRepeatDescription').text("This is required field.");
			$('#pswRepeatDescription').css("color","red");
		}else{
			if(passwordRepeat != '' && password != passwordRepeat){
			$('#pswRepeatDescription').text("Passwords do not match");
			$('#pswRepeatDescription').css("color","red");
			}else{
				$('#pswRepeatDescription').text("");
			}
		}
	});
	
	/*Registrate patient on submit*/
	$('form#updatePasswordForm').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();

		let oldPassword = escapeHtml($('#oldPassword').val());
		let password = escapeHtml($('#password').val());
		let passwordRepeat = escapeHtml($('#passwordRepeat').val());
		
		if(oldPassword == null || oldPassword == "")
			return;
			
		if(password == null || password == "")
			return;
			
		if(passwordRepeat == null || passwordRepeat == "")
			return;
			
		if(password != passwordRepeat)
			return;
	
		
		var updatePasswordDTO = {
			"oldPassword": oldPassword,
			"newPassword": password
		};
		
		$('#updatePassword').attr("disabled",true);
		
		$.ajax({
			url: "/api/auth/profile/edit/new-password",
			headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       		},			
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(updatePasswordDTO),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful update password! Please, log in again.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				localStorage.clear();
				window.setTimeout(function(){window.location.href="login.html"},1000);
				return;
			},
			error: function (xhr) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText +
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#updatePassword').attr("disabled",false);
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