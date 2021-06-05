$(document).ready(function () {
	
	alert("search js");	
	
	getProfilesAndLocationsAndHashtags();

});



function getProfilesAndLocationsAndHashtags() {
	
    $.ajax({
        url: "/api/aggregation/search",
		type: 'GET',
		contentType: 'application/json',
        success: function (results) {
            if (results.length == 0) {
                let alert = '<div id="loading" class="alert alert-info" role="alert"> Loading... </div>';
                $("#loading").hide();
                $("#div_alert").prepend(alert);
            }
            else {
                for (let i = 0; i < results.length; i++) {
//					alert(results[i].contentName);
                    addResultRow(results[i].contentName);
                }
                $("#loading").hide();
            }
        },
        error: function (jqXHR) {
            let alert = '<div id="loading" class="alert alert-danger" role="alert"> Error! ' + jqXHR.responseText + '</div>';
            $("#loading").hide();
            $("#div_alert").prepend(alert);
        }
    });				
	
}


function addResultRow(contentName) {	
	let row = $('<tr><td>'+ contentName +'</td></tr>');	
	$('#body_table').append(row);	
}


