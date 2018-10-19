<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-panel.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

    <title>Admin Panel</title>
</head>
<body>
<div id="all" class="container-fluid">
    <div style="min-height: 100vh;" class="row">

        <%--// side bar--%>
        <div class="col-sm-2" style="background-color: #3b5264">
            <div class="row" style="height: 18vh;">
                <div class="card" style="width: 18rem; background-color: inherit; border: none">
                    <div class="card-body" style="color: white; margin-top: 1%">
                        <h5 class="card-title"><i class="fas fa-user"></i> Welcome, ${user.username}</h5>
                        <%--<h6>${user.email}</h6>--%>
                        <br>

                        <%--<a href="#" class="card-link">Card link</a>--%>
                        <form:form action="${pageContext.request.contextPath}/logout" method="POST">
                            <input type="submit" class="btn btn-outline-info" value="Logout"/>
                        </form:form>
                    </div>
                </div>
            </div>
            <div class="row">

                <br>
                <hr style="width: 100%; border: none; height: 1px; color: #333; background-color: #333; ">
                <br>

                <div class="col-sm-12">
                        <div class="nav flex-column nav-pills nav-fill" id="v-pills-tab" role="tablist" aria-orientation="vertical" >
                            <a class="nav-link" id="v-pills-news-tab" href="${pageContext.request.contextPath}/user/news" role="tab" aria-controls="v-pills-news" aria-selected="false"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                            <a class="nav-link" id="v-pills-dashboard-tab" href="${pageContext.request.contextPath}/dashboard" role="button" aria-controls="v-pills-dashboard" aria-selected="false"><i class="fas fa-exchange-alt"></i> Dashboard</a>                        <a class="nav-link" id="v-pills-copy-tab" href="${pageContext.request.contextPath}/copy" role="button" aria-controls="v-pills-copy" aria-selected="false"><i class="fas fa-chart-line"></i> Copy Trader</a>
                            <a class="nav-link" id="v-pills-th-tab" data-toggle="pill" href="#v-pills-th" role="tab" aria-controls="v-pills-th" aria-selected="false"><i class="fas fa-history"></i> Transaction History</a>
                            <a class="nav-link" id="v-pills-wallet-tab" data-toggle="pill" href="#v-pills-wallet" role="tab" aria-controls="v-pills-wallet" aria-selected="false"><i class="fas fa-wallet"></i> Wallet</a>
                            <a class="nav-link" id="v-pills-settings-tab" href="${pageContext.request.contextPath}/user/settings" role="tab" aria-controls="v-pills-settings" aria-selected="false"><i class="fas fa-cogs"></i> Settings</a>
                            <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                                <a class="nav-link" id="v-pills-trades-tab" href="${pageContext.request.contextPath}/trade" role="button" aria-controls="v-pills-trades" aria-selected="false"><i class="fas fa-exchange-alt"></i> Trade</a>
                            </security:authorize>
                            <security:authorize access="hasRole('ADMIN')">
                                <a class="nav-link active" id="v-pills-trades-tab" href="${pageContext.request.contextPath}/admin" role="button" aria-controls="v-pills-admin" aria-selected="true"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                            </security:authorize>
                        </div>
                    </div>
                </div>
            </div>

            <%-----------------------------------------main bar---------------------------------------------------------------%>
            <div class="col-sm-10" style="background-color: #bac9d6">
                <div class="card" style="height: 100%; margin-top: 12px">
                    <div class="card-header" style="background-color: #eeeeee">
                        <span style="">Bitcoin Syndicate</span>
                    </div>

                    <div class="card-body">
                        <div class="col-12">
                            <div class="tab-content" id="v-pills-tabContent">

                                <div class="row">
                                    <h1>Admin panel</h1>
                                </div>

                                <%---------------------------------------logins ----------------------------------------------------------------%>
                                <div class="tab-pane fade" id="v-pills-logins" role="tabpanel" aria-labelledby="v-pills-logins-tab">
                                    <div class="row">
                                        <h3>Logins</h3></div>
                                        <br>
                                    <div class="row">
                                        <table class="table table-hover table-sm"><br/>

                                            <thead class="thead bg-info">
                                            <tr>
                                                <th scope="col">User<a href="${sortByIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByIdLink}"></a></th>
                                                <th scope="col">Login Date<a href=""><i class="fas fa-chevron-up"></i></a><a href="${sortDescByNameLink}"></a></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="temp" items="${logins}">
                                                    <tr>
                                                        <th scope="row">${temp.user.username}</th>
                                                        <td>${temp.create_date}</td>
                                                    </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <%-------------------------------------------------  deposits ----------------------------------------%>
                                <div class="tab-pane fade" id="v-pills-deposits" role="tabpanel" aria-labelledby="v-pills-deposits-tab">
                                        <div class="row">
                                            <h3>Deposits</h3>
                                        </div>
                                        <br>
                                    <div class="row">
                                        <table class="table table-hover table-sm"><br/>
                                            <thead class="thead bg-info">
                                            <tr>
                                                <th scope="col">User<a href="${sortByIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByIdLink}"></a></th>
                                                <th scope="col">Amount<a href="${sortByCategoryLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByCategoryLink}"></a></th>
                                                <th scope="col">Login Date<a href=""><i class="fas fa-chevron-up"></i></a><a href="${sortDescByNameLink}"></a></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="temp" items="${deposits}">
                                                <tr>
                                                    <th scope="row">${temp.user.username}</th>
                                                    <td>${temp.amount}</td>
                                                    <td>${temp.create_date}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
