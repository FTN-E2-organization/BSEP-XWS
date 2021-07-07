
checkUserRole("ROLE_REGULAR");
var username = getUsernameFromToken();

var timeList = new Array();
var campaignsMap = {};
var updatedCampaign = 0;

$(document).ready(function () {
	var d = new Date();
	d.setDate(d.getDate() + 1);
	
	$('#startDate').prop("min", d.toISOString().split("T")[0]);
	$('#endDate').prop("min",  d.toISOString().split("T")[0]);
	
	getAllCampaigns();
	
});


function getAllCampaigns() {	
	 $.ajax({
        type: "GET",
        url: "/api/campaign/requests/"+username,
		headers: {
           	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
        contentType: "application/json",
        success: function(campaigns) {
        	localStorage.setItem("campaigns",JSON.stringify(campaigns));
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
	campaignsMap[campaign.id] = campaign;
	
	let times = campaign.dailyFrequency[0];
	for (let i = 1; i < campaign.dailyFrequency.length; i++) {
		times += ", " + campaign.dailyFrequency[i];
	}
	
	let btnAccept = '<button onclick="acceptCampaign(this.id)" class="btn btn-info btn-sm" id="' + campaign.id + '" >accept</button>';	
	let btnDeny = '<button onclick="denyCampaign(this.id)" class="btn btn-info btn-sm" id="' + campaign.id + '" >deny</button>';	
	
	let today = new Date().toISOString().slice(0, 10);
	let t = new Date(campaign.lastUpdateTime);
	t.setTime(t.getTime() + (24*60*60*1000));
	
	let row = $('<tr>'
				+ '<td>' + campaign.campaignType + '</td>'
				+ '<td>' + campaign.categoryName + '</td>'
				+ '<td>' + campaign.name + '</td>'
				+ '<td>' + campaign.startDate + '</td>'
				+ '<td>' + campaign.endDate + '</td>'
				+ '<td>' + times + '</td>'
				+ '<td>' + btnAccept + '</td>'
				+ '<td>' + btnDeny + '</td>'
				+ '</tr>');
				
	$('#body_table').append(row);			
}

function acceptCampaign(campaignId) {
console.log(campaignId)
	let campaigns = JSON.parse(localStorage.getItem("campaigns"));
	let id=-1;
	for(let c of campaigns){
		if(c.id==campaignId){
			id=c.requestId;
			break;
		}
	}
	var requestDTO = {
	        "campaignId": campaignId,
	        "profileUsername": username,
	        "isApproved": true,
	        "id": id,
		};
	 $.ajax({
        type: "POST",
        url: "/api/campaign/judge/influence-request",
		headers: {
           	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
        contentType: "application/json",
        data: JSON.stringify(requestDTO),
        success: function() {

			getAllCampaigns();
        },
        error: function() {
            console.log('error accepting campaign');
        }
    });	
}

function denyCampaign(campaignId) {
	let campaigns = JSON.parse(localStorage.getItem("campaigns"));
		let id=-1;
		for(let c of campaigns){
			if(c.id==campaignId){
				id=c.requestId;
				break;
			}
		}
	var requestDTO = {
	        "campaignId": campaignId,
	        "profileUsername": username,
	        "isApproved": false,
	        "id": id,
		};
	 $.ajax({
        type: "POST",
        url: "/api/campaign/judge/influence-request",
		headers: {
           	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
        contentType: "application/json",
        data: JSON.stringify(requestDTO),
        success: function() {
			
			getAllCampaigns();
        },
        error: function() {
            console.log('error denying campaign');
        }
    });	
}
