checkUserRole("ROLE_REGULAR");
var loggedInUsername = getUsernameFromToken();
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
		url: "/api/notification/message/requests/" + loggedInUsername,
		contentType: "application/json",
		success:function(messageRequests){
			$('#messageRequestsBody').empty();
			for(let m of messageRequests)
				addMessageRequestToTable(m);
		},
		error:function(){
			console.log('error getting message requests');
		}
	});
	
});

function addMessageRequestToTable(m){
	
	let approveBtn = '<button class="btn btn-success btn-sm" type="button" id="' + m.idMongo +'" onclick="approveRequest(this.id)">Approve</button>';
	let rejectBtn = '<button class="btn btn-warning btn-sm" type="button" id="' + m.idMongo +'" onclick="rejectRequest(this.id)">Reject</button>';
	
	if(m.requestType == "rejected"){
		approveBtn = '';
		rejectBtn = '';
	}
	
	let row = $('<tr style="height:130px;"><td style="width:60%"><table>'
				+ '<tr><td style="font-size: 30px;"><b>' + m.senderUsername + '</b></td></tr>'
				+ '<tr><td><i>' + m.timestamp.split('T')[0] + "  " + m.timestamp.split('T')[1].substring(0, 5) + '</i></td></tr>'
				+ '<tr><td>' + m.text + '</td></tr>'
				+'</table></td>'
				+ '<td>' + approveBtn
				+ rejectBtn
				+'<button class="btn btn-danger btn-sm" type="button" id="' + m.idMongo +'" onclick="deleteRequest(this.id)">Delete</button></td></tr>');	
	$('#messageRequestsBody').append(row);	
}

function approveRequest(id){
	
	$.ajax({
		type:"PUT", 
		url: "/api/notification/message/approve/" + id,
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

function rejectRequest(id){
	
	$.ajax({
		type:"PUT", 
		url: "/api/notification/message/reject/" + id,
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

function deleteRequest(id){
	
	$.ajax({
		type:"PUT", 
		url: "/api/notification/message/delete/" + id,
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