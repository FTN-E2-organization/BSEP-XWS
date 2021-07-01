checkUserRole("ROLE_ADMIN");
var username = getUsernameFromToken();
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
	divJudge = '<div class="carousel-inner" id="img'+c.id+'"></div>';
	getImage(c.id);
	let row = $('<tr><td style="vertical-align: middle;">' + c.username + '</td><td style="vertical-align: middle;">' + c.name + '</td>' +
				'<td style="vertical-align: middle;">' + c.surname + '</td>' +
				'<td style="vertical-align: middle;">' + c.category + '</td>' + '<td style="vertical-align: middle;">' + divJudge + '</td>' +
				'<td style="vertical-align: middle;">' + btnVerify + '</td>'  + '<td style="vertical-align: middle;">' + btnDeny + '</td>' +
				'</tr>');
				
	$('#certificates').append(row);
};

function getImage(idI){
	$.ajax({
		        type: "GET",
		        url: "/api/media/one/" + idI + "/" + "request",
		        contentType: "application/json",
		        success: function(media) {
		        	let grouped={}
		        	for(let m of media){
		        	$('#post_image').empty();
		  				if(grouped[m.idContent]){
		  				grouped[m.idContent].push(m)
		  				}      	else{
		  				grouped[m.idContent]=[m]
		  				}
		        	}

		            for (let m in grouped) {

		                fetch('/api/media/files/' +grouped[m][0].path)
		                    .then(resp => resp.blob())
		                    .then(blob => {
		                        const url = window.URL.createObjectURL(blob);
		                        addImage(url,idI);
		                    })
		                    .catch(() => alert('oh no!'));


		            }
			 },
	        error: function() {
	            console.log('error getting image');
	        }
	    }); 
	
};
function addImage(path,id) {
	$('div#img'+id).empty();
    let image_div = $('<div class="column">' +
        '<img height="150px" width="150px"   src="' + path + '">' +
        '</div>');
    $('div#img'+id).append(image_div);
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
