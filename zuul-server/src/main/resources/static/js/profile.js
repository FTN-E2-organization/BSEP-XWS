var username = "ana00";
var loggedInUsername = "pero123";
var isPublic;
var isFollow;
$(document).ready(function () {	

	$.ajax({
		type:"GET", 
		url: "/api/aggregation/profile-overview/" + username,
		contentType: "application/json",
		success:function(profile){
			isPublic = profile.isPublic;
			isFollow = profile.followers.includes(loggedInUsername);
			$('#username').append(profile.username);
			$('#name').append(profile.name);
			$('#dateOfBirth').append(profile.dateOfBirth);
			$('#biography').append(profile.biography);
			$('#website').append(profile.website);
			if(isPublic == true){
				$('#isPublic').append("PUBLIC");
			}else{
				$('#isPublic').append("PRIVATE");
			}
			
			$('#followers').empty();
			for (let f of profile.followers){
				addRowInTableFollowers(f);
			}
			
			$('#following').empty();
			for (let f of profile.following){
				addRowInTableFollowing(f);
			}
			
			let btn;
			if(isFollow == true){
				btn = '<button class="btn btn-info btn-sm" type="button" id="unfollow_btn" onclick="unfollow()">UNFOLLOW</button>'
			}else{
				btn = '<button class="btn btn-info btn-sm" type="button" id="follow_btn" onclick="follow()">FOLLOW</button>'
			}
			$('div#info-profile').append(btn);
					
					
		},
		error:function(){
			console.log('error getting profile info');
		}
	});
	
	if(isPublic == false){ 
			if(isFollow == false){
			//u ovom slucaju ne prikazuj postove i storije jer se ne prate i profil je privatan
			//u svakom drugom slucaju prikazi postove i slike
			}else{
	 			$.ajax({
				type:"GET", 
				url: "/api/aggregation/posts/" + username,
				contentType: "application/json",
				success:function(media){
					for(let m of media){
				addImage(m.path);
					}					
				},
				error:function(){
				console.log('error getting posts');
				}
				});
			}
	}

});

function addRowInTableFollowers(f){
	
	let row = $('<tr><td>' + f + '</td>' + 
				'</tr>');
				
	$('#followers').append(row);
};

function addRowInTableFollowing(f){
	
	let row = $('<tr><td>' + f + '</td>' + 
				'</tr>');
				
	$('#following').append(row);
};

function addImage(path){
	
	let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">'
						 + '<img height="250px" width="300px" src="http://localhost:8085/uploads/' + path + '">'
						 + '</div>');
			$('div#posts_images').append(image_div);
};

function follow(){
	if(isPublic==true){
	
		$.ajax({
				type:"PUT", 
				url: "/api/following/profile/create-friendship/"+ loggedInUsername + "/" + username,
				contentType: "application/json",
				success:function(){
					location.reload();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully following.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error:function(){
				console.log('error creating friendship');
				}
			});
			
	}else{
	
		$.ajax({
				type:"PUT", 
				url: "/api/following/profile/create-request/"+ loggedInUsername + "/" + username,
				contentType: "application/json",
				success:function(){
					location.reload();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully create follow request.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error:function(){
				console.log('error creating follow request');
				}
			});
		
	}
};

function unfollow(){

	$.ajax({
				type:"PUT", 
				url: "/api/following/profile/delete-friendship/"+ loggedInUsername + "/" + username,
				contentType: "application/json",
				success:function(){
					location.reload();
					let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully unfollowing.'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
					$('#div_alert').append(alert);
					return;
				},
				error:function(){
				console.log('error deleting friendship');
				}
			});
	
};