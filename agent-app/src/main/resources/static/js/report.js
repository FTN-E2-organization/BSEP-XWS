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

var nistagramAgent="";
	
	

$(document).ready(function () {

	$.ajax({
			url: "/api/agent/token/" + username,
			type: 'GET',
			contentType: 'application/json',
			success: function (token) {
			nistagramAgent = getUsernameFromApiToken(token);
				let a_div = '<a style="margin-right:30px;" href="https://localhost:8091/api/agent/report/pdf/' + nistagramAgent + '">REPORT</a>';
				$('div#report_div').append(a_div);
			},
			error: function () {
				console.log('error setting api token');
			}
});		
	
});	