$(document).ready(function () {	

	let url_split  = window.location.href.split("?")[1]; 
	var idContent = url_split.split("=")[1];
	var type = "story";
	
	 $.ajax({
        type: "GET",
        url: "/api/media/one/" + idContent + "/" + type,
        contentType: "application/json",
        success: function(media) {
        	let grouped={}
        	for(let m of media){
        	$('#story_image').empty();
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
                        addStory(url);
                    })
                    .catch(() => alert('oh no!'));


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
   						 }, 2500); //will call the function after 2 secs.
					
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

function addStory(path) {

    let image_div = $('<div class="column">' +
        '<img height="520px" width="640px"   src="' + path + '">' +
        '</div>');
    $('div#story_image').append(image_div);
};