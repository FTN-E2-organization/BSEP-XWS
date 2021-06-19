$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark fixed-top" style="background-color:white;">'
        + '<h3 style="margin-right:50px;color:black;"><a class="nav-link .text-dark" href="unauthenticated_index.html" style="color:black;"><b>AGENT APP</b></a></h3>'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav" >'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="products.html" style="color:black;">Products</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="shoppingCart.html" style="color:black;">My shopping cart</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="finishedShoppingCart.html" style="color:black;">My order</a>'
        + '  </li>'
        + ' </ul>'
        + ' </div>'
        + ' </nav>'
    ));
});