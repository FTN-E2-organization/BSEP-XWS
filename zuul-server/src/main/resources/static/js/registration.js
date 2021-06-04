$(document).ready(function () {
	
	/*Registrate patient on submit*/
	$('form#registration').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();

		let email = $('#email').val();
		let username = $('#username').val();
		let password = $('#password').val();
		
		let name = $('#name').val();
		let phone = $('#phone').val();
		let dateOfBirth = $('#dateOfBirth').val();
		let bio = $('#bio').val();
		let webSite = $('#webSite').val();
		let gender = "male";
		let allowedTagging = $('#allowedTagging').is(':checked');
		let allowedUnfollowerMessages = $('#allowedUnfollowerMessages').is(':checked');
		
		if($('#female').is(':checked')){
			gender = "female";
		}
		
		var profileDTO = {
			"email": email,
			"username": username,
			"password": password,
			"name": name,
			"phone": phone,
			"dateOfBirth": dateOfBirth,
			"bio": bio,
			"webSite": webSite,
			"gender": gender,
			"allowedTagging": allowedTagging,
			"allowedUnfollowerMessages": allowedUnfollowerMessages
		};
		
		$.ajax({
			url: "/api/auth/profile",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(profileDTO),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful registration!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			},
			error: function () {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">ERROR!' + 
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});		
	});

});