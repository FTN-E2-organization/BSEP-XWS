
$(document).ready(function () {	


	let url_split  = window.location.href.split("?")[1]; 
	var idContent = url_split.split("=")[1];
	var type = "story";

	
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
            
            $.ajax({
				type:"GET", 
				url: "/api/publishing/story/" + idContent,
				contentType: "application/json",
				success:function(story){
					$('#username').append(story.ownerUsername);
					$('#date').append(story.timestamp);
					$('#description').append(story.description);
					$('#location').append(story.location);
					$('#hash').append(story.hashtags);
					$('#profiles').append(story.taggedUsernames);
					
					
					setTimeout(function () {
       					window.location.href = "myProfile.html"; 
   						 }, 5000);					
					
				},
				error:function(){
				console.log('error getting info story');
				}
		});
            
        },
        error: function() {
            console.log('error getting story media');
        }
    }); 
    
});

function addStory(path, j) {
	let image_div;
	if(j==0){
		image_div = $('<div class="carousel-item active">' +
        '<img class="d-block" height="520px" width="640px" src="' + path + '">' +
        '</div>');
     
	}else{
    	image_div = $('<div class="carousel-item">' +
        '<img class="d-block" height="520px" width="640px" src="' + path + '">' +
        '</div>');
    }
    $('div#story_image').append(image_div);
};