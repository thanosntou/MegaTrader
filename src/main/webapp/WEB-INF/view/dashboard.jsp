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
                            <a class="nav-link" id="v-pills-news-tab" data-toggle="pill" href="#v-pills-news" role="tab" aria-controls="v-pills-news" aria-selected="false"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                            <a class="nav-link active" id="v-pills-dashboard-tab" data-toggle="pill" href="#v-pills-dashboard" role="tab" aria-controls="v-pills-dashboard" aria-selected="true"><i class="fas fa-columns"></i> Dashboard</a>
                            <a class="nav-link" id="v-pills-traders-tab" data-toggle="pill" href="#v-pills-traders" role="tab" aria-controls="v-pills-traders" aria-selected="false"><i class="fas fa-chart-line"></i> Invest with Traders</a>
                            <a class="nav-link" id="v-pills-portofolio-tab" data-toggle="pill" href="#v-pills-portofolio" role="tab" aria-controls="v-pills-portofolio" aria-selected="false"><i class="fas fa-briefcase"></i> Portofolio</a>
                            <a class="nav-link" id="v-pills-th-tab" data-toggle="pill" href="#v-pills-th" role="tab" aria-controls="v-pills-th" aria-selected="false"><i class="fas fa-history"></i> Transaction History</a>
                            <a class="nav-link" id="v-pills-wallet-tab" data-toggle="pill" href="#v-pills-wallet" role="tab" aria-controls="v-pills-wallet" aria-selected="false"><i class="fas fa-wallet"></i> Wallet</a>
                            <a class="nav-link" id="v-pills-payments-tab" data-toggle="pill" href="#v-pills-payments" role="tab" aria-controls="v-pills-payments" aria-selected="false"><i class="fas fa-credit-card"></i> Payments</a>
                            <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab" aria-controls="v-pills-settings" aria-selected="false"><i class="fas fa-cogs"></i> Settings</a>
                            <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                                <a class="nav-link" id="v-pills-trades-tab" href="${pageContext.request.contextPath}/trade" role="button" aria-controls="v-pills-trades" aria-selected="false"><i class="fas fa-exchange-alt"></i> Trade</a>
                            </security:authorize>
                            <security:authorize access="hasRole('ADMIN')">
                                <a class="nav-link" id="v-pills-trades-tab" href="${pageContext.request.contextPath}/admin" role="button" aria-controls="v-pills-admin" aria-selected="false"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                            </security:authorize>
                        </div>
                    </div>



            </div>
        </div>


            <%-- main bar --%>
        <div class="col-sm-10">
            <div class="card" style="height: 100%; margin-top: 15px">
                <div class="card-header">
                    <div class="card-header">
                        <%--<span style="position: relative; right: 690px; opacity: 0.7;">Bitcoin Syndicate</span>--%>
                    </div>
                </div>

                <div class="card-body">
                    <div class="col-12">
                    <div class="tab-content" id="v-pills-tabContent">

                        <%-------------------------------------------------------------- News -----------------------------------------------------------------------%>
                        <div class="tab-pane fade" id="v-pills-news" role="tabpanel" aria-labelledby="v-pills-news-tab">
                            <div class="row">
                                <h3>News</h3>
                            </div>
                            <br>
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

                                        <%--</c:if>--%>

                                    </c:forEach>

                        </div>

                    <%---------------------------------------------------------------- dashboard ---------------------------------------------------------------------%>
                    <div class="tab-pane fade show active" id="v-pills-dashboard" role="tabpanel" aria-labelledby="v-pills-dashboard-tab">
                        <div class="row" style="margin: 20px 0px">
                            <h2>Account Dashboard <a href="${pageContext.request.contextPath}/dashboard"><i class="fas fa-sync" class="button"></i></a></h2>
                        </div>
                        <br>
                        <form:form action="${pageContext.request.contextPath}/dashboard" method="GET" id="clientView">
                            Client:
                            <select name="client" form="clientView">
                                <option value="bitmex" selected disabled hidden>Choose here</option>
                                <option value="bitmex">Bitmex</option>
                                <option value="testnet">Bitmex Testnet</option>
                            </select>
                            <input type="submit" value="View" />
                        </form:form>
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

                        <%----------------------------------------------- invest with traders section ----------------------------------------------------------%>
                    <div class="tab-pane fade" id="v-pills-traders" role="tabpanel" aria-labelledby="v-pills-traders-tab">
                        <h3>Invest with Traders</h3>
                        <br>
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

                        <%-------------------------------------------------------------- Settings -----------------------------------------------------------------------%>
                        <div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">
                            <div class="row">
                                <h3>Settings</h3>
                            </div>
                            <br>
                            <br>
                            <div class="row">
                                <h4>Fixed Qty</h4>
                            </div>
                            <br>
                            <div class="row">
                                XBTUSD: ${user.fixedQtyXBTUSD}
                            </div>
                            <div class="row">
                                XBTJPY: ${user.fixedQtyXBTJPY}
                            </div>
                            <div class="row">
                                ADAZ18: ${user.fixedQtyADAZ18}
                            </div>
                            <div class="row">
                                BCHZ18: ${user.fixedQtyBCHZ18}
                            </div>
                            <div class="row">
                                EOSZ18: ${user.fixedQtyEOSZ18}
                            </div>
                            <div class="row">
                                ETHUSD: ${user.fixedQtyETHUSD}
                            </div>
                            <div class="row">
                                LTCZ18: ${user.fixedQtyLTCZ18}
                            </div>
                            <div class="row">
                                TRXZ18: ${user.fixedQtyTRXZ18}
                            </div>
                            <div class="row">
                                XRPZ18: ${user.fixedQtyXRPZ18}
                            </div>
                            <div class="row">
                                XBTKRW: ${user.fixedQtyXBTKRW}
                            </div>

                            <div class="row">
                                <div class="col-sm-1">
                                    API Key:
                                </div>
                                <div class="col-sm-11">
                                    ${apiKey}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-1">
                                    API Secret:
                                </div>
                                <div class="col-sm-11">
                                    ${apiSecret}
                                </div>
                            </div>

                            <br>
                            <div class="row">
                                <h4>Edit Keys</h4>
                            </div>
                            <div class="row">
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/apikey" method="POST">

                                        <%--<div class="row">--%>
                                            <%--<div class="col-sm-1">--%>
                                                <%--API Key: ${apiKey}--%>
                                            <%--</div>--%>
                                            <%--<div class="col-sm-1">--%>
                                                <%--API Secret: ${apiSecret}--%>
                                            <%--</div>--%>
                                        <%--</div>--%>

                                        <div class="row">
                                            <div class="col-sm-1">
                                                API Key:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="apikey"/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                API Secret:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="apisecret"/>
                                            </div>
                                        </div>
                                        <br>
                                        <input type="submit" value="Save" />
                                    </form:form>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <h4>Fixed Qty Order</h4>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="XBTUSD" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                               XBTUSD:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>

                                    </form:form>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="XBTJPY" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                XBTJPY:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>

                                    </form:form>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="ADAZ18" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                ADAZ18:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>
                                    </form:form>
                                </div>

                            </div>

                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="BCHZ18" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                BCHZ18:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>
                                    </form:form>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="EOSZ18" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                EOSZ18:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>

                                    </form:form>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="ETHUSD" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                ETHUSD:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>

                                    </form:form>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="LTCZ18" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                LTCZ18:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>

                                    </form:form>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="TRXZ18" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                TRXZ18:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>

                                    </form:form>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="XRPZ18" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                XRPZ18:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>

                                    </form:form>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <form:form action="${pageContext.request.contextPath}/user/fixedQty" method="POST">
                                        <input type="hidden" value="XBTKRW" name="symbol"/>
                                        <div class="row">
                                            <div class="col-sm-1">
                                                XBTKRW:
                                            </div>
                                            <div class="col-sm-11">
                                                <input type="text" name="fixedQty"/> <input type="submit" value="Save" />
                                            </div>
                                        </div>
                                        <br>

                                    </form:form>
                                </div>

                            </div>

                        </div>
                    </div>

                    </div>
                    <%--<h5 class="card-title">Special title treatment</h5>--%>
                    <%--<p class="card-text">With supporting text below as a natural lead-in to additional content.</p>--%>
                    <%--<a href="#" class="btn btn-primary">Go somewhere</a>--%>
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
