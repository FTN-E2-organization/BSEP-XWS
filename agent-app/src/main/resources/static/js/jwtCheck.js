
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
		return decodeToken(localStorage.getItem("token")).role;
	}
    catch(err){
		return null;
	}
}

function getUsernameFromToken() {
	try{
		return decodeToken(localStorage.getItem("token")).sub;
	}
    catch(err){
		return null;
	}
}


function checkUserRole(trueRole) {
    let roles = getRolesFromToken();
    
    if (roles.indexOf(trueRole) <= -1) {
		if(roles.indexOf("ROLE_AGENT") > -1){
			window.location.href = "../html/checkToken.html";
		}
		else if(roles.indexOf("ROLE_CUSTOMER") > -1){
			window.location.href = "../html/products.html";
		}
	}
}

function logOut() {
    localStorage.clear();
    window.location.href = "../html/login.html";
}

function getUsernameFromApiToken(token) {
	try{
		return decodeToken(token).sub;
	}
    catch(err){
		return null;
	}
}