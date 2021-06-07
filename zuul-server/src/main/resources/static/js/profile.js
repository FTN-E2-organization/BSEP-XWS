var username = "ana00";

var searchedUsername = localStorage.getItem("contentName");

var loggedInUsername = "pero123";
var isPublic;
var isFollow;

$(document).ready(function () {	

	$.ajax({
		type:"GET", 
		url: "/api/aggregation/profile-overview/" + searchedUsername,
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
			if(searchedUsername != loggedInUsername){
				$('div#info-profile').append(btn);
			}
			
			if(isFollow == true){
				
				$.ajax({
					type:"GET", 
					url: "/api/following/profile/close/" + loggedInUsername + "/" + username,
					contentType: "application/json",
					success:function(isClose){
					let close;
					if(isClose == true){
						close='<h6 style="color:green;">CLOSED FRIEND</h6><button class="btn btn-info btn-sm" type="button" id="remove_btn" onclick="removeClosed()">REMOVE FROM CLOSES</button>'
					}else{
						close='<button class="btn btn-success btn-sm" type="button" id="close_btn" onclick="addClosed()">ADD TO CLOSES</button>'
					}		
					$('div#info-profile').append(close);
				},
				error:function(){
				console.log('error getting close followers');
				}
				});
			}	
					
		},
		error:function(){
			console.log('error getting profile info');
		}
	});
	
	$.ajax({
        type: "GET",
        url: "/api/aggregation/highlight/" + username,
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
                        addStory(url);
                    })
                    .catch(() => alert('oh no!'));


            }
        },
        error: function() {
            console.log('error getting stories');
        }
    }); 
    
    $.ajax({
        type: "GET",
        url: "/api/aggregation/posts/" + username,
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
                    .catch(() => alert('oh no!'));

            }
        },
        error: function() {
            console.log('error getting posts');
        }
    });
    
	
	if(isPublic == false){ 
			if(isFollow == false){
			//u ovom slucaju ne prikazuj postove i storije jer se ne prate i profil je privatan
			//u svakom drugom slucaju prikazi postove i slike
			}else{
	 			
    
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


function addStory(path) {

    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        '<img class="rounded-circle" height="90px" width="70px"  src="' + path + '">' +
        '</div>');
    $('div#story_images').append(image_div);
};

function addPost(path, postId) {

alert(path);
    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        ' <a href="onePost.html?id=' + postId + ' "> <img height="250px" width="300px"  src="' + path + '">' +
        '</a> </div>');
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

function addClosed(){

	let isClosed = true;

	$.ajax({
				type:"PUT", 
				url: "/api/following/profile/close/" + loggedInUsername + "/" + username + "/" + isClosed,
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
				url: "/api/following/profile/close/" + loggedInUsername + "/" + username + "/" + isClosed,
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