/*checkUserRole("ROLE_REGULAR");
var loggedInUsername = getUsernameFromToken();
var roles = getRolesFromToken();*/

var loggedInUsername = "sladja";
var messagesLength = 0;

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
		let receiverUsername = $('#receiverUsername').text();	
		let fileLenght = $("#file")[0].files.length;
		let isOneTimeContent = false;
		
		
		if((text == null || text == "") && fileLenght == 0)
			return;
			
		if(fileLenght != 0){
			text = "";
			isOneTimeContent = true;
		}
		
		let messageDTO = {
			"text": text,
			"senderUsername": loggedInUsername,
			"receiverUsername": receiverUsername,
			"isOneTimeContent":isOneTimeContent,
			"requestType":"approved"
		};	
		
		$.ajax({
			url: "/api/aggregation/send-message",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(messageDTO),
			success: function (sentMessageDTO) {
								
				if(fileLenght != 0){
					var actionPath = "/api/aggregation/files-upload/message?idContent=" + sentMessageDTO.id + "&type=message";
					$('#form_image').attr('action', actionPath)
					$('#form_image').submit();
				}
				
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
		contentType: "application/json",
		success:function(messageDTOs){			
			if(messageDTOs.length != messagesLength){
				messagesLength = messageDTOs.length;
				currentId = 1;
				$('#receivedMessagesBody').empty();
				for(let dto of messageDTOs){
					addReceivedMessageToTable(dto);
				}
			}
		},
		error:function(){
			console.log('error getting received messages');
		}
	});	
}

function addReceivedMessageToTable(dto){
			
	if(dto.text.includes("http")){
		addLinkMessage(dto);		
	}else if(dto.text == "" && dto.isOneTimeContent){
		addFileMessage(dto);
	}else{
		addTextMessage(dto);
	}
			  
}

function addLinkMessage(dto){
	let text;
	let row;
		
	let textAlign = "left";
	let newTd = '';
	
	if(dto.senderUsername == loggedInUsername){
		textAlign = "right";
		newTd = '<td width="75%"></td>';
	}

	let messageDTO = {
		"text": dto.text
	};	
	
	$.ajax({
		type:"POST", 
		url: "/api/aggregation/message-content",
		contentType: "application/json",
		data: JSON.stringify(messageDTO),
		success:function(msg){
			
			if(msg != null && msg != ""){
				text = '<tr><td style="text-align: ' + textAlign +'; font-size:18px;">' + msg + '</td></tr>';
				row = $('<tr>' + newTd +'<td><table style="width:100%">'
				  + '<tr><td style="text-align: ' + textAlign +';"><i>' + dto.timestamp.split('T')[0] + "  " + dto.timestamp.split('T')[1].substring(0, 5) + '</i></td></tr>'
				  + text
				  + '</table></td></tr>');	
				  
				 $('#receivedMessagesBody').append(row);
			}
			else{
				text = '<tr><td style="text-align: ' + textAlign +'; font-size:18px;"><a href="' + dto.text +'">' + dto.text + '</a></td></tr>';
				row = $('<tr>'+ newTd +'<td><table style="width:100%">'
				  + '<tr><td style="text-align: ' + textAlign +';"><i>' + dto.timestamp.split('T')[0] + "  " + dto.timestamp.split('T')[1].substring(0, 5) + '</i></td></tr>'
				  + text
				  + '</table></td></tr>');	
				  
				 $('#receivedMessagesBody').append(row);
			}
			
		},
		error:function(xhr){
			console.log(xhr.responseText);
			help(row);
			return row; 
		}
	});	
}

function addFileMessage(dto){
	$.ajax({
        type: "GET",
        url: "/api/media/one/" + dto.id + "/" + "message",
        contentType: "application/json",
        success: function(media) {
        	let grouped={}
        	for(let m of media){
  				if(grouped[m.idContent]){
  					grouped[m.idContent].push(m)
  				}else{
  					grouped[m.idContent]=[m]
  				}
        	}

            for (let m in grouped) {

                fetch('/api/media/files/' +grouped[m][0].path)
                    .then(resp => resp.blob())
                    .then(blob => {
                        const url = window.URL.createObjectURL(blob);
                        
                        let textAlign = "left";
						let newTd = '';
						
						if(dto.senderUsername == loggedInUsername){
							textAlign = "right";
							newTd = '<td width="75%"></td>';
						}
                       
						row = $('<tr>' + newTd +'<td><table style="width:100%">'
							  + '<tr><td style="text-align: ' + textAlign +'"><i>' + dto.timestamp.split('T')[0] + "  " + dto.timestamp.split('T')[1].substring(0, 5) + '</i></td></tr>'
							  + '<tr><td style="text-align: ' + textAlign +'"><div><img height="150px" width="150px"   src="' + url + '"></div></td></tr>'
							  + '</table></td></tr>');	
							  
						$('#receivedMessagesBody').append(row);
                    })
                    .catch(() => console.log('oh no!'));
            }
	 },
    error: function() {
        console.log('error getting image');
    }
	}); 
};

function addTextMessage(dto){
	let text;
	let row;
	
	let textAlign = "left";
	let newTd = '';
	
	if(dto.senderUsername == loggedInUsername){
		textAlign = "right";
		newTd = '<td width="75%"></td>';
	}
	
	text = '<tr><td style="text-align: ' + textAlign +'; font-size:18px;">' + dto.text + '</td></tr>';	
		
	row = $('<tr>' + newTd +'<td><table style="width:100%">'
			  + '<tr><td style="text-align: ' + textAlign +'"><i>' + dto.timestamp.split('T')[0] + "  " + dto.timestamp.split('T')[1].substring(0, 5) + '</i></td></tr>'
			  + text
			  + '</table></td></tr>');	
			  
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

function help(row){
	console.log(row);
}