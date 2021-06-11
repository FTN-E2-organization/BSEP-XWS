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
	
	/*Registrate patient on submit*/
	$('form#registration').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();

		let email = escapeHtml($('#email').val());
		let username = escapeHtml($('#username').val());
		let password = escapeHtml($('#password').val());
		
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
		
		$.ajax({
			url: "/api/auth/profile",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(profileDTO),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful registration!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				window.setTimeout(function(){window.location.href="login.html"},1000);
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