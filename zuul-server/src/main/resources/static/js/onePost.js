var entityMap = {
	'&': '&amp;',
	'<': '&lt;',
	'>': '&gt;',
	'"': '&quot;',
	"'": '&#39;',
	'/': '&#x2F;',
	'`': '&#x60;',
	'=': '&#x3D;'
};


//checkUserRole("ROLE_REGULAR");
var loggedInUsername = getUsernameFromToken();

﻿var params = (new URL(window.location.href)).searchParams;
var postId = params.get("id");

$(document).ready(function () {

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
	
	/*Click on Report content button*/
	$('#reportBtn').click(function(){
		
		let reason = $('#reportReason').val();
		
		var reportDTO = {
			"reason": reason,
			"contentId": postId,
			"type":"post"
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


function addProfile(p){
	let row = $('<tr class="tagged"><td>'+ p +'</td></tr>');	
	$('#bodyTagged').append(row);
}


function publishComment() {
	
	let taggedUsernames = $('#tagged').val();
	taggedUsernames = taggedUsernames.substring(1,taggedUsernames.length).split("@");
	
	if ($('#comment_text').val() == "" && $('#comment_text').val() == null && taggedUsernames.length == 0) {
		return;
	}	
	
	var comment = {
			"text": escapeHtml($('#comment_text').val()),
			"postId": postId,
			"ownerUsername": loggedInUsername,
			"postType": "regular",
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
            alert('Error ' + jqXHR.responseText);
        }
    });				
}



function getPostInfo() {
	
    $.ajax({
        url: "/api/publishing/post/" + postId,
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		type: 'GET',
		contentType: 'application/json',
        success: function (post) {
			
			if(post.ownerUsername == getUsernameFromToken()){
				$('#reportIcon').attr("hidden",true);
			}
			
			$('#usernameH5').append(" " + post.ownerUsername);
			
			$('#body_table').empty();
			if (post.description != null && post.description != "") {
				let row = $('<tr><td> ' + post.description +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (post.location != null && post.location != "") {
				let row = $('<tr><td> Location: <a class="text-info" href="location.html?id=' + post.location +  '">' +  post.location + ' </a></td></tr>');	
				$('#body_table').append(row);			
			}			
			if (post.timestamp != null) {
				let row = $('<tr><td> ' + post.timestamp +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (post.hashtags.length > 0) {
				let hashtagText = "Hashtags:&nbsp;";
				for (let h of post.hashtags) {
					hashtagText += '<a class="text-info" href="hashtag.html?id=' + h.substring(1) +  '">' + h + ' </a>' + '&nbsp;';
				}
				let row = $('<tr><td>' + hashtagText + '</td></tr>');	
				$('#body_table').append(row);			
			}
			if (post.taggedUsernames.length > 0) {
				let taggedText = "People: ";
				for (let t of post.taggedUsernames) {
					t = '<a class="text-info" href="profile.html?id=' + t + '">' + t + '</a>';
					taggedText += " " + t
				}
				let row = $('<tr><td> ' + taggedText +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			
			$.ajax({
		        type: "GET",
		        url: "/api/media/one/" + postId + "/" + "post",
				headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		       	},
		        contentType: "application/json",
		        success: function(media) {
		        	let grouped={}
		        	for(let m of media){
		        	$('#post_image').empty();
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
	        error: function() {
	            console.log('error getting story media');
	        }
	    }); 	
			
        },
        error: function (jqXHR) {
            alert('Error ' + jqXHR.responseText);
        }
    });	
}



function showComments() {			
    $.ajax({
        url: "/api/activity/comment/" + postId + "/post-id",
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
            alert('Error ' + jqXHR.responseText);
        }
    });	
}


function showLikes() {		
    $.ajax({
        url: "/api/activity/reaction/likes/" + postId,
        headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		       	},
		type: 'GET',
		contentType: 'application/json',
        success: function (likes) {
			$('#like_body_table').empty();
			for (let l of likes) {
				let row = $('<tr><td><a class="text-info" href="profile.html?id=' + l.ownerUsername + '">' + l.ownerUsername + '</a></td></tr>');	
				$('#like_body_table').append(row);				
			}
        },
        error: function (jqXHR) {
            alert('Error ' + jqXHR.responseText);
        }
    });	
}


function showDislikes() {	
    $.ajax({
        url: "/api/activity/reaction/dislikes/" + postId,
        headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		       	},
		type: 'GET',
		contentType: 'application/json',
        success: function (likes) {
			$('#dislike_body_table').empty();
			for (let l of likes) {
				let row = $('<tr><td><a class="text-info" href="profile.html?id=' + l.ownerUsername + '">' + l.ownerUsername + '</a></td></tr>');	
				$('#dislike_body_table').append(row);				
			}
        },
        error: function (jqXHR) {
            alert('Error ' + jqXHR.responseText);
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
			"postId": postId,
			"ownerUsername": loggedInUsername,
			"postType": "regular"
	};		
    $.ajax({
        url: "/api/activity/reaction",
        headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		       	},
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(like),
        success: function () {
			showLikes();
			showDislikes()
        },
        error: function (jqXHR) {
            alert('Error ' + jqXHR.responseText);
        }
    });			
}


function dislikePost() {	
	reactionToPost("dislike") 
}


function likePost() {	
	reactionToPost("like") 
}


function addToFavorites() {	
	var favouritePost = {
			"postId": postId,
			"ownerUsername": loggedInUsername,
			"collectionName": $('#collection_name').val()
	};		
    $.ajax({
        url: "/api/publishing/favourite-post",
        headers: {
		            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
		       	},
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(favouritePost),
        success: function () {
			$('#topModal').modal('hide');
        },
        error: function (jqXHR) {
            alert('Error ' + jqXHR.responseText);
        }
    });	
}


function openDialog() {
	$('#topModal').modal('show');	
}


function addPost(path, j) {
	
	let image_div;
	if(j==0){
	    image_div = $('<div class="carousel-item active">' +
	        '<video id="videoPlay" class="d-block" height="520px" width="640px"   poster="' + path + '">' +
	        '<source src= "' + path + '" type="video/mp4"></video></div>');
    }else{
	    image_div = $('<div class="carousel-item">' +
	        '<video id="videoPlay" class="d-block" height="520px" width="640px"   poster="' + path + '">' +
	        '<source src= "' + path + '" type="video/mp4"></video></div>');
	    }
    $('div#post_image').append(image_div);
    
	$('#videoPlay').trigger('play');
};

function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};




