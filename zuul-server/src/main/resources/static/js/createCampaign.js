var entityMap = {
	'&': '&amp;',
	'<': '&lt;',
	'>': '&gt;',
	'"': '&quot;',
	"'": '&#39;',
	'/': '&#x2F;',
	'`': '&#x60;',
	'=': '&#x3D;'
};

//checkUserRole("ROLE_AGENT");
//var username = getUsernameFromToken();

var username = "pera";

$(document).ready(function () {
	
	getAllCategories();
	
});


function getAllCategories() {
	
	$.ajax({
		type:"GET", 
		url: "/api/auth/profile/categories",
//		headers: {
//            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
//      },
		contentType: "application/json",
		success:function(categories){					
			for(i = 0; i < categories.length; i++) {
				addCategoryInComboBox(categories[i]);
			}								
		},
		error:function(jqXHR){
			console.log(jqXHR);
			console.log('Error getting categories');
		}
	});		
}


function addCategoryInComboBox(category) {
	let option = $('<option id="' + category.id + '" value="' + category.name + '">' + category.name + '</option>');
	$('select#category').append(option);		
}













