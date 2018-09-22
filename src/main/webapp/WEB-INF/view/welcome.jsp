<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bio-Union</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="stylesheet" href="css/main.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
</head>

<body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="60">

    <nav class="navbar navbar-inverse navbar-fixed-top"id="it">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span> 
                </button>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li class=" active"><button class="btn btn-outline-success and-all-other-classes" style="margin-top: 9px; margin-right: 100px; margin-left: -100px; background-color: #888; color: #303030;font-weight: 900;"><a href="#myPage" style="background-color:inherit;color: #303030">MegaTrader</a></button></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                <c:if test="${empty pageContext.request.userPrincipal}">
                    <li><a href="${pageContext.request.contextPath}/register/showRegistrationFormOption"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                    <li><a data-toggle="modal" data-target="#myModal"> Login</a></li>
                </c:if>
                <c:if test="${not empty pageContext.request.userPrincipal}">
                    <form:form action="${pageContext.request.contextPath}/logout" method="POST">
                        <input type="submit" class="btn btn-dark" value="Logout"/>
                    </form:form>
                </c:if>
                </ul>
            </div>
        </div>
    </nav>

<div class="jumbotron text-center">
    <%--<div class="container"><br><br><br><br><br>--%>
        <%--<h1 style="color: #090"><strong>BioUnion</strong></h1>--%>
        <%--<hr style="width: 5%; border: none; height: 1px; background-color: #3C3D41; "><br/>--%>
        <%--<form:form action="${pageContext.request.contextPath}/shop" method="GET" >--%>
            <%--<input type="submit" class="btn btn-dark" value="Enter the E-Shop" />--%>
        <%--</form:form>--%>
        <%----%>
    <%--</div>--%>
</div>

<footer class="container-fluid text-center">
  <a href="#myPage" title="To Top">
    <span class="glyphicon glyphicon-chevron-up"></span>
  </a>
  <p>Pwored by <a href="main.jsp">Tziganoi Productions</a></p>
</footer>

<!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="padding:35px 50px;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4><span class="glyphicon glyphicon-lock"></span> Login</h4>
        </div>
        <div class="modal-body" style="padding:40px 50px;">
          <form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="POST" class="form-horizontal" >
              <!-- Place for messages: error, alert etc ... -->
              <div class="form-group">
                  <div class="col-xs-15">
                      <div>		
                          <!-- Check for login error -->
                          <c:if test="${param.error != null}">
                              <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                Invalid username and password.
                              </div>
                          </c:if>
                          <c:if test="${param.logout != null}">
                              <div class="alert alert-success col-xs-offset-1 col-xs-10">
				You have been logged out.
                              </div>
                          </c:if>
                      </div>
                  </div>
		</div>
						<!-- User name -->
		<div style="margin-bottom: 25px" class="input-group">
			<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
							
			<input type="text" name="username" placeholder="username" class="form-control">
		</div>
                    <!-- Password -->
		<div style="margin-bottom: 25px" class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
							
				<input type="password" name="password" placeholder="password" class="form-control" >
			</div>
			<!-- Login/Submit Button -->
			<div style="margin-top: 10px" class="form-group">						
				<div class="col-sm-6 controls">
					<button type="submit" class="btn btn-success">Login</button>
				</div>
			</div>
	</form:form>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
          <p>Not a member? <a href="${pageContext.request.contextPath}/register/showRegistrationFormOption">Sign Up</a></p>
          <p>Forgot <a href="#">Password?</a></p>
        </div>
      </div>
      
    </div>
  </div> 

 

<script>
$(document).ready(function(){
  // Add smooth scrolling to all links in navbar + footer link
  $(".navbar a, footer a[href='#myPage']").on('click', function(event) {
    // Make sure this.hash has a value before overriding default behavior
    if (this.hash !== "") {
      // Prevent default anchor click behavior
      event.preventDefault();

      // Store hash
      var hash = this.hash;

      // Using jQuery's animate() method to add smooth page scroll
      // The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area
      $('html, body').animate({
        scrollTop: $(hash).offset().top
      }, 900, function(){
   
        // Add hash (#) to URL when done scrolling (default click behavior)
        window.location.hash = hash;
      });
    } // End if
  });
  
  $(window).scroll(function() {
    $(".slideanim").each(function(){
      var pos = $(this).offset().top;

      var winTop = $(window).scrollTop();
        if (pos < winTop + 600) {
          $(this).addClass("slide");
        }
    });
  });
});
</script>
<script>
// Get the modal
var modal = document.getElementById('login');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};
</script>

</body>
</html>
