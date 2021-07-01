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
	$('form#create').submit(function (event) {

	
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
	let option = $('<option id="' + campaign.id + '" value="' + campaign.id + '">' + campaign.id + ' - ' + campaign.categoryName +
	                '  ' + campaign.campaignType + '  ' + campaign.contentType +'</option>');
	$('select#campaign').append(option);		
}

