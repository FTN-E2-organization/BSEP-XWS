checkUserRole("ROLE_REGULAR");
var ownerUsername = getUsernameFromToken();
var numberOfFiles = 0;

$(document).ready(function () {
	
	$('#selectedHashtags').val('');
	$('#location').val('');
	$('input#hashtags').val('');
	
	$('#locations').empty();
	$('#bodyHashtags').empty();
	$('#bodyTagged').empty();
	
	/*Get locations*/
	$('#searchLocation').click(function(){
		
		$.ajax({
			type:"GET", 
			url: "/api/publishing/location",
			contentType: "application/json",
			success:function(locations){
				$('#locations').empty();
				for (let l of locations){
					addLocation(l);
				}
			},
			error:function(){
				console.log('error getting locations');
			}
		});
	});
	
	/*Click on location*/
	$("#tableLocations").delegate("tr.location", "click", function(){
        let location = $(this).text();
        $('#btn_close_location').click();
        $('#location').val(location);
    });
	
	/*Get hashtags*/
	$('#searchHashtags').click(function(){
		
		$.ajax({
			type:"GET", 
			url: "/api/publishing/hashtag",
			contentType: "application/json",
			success:function(hashtags){
				$('#bodyHashtags').empty();
				for (let h of hashtags){
					addHashtag(h);
				}
			},
			error:function(){
				console.log('error getting hashtags');
			}
		});
	});
	
	/*Click on hashtag*/
	$("#tableHashtags").delegate("tr.hashtag", "click", function(){
        let hashtag = $(this).text();
     
		let currentHashtags = $('#selectedHashtags').val();
        $('#selectedHashtags').val(currentHashtags+hashtag);
        
    });
    
 	/*Click on Add button*/
	$('#addHashtags').click(function(){
		$('input#hashtags').val($('#selectedHashtags').val());
		$('#btn_close_hashtags').click();
	});
	
	/*Get profiles for tagging*/
	$('#addTagged').click(function(){
		
		$.ajax({
			type:"GET", 
			url: "/api/auth/profile/allowedTagging",
			contentType: "application/json",
			success:function(profiles){
				$('#bodyTagged').empty();
				for (let p of profiles){
					addProfile(p);
				}
			},
			error:function(){
				console.log('error getting profiles');
			}
		});
	});
	
	/*Click on tagged profile*/
	$("#tableTagged").delegate("tr.tagged", "click", function(){
        let hashtag = $(this).text();
     
		let currentTagged = $('#selectedTagged').val();
        $('#selectedTagged').val(currentTagged + '@' + hashtag);
    });
    
    /*Click on Tag button*/
	$('#addTaggedProfiles').click(function(){
		$('input#tagged').val($('#selectedTagged').val());
		$('#btn_close_tagged').click();
	});
	
	$("input[type='file']").on("change", function(){  
	  	numberOfFiles = $(this).get(0).files.length;
	});
   
	
	/*Publish post*/
	$('form#publishPost').submit(function (event) {

		event.preventDefault();
	
		$('#div_alert').empty();

		let description = $('#description').val();
		let location = $('#location').val();
		let hashtags = $('#hashtags').val();
		let taggedUsernames = $('#tagged').val();
		
		hashtags = hashtags.substring(1,hashtags.length).split("#");
		taggedUsernames = taggedUsernames.substring(1,taggedUsernames.length).split("@");
		
		if(hashtags == "")
			hashtags = [];
		if(taggedUsernames == "")
			taggedUsernames = [];
		
		var postDTO = {
			"description": description,
			"location": location,
			"hashtags": hashtags,
			"taggedUsernames": taggedUsernames,
			"ownerUsername": ownerUsername
		};

		
		$.ajax({
			url: "/api/publishing/post",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(postDTO),
			success: function (postId) {
				let alert = $('<div class="alert alert-success alert-dismissible fade show m-1" role="alert">Successful publishing post!'
					+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + '</div >')
				$('#div_alert').append(alert);
				
				window.setTimeout(function(){
					var actionPath = "/api/aggregation/files-upload?idContent=" + postId + "&type=post";
					$('#form_image').attr('action', actionPath)
					$('#form_image').submit();
				},1000);
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


function addLocation(l){
	let row = $('<tr class="location"><td>'+ l +'</td></tr>');	
	$('#locations').append(row);
}

function addHashtag(h){
	let row = $('<tr class="hashtag"><td>'+ h +'</td></tr>');	
	$('#bodyHashtags').append(row);
}

function addProfile(p){
	let row = $('<tr class="tagged"><td>'+ p +'</td></tr>');	
	$('#bodyTagged').append(row);
}