<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
    <head>
      <title>Product Form</title>
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!--      <link rel="stylesheet" href="css/register-form-customer.css">    -->
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
      <style>
          .error{
              color: red;
          }
      </style>
    </head> 

    <body>
        
        <%@include file="myNavbar.jsp" %>

        <div class="row" style="margin:70px">
            <div class="col-md-6 col-sm-12 col-lg-6 col-md-offset-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading"><h3>Add a new product</h3></div>
                        <div class="panel-body">
                            <form:form action="${pageContext.request.contextPath}/management-panel/save-product" modelAttribute="product" enctype="multipart/form-data" class="form-horizontal" method="POST" >
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

                                <form:hidden path="id" />
                                <form:hidden path="quantity" />

                                <!-- User name -->
                                Category name *
                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-tasks"></i></span>
                                    <form:select path="category">
                                        <form:options items="${theCategories}" />
                                    </form:select>
                                </div>

                                <!-- Password -->
                                Product name *
                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                                    <form:input type="text" path="name" placeholder="product name" class="form-control" />
                                </div>

                                <!-- Email -->
                                Buy price *
                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-usd "></i></span>
                                    <form:input type="number" path="priceBuy" placeholder="price from member" class="form-control" />
                                </div>

                                <!-- first name -->
                                Shop price *
                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-usd "></i></span>
                                    <form:input type="number" path="priceShop" placeholder="price at shop" class="form-control" />
                                </div>

                                <!-- first name -->
                                Product image *
                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-picture "></i></span>
                                    <input type="file" accept=".jpeg,.png" name="myfile" class="form-control" />
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
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>

