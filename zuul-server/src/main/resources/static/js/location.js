var params = (new URL(window.location.href)).searchParams;
var locationName = params.get("id");

var loggedInUsername = getUsernameFromToken();

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
	
	$('#location').append(" " + locationName);
	
	$.ajax({
		type:"GET", 
		url: "/api/aggregation/location-overview/" + locationName,
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
                    .catch(() => console('error!'));

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

