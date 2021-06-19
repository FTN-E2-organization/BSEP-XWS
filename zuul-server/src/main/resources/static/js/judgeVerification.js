
$(document).ready(function () {	
	
	$.ajax({
		type:"GET", 
		url: "/api/auth/profile/unverified",
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
		contentType: "application/json",
		success:function(certificates){
			$('#certificates').empty();
			for (let c of certificates){
				addRowInTable(c);
			}
			
		},
		error: function (xhr) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
		}
	});
	
});

function addRowInTable(c){
	
	btnDeny = '<button class="btn btn-danger btn-sm" type="button" id="' + c.id +'" onclick="deny(this.id)">Deny</button>';
	btnVerify = '<button class="btn btn-info btn-sm" type="button" id="' + c.id +'" onclick="verify(this.id)">Verify</button>';
	localStorage.setItem(c.id,JSON.stringify(c));
	let row = $('<tr><td>' + c.username + '</td><td>' + c.name + '</td>' +
				'<td>' + c.surname + '</td>' +
				'<td>' + c.category + '</td>' + 
				'<td>' + btnVerify + '</td>'  + '<td>' + btnDeny + '</td>'  + 
				'</tr>');
				
	$('#certificates').append(row);
};

function verify(idRequest) {

		event.preventDefault();
		let request = JSON.parse(localStorage.getItem(idRequest));
		var requestDTO = {
			"id":request.id,
			"username": request.username,
			"name": request.name,
			"surname": request.surname,
			"category": request.category,
			"isApproved":true,
		};
		$.ajax({
			type:"POST", 
			url: "/api/auth/profile/verification/request/judge",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			contentType: "application/json",
			data: JSON.stringify(requestDTO),
			success:function(){
				location.reload();
			},
			error:function(){
				console.log('error verifying request');
			}
	
		}); 	

};
function deny(idRequest) {
		
		event.preventDefault();
		let request = JSON.parse(localStorage.getItem(idRequest));
		var requestDTO = {
		"id":request.id,
			"username": request.username,
			"name": request.name,
			"surname": request.surname,
			"category": request.category,
			"isApproved":false,
		};
		$.ajax({
			type:"POST", 
			url: "/api/auth/profile/verification/request/judge",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	        },
			contentType: "application/json",
			data: JSON.stringify(requestDTO),
			success:function(){
				location.reload();
			},
			error:function(){
				console.log('error verifying request');
			}
	
		}); 	

};
