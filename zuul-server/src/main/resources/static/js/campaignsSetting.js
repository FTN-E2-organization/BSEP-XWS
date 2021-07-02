
//checkUserRole("ROLE_AGENT");
//var username = getUsernameFromToken();

var username = "pera";

$(document).ready(function () {
	
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
});



function addRow(campaign) {
	let times = campaign.dailyFrequency[0];
	for (let i = 1; i < campaign.dailyFrequency.length; i++) {
		times += ", " + campaign.dailyFrequency[i];
	}
	
	let row = $('<tr><td>' + campaign.id + '</td>'
				+ '<td>' + campaign.campaignType + '</td>'
				+ '<td>' + campaign.categoryName + '</td>'
				+ '<td>' + campaign.name + '</td>'
				+ '<td>' + campaign.startDate + '</td>'
				+ '<td>' + campaign.endDate + '</td>'
				+ '<td>' + times + '</td>'
				+ '<td>' + campaign.isDeleted + '</td>'			
				+ '  </td></tr>');
				
	$('#body_table').append(row);			
}
