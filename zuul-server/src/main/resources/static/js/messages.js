/*checkUserRole("ROLE_REGULAR");
var loggedInUsername = getUsernameFromToken();
var roles = getRolesFromToken();*/

loggedInUsername = "pera";

$(document).ready(function () {	
	
	$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');

	/*if(roles.indexOf("ROLE_AGENT") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
	}
	else if(roles.indexOf("ROLE_REGULAR") > -1){
		$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
	}*/
	
	
	setInterval(
	     function(){   
	          if($('#oneChat').is(":hidden") == false){
				let username = $('#receiverUsername').text();
				ajaxForGettingChat(username);
			  }
	     },
	     5000  
	);
	
	
	$.ajax({
		type:"GET", 
		url: "/api/notification/message/chat-usernames/" + loggedInUsername,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(usernames){
			$('#chatUsernamesBody').empty();
			for(let u of usernames)
				addUsernameToTable(u);
		},
		error:function(){
			console.log('error getting chat usernames');
		}
	});
	
	/*Send message in chat*/
	$('#sendMessage').click(function(){
		
		let text = $('#newMessagge').val();
		
		if(text == null || text == "")
			return;
			
		let receiverUsername = $('#receiverUsername').text();
			
		let messageDTO = {
			"text": text,
			"senderUsername": loggedInUsername,
			"receiverUsername": receiverUsername,
			"oneTimeContentPath":"",
			"requestType":"approved"
		};	
			
		$.ajax({
			url: "/api/aggregation/send-message",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(messageDTO),
			success: function () {
				$('#newMessagge').val('');
				ajaxForGettingChat(receiverUsername);
			},
			error: function (xhr) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});		
		
	});
	
	/*Get username for sending new message*/
	$('#searchUsernames').click(function(){
		
		$.ajax({
			type:"GET", 
			url: "/api/following/profile/all",
			contentType: "application/json",
			success:function(profiles){
				$('#profiles').empty();
				for (let p of profiles){
					addProfileToTable(p);
				}
			},
			error:function(){
				console.log('error getting profiles');
			}
		});
	});
	

});


function addUsernameToTable(u){
	let row = $('<tr><td style="font-size: 30px;"><a style="font-size:22px;" href="#" id="' + u +'" onclick="onClickUsername(this.id)">' + u + '</a></td></tr>');	
	$('#chatUsernamesBody').append(row);	
}

function onClickUsername(username){
	$('#oneChat').attr("hidden",false);
	$('#receiverUsername').text(username);
	
	ajaxForGettingChat(username);
		
}

function ajaxForGettingChat(username){
	$.ajax({
		type:"GET", 
		url: "/api/notification/message/chat/" + loggedInUsername + "/" + username,
		headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       	},
		contentType: "application/json",
		success:function(messageDTOs){
			$('#receivedMessagesBody').empty();
			for(let dto of messageDTOs)
				addReceivedMessageToTable(dto);
		},
		error:function(){
			console.log('error getting received messages');
		}
	});	
}

function addReceivedMessageToTable(dto){
	
	let text = '<tr><td>' + dto.text + '</td></tr>';
	
	if(dto.text.includes("http")){
		text = '<tr><td><a href="' + dto.text +'">' + dto.text + '</a></td></tr>';
	}
	
	let row = $('<tr><td><table width:100%>'
				  + '<tr><td><i>' + dto.timestamp.split('T')[0] + "  " + dto.timestamp.split('T')[1].substring(0, 5) + '</i></td></tr>'
				  + text
				  + '</table></td></tr>');	
	

	if(dto.senderUsername == loggedInUsername){
		let text = '<tr><td style="text-align: right">' + dto.text + '</td></tr>';
	
		if(dto.text.includes("http")){
			text = '<tr><td style="text-align: right"><a href="' + dto.text +'">' + dto.text + '</a></td></tr>';
		}
		
		
		row = $('<tr><td width="75%"></td><td><table width:100%>'
				  + '<tr><td style="text-align: right"><i>' + dto.timestamp.split('T')[0] + "  " + dto.timestamp.split('T')[1].substring(0, 5) + '</i></td></tr>'
				  + text
				  + '</table></td></tr>');	
	}
			  
	$('#receivedMessagesBody').append(row);
}

function addProfileToTable(profile){
	let row = $('<tr><td><a href="#" id="' + profile.username +'" onclick="sendMessageToUsername(this.id)">'+ profile.username +'</a></td></tr>');	
	$('#profiles').append(row);
}

function sendMessageToUsername(receiverUsername){
	 $('#btn_close').click();
	 onClickUsername(receiverUsername);
}