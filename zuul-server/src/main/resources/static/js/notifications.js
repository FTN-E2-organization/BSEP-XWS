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
			console.log('success - notifications');
			console.log(notifications.length + " notifications")
			for (let n of notifications) {
				addRow(n);
			}

        },
        error: function() {
            console.log('error getting notifications');
        }
    }); 	
});


function addRow(notification) {
	
	let row = $('<tr><td>' + notification.wantedUsername + ' </td> ' 
				+ '<td>' + notification.notificationType + ' </td><td> <a class="text-info" href="' + notification.contentLink + '" style="color:black;"> link </a> </td>'
				+ ' <td> ' + notification.timestamp + ' </td> ' +
				' <td> ' + notification.description + ' </td> </tr>');	
	$('#body_table').append(row);			
}


