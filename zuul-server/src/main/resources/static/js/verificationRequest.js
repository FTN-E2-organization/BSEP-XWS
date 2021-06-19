var entityMap = {
	'&': '&amp;',
	'<': '&lt;',
	'>': '&gt;',
	'"': '&quot;',
	"'": '&#39;',
	'/': '&#x2F;',
	'`': '&#x60;',
	'=': '&#x3D;'
};

checkUserRole("ROLE_REGULAR");
var ownerUsername = getUsernameFromToken();

$(document).ready(function () {
	
	$('#location').val('');
	$('#locations').empty();
	$('#username').val(ownerUsername);
	
	$.ajax({
			type:"GET", 
			url: "/api/auth/profile/categories",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	       	},
			contentType: "application/json",
			success:function(locations){
			
				$('#location').val(locations[0].name);
			},
			error:function(){
				console.log('error getting categories');
			}
		});
	

	
	/*Get categories*/
	$('#searchLocation').click(function(){
		
		$.ajax({
			type:"GET", 
			url: "/api/auth/profile/categories",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	       	},
			contentType: "application/json",
			success:function(locations){
				$('#locations').empty();
				for (let l of locations){
					addLocation(l);
				}
			},
			error:function(){
				console.log('error getting categories');
			}
		});
	});
	
	/*Click on location*/
	$("#tableLocations").delegate("tr.location", "click", function(){
        let location = $(this).text();
        $('#btn_close_location').click();
        $('#location').val(location);
    });
	 
	$("#name").on('input',function(){
		let name = $('#name').val();
		
		if(name === "" || name == null){
			$('#name').text("This is required field.");
			$('#one').css("color","red");
		}
	});
	$("#surname").on('input',function(){
		let surname = $('#surname').val();
		
		if(surname === "" || surname == null){
			$('#surname').text("This is required field.");
			$('#two').css("color","red");
		}
	});
	$("#location").on('input',function(){
		let location = $('#location').val();
		console.log(location)
		if(location === "" || location == null){
			$('#location').text("This is required field.");
			$('#three').css("color","red");
		}
	});
	

	/*Send request*/
	$('form#publishStory').submit(function (event) {

		event.preventDefault();
			
		$('#div_alert').empty();

		let name = escapeHtml($('#name').val());
		let surname = escapeHtml($('#surname').val());
		let location = escapeHtml($('#location').val());
				
		var requestDTO = {
			"username": ownerUsername,
			"name": name,
			"surname": surname,
			"category": location,
			
		};
		
		if($('#file').val() == "" || $('#file').val() == null){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Choose file!' + 
			'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}

				
		$.ajax({
			url: "/api/auth/profile/verification/request",
			headers: {
	            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
	       	},
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(requestDTO),
			success: function (id) {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successfully requested verification!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				
				window.setTimeout(function(){
					var actionPath = "/api/aggregation/files-upload?idContent=" + id + "&type=request";
					$('#form_image').attr('action', actionPath)
					$('#form_image').submit();
				},1000);
				return;
			},
			error: function (xhr) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});		
	});
	
});


function addLocation(l){
	let row = $('<tr class="location"><td>'+ l.name +'</td></tr>');	
	$('#locations').append(row);
}


function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};
