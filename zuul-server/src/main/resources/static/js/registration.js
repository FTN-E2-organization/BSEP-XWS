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
	
	$("#email").on('input',function(){
		let email = $('#email').val();
		
		let emailRegex = /^[^\s@]+@[^\s@]+$/i
		
		if(emailRegex.test(email)){
			$('#emailDescription').text("");
		}else{
			$('#emailDescription').text("Wrong format of email.");
			$('#emailDescription').css("color","red");
		}
		
		if(email === "" || email == null){
			$('#emailDescription').text("This is required field.");
			$('#emailDescription').css("color","red");
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
	$('form#registration').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();

		let email = escapeHtml($('#email').val());
		let username = escapeHtml($('#username').val());
		let password = escapeHtml($('#password').val());
		let passwordRepeat = $('#passwordRepeat').val();
		
		let name = escapeHtml($('#name').val());
		let phone = escapeHtml($('#phone').val());
		let dateOfBirth = $('#dateOfBirth').val();
		let bio = escapeHtml($('#bio').val());
		let webSite = escapeHtml($('#webSite').val());
		let gender = "male";
		let isPublic = true;
		let allowedTagging = $('#allowedTagging').is(':checked');
		let allowedUnfollowerMessages = $('#allowedUnfollowerMessages').is(':checked');
		
		if($('#female').is(':checked')){
			gender = "female";
		}
		
		if($('#private').is(':checked')){
			isPublic = false;
		}
		
		if(username == null || username == "")
			return;
		
		if(email == null || email == "")
			return;
		
		if(password == null || password == "")
			return;
			
		if(passwordRepeat == null || passwordRepeat == "")
			return;
			
		if(password != passwordRepeat)
			return;
		
		var profileDTO = {
			"email": email,
			"username": username,
			"password": password,
			"name": name,
			"phone": phone,
			"dateOfBirth": dateOfBirth,
			"biography": bio,
			"webSite": webSite,
			"gender": gender,
			"isPublic": isPublic,
			"allowedTagging": allowedTagging,
			"allowedUnfollowerMessages": allowedUnfollowerMessages
		};
		
		$('#register').attr("disabled",true);
		
		$.ajax({
			url: "/api/auth/profile",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(profileDTO),
			success: function () {
				
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Wait, please.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				
				window.setTimeout(function(){
					$('#div_alert').empty();
					
					$.ajax({
					type:"GET", 
					url: "/api/auth/profile/" + profileDTO.username,
					contentType: "application/json",
					success:function(){
						let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful registration!'
							+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						window.setTimeout(function(){window.location.href="login.html"},1000);
						return;
					},
					error:function(){
						let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">An error occurred while creating the user. Please try again.' +
							 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						$('#register').attr("disabled",false);
						return;
					}
				});
				},3000);
				
				
			},
			error: function (xhr) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText +
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#register').attr("disabled",false);
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