<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
    <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <title>Offer Form</title>
    </head>
    <body>
    
        <%@include file="myNavbar.jsp" %>
      
        <div class="row" style="margin:70px">
            <div class="col-md-6 col-sm-12 col-lg-6 col-md-offset-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading"><h3>Make an offer</h3>
                            </div>
                            <div class="panel-body">
                                <form:form action="${pageContext.request.contextPath}/offers/save-offer" modelAttribute="offer" class="form-horizontal" method="GET" >

                                   <div class="form-group">
					<div class="col-xs-15">
					    <div>
						<!-- Check for registration error -->
						<c:if test="${registrationError != null}">
                                                    <div class="alert alert-danger col-xs-offset-1 col-xs-10">${registrationError}</div>
                                                </c:if>
                                            </div>
					</div>
                                    </div>
                                    
                                    
                                    
                                    <!-- Product id -->
                                    Product *
                                    <div style="margin-bottom: 25px" class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-tasks"></i></span>
                                        <form:select  path="product" >
                                        <form:options items="${products}" itemValue="id" itemLabel="name" />
                                        </form:select> 
                                        
                                    </div>

                                    <!-- Quantity -->
                                    Quantity *
                                    <div style="margin-bottom: 25px" class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span> 
					<form:input type="number" path="quantity" placeholder="qnt" class="form-control" />
                                    </div>

                                    <!-- Register Button -->
                                    <div style="margin-top: 10px" class="form-group">						
                                            <div class="col-sm-6 controls">
                                                <button type="submit" class="btn btn-primary">Save</button>
                                            </div>
                                    </div>
                                </form:form>
                                    
                            </div>	
                    </div>
            </div>
        </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
  </body>
</html>
