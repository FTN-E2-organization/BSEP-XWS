
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
    let role = getRolesFromToken();
    
    if(role == null){
		window.location.href = "../html/login.html";
		return;
	}
    
    if(role != trueRole){
		if(role == "ROLE_CUSTOMER"){
			//pocetna strana customera
			window.location.href = "../html/products.html";
		}
		else if(role == "ROLE_AGENT"){
			//pocetna strana agenta
			window.location.href = "../html/checkToken.html";
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