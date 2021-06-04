var username = "ana00";
$(document).ready(function () {	

	$.ajax({
		type:"GET", 
		url: "/api/aggregation/profile-overview/" + username,
		contentType: "application/json",
		success:function(profile){
			$('#username').append(profile.username);
			$('#name').append(profile.name);
			$('#dateOfBirth').append(profile.dateOfBirth);
			$('#biography').append(profile.biography);
			$('#website').append(profile.website);
			if(profile.isPublic == true){
				$('#isPublic').append("PUBLIC");
			}else{
				$('#isPublic').append("PRIVATE");
			}
			
			$('#followers').empty();
			for (let f of profile.followers){
				addRowInTableFollowers(f);
			}
			
			$('#following').empty();
			for (let f of profile.following){
				addRowInTableFollowing(f);
			}
					
					
		},
		error:function(){
			console.log('error getting profile info');
		}
	});
	
	$.ajax({
		type:"GET", 
		url: "/api/aggregation/posts/" + username,
		contentType: "application/json",
		success:function(media){
			for(let m of media){
				addImage(m.path);
			}					
		},
		error:function(){
			console.log('error getting posts');
		}
	});

});

function addRowInTableFollowers(f){
	
	let row = $('<tr><td>' + f + '</td>' + 
				'</tr>');
				
	$('#followers').append(row);
};

function addRowInTableFollowing(f){
	
	let row = $('<tr><td>' + f + '</td>' + 
				'</tr>');
				
	$('#following').append(row);
};

function addImage(path){
	
	let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">'
						 + '<img height="250px" width="300px" src="http://localhost:8085/uploads/' + path + '">'
						 + '</div>');
			$('div#posts_images').append(image_div);
};