var username = "pero123";

$(document).ready(function() {

    $.ajax({
        type: "GET",
        url: "/api/aggregation/profile-overview/" + username,
        contentType: "application/json",
        success: function(profile) {
            $('#username').append(profile.username);
            $('#name').append(profile.name);
            $('#dateOfBirth').append(profile.dateOfBirth);
            $('#biography').append(profile.biography);
            $('#website').append(profile.website);
            if (profile.isPublic == true) {
                $('#isPublic').append("PUBLIC");
            } else {
                $('#isPublic').append("PRIVATE");
            }

            $('#followers').empty();
            for (let f of profile.followers) {
                addRowInTableFollowers(f);
            }

            $('#following').empty();
            for (let f of profile.following) {
                addRowInTableFollowing(f);
            }


        },
        error: function() {
            console.log('error getting profile info');
        }
    });

    $.ajax({
        type: "GET",
        url: "/api/aggregation/highlight/" + username,
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
                        addStory(url);
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
        url: "/api/aggregation/posts/" + username,
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

function addRowInTableFollowers(f) {

    let row = $('<tr><td>' + f + '</td>' +
        '</tr>');

    $('#followers').append(row);
};

function addRowInTableFollowing(f) {

    let row = $('<tr><td>' + f + '</td>' +
        '</tr>');

    $('#following').append(row);
};

function addStory(path) {

    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        '<img class="rounded-circle" height="90px" width="70px"  src="' + path + '">' +
        '</div>');
    $('div#story_images').append(image_div);
};

function addPost(path, postId) {
	
    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        ' <a href="onePost.html?id=' + postId + ' "> <img height="250px" width="300px"  src="' + path + '">' +
        ' </a> </div>');
    $('div#posts_images').append(image_div);
};

