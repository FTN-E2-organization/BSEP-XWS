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

checkUserRole("ROLE_ADMIN");
var username = getUsernameFromToken();
var resultsList = null;

$(document).ready(function () {
	
	getProfiles();
	
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
		}else{
			$('#webSiteDescription').text("");
		}
	});
	
	
	$('#username').on('input',function(){
		let username = $('#username').val();
		
		$('#resultHidden').attr("hidden",false);
    	getSearchedProfiles();
    	
    	if(username == "" || username == null){
			$('#resultHidden').attr("hidden",true);
			$('#usernameDescription').text("This is required field.");
			$('#usernameDescription').css("color","red");
			
			$('#email').val('');
			$('#webSite').val('');
		}else{
			$('#usernameDescription').text("");
		}
	});	
			
	/*Getting disapproved requests*/
	$.ajax({
		type:"GET", 
		url: "/api/auth/agent-registration/disapproved",
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
	
	/*Click on username*/
	$("#resultHidden").delegate("tr.result", "click", function(){
        let username = $(this).text();
     
		$('#username').val(username);   
		
		for (let i = 0; i < resultsList.length; i++) 
			if(resultsList[i].username == username){
				$('#email').val(resultsList[i].email);
				$('#webSite').val(resultsList[i].website);
				break;
			}
		
    });
    
    /*Send request for registration*/
	$('form#agentRegistration').submit(function (event) {

		event.preventDefault();
	
		$('#div_alert').empty();

		let email = escapeHtml($('#email').val());
		let webSite =escapeHtml($('#webSite').val());
		let username =escapeHtml($('#username').val());
		
		
		var requestDTO = {
			"email": email,
			"webSite": webSite,
			"username":username
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


function addRequestRow(r){
	
	let row = $('<tr><td style="vertical-align: middle;">' + r.timestamp.split('T')[0] + "  " + r.timestamp.split('T')[1].substr(0,5) + '</td>'+
				'<td  style="vertical-align: middle;">' + r.username + '</td>'+ 
				'<td  style="vertical-align: middle;">' + r.email + '</td>'+ 
				'<td  style="vertical-align: middle;">' + r.webSite + '</td>'+ 
				'<td width=20%><button class="btn btn-success btn-sm" type="button" id="' + r.id +'" onclick="approveRequest(this.id)">Approve</button></td>' +
				'<td width=20%><button class="btn btn-danger btn-sm" type="button" id="' + r.id +'" onclick="deleteRequest(this.id)">Delete</button></td></tr>');	
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


function approveRequest(id){
	
	$.ajax({
		type:"PUT", 
		url: "/api/auth/agent-registration/approve/" + id,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful approved request!'
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

function getProfiles(){
	
	$.ajax({
        url: "/api/auth/profile",
		type: 'GET',
		contentType: 'application/json',
        success: function (results) {
			resultsList = results;
        },
        error: function () {
            console.log('Error');
        }
    });		
}

function getSearchedProfiles(){
	$('#resultBody').empty();
		
	let username = escapeHtml($('#username').val());
	if (username == "")
		return;
	
	for (let i = 0; i < resultsList.length; i++) {
		if(resultsList[i].username.toLowerCase().includes(username.toLowerCase()))
			addProfileRow(resultsList[i]);
	}	
}


function addProfileRow(result) {	
	let row = $('<tr class="result"><td class="resultUsername">' + result.username + '</td></tr>');	
	$('#resultBody').append(row);	
}

function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};
