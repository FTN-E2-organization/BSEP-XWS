var loggedInUsername = "pero123";
$(document).ready(function () {	

	$.ajax({
		type:"GET", 
		url: "/api/following/profile/received-requests/" + loggedInUsername,
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
				contentType: "application/json",
				success:function(){
				
					$.ajax({
						type:"PUT", 
						url: "/api/following/profile/create-friendship/"+ username + "/" + loggedInUsername,
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