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
	
	
		
	$("#webSite").on('input',function(){
		let webSite = $('#webSite').val();
				
		if(webSite === "" || webSite == null){
			$('#webSiteDescription').text("This is required field.");
			$('#webSiteDescription').css("color","red");
		}
	});
	
	
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
	
	/*Getting sent requests*/
	$.ajax({
		type:"GET", 
		url: "/api/auth/agent-registration",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(requestDTOs){
			$('#requstsBodyTable').empty();
			for(let r of requestDTOs)
				addRequestRow(r);
		},
		error:function(){
			console.log('error getting requests');
		}
	});
	
	/*Update personal data on submit*/
	$('form#agentRegistration').submit(function (event) {

		event.preventDefault();
	
		$('#div_alert').empty();

		let email = escapeHtml($('#email').val());
		let webSite =escapeHtml($('#webSite').val());
		
		
		var requestDTO = {
			"email": email,
			"webSite": webSite
		};
		
		
		$.ajax({
			url: "/api/auth/agent-registration",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	       	},
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(requestDTO),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful creating request.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);	
				window.setTimeout(function(){window.location.reload();},1000);			
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
	$('#webSite').val(profileDTO.webSite);

}

function addRequestRow(r){
	
	let row;
	let status = "approved";
	if(!r.isApproved) status = "disapproved";
	
	if(status == "approved"){
		row = $('<tr><td style="vertical-align: middle;">' + r.timestamp.split('T')[0] + "  " + r.timestamp.split('T')[1].substr(0,5) + '</td>'+
				'<td  style="vertical-align: middle;">' + r.email + '</td>'+ 
				'<td  style="vertical-align: middle;">' + r.webSite + '</td>'+ 
				'<td  style="vertical-align: middle;">' + status + '</td></tr>');	
	}
	else{
		row = $('<tr><td style="vertical-align: middle;">' + r.timestamp.split('T')[0] + "  " + r.timestamp.split('T')[1].substr(0,5) + '</td>'+
					'<td  style="vertical-align: middle;">' + r.email + '</td>'+ 
					'<td  style="vertical-align: middle;">' + r.webSite + '</td>'+ 
					'<td  style="vertical-align: middle;">' + status + '</td>'+ 
					'<td width=20%><button class="btn btn-danger btn-sm" type="button" id="' + r.id +'" onclick="deleteRequest(this.id)">Delete</button></td></tr>');	
		}
	$('#requstsBodyTable').append(row);	
}

function deleteRequest(id){
	
	$.ajax({
		type:"PUT", 
		url: "/api/auth/agent-registration/delete/" + id,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful deleted request!'
			+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			window.setTimeout(function(){window.location.reload(); },1000);
			return;
		},
		error:function(xhr){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText +
			'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
	});
	
}

function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};
