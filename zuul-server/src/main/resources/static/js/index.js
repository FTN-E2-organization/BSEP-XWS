var username = "pero123";

$(document).ready(function() {
	
	 $.ajax({
        type: "GET",
        url: "/api/aggregation/following/stories/" + username,
        contentType: "application/json",
        success: function(media) {
        	let grouped={}
        	for(let m of media){
  				if(grouped[m.idContent]){
  					grouped[m.idContent].push(m)
  				}else{
  					grouped[m.idContent]=[m]
  				}
        	}
        
            for (let m in grouped) {
				
                fetch('/api/media/files/' +grouped[m][0].path)
                    .then(resp => resp.blob())
                    .then(blob => {
                        const url = window.URL.createObjectURL(blob);
                        addStory(url, grouped[m][0].ownerUsername, m);
                    })
                    .catch(() => alert('oh no!'));


            }
        },
        error: function() {
            console.log('error getting stories');
        }
    }); 
    
    
    $.ajax({
        type: "GET",
        url: "/api/aggregation/following/posts/" + username,
        contentType: "application/json",
        success: function(media) { 
        	let grouped={}
        	for(let m of media){
  				if(grouped[m.idContent]){
  					grouped[m.idContent].push(m)
  				}else{
  					grouped[m.idContent]=[m]
  				}
        	}
        
            for (let m in grouped) {
                fetch('/api/media/files/' + grouped[m][0].path)
                    .then(resp => resp.blob())
                    .then(blob => {
                        const url = window.URL.createObjectURL(blob);
                        addPost(url, grouped[m][0].ownerUsername); 
                    })
                    .catch(() => alert('oh no!'));

            }
        },
        error: function() {
            console.log('error getting posts');
        }
    });
    
    /*Click on New button*/
	$('#addStory').click(function(){
		window.location.href = "publishStory.html";
	});
	
	
});


function addStory(path, ownerUsername, id) {

    let image_div = $('<div style="margin-right:25px; margin-bottom:10px;" class="column">' +
        '<a  id="' + id +'" onclick="func(this.id)";><img class="rounded-circle" height="90px" width="70px"  src="' + path + '"></a>' +
        '<div style="margin-top:10px;margin-bottom:10px;font-size:18px;"><a style="color:black;" href="profile.html?username=' + ownerUsername + '"><b>' + ownerUsername + '</b></a></div>' +
        '</div>');
    $('div#story_images').append(image_div);
    
};

function addPost(path, ownerUsername) {

    let image_div = $('<div style="margin-right: 10px; margin-bottom:50px;" class="column">' +
        '<div style="margin-bottom:10px;font-size:20px;"><a style="color:black;" href="profile.html?username=' + ownerUsername + '"><b>' + ownerUsername + '</b></a></div>' +
        '<img height="500px" width="600px"  src="' + path + '">' +
        '</div>');
    $('div#posts_images').append(image_div);
};

function func(id){
	window.location.href = "http://localhost:8111/html/story.html?id=" + id;
};