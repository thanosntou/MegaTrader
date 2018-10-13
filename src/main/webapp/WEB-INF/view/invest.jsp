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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <title>Invest</title>
</head>
<body>
<div id="all" class="container-fluid" style="width: 110%">

    <%--wallstreetbar--%>
    <%--<div class="row">--%>
        <%--<div class="col-sm-12" id="wsbar">--%>
            <%--<%@include file="wallstreet-navbar.jsp" %>--%>
        <%--</div>--%>
    <%--</div>--%>

    <%-- menu--%>
    <%--<div class="row" id="dash-nav" style="margin-top: 1%; height: 5vh; background-color: #2e3f4d;" >--%>
        <%--<div class="col-sm-2" id="brand">--%>

        <%--</div>--%>
        <%--<div class="col-sm-10" id="menu">--%>
            <%--<a class="navbar-brand" href="${pageContext.request.contextPath}/trade">Trade</a>--%>
            <%--&lt;%&ndash;<form:form action="${pageContext.request.contextPath}/logout" method="POST">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<input type="submit" class="btn btn-outline-dark" value="Logout"/>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</form:form>&ndash;%&gt;--%>
        <%--</div>--%>
    <%--</div>--%>

    <%--main content--%>
    <%--<div style="height: 100vh;" class="row">--%>

        <%--&lt;%&ndash;// side bar&ndash;%&gt;--%>
        <%--<div class="col-sm-2" style="background-color: #3b5264">--%>
            <%--<div class="row" style="height: 18vh;">--%>
                <%--<div class="card" style="width: 18rem; background-color: inherit; border: none">--%>
                    <%--<div class="card-body" style="color: white; margin-top: 1%">--%>
                        <%--<h5 class="card-title"><i class="fas fa-user"></i> Welcome, ${user.username}</h5>--%>
                        <%--<h6>${user.email}</h6>--%>
                        <%--<br>--%>

                        <%--&lt;%&ndash;<a href="#" class="card-link">Card link</a>&ndash;%&gt;--%>
                            <%--<form:form action="${pageContext.request.contextPath}/logout" method="POST">--%>
                                <%--<input type="submit" class="btn btn-outline-info" value="Logout"/>--%>
                            <%--</form:form>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="row">--%>

                <%--<br>--%>
                <%--<hr style="width: 100%; border: none; height: 1px; color: #333; background-color: #333; ">--%>
                <%--<br>--%>

                    <%--<div class="col-sm-12">--%>
                        <%--<div class="nav flex-column nav-pills nav-fill" id="v-pills-tab" role="tablist" aria-orientation="vertical" >--%>
                            <%--<a class="nav-link active" id="v-pills-dashboard-tab" data-toggle="pill" href="#v-pills-dashboard" role="tab" aria-controls="v-pills-dashboard" aria-selected="true">Dashboard</a>--%>
                            <%--<a class="nav-link" id="v-pills-traders-tab" data-toggle="pill" href="#v-pills-traders" role="tab" aria-controls="v-pills-traders" aria-selected="false">Invest with Traders</a>--%>
                            <%--<a class="nav-link" id="v-pills-portofolio-tab" data-toggle="pill" href="#v-pills-portofolio" role="tab" aria-controls="v-pills-portofolio" aria-selected="false">Portofolio</a>--%>
                            <%--<a class="nav-link" id="v-pills-th-tab" data-toggle="pill" href="#v-pills-th" role="tab" aria-controls="v-pills-th" aria-selected="false">Transaction History</a>--%>
                            <%--<a class="nav-link" id="v-pills-wallet-tab" data-toggle="pill" href="#v-pills-wallet" role="tab" aria-controls="v-pills-wallet" aria-selected="false">Wallet</a>--%>
                            <%--<a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab" aria-controls="v-pills-settings" aria-selected="false">Settings</a>--%>
                            <%--<security:authorize access="hasAnyRole('ADMIN', 'MEMBER')">--%>
                                <%--<a class="nav-link" id="v-pills-trades-tab" data-toggle="pill" href="#v-pills-trades" role="tab" aria-controls="v-pills-trades" aria-selected="false">Trade</a>--%>
                            <%--</security:authorize>--%>
                        <%--</div>--%>
                    <%--</div>--%>



            <%--</div>--%>
        <%--</div>--%>

        <%--<div class="col-sm-10">--%>
            <%--<div class="card text-center" style="height: 100%">--%>
                <%--<div class="card-header">--%>
                    <%--<ul class="nav nav-tabs card-header-tabs">--%>
                        <%--<li class="nav-item">--%>
                            <%--<a class="nav-link active" href="#">Active</a>--%>
                        <%--</li>--%>
                        <%--<li class="nav-item">--%>
                            <%--<a class="nav-link" href="#">Link</a>--%>
                        <%--</li>--%>
                        <%--<li class="nav-item">--%>
                            <%--<a class="nav-link disabled" href="#">Disabled</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
                <%--<div class="card-body">--%>
                    <%--<div class="col-12">--%>
                    <%--<div class="tab-content" id="v-pills-tabContent">--%>
                    <%--<div class="tab-pane fade show active" id="v-pills-dashboard" role="tabpanel" aria-labelledby="v-pills-dashboard-tab">Account Dashboard</div>--%>
                        <%--<div class="tab-pane fade" id="v-pills-traders" role="tabpanel" aria-labelledby="v-pills-traders-tab">Traders</div>--%>
                        <%--<div class="tab-pane fade" id="v-pills-portofolio" role="tabpanel" aria-labelledby="v-pills-portofolio-tab">Portofolio</div>--%>
                    <%--<div class="tab-pane fade" id="v-pills-th" role="tabpanel" aria-labelledby="v-pills-th-tab">Transaction History</div>--%>
                    <%--<div class="tab-pane fade" id="v-pills-wallet" role="tabpanel" aria-labelledby="v-pills-wallet-tab">Wallet</div>--%>
                    <%--<div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">Settings</div>--%>
                    <%--<div class="tab-pane fade" id="v-pills-trades" role="tabpanel" aria-labelledby="v-pills-trades-tab">Settings</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--&lt;%&ndash;<h5 class="card-title">Special title treatment</h5>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<p class="card-text">With supporting text below as a natural lead-in to additional content.</p>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<a href="#" class="btn btn-primary">Go somewhere</a>&ndash;%&gt;--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>

</div>

<%--<div id="myfooter" class="card text-white">--%>
    <%--&lt;%&ndash;<img class="card-img" src=".../100px270/#55595c:#373a3c/text:Card image" alt="Card image">&ndash;%&gt;--%>
    <%--<div class="card-img-overlay">--%>
        <%--<h5 class="card-title"></h5>--%>
        <%--<p class="card-text"></p>--%>
        <%--<p class="card-text"></p>--%>
    <%--</div>--%>
<%--</div>--%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
