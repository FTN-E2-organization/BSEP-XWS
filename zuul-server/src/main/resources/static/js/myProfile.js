checkUserRole("ROLE_REGULAR");
var username = getUsernameFromToken();
var roles = getRolesFromToken();

$(document).ready(function() {
		
	if(roles.indexOf("ROLE_AGENT") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
	}
	else if(roles.indexOf("ROLE_REGULAR") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
	}
	
    $.ajax({
        type: "GET",
        url: "/api/aggregation/profile-overview/" + username,
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
        contentType: "application/json",
        success: function(profile) {
            $('#username').append(profile.username);
            $('#name').append(profile.name);
            $('#dateOfBirth').append(profile.dateOfBirth);
            $('#biography').append(profile.biography);
            $('#website').append(profile.website);
            if (profile.isPublic == true) {
                $('#isPublic').append("PUBLIC");
            } else {
                $('#isPublic').append("PRIVATE");
            }
        
            if(profile.isVerified == true){
				$('#isVerified').append("VERIFIED");
				$('div#request').empty();
					$.ajax({
						type:"GET", 
						url: "/api/auth/profile/category/" + username,
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
				//da li ima aktivan zahtjev, ako nema ostavi link
				$.ajax({
						type:"GET", 
						url: "/api/auth/profile/verification/exist/" + username,
						headers: {
				            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
				       	},
						contentType: "application/json",
						success:function(exist){
							if(exist == false){
								$('div#request').append('<div><a style="color:white;" href="verificationRequest.html"><u>Request verification</u></a></div>');
							
							}else{
								$('div#request').append('<label>Requested verification</label>');
							}
						},
						error: function (xhr) {
							let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
						    '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
							$('#div_alert').append(alert);
							return;
						}
					});
			}

            $('#followers').empty();
            for (let f of profile.followers) {
                addRowInTableFollowers(f);
            }

            $('#following').empty();
            for (let f of profile.following) {
                addRowInTableFollowing(f);
            }


        },
        error: function() {
            console.log('error getting profile info');
        }
    }); 

    $.ajax({
        type: "GET",
        url: "/api/aggregation/highlight/" + username,
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
        url: "/api/aggregation/posts/" + username,
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


});

function addRowInTableFollowers(f) {

    let row = $('<tr><td>' + f + '</td>' +
        '</tr>');

    $('#followers').append(row);
};

function addRowInTableFollowing(f) {

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
        ' <source src= "' + path + '" type="video/mp4"></video></a> </div>');
    $('div#posts_images').append(image_div);
    
    $('#' + id).trigger('play');
};

function func(id){
	window.location.href = "https://localhost:8111/html/story.html?id=" + id;
};
