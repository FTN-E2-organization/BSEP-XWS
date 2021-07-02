
//checkUserRole("ROLE_AGENT");
//var username = getUsernameFromToken();

var username = "pera";

$(document).ready(function () {
	
	getAllCampaigns();

});


function getAllCampaigns() {	
	 $.ajax({
        type: "GET",
        url: "/api/campaign/" + username,
        contentType: "application/json",
        success: function(campaigns) {
			console.log(campaigns.length + " campaigns")
			$('#body_table').empty();
			for (let i = campaigns.length-1; i >= 0; i--) {
				addRow(campaigns[i]);
			}
        },
        error: function() {
            console.log('error getting campaigns');
        }
    }); 	
}



function addRow(campaign) {
	let times = campaign.dailyFrequency[0];
	for (let i = 1; i < campaign.dailyFrequency.length; i++) {
		times += ", " + campaign.dailyFrequency[i];
	}
	
	let btnEdit = '<button onclick="fillInTheCheckbox()" class="btn btn-info btn-sm" data-toggle="modal" data-target="#centralModalNotificationSettings" class="btn btn-link" id="btnEdit" >edit</button>';
	let btnDelete = '<button onclick="deleteCampaign(this.id)" class="btn btn-danger btn-sm" id="' + campaign.id + '" >delete</button>';
	
	if (campaign.isDeleted == true || campaign.campaignType == "once_time" /* ili je startDate prosao */) {
		btnEdit = "";
	}
	if (campaign.isDeleted == true) {
		btnDelete = "";
	}	
	
	let row = $('<tr><td>' + campaign.id + '</td>'
				+ '<td>' + campaign.campaignType + '</td>'
				+ '<td>' + campaign.categoryName + '</td>'
				+ '<td>' + campaign.name + '</td>'
				+ '<td>' + campaign.startDate + '</td>'
				+ '<td>' + campaign.endDate + '</td>'
				+ '<td>' + times + '</td>'
				+ '<td>' + campaign.isDeleted + '</td>'	
				+ '<td>' + btnEdit + '</td>'
				+ '<td>' + btnDelete + '</td>'			
				+ ' </tr>');
				
	$('#body_table').append(row);			
}



function deleteCampaign(campaignId) {
	 $.ajax({
        type: "PUT",
        url: "/api/campaign/" + campaignId,
        contentType: "application/json",
        success: function() {
			console.log("successful deletion")
			getAllCampaigns();
        },
        error: function() {
            console.log('error deleting campaign');
        }
    });	
}


