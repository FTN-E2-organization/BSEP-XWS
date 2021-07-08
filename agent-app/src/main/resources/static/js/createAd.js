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

var username = getUsernameFromToken();
var nistagramAgent = "";

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

$(document).ready(function () {
	
	localStorage.removeItem('link');

	$.ajax({
			url: "/api/agent/token/" + username,
			type: 'GET',
			contentType: 'application/json',
			success: function (token) {
			nistagramAgent = getUsernameFromApiToken(token);
					getAllCampaigns();
			},
			error: function () {
				console.log('error setting api token');
			}
	});		
	
	
	/* on submit */
	$('form#publishAd').submit(function (event) {

		event.preventDefault();	
		$('#div_alert').empty();

		let productLink = $('#productLink').val();
		let campaignId = $("#campaign option:selected").attr("id");
				
		var dto = {
			"productLink": productLink,
			"campaignId": campaignId
		};
				
		if($('#file').val() == "" || $('#file').val() == null){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">Choose file!' + 
			'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			return;
		}
		
		$.ajax({
			url: "/api/agent/ad",
			type: 'POST',
			headers: {
           		'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       		},
			contentType: 'application/json',
			data: JSON.stringify(dto),
			success: function (adId) {
				console.log("success");
																
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Success!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				
				window.setTimeout(function(){
					var actionPath = "/api/agent/files-upload/ad?idContent=" + adId + "&type=ad";
					$('#form_image').attr('action', actionPath)
					$('#form_image').submit();
				},1000);
				return;
			},
			error: function (xhr) {
				console.log(xhr);
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});		
	});
	
	$('#pasteLink').click(function(){
	
		let link = localStorage.getItem('link');
		$('#productLink').text(link);
		//localStorage.removeItem('link');
		
	});
	
});


function getAllCampaigns() {	

	$.ajax({
		type:"GET", 
		url: "/api/agent/future/" + nistagramAgent,
		contentType: "application/json",
		success:function(campaigns){					
			for(i = 0; i < campaigns.length; i++) {
				addCampaignInComboBox(campaigns[i]);
			}								
		},
		error:function(jqXHR){
			console.log(jqXHR);
			console.log('Error getting campaigns');
		}
	});		
}


function addCampaignInComboBox(campaign) {
	let option = $('<option id="' + campaign.id + '" value="' + campaign.id + '">' + campaign.name + ' - ' + campaign.categoryName +
	                '  ' + campaign.campaignType.toLowerCase() + ' campaign - ' + campaign.contentType +'</option>');
	$('select#campaign').append(option);		
}

