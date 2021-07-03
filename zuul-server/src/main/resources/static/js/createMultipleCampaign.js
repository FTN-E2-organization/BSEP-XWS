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

var timeList = new Array(); 

$(document).ready(function () {
	
	var d = new Date();
	d.setDate(d.getDate() + 1);
	
	$('#startDate').prop("min", d.toISOString().split("T")[0]);
	$('#endDate').prop("min",  d.toISOString().split("T")[0]);
	
	getAllCategories();
	
	/* on submit */
	$('form#create').submit(function (event) {

		event.preventDefault();
		$('#div_alert').empty();
		
		let startDate = $('#startDate').val();
		let endDate = $('#endDate').val();
		let name = $('#name').val();		
		let contentType = "post";
		let category = $("#category option:selected").val();
		
		if($('#story').is(':checked')){
			contentType = "story";
		}	
		
		if(startDate > endDate) {
			let alert = $('<div class="alert alert-info alert-dismissible fade show m-1" role="alert">The start date must be after the end date!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div>')
			$('#div_alert').append(alert);
			return;
		}
		
		if (timeList.length == 0) {
			let alert = $('<div class="alert alert-info alert-dismissible fade show m-1" role="alert">You have to add time!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div>')
			$('#div_alert').append(alert);
			return;
		}
		
		var dto = {
			"startDate": startDate,
			"endDate": endDate,
			"name": name,
			"dailyFrequency": timeList,
			"contentType": contentType,
			"categoryName": category,
			"agentUsername": username
		};
				
		$.ajax({
			url: "/api/campaign/multiple",
			type: 'POST',
			headers: {
          	 	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       		},				
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
		headers: {
           	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},		
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


function chooseTime() {	
	let time = $('#time').val();
	if (!timeList.includes(time)){		
		timeList.push(time);		
		$('table#table_times').append('<tr><td>' + time + ' </td></tr>');		
	}			
};
