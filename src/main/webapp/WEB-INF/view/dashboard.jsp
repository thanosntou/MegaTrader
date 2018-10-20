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
                            <%--<form:form action="${pageContext.request.contextPath}/dashboard" method="GET" id="clientView">--%>
                                <%--Client:--%>
                                <%--<select name="client" form="clientView">--%>
                                    <%--<option value="bitmex" selected disabled hidden>Choose here</option>--%>
                                    <%--<option value="bitmex">Bitmex</option>--%>
                                    <%--<option value="testnet">Bitmex Testnet</option>--%>
                                <%--</select>--%>
                                <%--<input type="submit" value="View" />--%>
                            <%--</form:form>--%>
                            <%--<br>--%>
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
                            <br><br>
                            <div class="row">
                                <table class="table table-hover table-sm">
                                    <tbody>
                                    <c:forEach var="tempMap" items="${positions}">
                                        <%--<c:if test="${tempMap['ordStatus'] == 'New'}" >--%>
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>
                                            <td>${tempMap['commission']}</td>
                                            <td>${tempMap['initMarginReq']}</td>
                                            <td>${tempMap['maintMarginReq']}</td>
                                            <td>${tempMap['leverage']}</td>
                                            <td>${tempMap['prevClosePrice']}</td>
                                            <td>${tempMap['openingComm']}</td>
                                            <td>${tempMap['execBuyQty']}</td>
                                            <td>${tempMap['execBuyCost']}</td>
                                            <td>${tempMap['execSellQty']}</td>
                                            <td>${tempMap['execSellCost']}</td>
                                            <td>${tempMap['execQty']}</td>
                                            <td>${tempMap['execComm']}</td>
                                            <td>${tempMap['execBuyCost']}</td>
                                            <td>${tempMap['execSellQty']}</td>
                                            <td>${tempMap['execSellCost']}</td>

                                        </tr>
                                        <%--</c:if>--%>

                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                                <%-------------------------------------------------------------- Portofolio -----------------------------------------------------------------------%>
                        <div class="tab-pane fade" id="v-pills-portofolio" role="tabpanel" aria-labelledby="v-pills-portofolio-tab">
                            <h3>Portofolio</h3>
                            <br>
                                <c:forEach var="temp" items="${activeTraders}">
                                    <%--<c:url var="disableLink" value="/management-panel/updateProduct">--%>
                                    <%--<c:param name="productId" value="${temp.id}" />--%>
                                    <%--</c:url>--%>
                                    <div class="row">
                                        <div class="card text-center" style="max-width: 100%; min-width: 100%; margin-bottom: 20px">
                                            <h5 class="card-header">Featured</h5>
                                            <div class="card-body">
                                                <h5 class="card-title">Special title treatment</h5>
                                                <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                                <a href="#" class="btn btn-primary">Go somewhere</a>
                                            </div>
                                        </div>
                                    </div>

                                </c:forEach>
                        </div>

                     <%-------------------------------------------------------------- Transaction History -----------------------------------------------------------------------%>
                        <div class="tab-pane fade" id="v-pills-th" role="tabpanel" aria-labelledby="v-pills-th-tab">
                            <div class="row">
                                <h3>Transaction History</h3>
                            </div>
                            <%--<div class="row">--%>
                                <%--<h4>Closed Orders</h4>--%>
                                <%--<table class="table table-hover table-sm">--%>
                                    <%--<tbody>--%>
                                    <%--<c:forEach var="tempMap" items="${closedOrders}">--%>
                                        <%--<c:if test="${tempMap['ordStatus'] == 'Filled' or tempMap['ordStatus'] == 'Close'}" >--%>
                                            <%--<tr>--%>
                                                <%--<th scope="row">${tempMap['symbol']}</th>--%>
                                                <%--<td>${tempMap['side']}</td>--%>
                                                <%--<td>${tempMap['price']}</td>--%>
                                                <%--<td>${tempMap['ordType']}</td>--%>
                                                <%--<td>${tempMap['orderStatus']}</td>--%>
                                                <%--<td>${tempMap['orderQty']}</td>--%>
                                                <%--<td>${tempMap['currency']}</td>--%>
                                                <%--<td>${tempMap['ordStatus']}</td>--%>
                                                <%--<td>${tempMap['transactTime']}</td>--%>
                                                <%--<td>${tempMap['cumQty']}</td>--%>
                                                <%--<td>${tempMap['avgPx']}</td>--%>

                                            <%--</tr>--%>
                                        <%--</c:if>--%>

                                    <%--</c:forEach>--%>
                                    <%--</tbody>--%>
                                <%--</table>--%>
                            <%--</div>--%>
                            <%--<br>--%>
                            <%--<div class="row">--%>
                                <%--<h4>Cancelled Orders</h4>--%>
                                <%--<table class="table table-hover table-sm">--%>
                                    <%--<thead class="thead bg-info">--%>
                                    <%--<tr>--%>
                                        <%--<th scope="col">Symbol</th>--%>
                                        <%--<th scope="col">Side</th>--%>
                                        <%--<th scope="col">Price</th>--%>
                                        <%--<th scope="col">Order Type</th>--%>
                                        <%--<th scope="col">order Qty</th>--%>
                                        <%--<th scope="col">Currency</th>--%>
                                        <%--<th scope="col">Order Status</th>--%>
                                        <%--<th scope="col">Transaction</th>--%>
                                    <%--</tr>--%>
                                    <%--</thead>--%>
                                    <%--<tbody>--%>
                                    <%--<c:forEach var="tempMap" items="${cancelledOrders}">--%>
                                        <%--&lt;%&ndash;<c:if test="${tempMap['ordStatus'] == 'Filled' or tempMap['ordStatus'] == 'Close'}" >&ndash;%&gt;--%>
                                            <%--<tr>--%>
                                                <%--<th scope="row">${tempMap['symbol']}</th>--%>
                                                <%--<td>${tempMap['side']}</td>--%>
                                                <%--<td>${tempMap['price']}</td>--%>
                                                <%--<td>${tempMap['ordType']}</td>--%>
                                                <%--<td>${tempMap['orderQty']}</td>--%>
                                                <%--<td>${tempMap['currency']}</td>--%>
                                                <%--<td>${tempMap['ordStatus']}</td>--%>
                                                <%--<td>${tempMap['transactTime']}</td>--%>
                                            <%--</tr>--%>
                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>

                                    <%--</c:forEach>--%>
                                    <%--</tbody>--%>
                                <%--</table>--%>
                            <%--</div>--%>
                            <br>
                            <div class="row">
                                <h4>All Orders</h4>
                                <table class="table table-hover table-sm">
                                    <thead class="thead bg-info">
                                    <tr>
                                        <th scope="col">Symbol</th>
                                        <th scope="col">Side</th>
                                        <th scope="col">Price</th>
                                        <th scope="col">Order Type</th>
                                        <th scope="col">order Qty</th>
                                        <th scope="col">Currency</th>
                                        <th scope="col">Order Status</th>
                                        <th scope="col">Transaction</th>
                                        <th scope="col">cumQty</th>
                                        <th scope="col">averagePrice</th>

                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="tempMap" items="${allOrders}">
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>
                                            <td>${tempMap['side']}</td>
                                            <td>${tempMap['price']}</td>
                                            <td>${tempMap['ordType']}</td>
                                            <td>${tempMap['orderQty']}</td>
                                            <td>${tempMap['currency']}</td>
                                            <td>${tempMap['ordStatus']}</td>
                                            <td>${tempMap['transactTime']}</td>
                                            <td>${tempMap['cumQty']}</td>
                                            <td>${tempMap['avgPx']}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                             <%-------------------------------------------------------------- Wallet -----------------------------------------------------------------------%>
                            <div class="tab-pane fade" id="v-pills-wallet" role="tabpanel" aria-labelledby="v-pills-wallet-tab">
                                <h3>Wallet</h3>
                                <br>
                                <div class="card" style="width: 18rem;">
                                    <div class="card-body">
                                        <h5 class="card-title">Balance: </h5>
                                    </div>
                                    <div class="card-body">
                                        <a href="#" class="card-link">Deposit</a>
                                        <a href="#" class="card-link">Withdraw</a>
                                    </div>
                                </div>
                                <br>
                                <div>
                                    <a class="donate-with-crypto" href="https://commerce.coinbase.com/checkout/18942dc4-8e2c-485e-a44a-c10f4d35170a">
                                        <span><i class="fab fa-bitcoin"></i> Deposit</span>
                                    </a>
                                    <script src="https://commerce.coinbase.com/v1/checkout.js">
                                    </script>
                                </div>
                            </div>

                            <%-------------------------------------------------------------- Payments -----------------------------------------------------------------------%>
                            <div class="tab-pane fade" id="v-pills-payments" role="tabpanel" aria-labelledby="v-pills-payments-tab">
                                <h3>Payments</h3>
                                <br>
                                <br>

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
