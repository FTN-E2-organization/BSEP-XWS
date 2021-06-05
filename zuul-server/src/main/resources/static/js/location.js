var username = "ana00";

var locationName = localStorage.getItem("contentName");
alert(locationName);


$(document).ready(function () {	
	
	$.ajax({
		type:"GET", 
		url: "/api/aggregation/location-overview/" + locationName,
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