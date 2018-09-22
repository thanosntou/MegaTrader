<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wallstreet-navbar.css">
</head>
<body>
    <div id="main" class="navbar fixed-top navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}">XBTUSD</a></li>
            <li><a class="nav-link" href="${pageContext.request.contextPath}">XBTZ18</a></li>
            <li><a class="nav-link" href="${pageContext.request.contextPath}">XBTU18</a></li>
            <li><a class="nav-link" href="${pageContext.request.contextPath}">XBTH19</a></li>
            <li><a class="nav-link" href="${pageContext.request.contextPath}">XBT7D_95</a></li>
            <li><a class="nav-link" href="${pageContext.request.contextPath}">XBT7D_U105</a></li>
            <li><a class="nav-link" href="${pageContext.request.contextPath}">ADAU18</a></li>
            <%--<security:authorize access="hasAnyRole('ADMIN', 'MEMBER')">--%>
                <%--<li class="nav-item active"><a class="nav-link" href="${pageContext.request.contextPath}/management-panel">Management Panel <span class="sr-only">(current)</span></a></li>--%>
            <%--</security:authorize>--%>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <%--<c:if test="${empty pageContext.request.userPrincipal}">--%>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/register/showRegistrationFormOption"><span class="glyphicon glyphicon-user"></span>Funding: 00:00:00@ 0.0% Time: </a></li>
                <%--<li class="nav-item"><a class="nav-link" data-toggle="modal" data-target="#myModal" >Time</a></li>--%>
            <%--</c:if>--%>

            <%--<c:if test="${not empty pageContext.request.userPrincipal}">--%>
                <%--<div class="btn-group dropleft" style="margin-right: 100px">--%>
                    <%--<!--                                <button type="button" class="btn btn-secondary">Account</button>-->--%>
                    <%--<button type="button" class="btn btn-secondary dropdown-toggle" id="dropdownMenuReference" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
                        <%--Account--%>
                    <%--</button>--%>
                    <%--<div class="dropdown-menu" aria-labelledby="dropdownMenuReference">--%>
                        <%--<a class="dropdown-item" href="#">Profile</a>--%>
                        <%--<security:authorize access="hasAnyRole('CUSTOMER', 'MEMBER')">--%>
                            <%--<a class="dropdown-item" href="${pageContext.request.contextPath}/cart">Cart</a>--%>
                            <%--<a class="dropdown-item" href="#">Orders</a>--%>
                            <%--<a class="dropdown-item" href="#">Shipping Details</a>--%>
                        <%--</security:authorize>--%>
                        <%--<div class="dropdown-divider"></div>--%>
                        <%--<a class="dropdown-item" href="#">--%>
                            <%--<form:form action="${pageContext.request.contextPath}/logout" method="POST" >--%>
                                <%--<input type="submit" class="btn btn-outline-dark" value="Logout" />--%>
                            <%--</form:form>--%>
                        <%--</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</c:if>--%>
        </ul>

        </div>
    </div>

</body>
</html>
