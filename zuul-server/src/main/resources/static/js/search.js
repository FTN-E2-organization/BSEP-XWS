
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
			addResultRow(resultsList[i].contentName);				
		else if (resultsList[i].section == "location" && document.getElementById("locations").checked && resultsList[i].contentName.includes(name))
			addResultRow(resultsList[i].contentName);
		else if (resultsList[i].section == "profile" && document.getElementById("profiles").checked && resultsList[i].contentName.includes(name))
			addResultRow(resultsList[i].contentName);
		else if (resultsList[i].section == "hashtag" && document.getElementById("hashtags").checked && resultsList[i].contentName.includes(name))
			addResultRow(resultsList[i].contentName);
	}	
}


function addResultRow(contentName) {	
	let row = $('<tr><td>'+ contentName +'</td></tr>');	
	$('#body_table').append(row);	
}


