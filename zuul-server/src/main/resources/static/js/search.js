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

var resultsList = null;
var loggedInUsername = getUsernameFromToken();

$(document).ready(function () {
	
	getProfilesAndLocationsAndHashtags();
	
	$('#name').on('input',function(e){
		$('#resultHidden').attr("hidden",false);
    	getSearchedProfilesAndLocationsAndHashtags();
    	
    	if($('#name').val() == ""){
			$('#resultHidden').attr("hidden",true);
		}
	});	
		
});


function handleClick() {
	getSearchedProfilesAndLocationsAndHashtags();
}


function getProfilesAndLocationsAndHashtags() {
	
	var url;
	if (loggedInUsername == null) {
		url = "/api/aggregation/search/public"
	}
	else {
		url = "/api/aggregation/search/public-and-private"
	}
	
    $.ajax({
        url: url,
		type: 'GET',
		contentType: 'application/json',
        success: function (results) {
			resultsList = results;
        },
        error: function (jqXHR) {
            console.log('Error ' + jqXHR.responseText);
        }
    });					
}



function getSearchedProfilesAndLocationsAndHashtags() {
	$('#body_table').empty();
		
	let name = escapeHtml($('#name').val());
	if (name == "")
		return;
	
	for (let i = 0; i < resultsList.length; i++) {
		if (document.getElementById("all").checked && (resultsList[i].contentName.toLowerCase()).includes(name.toLowerCase())) {
			if (resultsList[i].section == "profile") 
				addProfileRow(resultsList[i]);
			else if (resultsList[i].section == "location")
				addLocationRow(resultsList[i]);		
			else if (resultsList[i].section == "hashtag")
				addHashtagRow(resultsList[i]);	
		}				
		else if (resultsList[i].section == "location" && document.getElementById("locations").checked && (resultsList[i].contentName.toLowerCase()).includes(name.toLowerCase())) {
			addLocationRow(resultsList[i]);
		}			
		else if (resultsList[i].section == "profile" && document.getElementById("profiles").checked && (resultsList[i].contentName.toLowerCase()).includes(name.toLowerCase())) {
			addProfileRow(resultsList[i]);
		}
		else if (resultsList[i].section == "hashtag" && document.getElementById("hashtags").checked && (resultsList[i].contentName.toLowerCase()).includes(name.toLowerCase())) {
			addHashtagRow(resultsList[i]);
		}
	}	
}


function addProfileRow(result) {	
	let row = $('<tr><td> <a href="profile.html?id=' + result.contentName + '" style="color:black;"> '+ result.contentName +' </a> </td></tr>');	
	$('#body_table').append(row);	
}


function addLocationRow(result) {	
	let row = $('<tr><td> <a href="location.html?id=' + result.contentName + '" style="color:black;"> '+ result.contentName +' </a> </td></tr>');
	$('#body_table').append(row);	
}


function addHashtagRow(result) {	
	let row = $('<tr><td> <a href="hashtag.html?id=' + result.contentName.substring(1) + '" style="color:black;"> '+ result.contentName +' </a></td></tr>');	
	$('#body_table').append(row);	
}

function escapeHtml(string) {
	return String(string).replace(/[&<>"'`=\/]/g, function (s) {
		return entityMap[s];
	});
};