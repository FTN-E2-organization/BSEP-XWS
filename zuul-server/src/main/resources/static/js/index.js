checkUserRole("ROLE_REGULAR");
var username = getUsernameFromToken();

$(document).ready(function() {
	
	 $.ajax({
        type: "GET",
        url: "/api/aggregation/following/stories/" + username,
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
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
                    .catch(() => console('error getting stories!'));


            }
        },
        error: function() {
            console.log('error getting stories');
        }
    }); 
    
    
    $.ajax({
        type: "GET",
        url: "/api/aggregation/following/posts/" + username,
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
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
                        addPost(url, grouped[m][0].ownerUsername, m); 
                    })
                    .catch(() => console('eror getting posts!'));

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
        '<a  id="' + id +'" onclick="func(this.id)";><video id="' + id +'"  max-height="80px" height="80px" width="70px"  poster="' + path + '"><source src= "' + path + '" type="video/mp4"></video></a>' +
        '<div style="margin-top:0px;margin-bottom:10px;font-size:18px;"><a href="profile.html?id=' + ownerUsername + '"><b>' + ownerUsername + '</b></a></div>' +
        '</div>');
        
    $('div#story_images').append(image_div);
    
};

function addPost(path, ownerUsername, id) {
	
   let image_div = $('<div style="margin-right: 10px; margin-bottom:50px;" class="column">' +
    '<div style="margin-bottom:10px;font-size:20px;"><a href="profile.html?id=' + ownerUsername + '"><b>' + ownerUsername + '</b></a></div>' +
    ' <a href="onePost.html?id=' + id + '"><video id="' + id +'" max-height="500px" width="600px"  poster="' + path + '">' +
    '<source src= "' + path + '" type="video/mp4"></video></a> </div>');
    
	$('div#posts_images').append(image_div);
	
	$('#' + id).trigger('play');
	
};

function func(id){
	window.location.href = "http://localhost:8111/html/story.html?id=" + id;
};