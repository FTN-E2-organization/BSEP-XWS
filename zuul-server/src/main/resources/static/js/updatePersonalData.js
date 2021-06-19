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

checkUserRole("ROLE_REGULAR");
var username = getUsernameFromToken();
var roles = getRolesFromToken();

$(document).ready(function () {
	
	if(roles.indexOf("ROLE_AGENT") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
	}
	else if(roles.indexOf("ROLE_REGULAR") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
	}
		
	$.ajax({
		type:"GET", 
		url: "/api/auth/profile/" + username,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(profileDTO){
			fillProfileInfo(profileDTO);
		},
		error:function(){
			console.log('error getting profile');
		}
	});
	
	/*Update personal data on submit*/
	$('form#updatePersonal').submit(function (event) {

		event.preventDefault();
	
		$('#div_alert').empty();

		let email = escapeHtml($('#email').val());
		let newUsername = escapeHtml($('#username').val());
		let name = escapeHtml($('#name').val());
		let phone = escapeHtml($('#phone').val());
		let dateOfBirth = $('#dateOfBirth').val();
		let bio = escapeHtml($('#bio').val());
		let webSite =escapeHtml($('#webSite').val());
		let gender = "male";
		
		if($('#female').is(':checked')){
			gender = "female";
		}
		
		var profileDTO = {
			"email": email,
			"username": newUsername,
			"name": name,
			"phone": phone,
			"dateOfBirth": dateOfBirth,
			"biography": bio,
			"webSite": webSite,
			"gender": gender,
		};
		
		
		$.ajax({
			url: "/api/auth/profile/personal",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	       	},
			type: 'PUT',
			contentType: 'application/json',
			data: JSON.stringify(profileDTO),
			success: function () {
				
				if(profileDTO.username != getUsernameFromToken()){
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful updating data! Please, log in again.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					localStorage.clear();
					window.setTimeout(function(){window.location.href="login.html"},1000);
				}else{
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful updating data!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					window.setTimeout(function(){window.location.href="myProfile.html"},1000);
				}
				
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

function fillProfileInfo(profileDTO){
	$('#email').val(profileDTO.email);
	$('#username').val(profileDTO.username);
	$('#name').val(profileDTO.name);
	$('#dateOfBirth').val(profileDTO.dateOfBirth);
	$('#phone').val(profileDTO.phone);
	$('#bio').val(profileDTO.biography);
	$('#webSite').val(profileDTO.webSite);
	
	if(profileDTO.gender == "male"){
		$('#male').prop("checked",true);
	}else{
		$('#female').prop("checked",true);
	}
}

function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};