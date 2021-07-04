var loggedInUsername = getUsernameFromToken();
var roles = getRolesFromToken();
var storyOwner = null;
var idContent = null;
var type = null;

$(document).ready(function () {		
	
	if(loggedInUsername == null){
		$('head').append('<script type="text/javascript" src="../js/navbar/unauthenticated_user.js"></script>');
		hideComponents();
	}else{	
		if(roles.indexOf("ROLE_AGENT") > -1){
			$('head').append('<script type="text/javascript" src="../js/navbar/agent.js"></script>');
		}	
		else if(roles.indexOf("ROLE_REGULAR") > -1){
			$('head').append('<script type="text/javascript" src="../js/navbar/regular_user.js"></script>');
		}else if(roles.indexOf("ROLE_ADMIN") > -1){
			$('head').append('<script type="text/javascript" src="../js/navbar/admin.js"></script>');
			hideComponents();
		}
	}

	let url_split  = window.location.href.split("?")[1]; 
	idContent = url_split.split("=")[1];
	type = "ad";
	
	getPostInfo();
	    
    /*Click on Report content button*/
	$('#reportBtn').click(function(){

		let reason = $('#reportReason').val();
		
		var reportDTO = {
			"reason": reason,
			"contentId": idContent,
			"type":"story",
			"ownerUsername":storyOwner
		};
	
		
		$.ajax({
			url: "/api/publishing/report-content",
			headers: {
            	'Authorization': 'Bearer ' + window.localStorage.getItem('token')
       		},
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(reportDTO),
			success: function () {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful reporting content!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				$('#reportReason').val('');
				$('#btn_close_report').click();
				
				setTimeout(function () { history.back(); }, 5000);
				return;
			},
			error: function (xhr) {
				let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText +
					 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				return;
			}
		});		
		
	});
    
});

function addPost(path, j) {
	let image_div;
	if(j==0){
		image_div = $('<div class="carousel-item active">' +
        '<video id="videoPlay" class="d-block" height="520px" width="640px" poster="' + path + '">' +
        '<source src= "' + path + '" type="video/mp4"></video></div>');
     
	}else{
    	image_div = $('<div class="carousel-item">' +
        '<video id="videoPlay" class="d-block" height="520px" width="640px" poster="' + path + '">' +
        '<source src= "' + path + '" type="video/mp4"></video></div>');
    }
    $('div#story_image').append(image_div);
    
    $('#videoPlay').trigger('play');
};

function hideComponents(){
	$('#reportIcon').attr("hidden",true);
}

function getPostInfo(){
	
	$.ajax({
		type:"GET", 
		url: "/api/campaign/ad/" + idContent,
		contentType: "application/json",
		success:function(ad){
		
		$.ajax({
		type:"GET", 
		url: "/api/campaign/" + ad.campaignId,
		contentType: "application/json",
		success:function(campaign){
			
			getPostImage();

			$('#usernameH5').append(" " + campaign.name);
	
			$('#body_table').empty();
			
			
			if (ad.productLink != null && ad.productLink != "") {
				let row = $('<tr><td><a href = "' + ad.productLink +'">' + ad.productLink +  '</a> </td></tr>');	
				$('#body_table').append(row);			
			}
					
		},
		error:function(xhr){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
				 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			$('#divStoryInfo').attr('hidden',true);
			return;
		}
	})
					
		},
		error:function(xhr){
			let alert = $('<div class="alert alert-danger alert-dismissible fade show m-1" role="alert">' + xhr.responseText + 
				 '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
			$('#div_alert').append(alert);
			$('#divStoryInfo').attr('hidden',true);
			return;
		}
	});
	
};

function getPostImage(){
	
	$.ajax({
        type: "GET",
        url: "/api/media/one/" + idContent + "/" + type,
        contentType: "application/json",
        success: function(media) {
        	$('#story_image').empty();
        	let grouped={}
        	for(let m of media){
        	
  				if(grouped[m.idContent]){
  				grouped[m.idContent].push(m)
  				}      	else{
  				grouped[m.idContent]=[m]
  				}
        	}
        
			let t = 0;
			let j = 0;
            for (let m in grouped) {
            	for(let i in media){
                fetch('/api/media/files/' +grouped[m][t].path)
                    .then(resp => resp.blob())
                    .then(blob => {
                        const url = window.URL.createObjectURL(blob);         
                        addPost(url, j);
                        j = j + 1;
                    })
                    .catch(() => alert('oh no!'));
                    t = t + 1; 
                }
                
            }
         },
		error:function(){
			console.log('error getting post image');
		}
	});
	
};
