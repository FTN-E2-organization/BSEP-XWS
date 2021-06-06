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
				let row = $('<tr><td> ' + post.location +  ' </td></tr>');	
				$('#body_table').append(row);			
			}			
			if (post.timestamp != null) {
				let row = $('<tr><td> ' + post.timestamp +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (post.hashtags.length > 0) {
				let hashtagText = "";
				for (let h of post.hashtags) {
					hashtagText += " " + h
				}
				let row = $('<tr><td> ' + hashtagText +  ' </td></tr>');	
				$('#body_table').append(row);			
			}
			if (post.taggedUsernames.length > 0) {
				let taggedText = "";
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