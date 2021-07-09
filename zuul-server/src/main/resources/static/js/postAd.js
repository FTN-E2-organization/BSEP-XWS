var loggedInUsername = getUsernameFromToken();
var roles = getRolesFromToken();
var storyOwner = null;
var idContent = null;
var type = null;
var agentUsername = null;
var campaignID = 0;

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
	type = "ad";
	
	getPostInfo();
	
	/*Get profiles for tagging*/
	$('#addTagged').click(function(){
		
		$.ajax({
			type:"GET", 
			url: "/api/auth/profile/allowedTagging",
			headers: {
          	  'Authorization': 'Bearer ' + window.localStorage.getItem('token')
      	 	},
			contentType: "application/json",
			success:function(profiles){
				$('#bodyTagged').empty();
				for (let p of profiles){
					addProfile(p);
				}
			},
			error:function(){
				console.log('error getting profiles');
			}
		});
	});		
	
	/*Click on tagged profile*/
	$("#tableTagged").delegate("tr.tagged", "click", function(){
        let hashtag = $(this).text();
     
		let currentTagged = $('#selectedTagged').val();
        $('#selectedTagged').val(currentTagged + '@' + hashtag);
    });

    /*Click on Tag button*/
	$('#addTaggedProfiles').click(function(){
		$('input#tagged').val($('#selectedTagged').val());
		$('#btn_close_tagged').click();
	});   
	
	showLikes();
	showDislikes();	
	
});


function addProfile(p){
	let row = $('<tr class="tagged"><td>'+ p +'</td></tr>');	
	$('#bodyTagged').append(row);
}


function publishComment() {
	
	let taggedUsernames = $('#tagged').val();
	taggedUsernames = taggedUsernames.substring(1,taggedUsernames.length).split("@");
	
	if ($('#comment_text').val() == "" && $('#tagged').val() == "") {
		console.log("you did not write a comment");
		return;
	}	
	else {
			
	if (taggedUsernames[0] == "") {
		taggedUsernames = null;
	}
	
	var comment = {
			"text": $('#comment_text').val(),
			"postId": idContent,
			"ownerUsername": loggedInUsername,
			"postType": "campaign",
			"taggedUsernames": taggedUsernames
	};	
    $.ajax({
        url: "/api/activity/comment",
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(comment),
        success: function () {
			showComments();	
			document.getElementById('comment_text').value = '';
			document.getElementById('tagged').value = '';
			document.getElementById('selectedTagged').value = '';
							
        },
        error: function (jqXHR) {
            console.log('Error ' + jqXHR.responseText);
        }
    });		
	}	
}




function addPost(path, j) {
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


function getPostInfo(){
		
	$.ajax({
		type:"GET", 
		url: "/api/campaign/ad/" + idContent,
		contentType: "application/json",
		success:function(ad){
		
		$.ajax({
		type:"GET", 
		url: "/api/campaign/" + ad.campaignId,
		contentType: "application/json",
		success:function(campaign){
			
			agentUsername = campaign.agentUsername;
			campaignID = ad.campaignId
			
			getPostImage();

			$('#usernameH5').append(" " + campaign.name);
	
			$('#body_table').empty();
						
			if (ad.productLink != null && ad.productLink != "") {
				let row = $('<tr><td><a style="color:blue;" onclick="createClickDTO()" href = "' + ad.productLink +'">' + ad.productLink +  '</a> </td></tr>');	
				$('#body_table').append(row);			
			}					
		},
		error:function(xhr){
			console.log(xhr.responseText);
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert"> error! ' + 
				 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			$('#divStoryInfo').attr('hidden',true);
			return;
		}
	})					
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

function getPostImage(){
	
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
                        addPost(url, j);
                        j = j + 1;
                    })
                    .catch(() => alert('oh no!'));
                    t = t + 1; 
                }                
            }
         },
		error:function(){
			console.log('error getting post image');
		}
	});	
};


