<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/trade-panel2.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <title>Trade Panel</title>
</head>
<body>
    <div id="all" class="container-fluid">
        <div style="min-height: 100vh;" class="row">
            <%--side bar--%>
            <%@ include file="side-bar.jsp" %>
            <%-- main bar --%>
            <div class="col-sm-10" style="background-color: #bac9d6">
                <div class="card" style="height: 100%; margin-top: 12px">
                    <div class="card-header" style="background-color: #eeeeee">
                        <span style="">Bitcoin Syndicate</span>
                    </div>
                    <div class="card-body">
                        <div class="row" style="margin: 20px 0px">
                            <h2>Trade Panel <a href="${pageContext.request.contextPath}/trade"><i class="fas fa-sync" class="button"></i></a></h2>
                        </div>
                        <%-- coin list --%>
                        <div class="container-fluid" id="coinlist">
                            <div class="row" >
                                <div class="col-sm-10">
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/XBTUSD" role="button">Bitcoin</a>
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/XBTJPY" role="button">Bitcoin / Yen</a>
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/ADAZ18" role="button">Cardano</a>
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/BCHZ18" role="button">Bitcoin Cash</a>
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/EOSZ18" role="button">EOS Token</a>
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/ETHUSD" role="button">Ethereum</a>
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/LTCZ18" role="button">Litecoin</a>
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/TRXZ18" role="button">Tron</a>
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/XRPZ18" role="button">Ripple</a>
                                    <a  class="btn btn-outline-primary" href="${pageContext.request.contextPath}/trade/XBTKRW" role="button">Bitcoin / Won</a>
                                </div>
                            </div>
                        </div>
                        <br>
                        <%--forms--%>
                        <div class="container-fluid" id="forms">
                            <div class="row">
                                <%--signal form--%>
                                <div class="col-sm">
                                    <div class="container-fluid" id="signal-form" style="min-height: 100%">
                                        <%--title--%>
                                        <div class="row">
                                            <h3>Signal For All</h3>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <form:form action="${pageContext.request.contextPath}/trade/signal" method="POST" oninput="x.value=parseInt(a.value)">
                                                <div class="col-sm">
                                                    <div class="row">
                                                        <div class="col-sm">
                                                            Contract
                                                        </div>
                                                        <div class="col-sm">
                                                            <input type="hidden" name="symbol" value="${symbol}" >
                                                            <input type="text" value="${symbol}" disabled >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm">
                                                            Side
                                                        </div>
                                                        <div class="col-sm">
                                                            <select name="side">
                                                                <option value="Buy">Long</option>
                                                                <option value="Sell">Short</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm">
                                                            Stop Loss
                                                        </div>
                                                        <div class="col-sm">
                                                            <input type="text" name="stopLoss" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm">
                                                            Profit Trigger
                                                        </div>
                                                        <div class="col-sm">
                                                            <input type="text" name="profitTrigger" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm">
                                                            Leverage
                                                        </div>
                                                        <div class="col-sm">
                                                            <input type="number" name="leverage" value="${currentLeverage}" min="0" max="${maxLeverage}">
                                                            <%--<output name="x" for="a"></output>--%>
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="row">
                                                        <div class="col-sm">
                                                        </div>
                                                        <div class="col-sm">
                                                            <input type="submit" value="Create Signal" ><br>
                                                        </div>
                                                    </div>
                                                    <br>
                                                </div>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                                <%--manual form--%>
                                <div class="col-sm">
                                    <div class="container-fluid" id="manual-form" style="min-height: 100%">
                                        <%--title--%>
                                        <div class="row">
                                            <h3>Manual For All</h3>
                                        </div>
                                        <%-- limit market stop market bar --%>
                                        <div class="row">
                                            <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                                                <li class="nav-item">
                                                    <a class="nav-link active" id="pills-limit-tab" data-toggle="pill" href="#pills-limit" role="tab" aria-controls="pills-limit" aria-selected="true">Limit</a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link" id="pills-market-tab" data-toggle="pill" href="#pills-market" role="tab" aria-controls="pills-market" aria-selected="false">Market</a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link" id="pills-stopMarket-tab" data-toggle="pill" href="#pills-stopMarket" role="tab" aria-controls="pills-stopMarket" aria-selected="false">Stop Market</a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link" id="pills-stopLimit-tab" data-toggle="pill" href="#pills-stopLimit" role="tab" aria-controls="pills-stopLimit" aria-selected="false">Stop Limit</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <%--tab-content--%>
                                        <div class="row">
                                            <div class="tab-content" id="pills-tabContent">
                                                    <%---------------------------------------------------LIMIT ----------------------------------%>
                                                    <div class="tab-pane fade show active" id="pills-limit" role="tabpanel" aria-labelledby="pills-limit-tab">
                                                        <form:form action="${pageContext.request.contextPath}/trade/orderAll" method="POST" id="limit-form" oninput="x.value=parseInt(aa.value)">
                                                            <div class="col-sm-12">
                                                                <input type="text" name="ordType" value="Limit" hidden />
                                                                <input type="text" name="symbol" value="${symbol}" hidden />
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Contract
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="text" name="symbol" value="${symbol}" disabled/>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Side
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <select name="side">
                                                                            <option value="Buy">Buy</option>
                                                                            <option value="Sell">Sell</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Limit Price
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="number" step="${priceStep}" name="price"/>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Leverage
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="number" name="leverage" value="${currentLeverage}" min="0" max="${maxLeverage}">
                                                                            <%--<output name="x" for="a"></output>--%>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="submit" value="Place Order" />
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form:form>
                                                    </div>
                                                    <%----------------------------------------------- MARKET TAB ------------------------------------------------------------------------------- --%>
                                                    <div class="tab-pane fade" id="pills-market" role="tabpanel" aria-labelledby="pills-market-tab">
                                                        <form:form action="${pageContext.request.contextPath}/trade/orderAll" method="POST" id="market-form" oninput="x.value=parseInt(a2.value)">
                                                            <div class="col-sm-12">
                                                                <input type="text" name="ordType" value="Market" hidden />
                                                                <input type="text" name="symbol" value="${symbol}" hidden />
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Contract
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="text" name="symbol" value="${symbol}" disabled/>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Side
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <select name="side">
                                                                            <option value="Buy">Buy</option>
                                                                            <option value="Sell">Sell</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Leverage
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="number" name="leverage" value="${currentLeverage}" min="0" max="${maxLeverage}">
                                                                            <%--<output name="x" for="a"></output>--%>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="submit" value="Place Order" />
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form:form>
                                                    </div>
                                                    <%------------------------------------------ STOP MARKET TAB ------------------------------------------------------------------------------- --%>
                                                    <div class="tab-pane fade" id="pills-stopMarket" role="tabpanel" aria-labelledby="pills-stopMarket-tab">
                                                        <form:form action="${pageContext.request.contextPath}/trade/orderAll" method="POST" id="stop-form" oninput="x.value=parseInt(a3.value)">
                                                            <div class="col-sm-12">
                                                                <input type="text" name="symbol" value="${symbol}" hidden />
                                                                <input type="text" name="execInst" value="Close,LastPrice" hidden />
                                                                <input type="text" name="ordType" value="Stop" hidden />
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Contract
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="text" name="symbol" value="${symbol}" disabled/>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Side
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <select name="side">
                                                                            <option value="Buy">Buy</option>
                                                                            <option value="Sell">Sell</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Stop Price
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="number" step="${priceStep}" name="stopPx"/>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                        Leverage
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="number" name="leverage" value="${currentLeverage}" min="0" max="${maxLeverage}">
                                                                            <%--<output name="x" for="a"></output>--%>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                                <div class="row">
                                                                    <div class="col-sm">
                                                                    </div>
                                                                    <div class="col-sm">
                                                                        <input type="submit" value="Place Order" />
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form:form>
                                                    </div>
                                                    <%------------------------------------------ STOP LIMIT TAB ------------------------------------------------------------------------------- --%>
                                                    <div class="tab-pane fade" id="pills-stopLimit" role="tabpanel" aria-labelledby="pills-stopLimit-tab">
                                                            <form:form action="${pageContext.request.contextPath}/trade/orderAll" method="POST" id="stop-form" oninput="x.value=parseInt(a3.value)">
                                                                <div class="col-sm-12">
                                                                    <input type="text" name="symbol" value="${symbol}" hidden />
                                                                    <input type="text" name="execInst" value="Close,LastPrice" hidden />
                                                                    <input type="text" name="ordType" value="StopLimit" hidden />
                                                                    <div class="row">
                                                                        <div class="col-sm">
                                                                            Contract
                                                                        </div>
                                                                        <div class="col-sm">
                                                                            <input type="text" name="symbol" value="${symbol}" disabled/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col-sm">
                                                                            Side
                                                                        </div>
                                                                        <div class="col-sm">
                                                                            <select name="side">
                                                                                <option value="Buy">Buy</option>
                                                                                <option value="Sell">Sell</option>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col-sm">
                                                                            Limit Price
                                                                        </div>
                                                                        <div class="col-sm">
                                                                            <input type="number" step="${priceStep}" name="price"/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col-sm">
                                                                            Stop Price
                                                                        </div>
                                                                        <div class="col-sm">
                                                                            <input type="number" step="${priceStep}" name="stopPx"/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col-sm">
                                                                            Leverage
                                                                        </div>
                                                                        <div class="col-sm">
                                                                            <input type="number" name="leverage" value="${currentLeverage}" min="0" max="${maxLeverage}">
                                                                                <%--<output name="x" for="a"></output>--%>
                                                                        </div>
                                                                    </div>
                                                                    <br>
                                                                    <div class="row">
                                                                        <div class="col-sm">
                                                                        </div>
                                                                        <div class="col-sm">
                                                                            <input type="submit" value="Place Order" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </form:form>
                                                        </div>
                                                </div>
                                    </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>
                        <%--active orders--%>
                        <div class="row">
                            <h3>-- Active Orders --</h3>
                        </div>
                        <div class="row">
                            <table class="table table-hover table-sm">
                                <thead class="thead bg-info">
                                <tr>
                                    <th scope="col">symbol</th>
                                    <th scope="col">Side</th>
                                    <th scope="col">Order Status</th>
                                    <th scope="col">Order Type</th>
                                    <th scope="col">Order Qty</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Stop Price</th>
                                    <th scope="col">Transaction Time</th>
                                    <th scope="col">
                                    <%--<c:if test="${randomActiveOrders.size() > 1}">--%>
                                    <form:form action="${pageContext.request.contextPath}/trade/order/cancelAll" method="POST">
                                    <input type="hidden" name="symbol" value="${symbol}"/>
                                    <input type="submit" class="btn btn-danger" value="Cancel All"/>
                                    </form:form>
                                    <%--</c:if>--%>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="tempMap" items="${randomActiveOrders}">
                                    <tr>
                                        <th scope="row">${tempMap['symbol']}</th>
                                        <td>${tempMap['side']}</td>
                                        <td>${tempMap['ordStatus']}</td>
                                        <td>${tempMap['ordType']}</td>
                                        <td>${tempMap['orderQty']}</td>
                                        <td>${tempMap['price']}</td>
                                        <td>${tempMap['stopPx']}</td>
                                        <td>${tempMap['transactTime']}</td>
                                            <td>
                                            <form:form action="${pageContext.request.contextPath}/trade/order/cancel" method="POST">
                                            <input type="hidden" name="symbol" value="${symbol}"/>
                                            <input type="hidden" name="orderID" value="${tempMap['orderID']}" />
                                            <input type="submit" class="btn btn-danger" value="Cancel"/>
                                            </form:form>
                                            </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <%--open positions--%>
                        <div class="row">
                            <h3>-- Open Positions --</h3>
                        </div>
                        <div class="row" id="openPos">
                            <table class="table table-hover table-sm">
                                <thead class="thead bg-info">
                                <tr>
                                    <th scope="col">Symbol</th>
                                    <th scope="col">Sum Size</th>
                                    <th scope="col">Entry Price</th>
                                    <th scope="col">Mark Price</th>
                                    <th scope="col">Liq. Price</th>
                                    <th scope="col">Margin</th>
                                    <th scope="col">Unrealised Pnl</th>
                                    <th scope="col">Roe%</th>
                                    <th scope="col">Realised Pnl</th>
                                    <%--<th scope="col">commission</th>--%>
                                    <th scope="col">Leverage</th>
                                    <%--<th scope="col">isOpen</th>--%>
                                    <%--<th scope="col">rebalancedPnl</th>--%>
                                    <%--<th scope="col">prevRealisedPnl</th>--%>
                                    <%--<th scope="col">prevUnrealisedPnl</th>--%>
                                    <%--<th scope="col">openingQty</th>--%>
                                    <%--<th scope="col">openingCost</th>--%>
                                    <%--<th scope="col">execQty</th>--%>
                                    <th scope="col">Exec Cost</th>
                                    <th scope="col">Current Cost</th>
                                    <%--<th scope="col">currentComm</th>--%>
                                    <%--<th scope="col">realisedCost</th>--%>
                                    <th scope="col">Unrealised Cost</th>

                                    <%--<th scope="col">grossOpenCost</th>--%>
                                    <%--<th scope="col">grossOpenPremium</th>--%>
                                    <%--<th scope="col">grossExecCost</th>--%>
                                    <th scope="col">Pos Margin</th>
                                    <%--<th scope="col">unrealisedGrossPnl</th>--%>
                                    <%--<th scope="col">unrealisedTax</th>--%>
                                    <%--<th scope="col">unrealisedPnlPcnt</th>--%>
                                    <%--<th scope="col">simpleQty</th>--%>
                                    <%--<th scope="col">simpleCost</th>--%>
                                    <%--<th scope="col">avgCostPrice</th>--%>
                                    <%--<th scope="col">breakEvenPrice</th>--%>
                                    <%--<th scope="col">marginCallPrice</th>--%>
                                    <%--<th scope="col">timestamp</th>--%>
                                    <%--<th scope="col">lastPrice</th>--%>
                                    <%--<th scope="col">lastValue</th>--%>
                                    <%--<th scope="col">Id<a href="${sortByIdLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByIdLink}"><i class="fas fa-chevron-down"></i></a></th>--%>
                                    <%--<th scope="col">Product Name<a href="${pageContext.request.contextPath}/management-panel"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByNameLink}"><i class="fas fa-chevron-down"></i></a></th>--%>
                                    <%--<th scope="col">Category<a href="${sortByCategoryLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByCategoryLink}"><i class="fas fa-chevron-down"></i></a></th>--%>
                                    <%--<th scope="col">Shop Price<a href="${sortByShopPriceLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByShopPriceLink}"><i class="fas fa-chevron-down"></i></a></th>--%>
                                    <%--<th scope="col">Buy Price<a href="${sortByBuyPriceLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByBuyPriceLink}"><i class="fas fa-chevron-down"></i></a></th>--%>
                                    <%--<th scope="col">Avail. Qty<a href="${sortByQtyLink}"><i class="fas fa-chevron-up"></i></a><a href="${sortDescByQtyLink}"><i class="fas fa-chevron-down"></i></a></th>--%>
                                    <%--<security:authorize access="hasRole('ADMIN')">--%>
                                    <%--<th scope="col">Action</th>--%>
                                    <%--</security:authorize>--%>
                                    <%--<th scope="col">--%>
                                    <%--<c:if test="${activeOrders.size() > 1}">--%>
                                    <%--<form:form action="${pageContext.request.contextPath}/trade/order/cancelAll" method="POST">--%>
                                    <%--&lt;%&ndash;<input type="hidden" value="${tempMap['orderID']}" name="orderID"/>&ndash;%&gt;--%>
                                    <%--<input type="submit" class="btn btn-danger" value="Cancel All"/>--%>
                                    <%--</form:form>--%>
                                    <%--</c:if>--%>
                                    <%--</th>--%>
                                    <th scope="col">Qty(%)</th>
                                    <th scope="col">Price</th>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="tempMap" items="${randomPositions}">
                                    <c:if test="${tempMap['isOpen'] == 'true'}" >
                                    <tr>
                                        <th scope="row">${tempMap['symbol']}</th>
                                        <td>${sumPosition}</td>
                                        <td>${tempMap['avgEntryPrice']}</td>
                                        <td id="markPriceTd"><span id="markPriceSpan">0</span></td>
                                        <td id="liquidationPriceTd"><span id="liquidationPriceSpan">${tempMap['liquidationPrice']}</span></td>
                                        <td id="maintMarginTd"><span id="maintMarginSpan">0</span></td>
                                        <td id="unrealisedPnlTd"><span id="unrealisedPnlSpan">${tempMap['unrealisedPnl']}</span></td>
                                        <td id="unrealisedRoePcntTd"><span id="unrealisedRoePcntSpan">${tempMap['unrealisedRoePcnt'] * 100}%</span></td>

                                        <td><span id="realisedPnl"> </span></td>
                                        <script>
                                            document.getElementById('realisedPnl').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8) ;
                                        </script>

                                            <%--<td>${tempMap['commission']}</td>--%>
                                        <td>${tempMap['leverage']}</td>
                                        <%--<td>${tempMap['isOpen']}</td>--%>
                                            <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                            <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                            <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                            <%--<td>${tempMap['openingQty']}</td>--%>
                                            <%--<td>${tempMap['openingCost']}</td>--%>
                                            <%--<td>${tempMap['execQty']}</td>--%>
                                        <td><span id="execCost"> </span></td>
                                        <script>
                                            document.getElementById('execCost').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8) ;
                                        </script>

                                        <td><span id="currentCost"> </span></td>
                                        <script>
                                            document.getElementById('currentCost').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8) ;
                                        </script>
                                            <%--<td>${tempMap['currentComm']}</td>--%>
                                            <%--<td>${tempMap['realisedCost']}</td>--%>
                                        <td><span id="unrealisedCost"> </span></td>
                                        <script>
                                            document.getElementById('unrealisedCost').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8) ;
                                        </script>
                                            <%--<td>${tempMap['grossOpenCost']}</td>--%>
                                            <%--<td>${tempMap['grossOpenPremium']}</td>--%>
                                            <%--<td>${tempMap['grossExecCost']}</td>--%>
                                        <%--<script>--%>
                                            <%--function someFunction1() {--%>
                                                <%--var num = 0;--%>
                                                <%--num = ${tempMap['posMargin']};--%>
                                                <%--var n = num / 100000000;--%>
                                                <%--n = num.toFixed(7);--%>

                                                <%--document.getElementById("this").innerHTML = n;--%>
                                            <%--}--%>
                                        <%--</script>--%>
                                        <td><span id="posMargin"> </span></td>
                                        <script>
                                            document.getElementById('posMargin').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
                                        </script>
                                            <%--<td>${tempMap['unrealisedGrossPnl']}</td>--%>
                                            <%--<td>${tempMap['unrealisedTax']}</td>--%>
                                            <%--<td>${tempMap['unrealisedPnlPcnt']}</td>--%>
                                            <%--<td>${tempMap['simpleQty']}</td>--%>
                                            <%--<td>${tempMap['simpleCost']}</td>--%>
                                            <%--<td>${tempMap['avgCostPrice']}</td>--%>
                                            <%--<td>${tempMap['breakEvenPrice']}</td>--%>
                                            <%--<td>${tempMap['marginCallPrice']}</td>--%>
                                            <%--<td>${tempMap['timestamp']}</td>--%>
                                            <%--<td>${tempMap['lastPrice']}</td>--%>
                                            <%--<td>${tempMap['lastValue']}</td>--%>
                                            <%--<td>--%>
                                            <%--<form:form action="${pageContext.request.contextPath}/trade/position/close" method="POST">--%>
                                            <%--<input type="hidden" name="symbol" value="${symbol}"/>--%>
                                            <%--<input type="number" name="limitPrice"/>--%>
                                            <%--<input type="submit" class="btn btn-danger" value="Close"/>--%>
                                            <%--</form:form>--%>

                                            <%--</td>--%>

                                        <td>
                                            <form:form action="${pageContext.request.contextPath}/trade/positionAll" method="POST">
                                                <input type="hidden" name="symbol" value="${symbol}"/>
                                                <input id="orderType" type="hidden" name="orderType"/>

                                                <c:if test="${tempMap['currentQty'] < '0'}">
                                                    <input id="side" type="hidden" name="side" value="Buy"/>
                                                </c:if>
                                                <c:if test="${tempMap['currentQty'] > '0'}">
                                                    <input id="side" type="hidden" name="side" value="Sell"/>
                                                </c:if>
                                                <%--<input id="side" type="hidden" name="side" value="${if tempMap['currentQty']}"/>--%>
                                                <input type="number" name="percentage" min="0" max="100"  value="0"/>
                                        </td>
                                        <td>
                                                <input type="number" name="price" min="0"  value="0"/>
                                        </td>
                                        <td>
                                            <input type="submit" onclick="document.getElementById('orderType').value = 'Market';" class="btn btn-danger" value="Market"/>

                                        </td>
                                        <td>
                                                <%--<input type="hidden" name="symbol" value="${symbol}"/>--%>
                                                <input type="submit" onclick="document.getElementById('orderType').value = 'Limit';" class="btn btn-danger" value="Close"/>
                                            </form:form>
                                        </td>
                                    </tr>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <%--followers--%>
                        <div class="row">
                            <h3>-- Followers & Fixed Qty --</h3>
                        </div>
                        <div class="row">
                            <table class="table table-hover table-sm">
                                <thead class="thead bg-info">
                                <tr>
                                    <th scope="col">User</th>
                                    <th scope="col">XBTUSD</th>
                                    <th scope="col">XBTJPY</th>
                                    <th scope="col">ADAZ18</th>
                                    <th scope="col">BCHZ18</th>
                                    <th scope="col">EOSZ18</th>
                                    <th scope="col">ETHUSD</th>
                                    <th scope="col">LTCZ18</th>
                                    <th scope="col">TRXZ18</th>
                                    <th scope="col">XRPZ18</th>
                                    <th scope="col">XBTKRW</th>
                                    <th scope="col"></th>
                                    <%--<form:form action="${pageContext.request.contextPath}/trade/order/cancelAll" method="POST">--%>
                                    <%--&lt;%&ndash;<input type="hidden" value="${tempMap['orderID']}" name="orderID"/>&ndash;%&gt;--%>
                                    <%--<input type="submit" class="btn btn-danger" value="Cancel All"/>--%>
                                    <%--</form:form>--%>
                                    <%--</th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="temp" items="${followers}">
                                    <%--<c:if test="${tempMap['ordStatus'] == 'New'}" >--%>
                                    <tr>
                                        <th scope="row">${temp.username}</th>
                                        <td>${temp.fixedQtyXBTUSD}</td>
                                        <td>${temp.fixedQtyXBTJPY}</td>
                                        <td>${temp.fixedQtyADAZ18}</td>
                                        <td>${temp.fixedQtyBCHZ18}</td>
                                        <td>${temp.fixedQtyEOSZ18}</td>
                                        <td>${temp.fixedQtyETHUSD}</td>
                                        <td>${temp.fixedQtyLTCZ18}</td>
                                        <td>${temp.fixedQtyTRXZ18}</td>
                                        <td>${temp.fixedQtyXRPZ18}</td>
                                        <td>${temp.fixedQtyXBTKRW}</td>
                                        <td>
                                            <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#followerModal">
                                                Manual
                                            </button>
                                        </td>
                                    </tr>
                                    <%--</c:if>--%>
                                </c:forEach>
                                </tbody>
                                <tfoot class="tfoot bg-info">
                                    <tr>
                                        <th scope="col">Sum</th>
                                        <th scope="col">${sumFixedQtys.get("sumXBTUSD")}</th>
                                        <th scope="col">${sumFixedQtys.get("sumXBTJPY")}</th>
                                        <th scope="col">${sumFixedQtys.get("sumADAZ18")}</th>
                                        <th scope="col">${sumFixedQtys.get("sumBCHZ18")}</th>
                                        <th scope="col">${sumFixedQtys.get("sumEOSZ18")}</th>
                                        <th scope="col">${sumFixedQtys.get("sumETHUSD")}</th>
                                        <th scope="col">${sumFixedQtys.get("sumLTCZ18")}</th>
                                        <th scope="col">${sumFixedQtys.get("sumTRXZ18")}</th>
                                        <th scope="col">${sumFixedQtys.get("sumXRPZ18")}</th>
                                        <th scope="col">${sumFixedQtys.get("sumXBTKRW")}</th>
                                        <th scope="col"></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--modal--%>
    <div class="modal fade" id="followerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <%--active orders--%>
                    <div class="row">
                        <h3>-- Active Orders --</h3>
                    </div>
                    <div class="row">
                        <table class="table table-hover table-sm">
                            <thead class="thead bg-info">
                            <tr>
                            <th scope="col">symbol</th>
                            <th scope="col">Side</th>
                            <th scope="col">Order Status</th>
                            <th scope="col">Order Type</th>
                            <th scope="col">Order Qty</th>
                            <th scope="col">Price</th>
                            <th scope="col">Stop Price</th>
                            <th scope="col">Transaction Time</th>
                            <%--<th scope="col">--%>
                            <%--<c:if test="${activeOrders.size() > 1}">--%>
                            <%--<form:form action="${pageContext.request.contextPath}/trade/order/cancelAll" method="POST">--%>
                            <%--&lt;%&ndash;<input type="hidden" name="symbol" value="${symbol}"/>&ndash;%&gt;--%>
                            <%--<input type="submit" class="btn btn-danger" value="Cancel All"/>--%>
                            <%--</form:form>--%>
                            <%--</c:if>--%>
                            <%--</th>--%>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="tempMap" items="${activeOrders}">
                            <tr>
                            <th scope="row">${tempMap['symbol']}</th>
                            <td>${tempMap['side']}</td>
                            <td>${tempMap['ordStatus']}</td>
                            <td>${tempMap['ordType']}</td>
                            <td>${tempMap['orderQty']}</td>
                            <td>${tempMap['price']}</td>
                            <td>${tempMap['stopPx']}</td>
                            <td>${tempMap['transactTime']}</td>
                            <%--<td>--%>
                            <%--<form:form action="${pageContext.request.contextPath}/trade/order/cancel" method="POST">--%>
                            <%--&lt;%&ndash;<input type="hidden" name="symbol" value="${symbol}"/>&ndash;%&gt;--%>
                            <%--<input type="hidden" name="orderID" value="${tempMap['orderID']}" />--%>
                            <%--<input type="submit" class="btn btn-danger" value="Cancel"/>--%>
                            <%--</form:form>--%>
                            <%--</td>--%>
                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="http://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
    <script>
        <%--document.getElementById("variablePosition").innerText = ${openPositions.get()};--%>
        var exampleSocket = new WebSocket("wss://testnet.bitmex.com/realtime");
        exampleSocket.onopen = function () {
            exampleSocket.send("{\"op\": \"authKeyExpires\", \"args\": [\"obt_f-85F7m2Olfi9IIUUlTG\", 1600883067, \"71c2f5ff56dc905bb9ada3b6f20b950b19b7c30716e9af2160a3e27c78d1b2ee\"]}");
            exampleSocket.send("{\"op\": \"subscribe\", \"args\": [\"position:XBTUSD\"]}");
        }
        // console.log(exampleSocket.readyState);
    </script>
    <script>
        exampleSocket.onmessage = function (event) {
            var msg = JSON.parse(event.data);

            if (document.getElementById("markPriceSpan") != msg.data["0"].markPrice) {
                $(function () {
                    $("#markPriceTd").delay(150).animate({
                        "background-color": "#ffeb79"
                    }, 350, function () {
                        $("#markPriceTd").animate({
                            "background-color": "#fff"
                        }, 200);
                    });
                });
                document.getElementById("markPriceSpan").innerText = msg.data[0].markPrice;
            }
            if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpan") != msg.data["0"].maintMargin) {
                $(function () {
                    $("#maintMarginTd").delay(150).animate({
                        "background-color": "#ffeb79"
                    }, 350, function () {
                        $("#maintMarginTd").animate({
                            "background-color": "#fff"
                        }, 200);
                    });
                });
                document.getElementById("maintMarginSpan").innerText = msg.data[0].maintMargin;
            }

            if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPrice") != msg.data["0"].liquidationPrice) {
                $(function () {
                    $("#liquidationPriceTd").delay(150).animate({
                        "background-color": "#ffeb79"
                    }, 350, function () {
                        $("#liquidationPriceTd").animate({
                            "background-color": "#fff"
                        }, 200);
                    });
                });
                document.getElementById("liquidationPriceSpan").innerText = msg.data[0].liquidationPrice;
            }

            if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcnt") != msg.data["0"].unrealisedRoePcnt) {
                $(function () {
                    $("#unrealisedRoePcntTd").delay(150).animate({
                        "background-color": "#ffeb79"
                    }, 350, function () {
                        $("#unrealisedRoePcntTd").animate({
                            "background-color": "#fff"
                        }, 200);
                    });
                });
                document.getElementById("unrealisedRoePcntSpan").innerText = msg.data[0].unrealisedRoePcnt * 100 + '%';
            }

            if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnl") != msg.data["0"].unrealisedPnl) {
                $(function () {
                    $("#unrealisedPnlTd").delay(150).animate({
                        "background-color": "#ffeb79"
                    }, 350, function () {
                        $("#unrealisedPnlTd").animate({
                            "background-color": "#fff"
                        }, 200);
                    });
                });
                document.getElementById("unrealisedPnlSpan").innerText = msg.data[0].unrealisedPnl;
            }


        }
    </script>
<%--<script>--%>
    <%--function myFunc(){--%>
        <%--var cb = document.getElementById('unrealCost');--%>
        <%--// var label = document.getElementsByName('label206451')[0]; // Get the first one of index--%>
        <%--// console.log(label);--%>
        <%--// cb.addEventListener('load',function(evt){ // use change here. not neccessarily--%>
        <%--// if(this.checked){--%>
        <%--cb.innerHTML = (55553 / 100000000).toFixed(7);--%>

        <%--// temp = temp / 100000000;--%>
        <%--// label.value='Thanks'--%>
        <%--// }else{--%>
        <%--//     label.value='0'--%>
        <%--// }--%>
        <%--// },false);--%>
    <%--}--%>

<%--</script>--%>

</body>
</html>