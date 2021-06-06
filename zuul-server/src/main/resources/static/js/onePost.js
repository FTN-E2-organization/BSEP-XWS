var loggedInUsername = "pero123";

var postId = 1;

$(document).ready(function () {

	getPostInfo();
			
});



function getPostInfo() {
    $.ajax({
        url: "/api/aggregation/post/" + postId,
		type: 'GET',
		contentType: 'application/json',
        success: function (post) {
			
			$('#usernameH5').append(" " + post.ownerUsername);
			
			$('#body_table').empty();
			if (post.description != null) {
				let row = $('<tr><td> ' + post.description +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (post.location != null) {
				let row = $('<tr><td> Location: ' + post.location +  ' </td></tr>');	
				$('#body_table').append(row);			
			}			
			if (post.timestamp != null) {
				let row = $('<tr><td> ' + post.timestamp +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (post.hashtags.length > 0) {
				let hashtagText = "Hashtags:";
				for (let h of post.hashtags) {
					hashtagText += " " + h
				}
				let row = $('<tr><td> ' + hashtagText +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (post.taggedUsernames.length > 0) {
				let taggedText = "People: ";
				for (let t of post.taggedUsernames) {
					taggedText += " " + t
				}
				let row = $('<tr><td> ' + taggedText +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			
        },
        error: function (jqXHR) {
            alert('Error ' + jqXHR.responseText);
        }
    });	
}



function showComments() {		
	alert("showComments");
    $.ajax({
        url: "/api/activity/comment/" + postId + "/post-id",
		type: 'GET',
		contentType: 'application/json',
        success: function (comments) {
			for (let c of comments) {
				let text1 = c.ownerUsername + " " + c.timestamp;
				let text2 = c.text;
				let text3 = "";
				for (let t of c.taggedUsernames) {
					text3 += t + " ";
				}				
				let row = $('<tr><td> '+ text1 + ' <br> ' + text2 + ' <br> ' + text3 + ' </td></tr>');	
				$('#comment_body_table').append(row);				
			}
        },
        error: function (jqXHR) {
            alert('Error ' + jqXHR.responseText);
        }
    });	
}


function showLikes() {		
	alert("showLikes");
}


function showDislikes() {	
	alert("showDislikes");
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
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(like),
        success: function (comments) {
			for (let c of comments) {
				let text1 = c.ownerUsername + " " + c.timestamp;
				let text2 = c.text;
				let text3 = "";
				for (let t of c.taggedUsernames) {
					text3 += t + " ";
				}				
				let row = $('<tr><td> '+ text1 + ' <br> ' + text2 + ' <br> ' + text3 + ' </td></tr>');	
				$('#comment_body_table').append(row);				
			}
        },
        error: function (jqXHR) {
            alert('Error ' + jqXHR.responseText);
        }
    });			
}


function dislikePost() {	
	alert("dislike post");
	reactionToPost("dislike") 
}


function likePost() {	
	alert("like post");
	reactionToPost("like") 
}


function savePost() {	
	alert("savePost");
}




