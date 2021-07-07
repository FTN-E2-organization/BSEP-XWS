var username = getUsernameFromToken();

$.ajax({
		type:"GET", 
		url: "/api/agent/api-token/" + username,
		contentType: "application/json",
		success:function(hasToken){					
			if(hasToken){
				checkUserRole("ROLE_AGENT");
			}else{
				checkUserRole("NOT_ROLE_AGENT");
			}							
		},
		error:function(){
		}
});	