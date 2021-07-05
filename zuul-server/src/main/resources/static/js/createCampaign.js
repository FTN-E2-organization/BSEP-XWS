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

checkUserRole("ROLE_AGENT");
var username = getUsernameFromToken();

$(document).ready(function () {
	
	var d = new Date();
	d.setDate(d.getDate());
	
	$('#date').prop("min", d.toISOString().split("T")[0]);
	
	getAllCategories();
	
	/* on submit */
	$('form#create').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();
		
		let date = $('#date').val();
		let time = $('#time').val();
		let name = $('#name').val();		
		let contentType = "post";
		let category = $("#category option:selected").val();
		
		if($('#story').is(':checked')){
			contentType = "story";
		}	
		
		var dto = {
			"date": date,
			"time": time,
			"name": name,
			"contentType": contentType,
			"categoryName": category,
			"agentUsername": username
		};
				
		$.ajax({
			url: "/api/campaign/once-time",		
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(dto),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Success!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div>')
				$('#div_alert').append(alert);
				return;
			},
			error: function (xhr) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText +
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div>')
				$('#div_alert').append(alert);
				return;
			}
		});		
	});	
});


function getAllCategories() {	
	$.ajax({
		type:"GET", 
		url: "/api/auth/profile/categories",
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

