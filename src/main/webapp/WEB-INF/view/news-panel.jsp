<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/copy.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <title>Copy Panel</title>
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
                            <a class="nav-link active" id="v-pills-news-tab" href="${pageContext.request.contextPath}/user/news" role="tab" aria-controls="v-pills-news" aria-selected="true"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                            <a class="nav-link" id="v-pills-dashboard-tab" href="${pageContext.request.contextPath}/dashboard/" role="button"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                            <a class="nav-link" id="v-pills-copy-tab" href="${pageContext.request.contextPath}/copy/" role="tab"><i class="fas fa-chart-line"></i> Copy Trader</a>
                            <a class="nav-link" id="v-pills-th-tab" data-toggle="pill" href="#v-pills-th" role="tab"><i class="fas fa-history"></i> Transaction History</a>
                            <a class="nav-link" id="v-pills-wallet-tab" data-toggle="pill" href="#v-pills-wallet" role="tab"><i class="fas fa-wallet"></i> Wallet</a>
                            <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab"><i class="fas fa-cogs"></i> Settings</a>
                            <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                                <a class="nav-link" id="v-pills-trades-tab" href="${pageContext.request.contextPath}/trade/" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                            </security:authorize>
                            <security:authorize access="hasRole('ADMIN')">
                                <a class="nav-link" id="v-pills-trades-tab" href="${pageContext.request.contextPath}/admin/" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                            </security:authorize>
                        </div>
                    </div>
                </div>
            </div>


            <%-- main bar --%>
            <div class="col-sm-10" style="background-color: #bac9d6">
                <div class="card" style="height: 100%; margin-top: 12px">
                    <div class="card-header" style="background-color: #eeeeee">
                        <span style="">Bitcoin Syndicate</span>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <h3>News</h3>
                        </div>
                        <br>
                        <div class="row">
                            <c:forEach var="tempMap" items="${announcements}">
                                <div class="card" style="margin: 20px; padding: 20px">
                                    <div class="card-body">
                                            <%--<c:if test="${tempMap['ordStatus'] == 'New'}" >--%>
                                        <div class="row" style="font-size: 1.4rem">
                                            Title: ${tempMap['title']}
                                        </div>
                                        <br>
                                        <div class="row">
                                                ${tempMap['content']}
                                        </div>
                                        <div class="row">
                                            Date: ${tempMap['date']}
                                        </div>
                                        <div class="row">
                                            <a href="${tempMap['link']}">${tempMap['link']}</a>
                                        </div>
                                        <br>
                                        <br>
                                    </div>
                                </div>
                            </c:forEach>
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