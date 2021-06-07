
var loggedInUsername = "pero123";

$(document).ready(function () {	

	$.ajax({
		type:"GET", 
		url: "/api/publishing/collection",
		contentType: "application/json",
		success:function(collections){					
			for(i = 0; i < collections.length; i++) {
				addCollectionRow(collections[i]);
			}								
		},
		error:function(jqXHR){
			console.log(jqXHR);
			alert('Error getting collection');
		}
	});	

	
//	$.ajax({
//		type:"GET", 
//		url: "/api/aggregation/location-overview/" + locationName,
//        contentType: "application/json",
//        success: function(media) { 
//        	let grouped={}
//        	for(let m of media){
//  				if(grouped[m.idContent]){
//  				grouped[m.idContent].push(m)
//  				}      	else{
//  				grouped[m.idContent]=[m]
//  				}
//        	}      
//            for (let m in grouped) {
//                fetch('/api/media/files/' +grouped[m][0].path)
//                    .then(resp => resp.blob())
//                    .then(blob => {
//                        const url = window.URL.createObjectURL(blob);
//                        addPost(url, m); 
//                    })
//                    .catch(() => alert('oh no!'));
//
//            }
//        },
//        error: function() {
//            console.log('error getting posts');
//        }
//    });
});


function addCollectionRow(collectionName) {	
	let row = $('<tr><td>'+ collectionName +'</td></tr>');	
	$('#body_table').append(row);	
}



function addPost(path, postId) {
	
    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        ' <a href="onePost.html?id=' + postId + ' "> <img height="250px" width="300px"  src="' + path + '">' +
        ' </a> </div>');
    $('div#posts_images').append(image_div);
};


