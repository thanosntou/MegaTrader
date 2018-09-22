<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <title>Dashboard</title>
</head>
<body>
<div id="all" class="container-fluid">

    <%--wallstreetbar--%>
    <div class="row">
        <div class="col-sm-12" id="wsbar">
            <%@include file="wallstreet-navbar.jsp" %>
        </div>
    </div>

    <%-- menu--%>
    <div class="row" id="dash-nav" style="margin-top: 1%; height: 5vh; background-color: #2e3f4d;" >
        <div class="col-sm-2" id="brand">

        </div>
        <div class="col-sm-10" id="menu">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/trade">Trade</a>
            <%--<form:form action="${pageContext.request.contextPath}/logout" method="POST">--%>
                <%--<input type="submit" class="btn btn-outline-dark" value="Logout"/>--%>
            <%--</form:form>--%>
        </div>
    </div>

    <%--main content--%>
    <div style="height: 75vh;" class="row">
        <div class="col-sm-2" style="background-color: #3b5264">


        </div>
        <div class="col-sm-10">
            <div class="card text-center" style="height: 100%">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">Active</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Link</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="#">Disabled</a>
                        </li>
                    </ul>
                </div>
                <div class="card-body">
                    <%--<h5 class="card-title">Special title treatment</h5>--%>
                    <%--<p class="card-text">With supporting text below as a natural lead-in to additional content.</p>--%>
                    <%--<a href="#" class="btn btn-primary">Go somewhere</a>--%>
                </div>
            </div>
        </div>
    </div>

</div>
<div id="myfooter" class="card text-white">
    <%--<img class="card-img" src=".../100px270/#55595c:#373a3c/text:Card image" alt="Card image">--%>
    <div class="card-img-overlay">
        <h5 class="card-title"></h5>
        <p class="card-text"></p>
        <p class="card-text"></p>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
