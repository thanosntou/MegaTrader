<%-- 
    Document   : product-buy-form
    Created on : Apr 30, 2018, 7:38:55 PM
    Author     : athan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-buy-form.css">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
        <title>Product Details</title>
    </head>
    <body>
        
        <%@include file="myNavbar.jsp" %>
        
        <div class="main">             
            <br/>
            <div data-price="987" class="item">
                    <img src="${pageContext.request.contextPath}/images/${product.name}.jpeg" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/${product.name}.png'" alt="kati" class="img-item">
                    <div class="info">
                        <p class="description">${product.name}</p>
                        <p class="description">${product.description}</p>
                        <security:authorize access="hasRole('CUSTOMER')">
                            <form:form action="${pageContext.request.contextPath}/cart/add" method="POST" >
                                Qnt: <input type="number" name="quantity" /> kg<br/><br/>
                                <input type="hidden" name="productId"value="${product.id}" />
                                <input type="submit" value="Add to Cart" />
                            </form:form>
                        </security:authorize>
                        
                    </div>
            </div><br/>
            ${addSuccessfully}
            
        </div>
    
    </body>
</html>
