<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Panel</title>
    </head>
    <body>
        <h1>This is the admin panel</h1>
        <hr>
        <div>
            <table>
                <p><h3>--Customers--</h3></p>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Action</th>
            </tr>
                    
            <c:forEach var="tempCustomer" items="${customers}">
                <c:url var="updateLink" value="/customer/showFormForUpdate">
                <c:param name="customerId" value="${tempCustomer.id}" />
            </c:url>
            <c:url var="deleteLink" value="/customer/delete">
                <c:param name="customerId" value="${tempCustomer.id}" />
            </c:url>
                        <tr>
                            <td>${tempCustomer.firstName}</td>
                            <td>${tempCustomer.lastName}</td>
                            <td>${tempCustomer.email}</td>
                            <td>
                                <a href="${updateLink}">Update</a>
                                |
                                <a href="${deleteLink}"
                                   onclick="if (!(confirm('Are you sure you want to delete this customer?')))return false;">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
        </div>
        <div>
            <table>
                <p><h3>--Members--</h3></p>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Action</th>
            </tr>
                    
            <c:forEach var="tempMember" items="${members}">
                <c:url var="updateLink" value="/customer/showFormForUpdate">
                <c:param name="memberId" value="${tempMember.id}" />
            </c:url>
            <c:url var="deleteLink" value="/customer/delete">
                <c:param name="customerId" value="${tempCustomer.id}" />
            </c:url>
                        <tr>
                            <td>${tempMember.firstName}</td>
                            <td>${tempMember.lastName}</td>
                            <td>${tempMember.email}</td>
                            <td>
                                <a href="${updateLink}">Update</a>
                                |
                                <a href="${deleteLink}"
                                   onclick="if (!(confirm('Are you sure you want to delete this customer?')))return false;">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
        </div>
            <hr>
        <div>
            <table>
                <p><h3>--Products--</h3></p>
            <tr>
                <th>Product name</th>
                <th>Price at shop</th>
                <th>Price we buy</th>
                <th>Action</th>
                
            </tr>
                    
            <c:forEach var="tempProduct" items="${products}">
                <c:url var="updateLink" value="/customer/showFormForUpdate">
                <c:param name="memberId" value="${tempMember.id}" />
            </c:url>
            <c:url var="deleteLink" value="/customer/delete">
                <c:param name="customerId" value="${tempCustomer.id}" />
            </c:url>
                        <tr>
                            <td>${tempProduct.name}</td>
                            <td>${tempProduct.priceShop}</td>
                            <td>${tempProduct.priceBuy}</td>
                            <td>
                                <a href="${updateLink}">Update</a>
                                |
                                <a href="${deleteLink}"
                                   onclick="if (!(confirm('Are you sure you want to delete this customer?')))return false;">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
        </div>
            <div>
                <h4><a href="${pageContext.request.contextPath}/shop">Back to Shop</a></h4>
            </div>
        <form:form action="${pageContext.request.contextPath}/logout" method="POST">
            
            <input type="submit" value="Logout" />
            
        </form:form>
    </body>
</html>
