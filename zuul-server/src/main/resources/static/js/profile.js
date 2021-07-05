var params = (new URL(window.location.href)).searchParams;
var searchedUsername = params.get("id");

var loggedInUsername = getUsernameFromToken();

var isPublic;
var isFollow;
var isBlocked;
var isClose;
var isMuted;
var isVerified;

var profileUsername;

$(document).ready(function () {
	
	if(loggedInUsername == null){
		$('head').append('<script type="text/javascript" src="../js/navbar/unauthenticated_user.js"></script>');
	}else{
		let roles = getRolesFromToken();
		if(roles.indexOf("ROLE_AGENT") > -1){
			$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
		}
		else if(roles.indexOf("ROLE_REGULAR") > -1){
			$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
		}
	}
	
	if(searchedUsername == loggedInUsername){
		location.href = "myProfile.html"
	}
	

	$.ajax({
		type:"GET", 
		url: "/api/aggregation/profile-overview/" + searchedUsername,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(profile){
			
			if(profile.isBlocked){
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Profile is blocked.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				setTimeout(function () {history.back();}, 1000);
				return;
			}
			
			isPublic = profile.isPublic;
			isFollow = profile.followers.includes(loggedInUsername);
			isVerified = profile.isVerified;
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
			if(profile.isVerified == true){
				$('#isVerified').append("VERIFIED");
					$.ajax({
						type:"GET", 
						url: "/api/auth/profile/category/" + searchedUsername,
						headers: {
				            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
				       	},
						contentType: "application/json",
						success:function(categoryDto){
							$('#categoryName').append(categoryDto.name);
							
						},
						error: function (xhr) {
							let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
						    '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							return;
						}
					});
			}else{
				$('#isVerified').append("");
			}
			
			
			$('#followers').empty();
			for (let f of profile.followers){
				addRowInTableFollowers(f);
			}
			
			$('#following').empty();
			for (let f of profile.following){
				addRowInTableFollowing(f);
			}
			
			if(loggedInUsername != null){
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
			let btnNotificationSettings;
			if(loggedInUsername != null){
				if(isBlocked == false){
					if(isFollow == true){
						fillInTheCheckbox();
						btn = '<button class="btn btn-info btn-sm" type="button" id="unfollow_btn" onclick="unfollow()">UNFOLLOW</button>';
						btnNotificationSettings = '<button onclick="fillInTheCheckbox()" class="btn btn-info btn-sm" data-toggle="modal" data-target="#centralModalNotificationSettings" class="btn btn-link" id="notificationSettings_btn" >Notification settings</button>';
					}else{
						btn = '<button class="btn btn-info btn-sm" type="button" id="follow_btn" onclick="follow()">FOLLOW</button>'
					}
					if(searchedUsername != loggedInUsername){
						$('div#info-profile').append(btn);
						let mainBtn = '<button class="btn btn-info btn-sm dropdown-toggle" type="button" id="dropDownMainBtn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Status</button>' + 
									  '<div class="dropdown-menu" aria-labelledby="dropDownMainBtn" id="mainMenu"></div>';
						$('div#info-profile').append(mainBtn);
						$('div#info-profile').append(btnNotificationSettings);
					}
				}else{
					let mainBtn = '<button class="btn btn-info btn-sm dropdown-toggle" type="button" id="dropDownMainBtn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Status</button>' + 
									  '<div class="dropdown-menu" aria-labelledby="dropDownMainBtn" id="mainMenu"></div>';
					$('div#info-profile').append(mainBtn);
				}
			}
			
			
			let block_btn;
			if(isBlocked == false){
				block_btn = '<a class="dropdown-item" href="#" onclick="block()"  id="block_btn" style="color:red;">BLOCK</a>';
				//block_btn = '<button class="btn btn-danger btn-sm" type="button" id="block_btn" onclick="block()">BLOCK</button>';
			}else{
				block_btn = '<a class="dropdown-item" href="#" onclick="unblock()"  id="block_btn" >UNBLOCK</a>';
				//block_btn = '<button class="btn btn-info btn-sm" type="button" id="block_btn" onclick="unblock()">UNBLOCK</button>';
			}
			if(searchedUsername != loggedInUsername){
				//$('div#info-profile').append(block_btn);
				$('div#mainMenu').append(block_btn);
			}
			
			checkIfProfileBlocked();
			
			
			if( (isPublic == false && isFollow == false)){
			//samo u ovom slucaju ne prikazuj postove i storije	
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
                    .catch(() => console.log('error'));

            		}
        			},
        			error: function() {
            		console.log('error getting posts');
        			}
    			});	
    			
    			 $.ajax({
        type: "GET",
        url: "/api/aggregation/ads/" + searchedUsername,
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
                        $.ajax({
						type:"GET", 
						url: "/api/campaign/ad/" + m,
						headers: {
				            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
				       	},
						contentType: "application/json",
						success:function(ad){
									 $.ajax({
										type:"GET", 
										url: "/api/campaign/is-post/" + ad.campaignId,
										headers: {
								            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
								       	},
										contentType: "application/json",
										success:function(isPost){
											
											if(isPost==true){
												 addAdPost(url, m); 
											}else{
												 addAdStory(url, m); 
											}
											
										},
										error: function () {
											return;
										}
									});
							
							
						},
						error: function () {
							return;
						}
					});
					
					
                       
                    })
                    .catch(() => console.log('error'));

            }
        },
        error: function() {
            console.log('error getting ads');
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
						close = '<a class="dropdown-item" href="#" id="remove_btn" onclick="removeClosed()">REMOVE FROM CLOSES</a>';
						//close='<button class="btn btn-info btn-sm" type="button" id="remove_btn" onclick="removeClosed()">REMOVE FROM CLOSES</button>'
					}else{
						close = '<a class="dropdown-item" href="#" id="close_btn" onclick="addClosed()" style="color:green;">ADD TO CLOSES</a>';
						//close='<button class="btn btn-success btn-sm" type="button" id="close_btn" onclick="addClosed()">ADD TO CLOSES</button>'
					}		
					//$('div#info-profile').append(close);
					$('div#mainMenu').append(close);
					
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
						mute = '<a class="dropdown-item" href="#" id="remove_btn" onclick="removeMuted()">UNMUTE</a>';
						//mute='<button class="btn btn-info btn-sm" type="button" id="remove_btn" onclick="removeMuted()">UNMUTE</button>'
					}else{
						mute = '<a class="dropdown-item" href="#" id="close_btn" onclick="addMuted()" >MUTE</a>';
						//mute='<button class="btn btn-info btn-sm" type="button" id="close_btn" onclick="addMuted()">MUTE</button>'
					}		
					//$('div#info-profile').append(mute);
					$('div#mainMenu').append(mute);
					
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
			
		}else{
			
			if(isPublic == true){
				
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
                    .catch(() => console.log('error'));

            		}
        			},
        			error: function() {
            		console.log('error getting posts');
        			}
    			});				
			}		
		}
			
					
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

