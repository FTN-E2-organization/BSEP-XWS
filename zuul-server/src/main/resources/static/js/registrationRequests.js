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

$(document).ready(function () {
			
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
	
});


function addRequestRow(r){
	
	let status = "approved";
	if(!r.isApproved) status = "disapproved";
	
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

function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};
