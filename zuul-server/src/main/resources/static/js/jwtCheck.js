$(document).ready(function () {
	
    token = localStorage.getItem("token");

    /*if (token == null) {
		localStorage.clear();
		
		if (window.location.href.indexOf("login.html") ==  -1)
        	document.body.appendChild(document.createElement('script')).src='../../js/navbar/unauthenticated_user.js';
            window.location.href = "../html/login.html"; 

        return;
    }
    else
    {
		roles = getRolesFromToken();
	
		//sadrzi ulogu regular usera
		if(roles.indexOf("ROLE_REGULAR") > -1){ 
			 document.body.appendChild(document.createElement('script')).src='../../js/navbar/regular_user.js';
		}
	}*/
});

function decodeToken(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

function getRolesFromToken() {
	try{
		return decodeToken(localStorage.getItem("token")).roles;
	}
    catch(err){
		window.location.href = "../html/login.html";
	}
}

function getUsernameFromToken() {
	try{
		return decodeToken(localStorage.getItem("token")).sub;
	}
    catch(err){
		//window.location.href = "../html/login.html";
		return null;
	}
}


function checkUserRole(trueRole) {
    var roles = getRolesFromToken();
    
    if (roles.indexOf(trueRole) <= -1) {
		if(rroles.indexOf("ROLE_REGULAR") > -1){
			window.location.href = "../html/index.html";
		}
		/*else if(role == "ROLE_ADMIN"){
			window.location.href = "../html/index.html";
		}*/
	}
}

function logOut() {
    localStorage.clear();
    window.location.href = "../html/login.html";
}