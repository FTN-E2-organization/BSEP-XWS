checkUserRole("ROLE_ADMIN");

$(document).ready(function () {	

	$.ajax({
		type:"GET", 
		url: "/api/publishing/report-content",
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
	
	let link = "onePost.html?id=" + r.contentId + "";
	if(r.type == "story")
		link = "story.html?id=" + r.contentId + "";
	
	let row = $('<tr><td style="vertical-align: middle;"><a style="color: blue;" href=' + link + '><u>' + r.type + /*" " + r.contentId + */'</u></a></td>'+
				'<td  style="vertical-align: middle;">' + r.reason + '</td>'+ 
				'<td  style="vertical-align: middle;">' + r.initiatorUsername + '</td>'+ 
				'<td width=20%><button class="btn btn-warning btn-sm" type="button" id="' + r.contentId + '/' + r.type +'" onclick="removeContent(this.id)">Remove content</button></td>'+
				'<td width=20%><button class="btn btn-danger btn-sm" type="button" id="' + r.ownerUsername +'" onclick="blockProfile(this.id)">Block profile</button></td></tr>');	
	$('#requstsBodyTable').append(row);	
}

function removeContent(id){
	
	contentId = id.split('/')[0];
	type = id.split('/')[1];
		
	$.ajax({
		type:"PUT", 
		url: "/api/publishing/report-content/remove/" + contentId + "/" + type,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful removed content!'
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

function blockProfile(username){
	
	$.ajax({
		type:"PUT", 
		url: "/api/auth/profile/block/" + username,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful blocked profile!'
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