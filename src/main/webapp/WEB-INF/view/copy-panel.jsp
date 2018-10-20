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

        <%@ include file="side-bar.jsp" %>

        <%-- main bar --%>
        <div class="col-sm-10" style="background-color: #bac9d6">
            <div class="card" style="height: 100%; margin-top: 12px">
                <div class="card-header" style="background-color: #eeeeee">
                    <%--<div class="row">--%>
                    <span style="">Bitcoin Syndicate</span>
                    <%--</div>--%>
                </div>

                <div class="card-body">
                    <div class="row" style="margin: 20px 0px">
                        <h2>Copy a Trader <a href="${pageContext.request.contextPath}/copy"><i class="fas fa-sync" class="button"></i></a></h2>
                    </div>
                        <br>
                        <div class="row">
                            <h4>Personal Trader</h4>
                        </div>
                        <div class="row">
                            <div class="card" style="max-width: 16rem; min-width: 16rem; margin-bottom: 20px">

                                <c:if test="${not empty personalTrader}">
                                    <div class="card-body row">
                                        <div class="col-sm-6">
                                            <img class="card-img-top" src="${pageContext.request.contextPath}/images/t1.jpg" alt="Card image cap">
                                        </div>
                                        <div class="col-sm-6">
                                            <h5 class="card-title">${personalTrader.username}</h5>
                                            <p class="card-text">The best trader in da hood</p>
                                        </div>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">Profit last week</li>
                                        <li class="list-group-item">Followed by</li>
                                    </ul>
                                    <div class="card-body">

                                        <form:form action="${pageContext.request.contextPath}/user/unlink" method="POST">
                                            <input type="hidden" value="${personalTrader.id}" name="traderId"/>
                                            <input type="submit" class="btn btn-outline-primary" value="Uncopy"/>
                                        </form:form>

                                    </div>
                                </c:if>
                            </div>

                        </div>
                        <br>
                        <br>
                        <div class="row">
                            <h4>Other Traders</h4>
                        </div>
                        <br>
                        <div class="card-deck">
                            <c:forEach var="temp" items="${activeTraders}">
                                <%--<c:url var="disableLink" value="/management-panel/updateProduct">--%>
                                <%--<c:param name="productId" value="${temp.id}" />--%>
                                <%--</c:url>--%>
                                <div class="card" style="max-width: 16rem; min-width: 16rem; margin-bottom: 20px">


                                    <div class="card-body row">
                                        <div class="col-sm-6">
                                            <img class="card-img-top" src="${pageContext.request.contextPath}/images/t1.jpg" alt="Card image cap">
                                        </div>
                                        <div class="col-sm-6">
                                            <h5 class="card-title">${temp.username}</h5>
                                            <p class="card-text">The best trader in da hood</p>
                                        </div>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">Profit last week</li>
                                        <li class="list-group-item">Followed by</li>
                                    </ul>
                                    <div class="card-body">
                                        <form:form action="${pageContext.request.contextPath}/user/link" method="POST">
                                            <input type="hidden" value="${temp.id}" name="traderId"/>
                                            <c:if test="${empty personalTrader}">
                                                <input type="submit" class="btn btn-outline-primary" value="Copy"/>
                                            </c:if>
                                        </form:form>

                                            <%--<form:form action="${pageContext.request.contextPath}/user/unlink" method="POST">--%>
                                            <%--<c:if test="${not empty personalTrader}">--%>
                                            <%--<input type="submit" class="btn btn-outline-primary" value="Copy"/>--%>
                                            <%--</c:if>--%>
                                            <%--</form:form>--%>
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