checkUserRole("ROLE_AGENT");
var loggedInUsername = getUsernameFromToken();

$(document).ready(function () {	
	
	$.ajax({
		type:"GET", 
		url: "/api/auth/agent-api-token/token/" + loggedInUsername,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(token){
			$('#token').val(token);				
		},
		error:function(){
			console.log('error getting api token');
		}
	});
	
});