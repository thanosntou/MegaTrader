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
                    <li class=" active"><button class="btn btn-outline-success and-all-other-classes" style="margin-top: 9px; margin-right: 100px; margin-left: -100px; background-color: #888; color: #303030;font-weight: 900;"><a href="#myPage" style="background-color:inherit;color: #303030">BioUnion</a></button></li>
                    <li><a href="#about">ABOUT</a></li>
                    <li><a href="#products">PRODUCTS</a></li>
                    <li><a href="#portfolio">NEWS</a></li>
                    <li><a href="#pricing">PRICING</a></li>
                    <li><a href="#contact">CONTACT</a></li> 
                    <li><a href="#contact"></a></li>
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
    <div class="container"><br><br><br><br><br>
        <h1 style="color: #090"><strong>BioUnion</strong></h1>
        <hr style="width: 5%; border: none; height: 1px; background-color: #3C3D41; "><br/>
        <form:form action="${pageContext.request.contextPath}/shop" method="GET" >
            <input type="submit" class="btn btn-dark" value="Enter the E-Shop" />
        </form:form>
        
    </div>
</div>

<!-- Container (About Section) -->
    <div id="about" class="container-fluid">
      <div class="row">
        <div class="col-sm-9">
          <h2> About Company</h2><br>
          <h4>All procedures followed in cultivation, sorting, packing and distribution have
              a single goal: the production of standardized, high-quality, safe products to 
              meet the high standards of the company as well as to comply with national and 
              European regulations.</h4>
          <h4>At the BioUnion, we’ve been championing issues that matter to our customers 
              for many years. We also take great care in understanding where our products
              come from, what ingredients go into them and how we can make our business 
              operate more efficiently. </h4><br>
              <h4>Our carefully selected ‘foundation’ farms provide us with quality meat 
                  and a transparent supply chain. We have also identified a number of 
                  ‘concept’ farms within the Farming Groups, where we focus on areas such 
                  as welfare, farming efficiency and the environment. This will enable us 
                  to provide research and knowledge to farmers to help improve on issues 
                  such as disease using new expertise or technology.</h4>
          <br><button class="btn btn-default btn-lg">Get in Touch</button>
        </div>
        <div class="col-sm-3">
          <img src="${pageContext.request.contextPath}/resources/images/biounion.png" alt="jacket" >
        </div>
      </div>
    </div>

    <div id="products" class="container-fluid bg-grey">
        <div class="row">
            <div class="col-sm-3">
                <img src="${pageContext.request.contextPath}/resources/images/bio.jpeg" alt="jacket" >
            </div>
            <div class="col-sm-9">
                <h2>Our Products</h2><br>
                <h4>While Quality as a term is usually connected to characteristics that are acceptable
                    to consumers, the true meaning of Quality goes well beyond formal stereotypes. 
                    Industrialized countries are becoming more demanding for food not only to be economical
                    but, more importantly, healthy, tasty, safe for consumption and grown showing respect
                    to the environment. </h4><br/>
                <h4><strong>MISSION:</strong> At Co-op, we’ve always supported the UK farming industry 
                    and promoted British food. Recently, you, our customers and members,have shown a big
                    interest in where your food comes from, and we know how important animal welfare,
                    the environment and fairness in the supply chain are to you.</h4><br>
                <h4><strong>VISION:</strong> We’ve taken this on board, and have created long-term
                    relationships with our farmers and suppliers so that we can give you complete
                    transparency in our supply chain, which is built on honesty, fairness and trust.
                    We have established Farming Groups, which, combined with a clear sourcing strategy,
                    gives us a great future. </h4>
            </div>
        </div>
    </div>

<!-- Container (Services Section) -->
<div id="services" class="container-fluid text-center">
  <h2>SERVICES</h2>
  <h4>What we offer</h4>
  <br>
  <div class="row slideanim">
    <div class="col-sm-4">
      <span class="glyphicon glyphicon-off logo-small"></span>
      <h4>POWER</h4>
      <p>Lorem ipsum dolor sit amet..</p>
    </div>
    <div class="col-sm-4">
      <span class="glyphicon glyphicon-heart logo-small"></span>
      <h4>LOVE</h4>
      <p>Lorem ipsum dolor sit amet..</p>
    </div>
    <div class="col-sm-4">
      <span class="glyphicon glyphicon-lock logo-small"></span>
      <h4>JOB DONE</h4>
      <p>Lorem ipsum dolor sit amet..</p>
    </div>
  </div>
  <br><br>
  <div class="row slideanim">
    <div class="col-sm-4">
      <span class="glyphicon glyphicon-leaf logo-small"></span>
      <h4>GREEN</h4>
      <p>Lorem ipsum dolor sit amet..</p>
    </div>
    <div class="col-sm-4">
      <span class="glyphicon glyphicon-certificate logo-small"></span>
      <h4>CERTIFIED</h4>
      <p>Lorem ipsum dolor sit amet..</p>
    </div>
    <div class="col-sm-4">
      <span class="glyphicon glyphicon-wrench logo-small"></span>
      <h4 style="color:#303030;">HARD WORK</h4>
      <p>Lorem ipsum dolor sit amet..</p>
    </div>
  </div>
