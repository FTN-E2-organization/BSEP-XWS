checkUserRole("ROLE_REGULAR");
var loggedInUsername = getUsernameFromToken();
var roles = getRolesFromToken();

$(document).ready(function () {	
	
	if(roles.indexOf("ROLE_AGENT") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
	}
	else if(roles.indexOf("ROLE_REGULAR") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
	}
	
	$.ajax({
		type:"GET", 
		url: "/api/publishing/collection",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(collections){					
			for(i = 0; i < collections.length; i++) {
				addCollectionRow(collections[i]);
			}								
		},
		error:function(jqXHR){
			console.log(jqXHR);
			console.log('Error getting collection');
		}
	});	
});


function addCollectionRow(collectionName) {	
	let row = $('<tr><td><a href="collectionPosts.html?id=' + collectionName + '" style="color:black;"> '+ collectionName +' </a></td></tr>');	
	$('#body_table').append(row);	
}
