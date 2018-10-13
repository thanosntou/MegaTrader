<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
        <title>Eshop</title>
    </head>
    
    <body>
        
        <%@include file="myNavbar.jsp" %>
        
        <!-----------------------Tabs---------------------------------------->
        <div class="main">
            <div style="color:red">
                <h3>${messageFromCart}</h3>
            </div>
        <div style="margin-left: 20px;">
        <div>
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                  <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">All</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Vegetables</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" id="messages-tab" data-toggle="tab" href="#messages" role="tab" aria-controls="messages" aria-selected="false">Fruits</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" id="settings-tab" data-toggle="tab" href="#settings" role="tab" aria-controls="settings" aria-selected="false">Mushrooms</a>
                </li>
            </ul>
        </div>
        

      <!-- Tab panes -->
        <div class="tab-content" >
            <div class="tab-pane active" id="home" role="tabpanel" aria-labelledby="home-tab">
                <br/><h3>All products</h3>
                <div class="items">
                    <c:forEach var="tempProduct" items="${products}">
                        <c:url var="buyLink" value="/shop/productDetails">
                            <c:param name="productId" value="${tempProduct.id}" />
                        </c:url>
                        <div data-price="987" class="item">
                            <a href="${buyLink}" >
                            <img src="${pageContext.request.contextPath}/images/${tempProduct.name}.jpeg" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/${tempProduct.name}.png'" alt="kati" class="img-item">
                                <div class="info">
                                    <p class="description" style="text-align: center">${tempProduct.name}<br/></p>
                                </div>
                            </a>
                        </div>
                            
                    </c:forEach>
                </div>
            </div>
            <div class="tab-pane" id="profile" role="tabpanel" aria-labelledby="profile-tab">
              <br/><h3>Vegetables</h3>
                <div class="items">
                    <c:forEach var="tempProduct" items="${vegetables}">
                        <c:url var="buyLink" value="/shop/productDetails">
                            <c:param name="productId" value="${tempProduct.id}" />
                        </c:url>
                        <div data-price="987" class="item">
                            <a href="${buyLink}">
                            <img src="images/${tempProduct.name}.jpeg" onerror="this.onerror=null; this.src='images/${tempProduct.name}.png'" alt="kati" class="img-item">
                            <div class="info">
                                <p class="description">${tempProduct.name}</p>
                            </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="tab-pane" id="messages" role="tabpanel" aria-labelledby="messages-tab">
                <br/><h3>Fruits</h3>
                <div class="items">
                    <c:forEach var="tempProduct" items="${fruits}">
                        <c:url var="buyLink" value="/shop/productDetails">
                            <c:param name="productId" value="${tempProduct.id}" />
                        </c:url>
                        <div data-price="987" class="item">
                            <a href="${buyLink}" >
                            <img src="${pageContext.request.contextPath}/images/${tempProduct.name}.jpeg" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/${tempProduct.name}.png'" alt="kati" class="img-item">
                            <div class="info">
                                <p class="description">${tempProduct.name}</p>
                            </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="tab-pane" id="settings" role="tabpanel" aria-labelledby="settings-tab">
              <br/><h3>Mushrooms</h3>
                    <div class="items">
                    <c:forEach var="tempProduct" items="${mushrooms}">
                        <c:url var="buyLink" value="/shop/productDetails">
                            <c:param name="productId" value="${tempProduct.id}" />
                        </c:url>
                        <div data-price="987" class="item">
                            <a href="${buyLink}" >
                            <img src="${pageContext.request.contextPath}/images/${tempProduct.name}.jpeg" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/${tempProduct.name}.png'" alt="kati" class="img-item">
                            <div class="info">
                                <p class="description">${tempProduct.name}</p>
                            </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
      </div>
        </div>
        
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
						<!-- user name -->
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
    
  
        
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>      
</html>