function addAdStory(path, id) {

    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        '<a  id="' + id +'" onclick="func1(this.id)";><video max-height="80px" height="80px" width="70px"  poster="' + path + '"><source src= "' + path + '" type="video/mp4"></video></a>' +
        '</div>');
    $('div#story_images').append(image_div);
};

function addAdPost(path, postId) {

    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        ' <a href="postAd.html?id=' + postId + ' "><video id="' + postId +'" max-height="250px" width="300px"  poster="' + path + '">' +
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
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully following.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				window.setTimeout(function(){ location.reload(); },1000);
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
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully create follow request.'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#follow_btn').attr("disabled",true);
				$('#follow_btn').text("Request sent");
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
	window.location.href = "https://localhost:8111/html/story.html?id=" + id;
};

function func1(id){
	window.location.href = "https://localhost:8111/html/storyAd.html?id=" + id;
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


function saveNotificationSettings() {
	var dto = {
		"activeLikesNotification": $('#activeLikesNotification').is(":checked"),
		"activeCommentNotification": $('#activeCommentNotification').is(":checked"),
		"activeStoryNotification": $('#activeStoryNotification').is(":checked"),
		"activePostNotification": $('#activePostNotification').is(":checked"),
		"activeMessageNotification": $('#activeMessageNotification').is(":checked"),
		"followingUsername": searchedUsername
	};	
	$.ajax({
			type:"POST", 
			url: "/api/following/profile/notification-settings",
			headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       		},			
			contentType: "application/json",
			data: JSON.stringify(dto),
			success:function(){
  				console.log("success");	
				$('#centralModalNotificationSettings').modal('hide');
				fillInTheCheckbox();
			},
			error:function(xhr){
				console.log('error saving notification - ' + xhr.responseText);
			}
	});			
}



function fillInTheCheckbox() {	
	$.ajax({
			type:"GET", 
			url: "/api/following/profile/notification-settings/" + searchedUsername,
			headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       		},			
			contentType: "application/json",
			success:function(dto){
  				console.log("success");	
  				var x1 = document.getElementById("activeLikesNotification");
  				x1.checked = dto.activeLikesNotification;			
  				var x2 = document.getElementById("activeCommentNotification");
  				x2.checked = dto.activeCommentNotification;
		  		var x3 = document.getElementById("activeStoryNotification");
  				x3.checked = dto.activeStoryNotification;		
  				var x4 = document.getElementById("activePostNotification");
  				x4.checked = dto.activePostNotification;		
  				var x5 = document.getElementById("activeMessageNotification");
  				x5.checked = dto.activeMessageNotification;
			},
			error:function(xhr){
				console.log('error getting saving notification - ' + xhr.responseText);
			}
	});				
}

function checkIfProfileBlocked(){
	$.ajax({
		type:"GET", 
		url: "/api/following/profile/blocked/" + searchedUsername,
		headers: {
    		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		},
		contentType: "application/json",
		success:function(profiles){
			for(let profile of profiles){
				if(profile.username == loggedInUsername){
					$('#container_posts').attr('hidden',true);
					$('#container_stories').attr('hidden',true);
				}
			}
		},
		eror:function(){}
	});
}

