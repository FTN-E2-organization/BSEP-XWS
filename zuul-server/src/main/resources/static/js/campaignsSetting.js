
//checkUserRole("ROLE_AGENT");
//var username = getUsernameFromToken();

var username = "pera";

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
	campaignsMap[campaign.id] = campaign;
	
	let times = campaign.dailyFrequency[0];
	for (let i = 1; i < campaign.dailyFrequency.length; i++) {
		times += ", " + campaign.dailyFrequency[i];
	}
	
	let btnEdit = '<button onclick="editCampaignModalDialog(this.id)" class="btn btn-info btn-sm" data-toggle="modal" data-target="#centralModal" class="btn btn-link" id="' + campaign.id + '" >edit</button>';
	let btnDelete = '<button onclick="deleteCampaign(this.id)" class="btn btn-danger btn-sm" id="' + campaign.id + '" >delete</button>';	
	
	let today = new Date().toISOString().slice(0, 10);
	let t = new Date(campaign.lastUpdateTime);
	t.setTime(t.getTime() + (24*60*60*1000));
	if (campaign.isDeleted == true || campaign.campaignType == "once_time" || (campaign.startDate <= today) || (t.toLocaleString() > new Date().toLocaleString())) {
		btnEdit = '<button onclick="editCampaignModalDialog(this.id)" class="btn btn-info btn-sm" data-toggle="modal" data-target="#centralModal" class="btn btn-link" id="' + campaign.id + '" disabled> edit </button>';
	}
	if (campaign.isDeleted == true) {
		btnDelete = '<button onclick="deleteCampaign(this.id)" class="btn btn-danger btn-sm" id="' + campaign.id + '" disabled> delete </button>';
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
				+ '</tr>');
				
	$('#body_table').append(row);			
}


function editCampaignModalDialog(campaignId) {
	updatedCampaign = campaignId;
	timeList.length = 0;
	for (let time of campaignsMap[campaignId].dailyFrequency) {
		timeList.push(time.slice(0, -3));
	}	
	$('#name').val(campaignsMap[campaignId].name);
	$('#startDate').val(campaignsMap[campaignId].startDate);
	$('#endDate').val(campaignsMap[campaignId].endDate);
	document.getElementById('time').value = '';
	$('#table_times').empty();	
	for (let time of timeList) {		
		$('table#table_times').append('<tr><td>' + time + ' </td></tr>');
	}
}


function saveSettings() {
	
	let startDate = $('#startDate').val();
	let endDate = $('#endDate').val();
	let name = $('#name').val();		
	
	if (timeList.length == 0) {
		let alert = $('<div class="alert alert-info alert-dismissible fade show m-1" role="alert">You have to add time!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div>')
		$('#div_alert').append(alert);
		return;
	}
	
	if(startDate > endDate) {
		let alert = $('<div class="alert alert-info alert-dismissible fade show m-1" role="alert">The start date must be after the end date!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div>')
		$('#div_alert').append(alert);
		return;
	}
	
	var dto = {
			"id": updatedCampaign,
			"startDate": startDate,
			"endDate": endDate,
			"name": name,
			"dailyFrequency": timeList
	};
	
	$.ajax({
			type:"POST", 
			url: "/api/campaign/multiple/update",			
			contentType: "application/json",
			data: JSON.stringify(dto),
			success:function(){
  				console.log("success");	
				$('#centralModal').modal('hide');
				getAllCampaigns();
			},
			error:function(xhr){
				console.log('error saving campaign - ' + xhr.responseText);
			}
	});			
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


function chooseTime() {	
	let time = $('#time').val();
	if (!timeList.includes(time)){		
		timeList.push(time);		
		$('table#table_times').append('<tr><td>' + time + ' </td></tr>');		
	}			
};


function removeTime() {	
	let time = $('#time').val();
	if (timeList.includes(time)){	
		let index = timeList.indexOf(time);
		timeList.splice(index, 1);		
		$('#table_times').empty();	
		for (let time of timeList) {		
			$('table#table_times').append('<tr><td>' + time + ' </td></tr>');
		}		
	}			
};

