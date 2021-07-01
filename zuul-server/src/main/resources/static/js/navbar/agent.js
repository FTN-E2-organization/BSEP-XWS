$(document).ready(function () {
    $('body').prepend($(
        '<nav class="navbar navbar-expand-lg navbar-dark fixed-top" style="background-color:white;">'
        + '<h3 style="margin-right:50px;color:black;"><a class="nav-link .text-dark" href="index.html" style="color:black;"><b>Agent Ništagram</b></a></h3>'
        + '<div class="collapse navbar-collapse" id="navbarNav">'
        + ' <ul class="navbar-nav" >'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="collection.html" style="color:black;">Collections</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="notifications.html" style="color:black;">Notifications</a>'
        + '  </li>'       
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="myProfile.html" style="color:black;">My profile</a>'
        + '  </li>'
        + '	 <li class="nav-item dropdown">'
        + '	 <a class="nav-link .text-dark" style="color:black;" dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Reactions</a>'
        + '	 <div class="dropdown-menu" aria-labelledby="navbarDropdown">'
        + '	 <a class="dropdown-item" href="likedPosts.html">Likes</a>'
        + '	 <a class="dropdown-item" href="dislikedPosts.html">Dislikes</a></div></li>'
        + '	 <li class="nav-item dropdown">'
        + '	 <a class="nav-link .text-dark" style="color:black;" dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Publish</a>'
        + '	 <div class="dropdown-menu" aria-labelledby="navbarDropdown">'
        + '	 <a class="dropdown-item" href="publishPost.html">Post</a>'
        + '	 <a class="dropdown-item" href="publishStory.html">Story</a></div></li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="followRequests.html" style="color:black;">Follow requests</a>'
        + '  </li>'
        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="createAd.html" style="color:black;">Create ad</a>'
        + '  </li>'

        + '	 <li class="nav-item dropdown">'
        + '	 <a class="nav-link .text-dark" style="color:black;" dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Create campaign</a>'
        + '	 <div class="dropdown-menu" aria-labelledby="navbarDropdown">'
        + '	 <a class="dropdown-item" href="createCampaign.html">Once time</a>'
        + '	 <a class="dropdown-item" href="createMultipleCampaign.html">Multiple</a></div></li>'

        + '  <li class="nav-item">'
        + '  <a class="nav-link .text-dark" href="javascript:logOut();" style="color:black;">Log out</a>'
        + '  </li>'
        + ' </ul>'
        + ' </div>'
        + ' </nav>'
    ));
});