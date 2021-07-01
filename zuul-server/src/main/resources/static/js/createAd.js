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

//checkUserRole("ROLE_AGENT");
//var username = getUsernameFromToken();

var username = "pera";

$(document).ready(function () {
	
	getAllCampaigns();
	
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
			url: "/api/campaign/ad",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(dto),
			success: function (adId) {
				console.log("success");
				
				//ne znam treba li slanje notifikacije...
												
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Success!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				
				window.setTimeout(function(){
					var actionPath = "/api/aggregation/files-upload/ad?idContent=" + adId + "&type=ad";
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
});


function getAllCampaigns() {	
	$.ajax({
		type:"GET", 
		url: "/api/campaign/" + username,
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
	                '  ' + campaign.campaignType + ' campaign - ' + campaign.contentType +'</option>');
	$('select#campaign').append(option);		
}

