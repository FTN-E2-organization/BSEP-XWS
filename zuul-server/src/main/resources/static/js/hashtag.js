
var hashtagName = localStorage.getItem("contentName");
alert(hashtagName);

$(document).ready(function () {	
	
	$('#hashtag').append(" " + hashtagName);
	
	$.ajax({
		type:"GET", 
		url: "/api/aggregation/hashtag/" + hashtagName.substring(1),
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



function addImage(path){
	
	let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">'
						 + '<img height="250px" width="300px" src="http://localhost:8085/uploads/' + path + '">'
						 + '</div>');
			$('div#posts_images').append(image_div);
};