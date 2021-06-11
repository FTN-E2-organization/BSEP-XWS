$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark fixed-top" style="background-color:white;">'
        + '<h3 style="margin-right:50px;color:black;"><a class="nav-link .text-dark" href="#" style="color:black;"><b>Ništagram</b></a></h3>'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav" >'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="registration.html" style="color:black;">Sign up</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="login.html" style="color:black;">Log in</a>'
        + '  </li>'
        + ' </ul>'
        + ' </div>'
        + ' </nav>'
    ));
});