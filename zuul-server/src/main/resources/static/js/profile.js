var params = (new URL(window.location.href)).searchParams;
var searchedUsername = params.get("id");

var loggedInUsername = getUsernameFromToken();

var isPublic;
var isFollow;
var isBlocked;
var isClose;
var isMuted;

$(document).ready(function () {	

	$.ajax({
		type:"GET", 
		url: "/api/aggregation/profile-overview/" + searchedUsername,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
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
			
			$.ajax({
				type:"GET", 
				url: "/api/following/profile/blocked/" + loggedInUsername,
				headers: {
            		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       		},
				contentType: "application/json",
				success:function(profiles){
		
				let profilesUsernames =[];
				for(let p of profiles){
					profilesUsernames.push(p.username);
				}
				isBlocked = profilesUsernames.includes(searchedUsername);
				
				let btn;
			if(isBlocked == false){
			if(isFollow == true){
				btn = '<button class="btn btn-info btn-sm" type="button" id="unfollow_btn" onclick="unfollow()">UNFOLLOW</button>'
			}else{
				btn = '<button class="btn btn-info btn-sm" type="button" id="follow_btn" onclick="follow()">FOLLOW</button>'
			}
			if(searchedUsername != loggedInUsername){
				$('div#info-profile').append(btn);
			}
			}
			
			let block_btn;
			if(isBlocked == false){
				block_btn = '<button class="btn btn-danger btn-sm" type="button" id="block_btn" onclick="block()">BLOCK</button>';
			}else{
				block_btn = '<button class="btn btn-info btn-sm" type="button" id="block_btn" onclick="unblock()">UNBLOCK</button>';
			}
			if(searchedUsername != loggedInUsername){
				$('div#info-profile').append(block_btn);
			}
			
			
			if(isPublic == false && isFollow == false){
					alert('uslo');				
			}else{
					
					$.ajax({
       				 type: "GET",
      			  url: "/api/aggregation/highlight/" + searchedUsername,
    		    headers: {
          	  'Authorization': 'Bearer ' + window.localStorage.getItem('token')
      	 	},
        contentType: "application/json",
        success: function(media) {
        	let grouped={}
        	for(let m of media){
  				if(grouped[m.idContent]){
  				grouped[m.idContent].push(m)
  				}      	else{
  				grouped[m.idContent]=[m]
  				}
        	}
        
            for (let m in grouped) {
				
                fetch('/api/media/files/' +grouped[m][0].path)
                    .then(resp => resp.blob())
                    .then(blob => {
                        const url = window.URL.createObjectURL(blob);
                        addStory(url, m);
                    })
                    .catch(() => console('error'));


            }
        },
        error: function() {
            console.log('error getting stories');
        }
    }); 
    
    $.ajax({
        type: "GET",
        url: "/api/aggregation/posts/" + searchedUsername,
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
        contentType: "application/json",
        success: function(media) { 
        	let grouped={}
        	for(let m of media){
  				if(grouped[m.idContent]){
  				grouped[m.idContent].push(m)
  				}      	else{
  				grouped[m.idContent]=[m]
  				}
        	}
        
            	for (let m in grouped) {
                fetch('/api/media/files/' +grouped[m][0].path)
                    .then(resp => resp.blob())
                    .then(blob => {
                        const url = window.URL.createObjectURL(blob);
                        addPost(url, m); 
                    })
                    .catch(() => console('error'));

            		}
        			},
        			error: function() {
            		console.log('error getting posts');
        			}
    			});				
		}
			
			
			if(isFollow == true){
				
				$.ajax({
					type:"GET", 
					url: "/api/following/profile/close/" + searchedUsername,
					headers: {
			            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
			       	},
					contentType: "application/json",
					success:function(isCls){
					isClose = isCls;
					let close;
					if(isClose == true){
						close='<button class="btn btn-info btn-sm" type="button" id="remove_btn" onclick="removeClosed()">REMOVE FROM CLOSES</button>'
					}else{
						close='<button class="btn btn-success btn-sm" type="button" id="close_btn" onclick="addClosed()">ADD TO CLOSES</button>'
					}		
					$('div#info-profile').append(close);
					
					$.ajax({
					type:"GET", 
					url: "/api/following/profile/muted/" + searchedUsername,
					headers: {
			            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
			       	},
					contentType: "application/json",
					success:function(isMt){
					isMuted=isMt;
					let mute;
					if(isMuted == true){
						mute='<button class="btn btn-info btn-sm" type="button" id="remove_btn" onclick="removeMuted()">UNMUTE</button>'
					}else{
						mute='<button class="btn btn-info btn-sm" type="button" id="close_btn" onclick="addMuted()">MUTE</button>'
					}		
					$('div#info-profile').append(mute);
					
				if(isClose == true){
				  $('div#info-profile').append('<h6 style="color:green;">CLOSE FRIEND</h6>');
				}
				if(isMuted == true){
				$('div#info-profile').append('<h6 style="color:red;">MUTED FRIEND</h6>');
				}
				
					
				},
				error:function(){
				console.log('error getting close followers');
				}
				});
					
				},
				error:function(){
				console.log('error getting close followers');
				}
				});
				
			}	
			
			
				
			},
			error:function(){
			console.log('error getting blocking profiles');
			}
			});
			
					
		},
		error:function(){
			console.log('error getting profile info');
		}
	});
	
	

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


function addStory(path, id) {

    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        '<a  id="' + id +'" onclick="func(this.id)";><video max-height="80px" height="80px" width="70px"  poster="' + path + '"><source src= "' + path + '" type="video/mp4"></video></a>' +
        '</div>');
    $('div#story_images').append(image_div);
};

