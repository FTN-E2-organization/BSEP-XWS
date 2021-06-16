var params = (new URL(window.location.href)).searchParams;
var hashtagName = "#" + params.get("id");

var loggedInUsername = getUsernameFromToken();

$(document).ready(function () {	
	
	if(loggedInUsername == null){
		$('head').append('<script type="text/javascript" src="../js/navbar/unauthenticated_user.js"></script>');
	}else{
		$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
	}
	
	$('#hashtag').append(" " + hashtagName);
	
	$.ajax({
		type:"GET", 
		url: "/api/aggregation/hashtag/" + hashtagName.substring(1),
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
});


function addPost(path, postId) {
	
    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        ' <a href="onePost.html?id=' + postId + ' "> <img height="250px" width="300px"  src="' + path + '">' +
        ' </a> </div>');
    $('div#posts_images').append(image_div);
};


