$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark fixed-top" style="background-color:white;">'
        + '<h3 style="margin-right:50px;color:black;"><b>Nistagram</b></h3>'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav" >'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="registration.html" style="color:black;">Registration</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="myProfile.html" style="color:black;">My profile</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="updatePersonalData.html" style="color:black;">Edit profile</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="followRequests.html" style="color:black;">Follow requests</a>'
        + '  </li>'
        + ' </ul>'
        + ' </div>'
        + ' </nav>'
    ));
});