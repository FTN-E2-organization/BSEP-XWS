$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark fixed-top" style="background-color:white;">'
        + '<h3 style="margin-right:50px;color:black;"><b>AGENT APP</b></h3>'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav" >'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="addProduct.html" style="color:black;">Create product</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="myProducts.html" style="color:black;">My products</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="javascript:logOut();" style="color:black;">Log out</a>'
        + '  </li>'
        + ' </ul>'
        + ' </div>'
        + ' </nav>'
    ));
});