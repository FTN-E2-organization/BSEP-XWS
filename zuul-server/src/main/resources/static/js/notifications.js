checkUserRole("ROLE_REGULAR");
var username = getUsernameFromToken();

$(document).ready(function() {
	
	 $.ajax({
        type: "GET",
        url: "/api/notification/all/profile/" + username,
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        },
        contentType: "application/json",
        success: function(notifications) {
			for (let n of notifications) {
				addRow(n);
			}

        },
        error: function() {
            console.log('error getting notifications');
        }
    }); 	
});


function addRow(notification) {//zavrsiti ovu metodu...
	
	let row = $('<tr><td> <a href="profile.html?id=' + result.contentName + '" style="color:black;"> '+ result.contentName +' </a> </td></tr>');	
	$('#body_table').append(row);			
}


