checkUserRole("ROLE_REGULAR");
var loggedInUsername = getUsernameFromToken();

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
});


function addCollectionRow(collectionName) {	
	let row = $('<tr><td><a href="collectionPosts.html?id=' + collectionName + '" style="color:black;"> '+ collectionName +' </a></td></tr>');	
	$('#body_table').append(row);	
}
