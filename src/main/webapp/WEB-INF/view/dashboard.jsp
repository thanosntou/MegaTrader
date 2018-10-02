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
<div id="all" class="container-fluid" style="width: 100%">

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
    <div style="height: 100vh;" class="row">

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
                                <a class="nav-link active" id="v-pills-dashboard-tab" data-toggle="pill" href="#v-pills-dashboard" role="tab" aria-controls="v-pills-dashboard" aria-selected="true"><i class="fas fa-columns"></i> Dashboard</a>
                            <a class="nav-link" id="v-pills-traders-tab" data-toggle="pill" href="#v-pills-traders" role="tab" aria-controls="v-pills-traders" aria-selected="false"><i class="fas fa-chart-line"></i> Invest with Traders</a>
                            <a class="nav-link" id="v-pills-portofolio-tab" data-toggle="pill" href="#v-pills-portofolio" role="tab" aria-controls="v-pills-portofolio" aria-selected="false"><i class="fas fa-briefcase"></i> Portofolio</a>
                            <a class="nav-link" id="v-pills-th-tab" data-toggle="pill" href="#v-pills-th" role="tab" aria-controls="v-pills-th" aria-selected="false"><i class="fas fa-history"></i> Transaction History</a>
                            <a class="nav-link" id="v-pills-wallet-tab" data-toggle="pill" href="#v-pills-wallet" role="tab" aria-controls="v-pills-wallet" aria-selected="false"><i class="far fa-credit-card"></i> Wallet</a>
                            <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab" aria-controls="v-pills-settings" aria-selected="false"><i class="fas fa-cogs"></i> Settings</a>
                            <security:authorize access="hasAnyRole('ADMIN', 'MEMBER')">
                                <a class="nav-link" id="v-pills-trades-tab" href="${pageContext.request.contextPath}/trade" role="button" aria-controls="v-pills-trades" aria-selected="false"><i class="fas fa-exchange-alt"></i> Trade</a>
                            </security:authorize>
                        </div>
                    </div>



            </div>
        </div>


            <%-- main bar --%>
        <div class="col-sm-10">
            <div class="card" style="height: 100%">
                <div class="card-header">
                    <div class="card-header">
                        <%--<span style="position: relative; right: 690px; opacity: 0.7;">Bitcoin Syndicate</span>--%>
                    </div>
                </div>
                <div class="card-body">
                    <div class="col-12">
                    <div class="tab-content" id="v-pills-tabContent">

                        <%-- dashboard --%>
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

                        <%-- invest with traders section --%>
                    <div class="tab-pane fade" id="v-pills-traders" role="tabpanel" aria-labelledby="v-pills-traders-tab">
                        <h3>Invest with Traders</h3>
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
                                    <a href="#" class="btn btn-primary">Copy</a>
                                </div>
                            </div>
                        </c:forEach>
                        </div>

                    </div>

                    <div class="tab-pane fade" id="v-pills-portofolio" role="tabpanel" aria-labelledby="v-pills-portofolio-tab">
                        <h3>Portofolio</h3>
                        <br>
                        <div>
                            <a class="donate-with-crypto"
                               href="https://commerce.coinbase.com/checkout/6da189f179bc31">
                                <span>Donate with Crypto</span>
                            </a>
                            <script src="https://commerce.coinbase.com/v1/checkout.js">
                            </script>
                        </div>

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

                    <div class="tab-pane fade" id="v-pills-th" role="tabpanel" aria-labelledby="v-pills-th-tab">
                        <h3>Transaction History</h3>
                            <table class="table table-hover table-sm">
                                <tbody>
                                <c:forEach var="tempMap" items="${oldOrders}">
                                    <c:if test="${tempMap['ordStatus'] == 'Filled' or tempMap['ordStatus'] == 'Close'}" >
                                    <tr>
                                        <th scope="row">${tempMap['symbol']}</th>
                                        <td>${tempMap['size']}</td>
                                        <td>${tempMap['price']}</td>
                                        <td>${tempMap['ordType']}</td>
                                        <td>${tempMap['orderStatus']}</td>
                                        <td>${tempMap['orderQty']}</td>
                                        <td>${tempMap['currency']}</td>
                                        <td>${tempMap['ordStatus']}</td>
                                        <td>${tempMap['transactTime']}</td>
                                        <td>${tempMap['cumQty']}</td>
                                        <td>${tempMap['avgPx']}</td>

                                    </tr>
                                    </c:if>

                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
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
                    </div>

                    <div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">
                        <h3>Settings</h3>
                        <br>
                        <br>
                        <h4>${currentClient} keys</h4>
                        API Key: ${apiKey}<br>
                        API Secret: ${apiSecret}<br>
                        <br>
                        <h3>Edit Keys</h3>
                        Bitmex
                        <form:form action="${pageContext.request.contextPath}/user/apikey" method="POST" id="bitmexkeys">
                            API Key<input type="text" name="apikey" /><br>
                            API Secret <input type="text" name="apisecret" /><br>
                            <input type="submit" value="Save" />
                        </form:form>
                        <br>

                    </div>

                    <%--<div class="tab-pane fade" id="v-pills-trades" role="tabpanel" aria-labelledby="v-pills-trades-tab">--%>
                        <%--<h3>Settings</h3>--%>
                    <%--</div>--%>

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