</div>

<!-- Container (Pricing Section) -->
<div id="pricing" class="container-fluid">
  <div class="text-center">
    <h2>Pricing</h2>
    <h4>Choose a payment plan that works for you</h4>
  </div>
  <div class="row slideanim">
    <div class="col-sm-4 col-xs-12">
      <div class="panel panel-default text-center">
        <div class="panel-heading">
          <h1>Basic</h1>
        </div>
        <div class="panel-body">
          <p><strong>20</strong> Lorem</p>
          <p><strong>15</strong> Ipsum</p>
          <p><strong>5</strong> Dolor</p>
          <p><strong>2</strong> Sit</p>
          <p><strong>Endless</strong> Amet</p>
        </div>
        <div class="panel-footer">
          <h3>$19</h3>
          <h4>per month</h4>
          <button class="btn btn-lg">Sign Up</button>
        </div>
      </div>      
    </div>     
    <div class="col-sm-4 col-xs-12">
      <div class="panel panel-default text-center">
        <div class="panel-heading">
          <h1>Pro</h1>
        </div>
        <div class="panel-body">
          <p><strong>50</strong> Lorem</p>
          <p><strong>25</strong> Ipsum</p>
          <p><strong>10</strong> Dolor</p>
          <p><strong>5</strong> Sit</p>
          <p><strong>Endless</strong> Amet</p>
        </div>
        <div class="panel-footer">
          <h3>$29</h3>
          <h4>per month</h4>
          <button class="btn btn-lg">Sign Up</button>
        </div>
      </div>      
    </div>       
    <div class="col-sm-4 col-xs-12">
      <div class="panel panel-default text-center">
        <div class="panel-heading">
          <h1>Premium</h1>
        </div>
        <div class="panel-body">
          <p><strong>100</strong> Lorem</p>
          <p><strong>50</strong> Ipsum</p>
          <p><strong>25</strong> Dolor</p>
          <p><strong>10</strong> Sit</p>
          <p><strong>Endless</strong> Amet</p>
        </div>
        <div class="panel-footer">
          <h3>$49</h3>
          <h4>per month</h4>
          <button class="btn btn-lg">Sign Up</button>
        </div>
      </div>      
    </div>    
  </div>
</div>

<!-- Container (Contact Section) -->
<div id="contact" class="container-fluid " >
  <h2 class="text-center">CONTACT</h2>
  <div class="row">
    <div class="col-sm-5">
      <p>Contact us and we'll get back to you within 24 hours.</p>
      <p><span class="glyphicon glyphicon-map-marker"></span> Chicago, US</p>
      <p><span class="glyphicon glyphicon-phone"></span> +00 1515151515</p>
      <p><span class="glyphicon glyphicon-envelope"></span> myemail@something.com</p>
    </div>
    <div class="col-sm-7 slideanim">
      <div class="row">
        <div class="col-sm-6 form-group">
          <input class="form-control" id="name" name="name" placeholder="Name" type="text" required>
        </div>
        <div class="col-sm-6 form-group">
          <input class="form-control" id="email" name="email" placeholder="Email" type="email" required>
        </div>
      </div>
      <textarea class="form-control" id="comments" name="comments" placeholder="Comment" rows="5"></textarea><br>
      <div class="row">
        <div class="col-sm-12 form-group">
          <button class="btn btn-default pull-right" type="submit">Send</button>
        </div>
      </div>
    </div>
  </div>
</div>

<footer class="container-fluid text-center">
  <a href="#myPage" title="To Top">
    <span class="glyphicon glyphicon-chevron-up"></span>
  </a>
  <p>Made By <a href="main.jsp">Athan Ntouzidis</a></p>
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
