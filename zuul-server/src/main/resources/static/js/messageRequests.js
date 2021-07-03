/*checkUserRole("ROLE_REGULAR");
var loggedInUsername = getUsernameFromToken();
var roles = getRolesFromToken();*/

loggedInUsername = "ada";

$(document).ready(function () {	
	
	$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');

	/*if(roles.indexOf("ROLE_AGENT") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
	}
	else if(roles.indexOf("ROLE_REGULAR") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
	}*/
	
	$.ajax({
		type:"GET", 
		url: "/api/notification/message/requests/" + loggedInUsername,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
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
	let row = $('<tr style="height:130px;"><td style="width:60%"><table>'
				+ '<tr><td style="font-size: 30px;"><b>' + m.senderUsername + '</b></td></tr>'
				+ '<tr><td><i>' + m.timestamp.split('T')[0] + "  " + m.timestamp.split('T')[1].substring(0, 5) + '</i></td></tr>'
				+ '<tr><td>' + m.text + '</td></tr>'
				+'</table></td>'
				+ '<td><button class="btn btn-success btn-sm" type="button" id="approveRequest">Approve</button>'
				+ '<button class="btn btn-warning btn-sm" type="button" id="rejectRequest">Reject</button>'
				+'<button class="btn btn-danger btn-sm" type="button" id="deleteRequest">Delete</button></td></tr>');	
	$('#messageRequestsBody').append(row);	
}