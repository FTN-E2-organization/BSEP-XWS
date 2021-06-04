var username = "pero123";
$(document).ready(function () {	

	$.ajax({
		type:"GET", 
		url: "/api/publishing/story/highlight/" + username,
		contentType: "application/json",
		success:function(stories){
			$('#highlight-stories').empty();
			for (let s of stories){
				addRowInTable(s);
			}
		},
		error:function(){
			console.log('error getting highlight stories');
		}
	});

});

function addRowInTable(s){
	
	let row = $('<tr><td>' + s.id + '</td><td>' + s.ownerUsername + '</td>' +
				'<td>' + s.description + '</td>' + 
				'<td>' + s.isHighlight + '</td>' +
				'</tr>');
				
	$('#highlight-stories').append(row);
};