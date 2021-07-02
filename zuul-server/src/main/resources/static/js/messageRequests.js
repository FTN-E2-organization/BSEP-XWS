/*checkUserRole("ROLE_REGULAR");
var loggedInUsername = getUsernameFromToken();
var roles = getRolesFromToken();*/

loggedInUsername = "pero";

$(document).ready(function () {	
	
	$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');

	/*if(roles.indexOf("ROLE_AGENT") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
	}
	else if(roles.indexOf("ROLE_REGULAR") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
	}*/
	
	

});