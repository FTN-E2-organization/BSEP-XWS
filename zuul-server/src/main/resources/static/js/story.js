var loggedInUsername = getUsernameFromToken();
var roles = getRolesFromToken();
var storyOwner = null;
var idContent = null;
var type = null;

$(document).ready(function () {		
	
	if(loggedInUsername == null){
		$('head').append('<script type="text/javascript" src="../js/navbar/unauthenticated_user.js"></script>');
		hideComponents();
	}else{	
		if(roles.indexOf("ROLE_AGENT") > -1){
			$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
		}	
		else if(roles.indexOf("ROLE_REGULAR") > -1){
			$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
		}else if(roles.indexOf("ROLE_ADMIN") > -1){
			$('head').append('<script type="text/javascript" src="../js/navbar/admin.js"></script>');
			hideComponents();
		}
	}

	let url_split  = window.location.href.split("?")[1]; 
	idContent = url_split.split("=")[1];
	type = "story";
	
	getStoryInfo();
	    
    /*Click on Report content button*/
	$('#reportBtn').click(function(){

		let reason = $('#reportReason').val();
		
		var reportDTO = {
			"reason": reason,
			"contentId": idContent,
			"type":"story",
			"ownerUsername":storyOwner
		};
	
		
		$.ajax({
			url: "/api/publishing/report-content",
			headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       		},
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(reportDTO),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful reporting content!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#reportReason').val('');
				$('#btn_close_report').click();
				
				setTimeout(function () { history.back(); }, 5000);
				return;
			},
			error: function (xhr) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText +
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});		
		
	});
    
});

function addStory(path, j) {
	let image_div;
	if(j==0){
		image_div = $('<div class="carousel-item active">' +
        '<video id="videoPlay" class="d-block" height="520px" width="640px" poster="' + path + '">' +
        '<source src= "' + path + '" type="video/mp4"></video></div>');
     
	}else{
    	image_div = $('<div class="carousel-item">' +
        '<video id="videoPlay" class="d-block" height="520px" width="640px" poster="' + path + '">' +
        '<source src= "' + path + '" type="video/mp4"></video></div>');
    }
    $('div#story_image').append(image_div);
    
    $('#videoPlay').trigger('play');
};

function hideComponents(){
	$('#reportIcon').attr("hidden",true);
}

function getStoryInfo(){
	
	$.ajax({
		type:"GET", 
		url: "/api/publishing/story/" + idContent,
		contentType: "application/json",
		success:function(story){
			
			getStoryImage();

			$('#usernameH5').append(" " + story.ownerUsername);
			storyOwner = story.ownerUsername;
	
			$('#body_table').empty();
			
			if(story.ownerUsername == getUsernameFromToken()){
				$('#reportIcon').attr("hidden",true);
			}

			
			if (story.description != null && story.description != "") {
				let row = $('<tr><td> ' + story.description +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (story.location != null && story.location != "") {
				let row = $('<tr><td> Location: <a class="text-info" href="location.html?id=' + story.location +  '">' +  story.location + ' </a></td></tr>');	
				$('#body_table').append(row);			
			}
			if (story.timestamp != null) {
				let row = $('<tr><td> ' + story.timestamp.split("T")[0] + "  " + story.timestamp.split("T")[1].substring(0, 5) +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (story.hashtags.length > 0) {
				let hashtagText = "Hashtags:&nbsp;";
				for (let h of story.hashtags) {
				hashtagText += '<a class="text-info" href="hashtag.html?id=' + h.substring(1) +  '">' + h + ' </a>' + '&nbsp;';
			}
				let row = $('<tr><td>' + hashtagText + '</td></tr>');	
				$('#body_table').append(row);			
			}
			if (story.taggedUsernames.length > 0) {
				let	taggedText = "People: ";
				for (let t of story.taggedUsernames) {
				t = '<a class="text-info" href="profile.html?id=' + t + '">' + t + '</a>';
				taggedText += " " + t
				}
				let row = $('<tr><td> ' + taggedText +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
					
		},
		error:function(xhr){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
				 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			$('#divStoryInfo').attr('hidden',true);
			return;
		}
	});
	
};

function getStoryImage(){
	
	$.ajax({
        type: "GET",
        url: "/api/media/one/" + idContent + "/" + type,
        contentType: "application/json",
        success: function(media) {
        	$('#story_image').empty();
        	let grouped={}
        	for(let m of media){
        	
  				if(grouped[m.idContent]){
  				grouped[m.idContent].push(m)
  				}      	else{
  				grouped[m.idContent]=[m]
  				}
        	}
        
			let t = 0;
			let j = 0;
            for (let m in grouped) {
            	for(let i in media){
                fetch('/api/media/files/' +grouped[m][t].path)
                    .then(resp => resp.blob())
                    .then(blob => {
                        const url = window.URL.createObjectURL(blob);         
                        addStory(url, j);
                        j = j + 1;
                    })
                    .catch(() => alert('oh no!'));
                    t = t + 1; 
                }
                
            }
         },
		error:function(){
			console.log('error getting story image');
		}
	});
	
};
