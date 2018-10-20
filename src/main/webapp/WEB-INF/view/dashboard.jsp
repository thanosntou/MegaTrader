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
    <title>Dashboard</title>
</head>
<body>
    <div id="all" class="container-fluid">
        <%--main content--%>
        <div style="min-height: 100vh;" class="row">

            <%@ include file="side-bar.jsp" %>

            <%------------------------------------------------- main bar --------------------------------------------------------------------------------------------------%>
            <div class="col-sm-10" style="background-color: #bac9d6">
                <div class="card" style="height: 100%; margin-top: 12px">
                    <div class="card-header" style="background-color: #eeeeee">
                            <span style="">Bitcoin Syndicate</span>
                    </div>

                    <div class="card-body">
                        <div class="col-12">
                        <%---------------------------------------------------------------- dashboard ---------------------------------------------------------------------%>
                        <div class="tab-pane fade show active" id="v-pills-dashboard" role="tabpanel" aria-labelledby="v-pills-dashboard-tab">
                            <div class="row" style="margin: 20px 0px">
                                <h2>Account Dashboard <a href="${pageContext.request.contextPath}/dashboard"><i class="fas fa-sync" class="button"></i></a></h2>
                            </div>
                            <br>
                            <br>
                            <h4>Current client: ${currentClient}</h4>
                            <br>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="card" style="width: 18rem;">
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">Bitmex Balance: ${walletBalance}</li>
                                            <li class="list-group-item">Earned: ${earned}</li>
                                            <li class="list-group-item">Wallet Balance: ${balance}</li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="card" style="width: 18rem;">
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">Balance: ${user.wallet.balance / 100000000} BTC</li>
                                            <li class="list-group-item">Active Balance for Orders: ${availableMargin}</li>
                                            <li class="list-group-item">Active on orders: ${activeBalance}</li>

                                        </ul>
                                    </div>
                                </div>

                            </div>
                            <br>
                            <br>
                            <%--<div class="row">--%>
                                <%--<table class="table table-hover table-sm">--%>
                                    <%--<tbody>--%>
                                        <%--<c:forEach var="tempMap" items="${positions}">--%>
                                            <%--<tr>--%>
                                                <%--<th scope="row">${tempMap['symbol']}</th>--%>
                                                <%--<td>${tempMap['commission']}</td>--%>
                                                <%--<td>${tempMap['initMarginReq']}</td>--%>
                                                <%--<td>${tempMap['maintMarginReq']}</td>--%>
                                                <%--<td>${tempMap['leverage']}</td>--%>
                                                <%--<td>${tempMap['prevClosePrice']}</td>--%>
                                                <%--<td>${tempMap['openingComm']}</td>--%>
                                                <%--<td>${tempMap['execBuyQty']}</td>--%>
                                                <%--<td>${tempMap['execBuyCost']}</td>--%>
                                                <%--<td>${tempMap['execSellQty']}</td>--%>
                                                <%--<td>${tempMap['execSellCost']}</td>--%>
                                                <%--<td>${tempMap['execQty']}</td>--%>
                                                <%--<td>${tempMap['execComm']}</td>--%>
                                                <%--<td>${tempMap['execBuyCost']}</td>--%>
                                                <%--<td>${tempMap['execSellQty']}</td>--%>
                                                <%--<td>${tempMap['execSellCost']}</td>--%>
                                            <%--</tr>--%>
                                        <%--</c:forEach>--%>
                                    <%--</tbody>--%>
                                <%--</table>--%>
                            <%--</div>--%>
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
