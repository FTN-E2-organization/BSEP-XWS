checkUserRole("ROLE_REGULAR");
var username = getUsernameFromToken();
var roles = getRolesFromToken();

$(document).ready(function() {
	
	if(roles.indexOf("ROLE_AGENT") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
	}
	else if(roles.indexOf("ROLE_REGULAR") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
	}
		
	getNotifications();
	
});

function getNotifications(){
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
			$('#body_table').empty();
			for (let i = notifications.length-1; i >= 0; i--) {
				addRow(notifications[i]);
			}
        },
        error: function() {
            console.log('error getting notifications');
        }
    }); 	
}


function addRow(notification) {
	
	
	
	let row = $('<tr><td><a class="text-info" href="' + notification.contentLink + '" style="color:black;">User ' + 
				notification.description + ' at ' + 
				notification.timestamp.split('T')[0] + '  ' + notification.timestamp.split('T')[1].substr(0,5) + '.</a></td></tr>');
				
	$('#body_table').append(row);			
}


