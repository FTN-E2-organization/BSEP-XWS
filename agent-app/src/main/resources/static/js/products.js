checkUserRole("ROLE_CUSTOMER");
var username = getUsernameFromToken();

$(document).ready(function () {
	
	$.ajax({
       	type: "GET",
        url: "/api/product/all",
        contentType: "application/json",
        success: function(products) {
        	let grouped={}
        	for(let p of products){
  				if(grouped[p.id]){
  				grouped[p.id].push(p)
  				}      	else{
  				grouped[p.id]=[p]
  				}
        	}
        
            for (let p in grouped) {
				
                fetch('/api/product/files/' +grouped[p][0].path)
                    .then(resp => resp.blob())
                    .then(blob => {
                        const url = window.URL.createObjectURL(blob);
                        addProduct(url, p);
                    })
                    .catch(() => console('error'));


            }
        },
        error: function() {
            console.log('error getting products');
        }
    });
	
});

function addProduct(path, id) {

    let image_div = $('<div style="margin-right: 10px; margin-bottom:10px;" class="column">' +
        '<a  id="' + id +'" onclick="func(this.id)";><img max-height="250px" width="300px"  src="' + path + '"></img></a>' +
        '</div>');
    $('div#product_images').append(image_div);
    
};

function func(id){
	window.location.href = "https://localhost:8091/html/product.html?id=" + id;
};