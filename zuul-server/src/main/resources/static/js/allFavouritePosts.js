var params = (new URL(window.location.href)).searchParams;

checkUserRole("ROLE_REGULAR");
var loggedInUsername = getUsernameFromToken();

$(document).ready(function () {	
	
	$.ajax({
		type:"GET", 
		url: "/api/aggregation/favourite-posts",
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
                    .catch(() => alert('oh no!'));
            }
        },
        error: function(message) {
            console.log('error getting posts ' + message.responseText);
        }
    });
});


function addPost(path, postId) {
	
    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        ' <a href="onePost.html?id=' + postId + ' "> <img height="250px" width="300px"  src="' + path + '">' +
        ' </a> </div>');
    $('div#posts_images').append(image_div);
};
