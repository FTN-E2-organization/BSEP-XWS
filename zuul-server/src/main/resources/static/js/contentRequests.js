$(document).ready(function () {	

	$.ajax({
		type:"GET", 
		url: "/api/publishing/report-content",
		/*headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},*/
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
	let row = $('<tr><td style="vertical-align: middle;"><a style="color: blue;" href="onePost.html?id=' + r.contentId + '"><u>' + r.type + " " + r.contentId + '</u></a></td>'+
				'<td  style="vertical-align: middle;">' + r.reason + '</td>'+ 
				'<td  style="vertical-align: middle;">' + r.initiatorUsername + '</td>'+ 
				'<td width=20%><button class="btn btn-warning btn-sm" type="button" id="' + r.contentId +'" onclick="removeContent(this.id)">Remove content</button></td>'+
				'<td width=20%><button class="btn btn-danger btn-sm" type="button" id="' + r.contentId +'" onclick="deleteProfile(this.id)">Delete profile</button></td></tr>');	
	$('#requstsBodyTable').append(row);	
}