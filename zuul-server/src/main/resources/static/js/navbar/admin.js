$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark fixed-top" style="background-color:white;">'
        + '<h3 style="margin-right:50px;color:black;"><a class="nav-link .text-dark" href="#" style="color:black;"><b>Ništagram</b></a></h3>'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav" >'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="contentRequests.html" style="color:black;">Content requests</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="registrationRequests.html" style="color:black;">Registration requests</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="javascript:logOut();" style="color:black;">Log out</a>'
        + '  </li>'
        + ' </ul>'
        + ' </div>'
        + ' </nav>'
    ));
});