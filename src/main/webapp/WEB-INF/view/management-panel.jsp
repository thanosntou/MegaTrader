<!------ Include the above in your HEAD tag ---------->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/management-panel.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
        <title>Management Panel</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">


            <!--------------------------- Side Menu----------------------------- -->
                <div class="col-sm-2" id="side-menu">
                    <aside class="sm-side">
                        <div class="user-head">
                            <div class="user-name"><br/>
                                <h4><a href="#">${user.lastName} ${user.firstName} </a></h4>
                                <span>Email:<a href="#"> ${user.email}</a></span><br/>
                                <span>Username:<a href="#"> ${user.username}</a></span><br/><br/>
                            </div>
                            <div>
                              <h5><a href="${pageContext.request.contextPath}/shop">Back to Shop</a></h5>
                            </div><br/>
                            <form:form action="${pageContext.request.contextPath}/logout" method="POST">
                                <input type="submit" class="btn btn-outline-dark" value="Logout"/>
                            </form:form>
                            <br/><h5><a href="${pageContext.request.contextPath}/management-panel/new-product">New Product</a></h5><br/>
                            <h5><a href="${newCustomerLink}">New Customer</a></h5>
                            <br/>
                            <h5>Total sales:<br/><span style="color: red">${sumSales} <i class="fas fa-euro-sign"></i></span></h5>
                            <br/>
                            <h5>Admin Sales profit:<br/><span style="color: red">${adminProfit} <i class="fas fa-euro-sign"></i></span></h5>
                            <br/>
                            <h5>Members Sales profit:<br/><span style="color: red">${membersProfit} <i class="fas fa-euro-sign"></i></span></h5>
                            <br/>
                            <security:authorize access="hasRole('MEMBER')">
                                <h5>Your Sales profit:<br/><span style="color: red">${personalProfit} <i class="fas fa-euro-sign"></i></span></h5>
                                <br/>
                            </security:authorize>
                        </div>  
                    </aside>
                </div>
                            
                <!--------------------------- Main Menu----------------------------- -->
                <div class="col-sm-10">
                    <div class="inbox-head">
                          <h3>MANAGEMENT PANEL</h3>
                          <form action="#" class="pull-right position">
                              <div class="input-append">
                                  <input type="text" class="sr-input" placeholder="Search Mail">
                                  <button class="btn" type="button">Search<i class="fa fa-search"></i></button>
                              </div>
                          </form>
                    </div>


                    <hr style="border: none; color: #333; height: 2px; background-color: #333;">
                              
                    <!-- Page components -->

                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/management-panel" role="button">Products</a>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/management-panel/customers?sortBy=username&orderBy=asc" role="button">Customers</a>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/management-panel/members?sortBy=username&orderBy=asc" role="button">Members</a>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/management-panel/offers?sortBy=product.id&orderBy=asc" role="button">Offers</a>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/management-panel/sales?sortBy=dateofc&orderBy=desc" role="button">Sales</a>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/management-panel/payments?sortBy=date&orderBy=desc" role="button">Payments</a>



                <div id="businessContent">


                    <c:if test="${businessEntity == 'products'}">

                        <!-- Product Tab -->
                        <div id="pills-products">
                            <!-- Ascendant order Links --->
                            <c:url var="sortByIdLink" value="/management-panel/products">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortByCategoryLink" value="/management-panel/products">
                                <c:param name="sortBy" value="category" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortByShopPriceLink" value="/management-panel/products">
                                <c:param name="sortBy" value="priceShop" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortByBuyPriceLink" value="/management-panel/products">
                                <c:param name="sortBy" value="priceBuy" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortByQtyLink" value="/management-panel/products">
                                <c:param name="sortBy" value="quantity" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <!-- Descedant order Links --->
                            <c:url var="sortDescByIdLink" value="/management-panel/products">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortDescByNameLink" value="/management-panel/products">
                                <c:param name="sortBy" value="name" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortDescByCategoryLink" value="/management-panel/products">
                                <c:param name="sortBy" value="category" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>

                            <c:url var="sortDescByShopPriceLink" value="/management-panel/products">
                                <c:param name="sortBy" value="priceShop" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortDescByBuyPriceLink" value="/management-panel/products">
                                <c:param name="sortBy" value="priceBuy" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortDescByQtyLink" value="/management-panel/products">
                                <c:param name="sortBy" value="quantity" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <table class="table table-hover table-sm"><br/>

                                <thead class="thead bg-info">
                                    <tr>
                                        <th scope="col">Id<a href="${sortByIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Product Name<a href="${pageContext.request.contextPath}/management-panel"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByNameLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Category<a href="${sortByCategoryLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByCategoryLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Shop Price<a href="${sortByShopPriceLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByShopPriceLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Buy Price<a href="${sortByBuyPriceLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByBuyPriceLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Avail. Qty<a href="${sortByQtyLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByQtyLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <security:authorize access="hasRole('ADMIN')">
                                            <th scope="col">Action</th>
                                        </security:authorize>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="temp" items="${businessList}">
                                        <c:url var="updateLink" value="/management-panel/updateProduct">
                                            <c:param name="productId" value="${temp.id}" />
                                        </c:url>
                                        <c:url var="deleteLink" value="/management-panel/deleteProduct">
                                            <c:param name="productId" value="${temp.id}" />
                                        </c:url>
                                        <tr>
                                            <th scope="row">${temp.id}</th>
                                            <td>${temp.name}</td>
                                            <td>${temp.category}</td>
                                            <td>${temp.priceShop}</td>
                                            <td>${temp.priceBuy}</td>
                                            <td>${temp.quantity}</td>
                                            <security:authorize access="hasRole('ADMIN')">
                                                <td>
                                                    <a href="${updateLink}">Update</a>
                                                        |
                                                    <a href="${deleteLink}"
                                                        onclick="if (!(confirm('Are you sure you want to delete this product?')))return false;">Delete</a>
                                                </td>
                                            </security:authorize>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <!----------------------------------------- Customer Tab ---------------------------------------------->
                    <c:if test="${businessEntity == 'customers'}">
                    <div id="pills-customers">
                        <!-- Ascendant order Links --->
                        <c:url var="sortCustomersByIdLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="id" />
                            <c:param name="orderBy" value="asc" />
                        </c:url>
                        <c:url var="sortCustomersByUsernameLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="username" />
                            <c:param name="orderBy" value="asc" />
                        </c:url>
                        <c:url var="sortCustomersByFirstNameLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="firstName" />
                            <c:param name="orderBy" value="asc" />
                        </c:url>
                        <c:url var="sortCustomersByLastNameLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="lastName" />
                            <c:param name="orderBy" value="asc" />
                        </c:url>
                        <c:url var="sortCustomersByEmailLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="email" />
                            <c:param name="orderBy" value="asc" />
                        </c:url>
                        <!-- Descedant order Links --->
                        <c:url var="sortCustomersDescByIdLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="id" />
                            <c:param name="orderBy" value="desc" />
                        </c:url>
                        <c:url var="sortCustomersDescByUsernameLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="username" />
                            <c:param name="orderBy" value="desc" />
                        </c:url>
                        <c:url var="sortCustomersDescByFirstNameLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="firstName" />
                            <c:param name="orderBy" value="desc" />
                        </c:url>

                        <c:url var="sortCustomersDescByLastNameLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="lastName" />
                            <c:param name="orderBy" value="desc" />
                        </c:url>
                        <c:url var="sortCustomersDescByEmailLink" value="/management-panel/customers">
                            <c:param name="sortBy" value="email" />
                            <c:param name="orderBy" value="desc" />
                        </c:url>
                        <table class="table table-hover table-sm"><br/>
                            <c:url var="newCustomerLink" value="/register/showRegistrationForm">
                                <c:param name="selector" value="customer" />
                            </c:url>
                        
                                <thead class="thead bg-info">
                                    <tr>
                                        <th scope="col">Id<a href="${sortCustomersByIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortCustomersDescByIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Username<a href="${sortCustomersByUsernameLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortCustomersDescByUsernameLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">First Name<a href="${sortCustomersByFirstNameLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortCustomersDescByFirstNameLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Last Name<a href="${sortCustomersByLastNameLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortCustomersDescByLastNameLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Email<a href="${sortCustomersByEmailLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortCustomersDescByEmailLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <security:authorize access="hasRole('ADMIN')">
                                            <th scope="col">Action</th>
                                        </security:authorize>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="temp" items="${businessList}">
                                        <c:url var="disableLink" value="/admin/disable">
                                            <c:param name="customerId" value="${temp.id}" />
                                        </c:url>
                                        <tr>
                                            <th scope="row">${temp.id}</th>
                                            <td>${temp.username}</td>
                                            <td>${temp.firstName}</td>
                                            <td>${temp.lastName}</td>
                                            <td>${temp.email}</td>
                                            <security:authorize access="hasRole('ADMIN')">
                                                <td><a href="${disableLink}">Disable</a></td>
                                            </security:authorize>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${businessEntity == 'members'}">
                        <!-- Member Tab -->
                        <div id="pills-members">
                            <!-- Ascendant order Links --->
                            <c:url var="sortMembersByIdLink" value="/management-panel/members">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortMembersByUsernameLink" value="/management-panel/members">
                                <c:param name="sortBy" value="username" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortMembersByFirstNameLink" value="/management-panel/members">
                                <c:param name="sortBy" value="firstName" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortMembersByLastNameLink" value="/management-panel/members">
                                <c:param name="sortBy" value="lastName" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortMembersByEmailLink" value="/management-panel/members">
                                <c:param name="sortBy" value="email" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>

                            <!-- Descedant order Links --->
                            <c:url var="sortMembersDescByIdLink" value="/management-panel/members">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortMembersDescByUsernameLink" value="/management-panel/members">
                                <c:param name="sortBy" value="username" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortMembersDescByFirstNameLink" value="/management-panel/members">
                                <c:param name="sortBy" value="firstName" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>

                            <c:url var="sortMembersDescByLastNameLink" value="/management-panel/members">
                                <c:param name="sortBy" value="lastName" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortMembersDescByEmailLink" value="/management-panel/members">
                                <c:param name="sortBy" value="email" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>

                                <table class="table table-hover table-sm"><br/>
                                    <c:url var="newMemberLink" value="/register/showRegistrationForm">
                                    <c:param name="selector" value="member" />
                                </c:url>
                                <h5><a href="${newMemberLink}">New Member</a></h5><br/>
                                <thead class="thead bg-info">
                                    <tr>
                                        <th scope="col">Id<a href="${sortMembersByIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortMembersDescByIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Username<a href="${sortMembersByUsernameLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortMembersDescByUsernameLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">First Name<a href="${sortMembersByFirstNameLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortMembersDescByFirstNameLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Last Name<a href="${sortMembersByLastNameLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortMembersDescByLastNameLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Email<a href="${sortMembersByEmailLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortMembersDescByEmailLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">SSN</th>
                                        <th scope="col" class="text-center">Verified</th>
                                        <security:authorize access="hasRole('ADMIN')">
                                        <th scope="col">Action</th>
                                        </security:authorize>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="temp" items="${businessList}">
                                        <c:url var="disableLink" value="/management-panel/updateProduct">
                                            <c:param name="productId" value="${temp.id}" />
                                        </c:url>
                                        <tr>
                                            <th scope="row">${temp.id}</th>
                                            <td>${temp.username}</td>
                                            <td>${temp.firstName}</td>
                                            <td>${temp.lastName}</td>
                                            <td>${temp.email}</td>
                                            <td>${temp.ssn}</td>
                                            <td class="text-center">
                                                <c:if test="${temp.verified == 1}">
                                                    <i class="far fa-check-circle"></i>
                                                </c:if>
                                                <c:if test="${temp.verified == 0}">
                                                    <i class="fas fa-minus"></i>
                                                </c:if>
                                            </td>
                                            <security:authorize access="hasRole('ADMIN')">
                                                <td>
                                                    <a href="${disableLink}">Disable</a>
                                                </td>
                                            </security:authorize>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${businessEntity == 'sales'}">
                        <!-- Sales Tab -->
                        <div id="pills-sales">
                            <!-- Ascendant order Links --->
                            <c:url var="sortSalesByIdLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortSalesByCustomerIdLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="customer.id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortSalesByProductIdLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="product.id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortSalesByPriceLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="price" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortSalesByQtyLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="quantity" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortSalesByDateLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="dateofc" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <!-- Descedant order Links --->
                            <c:url var="sortSalesDescByIdLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortSalesDescByProductIdLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="product.id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortSalesDescByCustomerIdLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="customer.id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortSalesDescByPriceLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="price" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortSalesDescByQtyLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="quantity" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortSalesDescByDateLink" value="/management-panel/sales">
                                <c:param name="sortBy" value="dateofc" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>

                            <c:url var="getSalesByDateOneMonthBefore" value="/management-panel/sales">
                                <c:param name="salesBefore" value="2" />
                            </c:url>
                            <c:url var="getSalesByDateThisMonth" value="/management-panel/sales">
                                <c:param name="salesBefore" value="1" />
                            </c:url>

                            <a href="${getSalesByDateThisMonth}"><i class="fas fa-chevron-left"></i></a>${currentMonth}${paginationMonth}<a href="${getSalesByDateOneMonthBefore}"><i class="fas fa-chevron-right"></i></a>

                            <table class="table table-hover table-sm"><br/>
                                <thead class="thead bg-info">
                                    <tr>
                                        <th scope="col">Id<a href="${sortSalesByIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortSalesDescByIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Product<a href="${sortSalesByProductIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortSalesDescByProductIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Customer<a href="${sortSalesByCustomerIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortSalesDescByCustomerIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Qnt<a href="${sortSalesByQtyLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortSalesDescByQtyLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Price<a href="${sortSalesByPriceLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortSalesDescByPriceLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Date<a href="${sortSalesByDateLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortSalesDescByDateLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col" class="text-center">Paid</th>
                                        <th scope="col" class="text-center">Completed</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="tempSale" items="${businessList}">
                                        <tr>
                                            <th scope="row">${tempSale.id}</th>
                                            <td>${tempSale.product.name}</td>
                                            <td>${tempSale.customer.username}</td>
                                            <td>${tempSale.quantity}</td>
                                            <td>${tempSale.price}</td>
                                            <td>${tempSale.dateofc}</td>
                                            <td class="text-center">
                                                <c:if test="${tempSale.paid == 1}">
                                                    <i class="far fa-check-circle"></i>
                                                </c:if>
                                                <c:if test="${tempSale.paid == 0}">
                                                    <i class="fas fa-minus"></i>
                                                </c:if>
                                            </td>
                                            <td class="text-center">
                                                <c:if test="${tempSale.completed == 1}">
                                                    <i class="far fa-check-circle"></i>
                                                </c:if>
                                                <c:if test="${tempSale.completed == 0}">
                                                    <i class="fas fa-minus"></i>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${businessEntity == 'offers'}">
                        <!-- Offers Tab -->
                        <div id="pills-offers">
                            <!-- Ascendant order Links --->
                            <c:url var="sortOffersByIdLink" value="/management-panel/offers">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortOffersByMemberIdLink" value="/management-panel/offers">
                                <c:param name="sortBy" value="member.id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortOffersByProductIdLink" value="/management-panel/offers">
                                <c:param name="sortBy" value="product.id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortOffersByQtyLink" value="/management-panel/offers">
                                <c:param name="sortBy" value="quantity" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortOffersByActiveLink" value="/management-panel/offers">
                                <c:param name="sortBy" value="active" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <!-- Descedant order Links --->
                            <c:url var="sortOffersDescByIdLink" value="/management-panel/offers">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortOffersDescByMemberIdLink" value="/management-panel/offers">
                                <c:param name="sortBy" value="member.id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortOffersDescByProductIdLink" value="/management-panel/offers">
                                <c:param name="sortBy" value="product.id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>

                            <c:url var="sortOffersDescByQtyLink" value="/management-panel/offers">
                                <c:param name="sorBy" value="quantity" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortOffersDescByActiveLink" value="/management-panel/offers">
                                <c:param name="sortBy" value="active" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>

                            <table class="table table-hover table-sm"><br/>
                                <security:authorize access="hasRole('MEMBER')">
                                    <h5><a href="${pageContext.request.contextPath}/offers/new-offer">New Offer</a></h5><br/>
                                </security:authorize>
                                <thead class="thead bg-info">
                                    <tr>
                                        <th scope="col">Id<a href="${sortOffersByIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortOffersDescByIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Product<a href="${sortOffersByProductIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortOffersDescByProductIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Member<a href="${sortOffersByMemberIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortOffersDescByMemberIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Qnt<a href="${sortOffersByQtyLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortOffersDescByQtyLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col" class="text-center">Active<a href="${sortOffersByActiveLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortOffersDescByActiveLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <security:authorize access="hasRole('ADMIN')">
                                        <th scope="col">Action</th>
                                        </security:authorize>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="tempOffer" items="${businessList}">
                                        <c:url var="activateLink" value="/admin/activate">
                                            <c:param name="offerId" value="${tempOffer.id}" />
                                        </c:url>
                                        <c:url var="deactivateLink" value="/admin/deactivate">
                                            <c:param name="offerId" value="${tempOffer.id}" />
                                        </c:url>
                                        <tr>
                                            <th scope="row">${tempOffer.id}</th>
                                            <td>${tempOffer.product.name}</td>
                                            <td>${tempOffer.member.username}</td>
                                            <td>${tempOffer.quantity}</td>
                                            <td class="text-center">
                                                <c:if test="${tempOffer.active == 1}">
                                                    <i class="far fa-check-circle"></i>
                                                </c:if>
                                                <c:if test="${tempOffer.active == 0}">
                                                    <i class="fas fa-minus"></i>
                                                </c:if>
                                            </td>
                                            <security:authorize access="hasRole('ADMIN')">
                                                <td><a href="${activateLink}">Activate</a>
                                                        |
                                                    <a href="${deactivateLink}">Deactivate</a>
        <!--                                                onclick="if (!(confirm('Are you sure you want to deactivate this product?')))return false;">Deactivate</a>-->
                                                </td>
                                            </security:authorize>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${businessEntity == 'payments'}">
                        <!-- Payments Tab -->
                        <div id="pills-payments">
                            <!-- Ascendant order Links --->
                            <c:url var="sortPaymentsByIdLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortPaymentsBySaleIdLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="sale.id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortPaymentsByMemberIdLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="member.id" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortPaymentsByAmountLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="amount" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <c:url var="sortPaymentsByDateLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="date" />
                                <c:param name="orderBy" value="asc" />
                            </c:url>
                            <!-- Descedant order Links --->
                            <c:url var="sortPaymentsDescByIdLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortPaymentsDescBySaleIdLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="sale.id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortPaymentsDescByMemberIdLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="member.id" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>

                            <c:url var="sortPaymentsDescByAmountLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="amount" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>
                            <c:url var="sortPaymentsDescByDateLink" value="/management-panel/payments">
                                <c:param name="sortBy" value="date" />
                                <c:param name="orderBy" value="desc" />
                            </c:url>

                            <br/>
                            <h3>--Payments--</h3>
                            <table class="table table-hover table-sm"><br/>
                                <thead class="thead bg-info">
                                    <tr>
                                        <th scope="col">Id <a href="${sortPaymentsByIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortPaymentsDescByIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Sale Id <a href="${sortPaymentsBySaleIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortPaymentsDescBySaleIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Member Id <a href="${sortPaymentsByMemberIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortPaymentsDescByMemberIdLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Amount <a href="${sortPaymentsByAmountLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortPaymentsDescByAmountLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <th scope="col">Date <a href="${sortPaymentsByDateLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortPaymentsDescByDateLink}"><i class="fas fa-chevron-down"></i></a></th>
                                        <security:authorize access="hasRole('ADMIN')">
                                        <th scope="col">Action</th>
                                        </security:authorize>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%--@elvariable id="payments" type="java.util.List"--%>
                                    <c:forEach var="temp" items="${businessList}">
                                        <c:url var="updateLink" value="/management-panel/updateProduct">
                                            <c:param name="productId" value="${temp.id}" />
                                        </c:url>
                                        <c:url var="deleteLink" value="/management-panel/deleteProduct">
                                            <c:param name="productId" value="${temp.id}" />
                                        </c:url>
                                        <tr>
                                            <th scope="row">${temp.id}</th>
                                            <td>${temp.sale.id}</td>
                                            <td>${temp.member.username}</td>
                                            <td>${temp.amount}</td>
                                            <td>${temp.date}</td>
                                            <security:authorize access="hasRole('ADMIN')">
                                                <td>
                                                    <a href="${updateLink}">Update</a>
                                                        |
                                                    <a href="${deleteLink}"
                                                        onclick="if (!(confirm('Are you sure you want to delete this product?')))return false;">Delete</a>
                                                </td>
                                            </security:authorize>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
                </div>
            </div>
        </div>

        
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
        <script>
            $(document).ready(function() {
                if (location.hash) {
                    $("a[href='" + location.hash + "']").tab("show");
                }
                $(document.body).on("click", "a[data-toggle]", function(event) {
                    location.hash = this.getAttribute("href");
                });
            });
            $(window).on("popstate", function() {
                var anchor = location.hash || $("a[data-toggle='tab']").first().attr("href");
                $("a[href='" + anchor + "']").tab("show");
            });
        </script>
    </body>
</html>