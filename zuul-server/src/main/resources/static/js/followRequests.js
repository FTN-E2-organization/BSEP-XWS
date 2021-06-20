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
		url: "/api/following/profile/received-requests",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(users){
			for(let u of users){
				addRowInTable(u);
			}					
		},
		error:function(){
			console.log('error getting follow requests');
		}
	});
	
});

function addRowInTable(u){
	
	let btn = '<button class="btn btn-success btn-sm" type="button" id="' + u.username +'" onclick="acceptRequest(this.id)">Accept</button>';
	let p ='PRIVATE';
	if(u.isPublic == true){
		p = 'PUBLIC';
	}
	let row = $('<tr><td>' + u.username + '</td>'+
				'<td>' + p + '</td>'+
				'<td>' + btn + '</td>'  + 
				'</tr>');
				
	$('#requests').append(row);
};

function acceptRequest(username){
	
	$.ajax({
				type:"PUT", 
				url: "/api/following/profile/delete-request/"+ username + "/" + loggedInUsername,
				headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		       	},
				contentType: "application/json",
				success:function(){
				
					$.ajax({
						type:"PUT", 
						url: "/api/following/profile/create-friendship/"+ username + "/" + loggedInUsername,
						headers: {
		            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		       			},
						contentType: "application/json",
							success:function(){
						location.reload();
						let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully accept follow request.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
						$('#div_alert').append(alert);
						return;
					},
					error:function(){
					console.log('error creating friendship');
					}
			});	
				
				},
				error:function(){
				console.log('error creating follow request');
				}
			});
			

};