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
	
	/*Update profile privacy on submit*/
	$('form#updatePrivacy').submit(function (event) {

		event.preventDefault();
	
		$('#div_alert').empty();
		
		let isPublic = true;
		let allowedTagging = $('#allowedTagging').is(':checked');
		let allowedUnfollowerMessages = $('#allowedUnfollowerMessages').is(':checked');
		
		let allowedAllLikes = $('#allowedAllLikes').is(':checked');
		let allowedAllComments = $('#allowedAllComments').is(':checked');
		let allowedAllMessages = $('#allowedAllMessages').is(':checked');
		
		if($('#private').is(':checked')){
			isPublic = false;
		}
		
		var profileDTO = {
			"isPublic": isPublic,
			"allowedTagging": allowedTagging,
			"allowedUnfollowerMessages": allowedUnfollowerMessages,
			"allowedAllLikes":allowedAllLikes,
			"allowedAllComments":allowedAllComments,
			"allowedAllMessages":allowedAllMessages
		};
		
		$.ajax({
			url: "/api/auth/profile/privacy",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	       	},
			type: 'PUT',
			contentType: 'application/json',
			data: JSON.stringify(profileDTO),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful updating profile privacy!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				window.setTimeout(function(){window.location.href="myProfile.html"},1000);
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
		
	if(profileDTO.isPublic){
		$('#public').prop("checked",true);
	}else{
		$('#private').prop("checked",true);
	}
	
	$('#allowedTagging').prop("checked",profileDTO.allowedTagging);
	$('#allowedUnfollowerMessages').prop("checked",profileDTO.allowedUnfollowerMessages);
	
	$('#allowedAllLikes').prop("checked",profileDTO.allowedAllLikes);
	$('#allowedAllComments').prop("checked",profileDTO.allowedAllComments);
	$('#allowedAllMessages').prop("checked",profileDTO.allowedAllMessages);
}

function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};