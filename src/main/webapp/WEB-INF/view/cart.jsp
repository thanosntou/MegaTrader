<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <title>Shopping Cart</title>
    </head>
    <body>
        
        <%@include file="myNavbar.jsp" %>
        
        <div class="container-fluid" id="productsList">
            <table class="center"><br/>
                <h3>Shopping Cart</h3>
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="temp" items="${productsOfCart}">
                    <c:url var="updateLink" value="/management-panel/updateProduct">
                        <c:param name="productId" value="${temp.id}" />
                    </c:url>
                    <tr>
                        <td>${temp.name}</td>
                        <%--<td>${temp.quantity}</td>--%>
                        <%--<td>${temp.product.priceShop}</td>--%>
                        <td>
                        <a href="${updateLink}">Update</a>
                                                    |
                                                    <a href="${deleteLink}"
                                                       onclick="if (!(confirm('Are you sure you want to delete this product?')))return false;">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>     
        <div style="margin: 50px 20px;">
            <form:form action="${pageContext.request.contextPath}/sales/checkout" method="POST" >
                <input type="submit" class="btn btn-outline-primary" value="Checkout" />
            </form:form>
        </div>
        
        
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>                        
    </body>
</html>