function addPost(path, postId) {

    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        ' <a href="onePost.html?id=' + postId + ' "><video id="' + postId +'" max-height="250px" width="300px"  poster="' + path + '">' +
        '<source src= "' + path + '" type="video/mp4"></video></a> </div>');
    $('div#posts_images').append(image_div);
    
    $('#' + id).trigger('play');
};

function follow(){
	if(isPublic==true){
	
		$.ajax({
			type:"PUT", 
			url: "/api/following/profile/create-friendship/" + loggedInUsername + "/"+ searchedUsername,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	       	},
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
			url: "/api/following/profile/create-request/" + searchedUsername,
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	       	},
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
		url: "/api/following/profile/delete-friendship/" + searchedUsername,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
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

function addClosed(){

	let isClosed = true;

	$.ajax({
		type:"PUT", 
		url: "/api/following/profile/close/" + searchedUsername + "/" + isClosed,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			location.reload();
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully add to closes.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		},
		error:function(){
		console.log('error adding to closes');
		}
	});
	
};

function removeClosed(){

	let isClosed = false;

	$.ajax({
		type:"PUT", 
		url: "/api/following/profile/close/" + searchedUsername + "/" + isClosed,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			location.reload();
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully removing from closes.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		},
		error:function(){
		console.log('error removing to closes');
		}
	});
	
};

function func(id){
	window.location.href = "http://localhost:8111/html/story.html?id=" + id;
};

function block(){

	$.ajax({
		type:"PUT", 
		url: "/api/following/profile/block/" + searchedUsername,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			location.reload();
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully block profile.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		},
		error:function(){
		console.log('error block profile');
		}
	});
	
};

function unblock(){

	$.ajax({
		type:"PUT", 
		url: "/api/following/profile/unblock/" + searchedUsername,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			location.reload();
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully unblock profile.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		},
		error:function(){
		console.log('error unblock profile');
		}
	});
	
};

function addMuted(){

	let isMuted = true;

	$.ajax({
		type:"PUT", 
		url: "/api/following/profile/muted/" + searchedUsername + "/" + isMuted,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			location.reload();
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully mute friend.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		},
		error:function(){
		console.log('error mute friend');
		}
	});
	
};

function removeMuted(){

	let isMuted = false;

	$.ajax({
		type:"PUT", 
		url: "/api/following/profile/muted/" + searchedUsername + "/" + isMuted,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(){
			location.reload();
			let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully unmute friend.'
				+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		},
		error:function(){
		console.log('error unmute friend');
		}
	});
	
};