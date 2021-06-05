
var resultsList = null;

$(document).ready(function () {
	
	getProfilesAndLocationsAndHashtags();
	
	$('#name').on('input',function(e){
    	getSearchedProfilesAndLocationsAndHashtags();
	});	
		
});


function handleClick() {
	getSearchedProfilesAndLocationsAndHashtags();
}


function getProfilesAndLocationsAndHashtags() {
	
    $.ajax({
        url: "/api/aggregation/search",
		type: 'GET',
		contentType: 'application/json',
        success: function (results) {
			resultsList = results;
        },
        error: function (jqXHR) {
            alert(jqXHR.responseText);
        }
    });					
}



function getSearchedProfilesAndLocationsAndHashtags() {
	$('#body_table').empty();
	
	let name = $('#name').val();
	if (name == "")
		return;
	
	for (let i = 0; i < resultsList.length; i++) {
		if (document.getElementById("all").checked && resultsList[i].contentName.includes(name))
			if (resultsList[i].section == "profile") 
				addProfileRow(resultsList[i]);
			else if (resultsList[i].section == "location")
				addLocationRow(resultsList[i]);		
			else if (resultsList[i].section == "hashtag")
				addHashtagRow(resultsList[i]);	
		else if (resultsList[i].section == "location" && document.getElementById("locations").checked && resultsList[i].contentName.includes(name))
			addLocationRow(resultsList[i]);
		else if (resultsList[i].section == "profile" && document.getElementById("profiles").checked && resultsList[i].contentName.includes(name))
			addProfileRow(resultsList[i]);
		else if (resultsList[i].section == "hashtag" && document.getElementById("hashtags").checked && resultsList[i].contentName.includes(name))
			addHashtagRow(resultsList[i]);
	}	
}


function addProfileRow(result) {	
	let row = $('<tr><td> <a id="'+ result.contentName + '" onclick="contentOverview(id);" class="nav-link .text-dark" href="profile.html" style="color:black;"> '+ result.contentName +' </a> </td></tr>');	
	$('#body_table').append(row);	
}

function contentOverview(contentName) {
	localStorage.setItem("contentName", contentName);	
}


function addLocationRow(result) {	
	let row = $('<tr><td> <a id="'+ result.contentName + '" onclick="contentOverview(id);" class="nav-link .text-dark" href="location.html" style="color:black;"> ' 
				+ result.contentName +' </a> </td></tr>');	
	$('#body_table').append(row);	
}


function addHashtagRow(result) {	
	let row = $('<tr><td> <a id="'+ result.contentName + '" onclick="contentOverview(id);" class="nav-link .text-dark" href="hashtag.html" style="color:black;"> ' 
				+ result.contentName +' </a> </td></tr>');	
	$('#body_table').append(row);	
}