function showComments() {			
    $.ajax({
        url: "/api/activity/comment/" + idContent + "/ad-id",   //idContent ovo je id reklame valjda
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		type: 'GET',
		contentType: 'application/json',
        success: function (comments) {
			$('#comment_body_table').empty();
			for (let c of comments) {
				let text = '<b><a class="text-primary" href="profile.html?id=' + c.ownerUsername + '">' + c.ownerUsername + '</a></b>'; 
				let text1 = c.timestamp;
				let text2 = c.text;
				let text3 = "";
				for (let t of c.taggedUsernames) {
					t = '<a class="text-info" href="profile.html?id=' + t + '">' + t + '</a>';
					text3 += t + " ";
				}				
				let row = $('<tr><td><p> '+ text + ' </p> <p class="text-light"> '+ text1 + ' </p>' + text2 + ' ' + text3 + '  </td></tr>');	
				$('#comment_body_table').append(row);				
			}
        },
        error: function (jqXHR) {
            console.log('Error ' + jqXHR.responseText);
        }
    });	
}


function showLikes() {		
		
    $.ajax({
        url: "/api/activity/reaction/likes/ad/" + idContent,
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		type: 'GET',
		contentType: 'application/json',
        success: function (likes) {
			$('#like_body_table').empty();
			for (let l of likes) {
				if(l.ownerUsername == loggedInUsername){
					changeBtnColorToInfo("like");
				}
				let row = $('<tr><td><a class="text-info" href="profile.html?id=' + l.ownerUsername + '">' + l.ownerUsername + '</a></td></tr>');	
				$('#like_body_table').append(row);				
			}
        },
        error: function (jqXHR) {
            console.log('Error ' + jqXHR.responseText);
        }
    });	
}



function showDislikes() {	
    $.ajax({
        url: "/api/activity/reaction/dislikes/ad/" + idContent,
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		type: 'GET',
		contentType: 'application/json',
        success: function (likes) {
			$('#dislike_body_table').empty();
			for (let l of likes) {
				if(l.ownerUsername == loggedInUsername){
					changeBtnColorToInfo("dislike");
				}
				let row = $('<tr><td><a class="text-info" href="profile.html?id=' + l.ownerUsername + '">' + l.ownerUsername + '</a></td></tr>');	
				$('#dislike_body_table').append(row);				
			}
        },
        error: function (jqXHR) {
            console.log('Error ' + jqXHR.responseText);
        }
    });	
}



function showReactions() {	
	document.getElementById("comment_table").hidden = false;
	document.getElementById("like_table").hidden = false;
	document.getElementById("dislike_table").hidden = false;
}



function reactionToPost(reaction) {
	var like = {
			"reactionType": reaction,
			"postId": idContent,
			"ownerUsername": loggedInUsername,
			"postType": "campaign"
	};		
    $.ajax({
        url: "/api/activity/reaction",
        headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		       	},
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(like),
        success: function (isLike) {
			showLikes();
			showDislikes()
			
			if (reaction == "like" && isLike == true) {
				changeBtnColorToInfo("like");
				changeBtnColorToInfoOutline("dislike");						
			    								
			}else if(reaction == "like" && isLike == false){
				changeBtnColorToInfoOutline("like");
			}else if(reaction == "dislike" && $('#dislike').hasClass("btn-outline-info")){
				changeBtnColorToInfo("dislike");
				changeBtnColorToInfoOutline("like");
			}else if(reaction == "dislike" && $('#dislike').hasClass("btn-info")){
				changeBtnColorToInfoOutline("dislike");
			}
        },
        error: function (jqXHR) {
            console.log('Error ' + jqXHR.responseText);
        }
    });			
}


function dislikePost() {	
	reactionToPost("dislike") 
}


function likePost() {	
	reactionToPost("like") 
}


function changeBtnColorToInfo(btnName){
	$('#' + btnName).removeClass("btn-outline-info");
	$('#' + btnName).addClass("btn-info");
}

function changeBtnColorToInfoOutline(btnName){
	$('#' + btnName).removeClass("btn-info");
	$('#' + btnName).addClass("btn-outline-info");
}



function createClickDTO() {  //???????????

	var clickDTO = {
			"campaigId": campaignID,
			"ownerType": "agent",
			"ownerUsername": agentUsername
	};	
	
	$.ajax({
		url: "/api/activity/click",
		type: 'POST',
		contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},		
		data: JSON.stringify(clickDTO),
		success: function () {
			console.log("success");			
		},
		error: function (xhr) {
			console.log("error - " + xhr.responseText);
		}
	});				
}




