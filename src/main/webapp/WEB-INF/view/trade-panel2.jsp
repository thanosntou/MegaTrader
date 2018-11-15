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
                            <table class="table table-hover table-sm ">
                                <thead class="thead bg-info">
                                <tr>
                                    <th scope="col">symbol</th>
                                    <th scope="col">Side</th>
                                    <th scope="col">Order Status</th>
                                    <th scope="col">Order Type</th>
                                    <%--<th scope="col">Order Qty</th>--%>
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
                                        <%--<td>${tempMap['orderQty']}</td>--%>
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
                                    <c:if test="${tempMap['isOpen'] == 'true' && tempMap['symbol'] == 'XBTUSD'}" >
                                    <tr>
                                        <th scope="row">${tempMap['symbol']}</th>

                                        <td>${sumPositionXBTUSD}</td>

                                        <td id="avgEntryPriceTdXBTUSD"><span id="avgEntryPriceSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('avgEntryPriceSpanXBTUSD').innerText = (${tempMap['avgEntryPrice']}).toFixed(2);
                                        </script>

                                        <td id="markPriceTdXBTUSD"><span id="markPriceSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('markPriceSpanXBTUSD').innerText = (${tempMap['markPrice']}).toFixed(2);
                                        </script>

                                        <td id="liquidationPriceTdXBTUSD"><span id="liquidationPriceSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('liquidationPriceSpanXBTUSD').innerText = (${tempMap['liquidationPrice'] / 100000000}).toFixed(8);
                                        </script>

                                        <td id="maintMarginTdXBTUSD"><span id="maintMarginSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('maintMarginSpanXBTUSD').innerText = (${tempMap['maintMargin'] / 100000000}).toFixed(8);
                                        </script>

                                        <td id="unrealisedPnlTdXBTUSD"><span id="unrealisedPnlSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('unrealisedPnlSpanXBTUSD').innerText = (${tempMap['unrealisedPnl'] / 100000000}).toFixed(8);
                                        </script>

                                        <td id="unrealisedRoePcntTdXBTUSD"><span id="unrealisedRoePcntSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('unrealisedRoePcntSpanXBTUSD').innerText = (${tempMap['unrealisedRoePcnt'] * 100}).toFixed(2)+'%';
                                        </script>

                                        <td id="realisedPnlTdXBTUSD"><span id="realisedPnlSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('realisedPnlSpanXBTUSD').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8);
                                        </script>

                                            <%--<td>${tempMap['commission']}</td>--%>
                                        <td id="leverageTdXBTUSD"><span id="leverageSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('leverageSpanXBTUSD').innerText = (${tempMap['leverage'] / 100000000}).toFixed(8);
                                        </script>
                                        <%--<td>${tempMap['isOpen']}</td>--%>
                                            <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                            <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                            <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                            <%--<td>${tempMap['openingQty']}</td>--%>
                                            <%--<td>${tempMap['openingCost']}</td>--%>
                                            <%--<td>${tempMap['execQty']}</td>--%>

                                        <td id="execCostTdXBTUSD"><span id="execCostSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('execCostSpanXBTUSD').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8);
                                        </script>

                                        <td id="currentCostTdXBTUSD"><span id="currentCostSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('currentCostSpanXBTUSD').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8);
                                        </script>
                                            <%--<td>${tempMap['currentComm']}</td>--%>
                                            <%--<td>${tempMap['realisedCost']}</td>--%>
                                        <td id="unrealisedCostTdXBTUSD"><span id="unrealisedCostSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('unrealisedCostSpanXBTUSD').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8);
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
                                        <td id="posMarginTdXBTUSD"><span id="posMarginSpanXBTUSD"></span></td>
                                        <script>
                                            document.getElementById('posMarginSpanXBTUSD').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
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
                                <c:forEach var="tempMap" items="${randomPositions}">
                                    <c:if test="${tempMap['isOpen'] == 'true' && tempMap['symbol'] == 'XBTJPY'}" >
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>

                                            <td>${sumPositionXBTJPY}</td>

                                            <td id="avgEntryPriceTdXBTJPY"><span id="avgEntryPriceSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('avgEntryPriceSpanXBTJPY').innerText = (${tempMap['avgEntryPrice']}).toFixed(2);
                                            </script>

                                            <td id="markPriceTdXBTJPY"><span id="markPriceSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('markPriceSpanXBTJPY').innerText = (${tempMap['markPrice']}).toFixed(2);
                                            </script>

                                            <td id="liquidationPriceTdXBTJPY"><span id="liquidationPriceSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('liquidationPriceSpanXBTJPY').innerText = (${tempMap['liquidationPrice'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="maintMarginTdXBTJPY"><span id="maintMarginSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('maintMarginSpanXBTJPY').innerText = (${tempMap['maintMargin'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedPnlTdXBTJPY"><span id="unrealisedPnlSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('unrealisedPnlSpanXBTJPY').innerText = (${tempMap['unrealisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedRoePcntTdXBTJPY"><span id="unrealisedRoePcntSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('unrealisedRoePcntSpanXBTJPY').innerText = (${tempMap['unrealisedRoePcnt'] * 100}).toFixed(2)+'%';
                                            </script>

                                            <td id="realisedPnlTdXBTJPY"><span id="realisedPnlSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('realisedPnlSpanXBTJPY').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                                <%--<td>${tempMap['commission']}</td>--%>
                                            <td id="leverageTdXBTJPY"><span id="leverageSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('leverageSpanXBTJPY').innerText = (${tempMap['leverage'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['isOpen']}</td>--%>
                                                <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['openingQty']}</td>--%>
                                                <%--<td>${tempMap['openingCost']}</td>--%>
                                                <%--<td>${tempMap['execQty']}</td>--%>

                                            <td id="execCostTdXBTJPY"><span id="execCostSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('execCostSpanXBTJPY').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="currentCostTdXBTJPY"><span id="currentCostSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('currentCostSpanXBTJPY').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['currentComm']}</td>--%>
                                                <%--<td>${tempMap['realisedCost']}</td>--%>
                                            <td id="unrealisedCostTdXBTJPY"><span id="unrealisedCostSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('unrealisedCostSpanXBTJPY').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8);
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
                                            <td id="posMarginTdXBTJPY"><span id="posMarginSpanXBTJPY"></span></td>
                                            <script>
                                                document.getElementById('posMarginSpanXBTJPY').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
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
                                <c:forEach var="tempMap" items="${randomPositions}">
                                    <c:if test="${tempMap['isOpen'] == 'true' && tempMap['symbol'] == 'ADAZ18'}" >
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>

                                            <td>${sumPositionADAZ18}</td>

                                            <td id="avgEntryPriceTdADAZ18"><span id="avgEntryPriceSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('avgEntryPriceSpanADAZ18').innerText = (${tempMap['avgEntryPrice']}).toFixed(2);
                                            </script>

                                            <td id="markPriceTdADAZ18"><span id="markPriceSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('markPriceSpanADAZ18').innerText = (${tempMap['markPrice']}).toFixed(2);
                                            </script>

                                            <td id="liquidationPriceTdADAZ18"><span id="liquidationPriceSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('liquidationPriceSpanADAZ18').innerText = (${tempMap['liquidationPrice'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="maintMarginTdADAZ18"><span id="maintMarginSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('maintMarginSpanADAZ18').innerText = (${tempMap['maintMargin'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedPnlTdADAZ18"><span id="unrealisedPnlSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedPnlSpanADAZ18').innerText = (${tempMap['unrealisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedRoePcntTdADAZ18"><span id="unrealisedRoePcntSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedRoePcntSpanADAZ18').innerText = (${tempMap['unrealisedRoePcnt'] * 100}).toFixed(2)+'%';
                                            </script>

                                            <td id="realisedPnlTdADAZ18"><span id="realisedPnlSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('realisedPnlSpanADAZ18').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                                <%--<td>${tempMap['commission']}</td>--%>
                                            <td id="leverageTdADAZ18"><span id="leverageSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('leverageSpanADAZ18').innerText = (${tempMap['leverage'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['isOpen']}</td>--%>
                                                <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['openingQty']}</td>--%>
                                                <%--<td>${tempMap['openingCost']}</td>--%>
                                                <%--<td>${tempMap['execQty']}</td>--%>

                                            <td id="execCostTdADAZ18"><span id="execCostSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('execCostSpanADAZ18').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="currentCostTdADAZ18"><span id="currentCostSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('currentCostSpanADAZ18').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['currentComm']}</td>--%>
                                                <%--<td>${tempMap['realisedCost']}</td>--%>
                                            <td id="unrealisedCostTdADAZ18"><span id="unrealisedCostSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedCostSpanADAZ18').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8);
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
                                            <td id="posMarginTdADAZ18"><span id="posMarginSpanADAZ18"></span></td>
                                            <script>
                                                document.getElementById('posMarginSpanADAZ18').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
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
                                <c:forEach var="tempMap" items="${randomPositions}">
                                    <c:if test="${tempMap['isOpen'] == 'true' && tempMap['symbol'] == 'BCHZ18'}" >
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>

                                            <td>${sumPositionBCHZ18}</td>

                                            <td id="avgEntryPriceTdBCHZ18"><span id="avgEntryPriceSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('avgEntryPriceSpanBCHZ18').innerText = (${tempMap['avgEntryPrice']}).toFixed(2);
                                            </script>

                                            <td id="markPriceTdBCHZ18"><span id="markPriceSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('markPriceSpanBCHZ18').innerText = (${tempMap['markPrice']}).toFixed(2);
                                            </script>

                                            <td id="liquidationPriceTdBCHZ18"><span id="liquidationPriceSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('liquidationPriceSpanBCHZ18').innerText = (${tempMap['liquidationPrice'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="maintMarginTdBCHZ18"><span id="maintMarginSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('maintMarginSpanBCHZ18').innerText = (${tempMap['maintMargin'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedPnlTdBCHZ18"><span id="unrealisedPnlSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedPnlSpanBCHZ18').innerText = (${tempMap['unrealisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedRoePcntTdBCHZ18"><span id="unrealisedRoePcntSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedRoePcntSpanBCHZ18').innerText = (${tempMap['unrealisedRoePcnt'] * 100}).toFixed(2)+'%';
                                            </script>

                                            <td id="realisedPnlTdBCHZ18"><span id="realisedPnlSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('realisedPnlSpanBCHZ18').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                                <%--<td>${tempMap['commission']}</td>--%>
                                            <td id="leverageTdBCHZ18"><span id="leverageSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('leverageSpanBCHZ18').innerText = (${tempMap['leverage'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['isOpen']}</td>--%>
                                                <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['openingQty']}</td>--%>
                                                <%--<td>${tempMap['openingCost']}</td>--%>
                                                <%--<td>${tempMap['execQty']}</td>--%>

                                            <td id="execCostTdBCHZ18"><span id="execCostSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('execCostSpanBCHZ18').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="currentCostTdBCHZ18"><span id="currentCostSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('currentCostSpanBCHZ18').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['currentComm']}</td>--%>
                                                <%--<td>${tempMap['realisedCost']}</td>--%>
                                            <td id="unrealisedCostTdBCHZ18"><span id="unrealisedCostSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedCostSpanBCHZ18').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8);
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
                                            <td id="posMarginTdBCHZ18"><span id="posMarginSpanBCHZ18"></span></td>
                                            <script>
                                                document.getElementById('posMarginSpanBCHZ18').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
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
                                <c:forEach var="tempMap" items="${randomPositions}">
                                    <c:if test="${tempMap['isOpen'] == 'true' && tempMap['symbol'] == 'EOSZ18'}" >
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>

                                            <td>${sumPositionEOSZ18}</td>

                                            <td id="avgEntryPriceTdEOSZ18"><span id="avgEntryPriceSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('avgEntryPriceSpanEOSZ18').innerText = (${tempMap['avgEntryPrice']}).toFixed(2);
                                            </script>

                                            <td id="markPriceTdEOSZ18"><span id="markPriceSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('markPriceSpanEOSZ18').innerText = (${tempMap['markPrice']}).toFixed(2);
                                            </script>

                                            <td id="liquidationPriceTdEOSZ18"><span id="liquidationPriceSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('liquidationPriceSpanEOSZ18').innerText = (${tempMap['liquidationPrice'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="maintMarginTdEOSZ18"><span id="maintMarginSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('maintMarginSpanEOSZ18').innerText = (${tempMap['maintMargin'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedPnlTdEOSZ18"><span id="unrealisedPnlSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedPnlSpanEOSZ18').innerText = (${tempMap['unrealisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedRoePcntTdEOSZ18"><span id="unrealisedRoePcntSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedRoePcntSpanEOSZ18').innerText = (${tempMap['unrealisedRoePcnt'] * 100}).toFixed(2)+'%';
                                            </script>

                                            <td id="realisedPnlTdEOSZ18"><span id="realisedPnlSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('realisedPnlSpanEOSZ18').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                                <%--<td>${tempMap['commission']}</td>--%>
                                            <td id="leverageTdEOSZ18"><span id="leverageSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('leverageSpanEOSZ18').innerText = (${tempMap['leverage'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['isOpen']}</td>--%>
                                                <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['openingQty']}</td>--%>
                                                <%--<td>${tempMap['openingCost']}</td>--%>
                                                <%--<td>${tempMap['execQty']}</td>--%>

                                            <td id="execCostTdEOSZ18"><span id="execCostSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('execCostSpanEOSZ18').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="currentCostTdEOSZ18"><span id="currentCostSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('currentCostSpanEOSZ18').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['currentComm']}</td>--%>
                                                <%--<td>${tempMap['realisedCost']}</td>--%>
                                            <td id="unrealisedCostTdEOSZ18"><span id="unrealisedCostSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedCostSpanEOSZ18').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8);
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
                                            <td id="posMarginTdEOSZ18"><span id="posMarginSpanEOSZ18"></span></td>
                                            <script>
                                                document.getElementById('posMarginSpanEOSZ18').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
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
                                <c:forEach var="tempMap" items="${randomPositions}">
                                    <c:if test="${tempMap['isOpen'] == 'true' && tempMap['symbol'] == 'ETHUSD'}" >
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>

                                            <td>${sumPositionETHUSD}</td>

                                            <td id="avgEntryPriceTdETHUSD"><span id="avgEntryPriceSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('avgEntryPriceSpanETHUSD').innerText = (${tempMap['avgEntryPrice']}).toFixed(2);
                                            </script>

                                            <td id="markPriceTdETHUSD"><span id="markPriceSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('markPriceSpanETHUSD').innerText = (${tempMap['markPrice']}).toFixed(2);
                                            </script>

                                            <td id="liquidationPriceTdETHUSD"><span id="liquidationPriceSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('liquidationPriceSpanETHUSD').innerText = (${tempMap['liquidationPrice'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="maintMarginTdETHUSD"><span id="maintMarginSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('maintMarginSpanETHUSD').innerText = (${tempMap['maintMargin'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedPnlTdETHUSD"><span id="unrealisedPnlSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('unrealisedPnlSpanETHUSD').innerText = (${tempMap['unrealisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedRoePcntTdETHUSD"><span id="unrealisedRoePcntSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('unrealisedRoePcntSpanETHUSD').innerText = (${tempMap['unrealisedRoePcnt'] * 100}).toFixed(2)+'%';
                                            </script>

                                            <td id="realisedPnlTdETHUSD"><span id="realisedPnlSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('realisedPnlSpanETHUSD').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                                <%--<td>${tempMap['commission']}</td>--%>
                                            <td id="leverageTdETHUSD"><span id="leverageSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('leverageSpanETHUSD').innerText = (${tempMap['leverage'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['isOpen']}</td>--%>
                                                <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['openingQty']}</td>--%>
                                                <%--<td>${tempMap['openingCost']}</td>--%>
                                                <%--<td>${tempMap['execQty']}</td>--%>

                                            <td id="execCostTdETHUSD"><span id="execCostSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('execCostSpanETHUSD').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="currentCostTdETHUSD"><span id="currentCostSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('currentCostSpanETHUSD').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['currentComm']}</td>--%>
                                                <%--<td>${tempMap['realisedCost']}</td>--%>
                                            <td id="unrealisedCostTdETHUSD"><span id="unrealisedCostSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('unrealisedCostSpanETHUSD').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8);
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
                                            <td id="posMarginTdETHUSD"><span id="posMarginSpanETHUSD"></span></td>
                                            <script>
                                                document.getElementById('posMarginSpanETHUSD').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
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
                                <c:forEach var="tempMap" items="${randomPositions}">
                                    <c:if test="${tempMap['isOpen'] == 'true' && tempMap['symbol'] == 'LTCZ18'}" >
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>

                                            <td>${sumPositionLTCZ18}</td>

                                            <td id="avgEntryPriceTdLTCZ18"><span id="avgEntryPriceSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('avgEntryPriceSpanLTCZ18').innerText = (${tempMap['avgEntryPrice']}).toFixed(2);
                                            </script>

                                            <td id="markPriceTdLTCZ18"><span id="markPriceSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('markPriceSpanLTCZ18').innerText = (${tempMap['markPrice']}).toFixed(2);
                                            </script>

                                            <td id="liquidationPriceTdLTCZ18"><span id="liquidationPriceSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('liquidationPriceSpanLTCZ18').innerText = (${tempMap['liquidationPrice'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="maintMarginTdLTCZ18"><span id="maintMarginSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('maintMarginSpanLTCZ18').innerText = (${tempMap['maintMargin'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedPnlTdLTCZ18"><span id="unrealisedPnlSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedPnlSpanLTCZ18').innerText = (${tempMap['unrealisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedRoePcntTdLTCZ18"><span id="unrealisedRoePcntSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedRoePcntSpanLTCZ18').innerText = (${tempMap['unrealisedRoePcnt'] * 100}).toFixed(2)+'%';
                                            </script>

                                            <td id="realisedPnlTdLTCZ18"><span id="realisedPnlSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('realisedPnlSpanLTCZ18').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                                <%--<td>${tempMap['commission']}</td>--%>
                                            <td id="leverageTdLTCZ18"><span id="leverageSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('leverageSpanLTCZ18').innerText = (${tempMap['leverage'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['isOpen']}</td>--%>
                                                <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['openingQty']}</td>--%>
                                                <%--<td>${tempMap['openingCost']}</td>--%>
                                                <%--<td>${tempMap['execQty']}</td>--%>

                                            <td id="execCostTdLTCZ18"><span id="execCostSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('execCostSpanLTCZ18').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="currentCostTdLTCZ18"><span id="currentCostSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('currentCostSpanLTCZ18').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['currentComm']}</td>--%>
                                                <%--<td>${tempMap['realisedCost']}</td>--%>
                                            <td id="unrealisedCostTdLTCZ18"><span id="unrealisedCostSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedCostSpanLTCZ18').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8);
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
                                            <td id="posMarginTdLTCZ18"><span id="posMarginSpanLTCZ18"></span></td>
                                            <script>
                                                document.getElementById('posMarginSpanLTCZ18').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
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
                                <c:forEach var="tempMap" items="${randomPositions}">
                                    <c:if test="${tempMap['isOpen'] == 'true' && tempMap['symbol'] == 'TRXZ18'}" >
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>

                                            <td>${sumPositionTRXZ18}</td>

                                            <td id="avgEntryPriceTdTRXZ18"><span id="avgEntryPriceSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('avgEntryPriceSpanTRXZ18').innerText = (${tempMap['avgEntryPrice']}).toFixed(2);
                                            </script>

                                            <td id="markPriceTdTRXZ18"><span id="markPriceSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('markPriceSpanTRXZ18').innerText = (${tempMap['markPrice']}).toFixed(2);
                                            </script>

                                            <td id="liquidationPriceTdTRXZ18"><span id="liquidationPriceSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('liquidationPriceSpanTRXZ18').innerText = (${tempMap['liquidationPrice'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="maintMarginTdTRXZ18"><span id="maintMarginSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('maintMarginSpanTRXZ18').innerText = (${tempMap['maintMargin'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedPnlTdTRXZ18"><span id="unrealisedPnlSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedPnlSpanTRXZ18').innerText = (${tempMap['unrealisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedRoePcntTdTRXZ18"><span id="unrealisedRoePcntSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedRoePcntSpanTRXZ18').innerText = (${tempMap['unrealisedRoePcnt'] * 100}).toFixed(2)+'%';
                                            </script>

                                            <td id="realisedPnlTdTRXZ18"><span id="realisedPnlSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('realisedPnlSpanTRXZ18').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                                <%--<td>${tempMap['commission']}</td>--%>
                                            <td id="leverageTdTRXZ18"><span id="leverageSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('leverageSpanTRXZ18').innerText = (${tempMap['leverage'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['isOpen']}</td>--%>
                                                <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['openingQty']}</td>--%>
                                                <%--<td>${tempMap['openingCost']}</td>--%>
                                                <%--<td>${tempMap['execQty']}</td>--%>

                                            <td id="execCostTdTRXZ18"><span id="execCostSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('execCostSpanTRXZ18').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="currentCostTdTRXZ18"><span id="currentCostSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('currentCostSpanTRXZ18').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['currentComm']}</td>--%>
                                                <%--<td>${tempMap['realisedCost']}</td>--%>
                                            <td id="unrealisedCostTdTRXZ18"><span id="unrealisedCostSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedCostSpanTRXZ18').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8);
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
                                            <td id="posMarginTdTRXZ18"><span id="posMarginSpanTRXZ18"></span></td>
                                            <script>
                                                document.getElementById('posMarginSpanTRXZ18').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
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
                                <c:forEach var="tempMap" items="${randomPositions}">
                                    <c:if test="${tempMap['isOpen'] == 'true' && tempMap['symbol'] == 'XRPZ18'}" >
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>

                                            <td>${sumPositionXRPZ18}</td>

                                            <td id="avgEntryPriceTdXRPZ18"><span id="avgEntryPriceSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('avgEntryPriceSpanXRPZ18').innerText = (${tempMap['avgEntryPrice']}).toFixed(2);
                                            </script>

                                            <td id="markPriceTdXRPZ18"><span id="markPriceSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('markPriceSpanXRPZ18').innerText = (${tempMap['markPrice']}).toFixed(2);
                                            </script>

                                            <td id="liquidationPriceTdXRPZ18"><span id="liquidationPriceSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('liquidationPriceSpanXRPZ18').innerText = (${tempMap['liquidationPrice'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="maintMarginTdXRPZ18"><span id="maintMarginSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('maintMarginSpanXRPZ18').innerText = (${tempMap['maintMargin'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedPnlTdXRPZ18"><span id="unrealisedPnlSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedPnlSpanXRPZ18').innerText = (${tempMap['unrealisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="unrealisedRoePcntTdXRPZ18"><span id="unrealisedRoePcntSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedRoePcntSpanXRPZ18').innerText = (${tempMap['unrealisedRoePcnt'] * 100}).toFixed(2)+'%';
                                            </script>

                                            <td id="realisedPnlTdXRPZ18"><span id="realisedPnlSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('realisedPnlSpanXRPZ18').innerText = (${tempMap['realisedPnl'] / 100000000}).toFixed(8);
                                            </script>

                                                <%--<td>${tempMap['commission']}</td>--%>
                                            <td id="leverageTdXRPZ18"><span id="leverageSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('leverageSpanXRPZ18').innerText = (${tempMap['leverage'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['isOpen']}</td>--%>
                                                <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                                <%--<td>${tempMap['openingQty']}</td>--%>
                                                <%--<td>${tempMap['openingCost']}</td>--%>
                                                <%--<td>${tempMap['execQty']}</td>--%>

                                            <td id="execCostTdXRPZ18"><span id="execCostSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('execCostSpanXRPZ18').innerText = (${tempMap['execCost'] / 100000000}).toFixed(8);
                                            </script>

                                            <td id="currentCostTdXRPZ18"><span id="currentCostSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('currentCostSpanXRPZ18').innerText = (${tempMap['currentCost'] / 100000000}).toFixed(8);
                                            </script>
                                                <%--<td>${tempMap['currentComm']}</td>--%>
                                                <%--<td>${tempMap['realisedCost']}</td>--%>
                                            <td id="unrealisedCostTdXRPZ18"><span id="unrealisedCostSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('unrealisedCostSpanXRPZ18').innerText = (${tempMap['unrealisedCost'] / 100000000}).toFixed(8);
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
                                            <td id="posMarginTdXRPZ18"><span id="posMarginSpanXRPZ18"></span></td>
                                            <script>
                                                document.getElementById('posMarginSpanXRPZ18').innerText = (${tempMap['posMargin'] / 100000000}).toFixed(8) ;
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
            exampleSocket.send("{\"op\": \"subscribe\", \"args\": [\"position\"]}");
            // exampleSocket.send("{\"op\": \"subscribe\", \"args\": [\"position:XBTUSD\"]}");
        }
        // console.log(exampleSocket.readyState);
    </script>
    <script>
        exampleSocket.onmessage = function (event) {
            var msg = JSON.parse(event.data);
            //
            // var arrayLength = msg.length;
            // console.log(msg.length);
            console.log(msg.data["0"]);

            if (msg.data["0"] != null) {
                if (msg.data["0"].symbol.toString() === 'XBTUSD') {
                    if (msg.data["0"].markPrice != null && document.getElementById("markPriceSpanXBTUSD") != msg.data["0"].markPrice) {
                        $(function () {
                            $("#markPriceTdXBTUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#markPriceTdXBTUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("markPriceSpanXBTUSD").innerText = msg.data["0"].markPrice;
                    }
                    if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpanXBTUSD") != msg.data["0"].maintMargin) {
                        $(function () {
                            $("#maintMarginTdXBTUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#maintMarginTdXBTUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("maintMarginSpanXBTUSD").innerText = (msg.data["0"].maintMargin / 100000000).toFixed(8);
                    }

                    if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPriceSpanXBTUSD") != msg.data["0"].liquidationPrice) {
                        $(function () {
                            $("#liquidationPriceTdXBTUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#liquidationPriceTdXBTUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("liquidationPriceSpanXBTUSD").innerText = msg.data["0"].liquidationPrice;
                    }

                    if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanXBTUSD").toString() != msg.data["0"].unrealisedRoePcnt.toString()) {
                        $(function () {
                            $("#unrealisedRoePcntTdXBTUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedRoePcntTdXBTUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedRoePcntSpanXBTUSD").innerText = (msg.data["0"].unrealisedRoePcnt * 100).toFixed(2) + '%';
                    }

                    if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanXBTUSD") != msg.data["0"].unrealisedPnl) {
                        $(function () {
                            $("#unrealisedPnlTdXBTUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedPnlTdXBTUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedPnlSpanXBTUSD").innerText = (msg.data["0"].unrealisedPnl / 100000000).toFixed(8);
                    }
                }
                else if (msg.data["0"].symbol.toString() === 'XBTJPY') {
                    if (msg.data["0"].markPrice != null && document.getElementById("markPriceSpanXBTJPY") != msg.data["0"].markPrice) {
                        $(function () {
                            $("#markPriceTdXBTJPY").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#markPriceTdXBTJPY").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("markPriceSpanXBTJPY").innerText = msg.data["0"].markPrice;
                    }
                    if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpanXBTJPY") != msg.data["0"].maintMargin) {
                        $(function () {
                            $("#maintMarginTdXBTJPY").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#maintMarginTdXBTJPY").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("maintMarginSpanXBTJPY").innerText = (msg.data["0"].maintMargin / 100000000).toFixed(8);
                    }

                    if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPriceSpanXBTJPY") != msg.data["0"].liquidationPrice) {
                        $(function () {
                            $("#liquidationPriceTdXBTJPY").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#liquidationPriceTdXBTJPY").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("liquidationPriceSpanXBTJPY").innerText = msg.data["0"].liquidationPrice;
                    }

                    if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanXBTJPY") != msg.data["0"].unrealisedRoePcnt) {
                        $(function () {
                            $("#unrealisedRoePcntTdXBTJPY").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedRoePcntTdXBTJPY").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedRoePcntSpanXBTJPY").innerText = (msg.data["0"].unrealisedRoePcnt * 100).toFixed(2) + '%';
                    }

                    if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanXBTJPY") != msg.data["0"].unrealisedPnl) {
                        $(function () {
                            $("#unrealisedPnlTdXBTJPY").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedPnlTdXBTJPY").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedPnlSpanXBTJPY").innerText = (msg.data["0"].unrealisedPnl / 100000000).toFixed(8);
                    }
                }
                else if (msg.data["0"].symbol.toString() === 'ADAZ18') {
                    if (msg.data["0"].markPrice != null && document.getElementById("markPriceSpanADAZ18") != msg.data["0"].markPrice) {
                        $(function () {
                            $("#markPriceTdADAZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#markPriceTdADAZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("markPriceSpanADAZ18").innerText = msg.data["0"].markPrice;
                    }
                    if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpanADAZ18") != msg.data["0"].maintMargin) {
                        $(function () {
                            $("#maintMarginTdADAZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#maintMarginTdADAZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("maintMarginSpanADAZ18").innerText = (msg.data["0"].maintMargin / 100000000).toFixed(8);
                    }

                    if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPriceSpanADAZ18") != msg.data["0"].liquidationPrice) {
                        $(function () {
                            $("#liquidationPriceTdADAZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#liquidationPriceTdADAZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("liquidationPriceSpanADAZ18").innerText = msg.data["0"].liquidationPrice;
                    }

                    if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanADAZ18") != msg.data["0"].unrealisedRoePcnt) {
                        $(function () {
                            $("#unrealisedRoePcntTdADAZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedRoePcntTdADAZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedRoePcntSpanADAZ18").innerText = (msg.data["0"].unrealisedRoePcnt * 100).toFixed(2) + '%';
                    }

                    if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanADAZ18") != msg.data["0"].unrealisedPnl) {
                        $(function () {
                            $("#unrealisedPnlTdADAZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedPnlTdADAZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedPnlSpanADAZ18").innerText = (msg.data["0"].unrealisedPnl / 100000000).toFixed(8);
                    }
                }
                else if (msg.data["0"].symbol.toString() === 'BCHZ18') {
                    if (msg.data["0"].markPrice != null && document.getElementById("markPriceSpanBCHZ18") != msg.data["0"].markPrice) {
                        $(function () {
                            $("#markPriceTdBCHZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#markPriceTdBCHZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("markPriceSpanBCHZ18").innerText = msg.data["0"].markPrice;
                    }
                    if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpanBCHZ18") != msg.data["0"].maintMargin) {
                        $(function () {
                            $("#maintMarginTdBCHZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#maintMarginTdBCHZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("maintMarginSpanBCHZ18").innerText = (msg.data["0"].maintMargin / 100000000).toFixed(8);
                    }

                    if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPriceSpanBCHZ18") != msg.data["0"].liquidationPrice) {
                        $(function () {
                            $("#liquidationPriceTdBCHZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#liquidationPriceTdBCHZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("liquidationPriceSpanBCHZ18").innerText = msg.data["0"].liquidationPrice;
                    }

                    if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanBCHZ18") != msg.data["0"].unrealisedRoePcnt) {
                        $(function () {
                            $("#unrealisedRoePcntTdBCHZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedRoePcntTdBCHZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedRoePcntSpanBCHZ18").innerText = (msg.data["0"].unrealisedRoePcnt * 100).toFixed(2) + '%';
                    }

                    if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanBCHZ18") != msg.data["0"].unrealisedPnl) {
                        $(function () {
                            $("#unrealisedPnlTdBCHZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedPnlTdBCHZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedPnlSpanBCHZ18").innerText = (msg.data["0"].unrealisedPnl / 100000000).toFixed(8);
                    }
                }
                else if (msg.data["0"].symbol.toString() === 'EOSZ18') {
                    if (msg.data["0"].markPrice != null && document.getElementById("markPriceSpanEOSZ18") != msg.data["0"].markPrice) {
                        $(function () {
                            $("#markPriceTdEOSZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#markPriceTdEOSZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("markPriceSpanEOSZ18").innerText = msg.data["0"].markPrice;
                    }
                    if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpanEOSZ18") != msg.data["0"].maintMargin) {
                        $(function () {
                            $("#maintMarginTdEOSZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#maintMarginTdEOSZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("maintMarginSpanEOSZ18").innerText = (msg.data["0"].maintMargin / 100000000).toFixed(8);
                    }

                    if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPriceSpanEOSZ18") != msg.data["0"].liquidationPrice) {
                        $(function () {
                            $("#liquidationPriceTdEOSZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#liquidationPriceTdEOSZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("liquidationPriceSpanEOSZ18").innerText = msg.data["0"].liquidationPrice;
                    }

                    if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanEOSZ18") != msg.data["0"].unrealisedRoePcnt) {
                        $(function () {
                            $("#unrealisedRoePcntTdEOSZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedRoePcntTdEOSZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedRoePcntSpanEOSZ18").innerText = (msg.data["0"].unrealisedRoePcnt * 100).toFixed(2) + '%';
                    }

                    if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanEOSZ18") != msg.data["0"].unrealisedPnl) {
                        $(function () {
                            $("#unrealisedPnlTdEOSZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedPnlTdEOSZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedPnlSpanEOSZ18").innerText = (msg.data["0"].unrealisedPnl / 100000000).toFixed(8);
                    }
                }
                else if (msg.data["0"].symbol.toString() === 'ETHUSD') {
                    if (msg.data["0"].markPrice != null && document.getElementById("markPriceSpanETHUSD") != msg.data["0"].markPrice) {
                        $(function () {
                            $("#markPriceTdETHUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#markPriceTdETHUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("markPriceSpanETHUSD").innerText = msg.data["0"].markPrice;
                    }
                    if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpanETHUSD") != msg.data["0"].maintMargin) {
                        $(function () {
                            $("#maintMarginTdETHUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#maintMarginTdETHUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("maintMarginSpanETHUSD").innerText = (msg.data["0"].maintMargin / 100000000).toFixed(8);
                    }

                    if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPriceSpanETHUSD") != msg.data["0"].liquidationPrice) {
                        $(function () {
                            $("#liquidationPriceTdETHUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#liquidationPriceTdETHUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("liquidationPriceSpanETHUSD").innerText = msg.data["0"].liquidationPrice;
                    }

                    if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanETHUSD") != msg.data["0"].unrealisedRoePcnt) {
                        $(function () {
                            $("#unrealisedRoePcntTdETHUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedRoePcntTdETHUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedRoePcntSpanETHUSD").innerText = (msg.data["0"].unrealisedRoePcnt * 100).toFixed(2) + '%';
                    }

                    if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanETHUSD") != msg.data["0"].unrealisedPnl) {
                        $(function () {
                            $("#unrealisedPnlTdETHUSD").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedPnlTdETHUSD").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedPnlSpanETHUSD").innerText = (msg.data["0"].unrealisedPnl / 100000000).toFixed(8);
                    }
                }
                else if (msg.data["0"].symbol.toString() === 'LTCZ18') {
                    if (msg.data["0"].markPrice != null && document.getElementById("markPriceSpanLTCZ18") != msg.data["0"].markPrice) {
                        $(function () {
                            $("#markPriceTdLTCZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#markPriceTdLTCZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("markPriceSpanLTCZ18").innerText = msg.data["0"].markPrice;
                    }
                    if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpanLTCZ18") != msg.data["0"].maintMargin) {
                        $(function () {
                            $("#maintMarginTdLTCZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#maintMarginTdLTCZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("maintMarginSpanLTCZ18").innerText = (msg.data["0"].maintMargin / 100000000).toFixed(8);
                    }

                    if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPriceSpanLTCZ18") != msg.data["0"].liquidationPrice) {
                        $(function () {
                            $("#liquidationPriceTdLTCZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#liquidationPriceTdLTCZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("liquidationPriceSpanLTCZ18").innerText = msg.data["0"].liquidationPrice;
                    }

                    if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanLTCZ18") != msg.data["0"].unrealisedRoePcnt) {
                        $(function () {
                            $("#unrealisedRoePcntTdLTCZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedRoePcntTdLTCZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedRoePcntSpanLTCZ18").innerText = (msg.data["0"].unrealisedRoePcnt * 100).toFixed(2) + '%';
                    }

                    if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanLTCZ18") != msg.data["0"].unrealisedPnl) {
                        $(function () {
                            $("#unrealisedPnlTdLTCZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedPnlTdLTCZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedPnlSpanLTCZ18").innerText = (msg.data["0"].unrealisedPnl / 100000000).toFixed(8);
                    }
                }
                else if (msg.data["0"].symbol.toString() === 'TRXZ18') {
                    if (msg.data["0"].markPrice != null && document.getElementById("markPriceSpanTRXZ18") != msg.data["0"].markPrice) {
                        $(function () {
                            $("#markPriceTdTRXZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#markPriceTdTRXZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("markPriceSpanTRXZ18").innerText = msg.data["0"].markPrice;
                    }
                    if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpanTRXZ18") != msg.data["0"].maintMargin) {
                        $(function () {
                            $("#maintMarginTdTRXZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#maintMarginTdTRXZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("maintMarginSpanTRXZ18").innerText = (msg.data["0"].maintMargin / 100000000).toFixed(8);
                    }

                    if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPriceSpanTRXZ18") != msg.data["0"].liquidationPrice) {
                        $(function () {
                            $("#liquidationPriceTdTRXZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#liquidationPriceTdTRXZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("liquidationPriceSpanTRXZ18").innerText = msg.data["0"].liquidationPrice;
                    }

                    if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanTRXZ18") != msg.data["0"].unrealisedRoePcnt) {
                        $(function () {
                            $("#unrealisedRoePcntTdTRXZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedRoePcntTdTRXZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedRoePcntSpanTRXZ18").innerText = (msg.data["0"].unrealisedRoePcnt * 100).toFixed(2) + '%';
                    }

                    if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanTRXZ18") != msg.data["0"].unrealisedPnl) {
                        $(function () {
                            $("#unrealisedPnlTdTRXZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedPnlTdTRXZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedPnlSpanTRXZ18").innerText = (msg.data["0"].unrealisedPnl / 100000000).toFixed(8);
                    }
                }
                else if (msg.data["0"].symbol.toString() === 'XRPZ18') {
                    if (msg.data["0"].markPrice != null && document.getElementById("markPriceSpanXRPZ18") != msg.data["0"].markPrice) {
                        $(function () {
                            $("#markPriceTdXRPZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#markPriceTdXRPZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("markPriceSpanXRPZ18").innerText = msg.data["0"].markPrice;
                    }
                    if (msg.data["0"].maintMargin != null && document.getElementById("maintMarginSpanXRPZ18") != msg.data["0"].maintMargin) {
                        $(function () {
                            $("#maintMarginTdXRPZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#maintMarginTdXRPZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("maintMarginSpanXRPZ18").innerText = (msg.data["0"].maintMargin / 100000000).toFixed(8);
                    }

                    if (msg.data["0"].liquidationPrice != null && document.getElementById("liquidationPriceSpanXRPZ18") != msg.data["0"].liquidationPrice) {
                        $(function () {
                            $("#liquidationPriceTdXRPZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#liquidationPriceTdXRPZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("liquidationPriceSpanXRPZ18").innerText = msg.data["0"].liquidationPrice;
                    }

                    if (msg.data["0"].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanXRPZ18") != msg.data["0"].unrealisedRoePcnt) {
                        $(function () {
                            $("#unrealisedRoePcntTdXRPZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedRoePcntTdXRPZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedRoePcntSpanXRPZ18").innerText = (msg.data["0"].unrealisedRoePcnt * 100).toFixed(2) + '%';
                    }

                    if (msg.data["0"].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanXRPZ18") != msg.data["0"].unrealisedPnl) {
                        $(function () {
                            $("#unrealisedPnlTdXRPZ18").delay(150).animate({
                                "background-color": "#ffeb79"
                            }, 350, function () {
                                $("#unrealisedPnlTdXRPZ18").animate({
                                    "background-color": "#fff"
                                }, 200);
                            });
                        });
                        document.getElementById("unrealisedPnlSpanXRPZ18").innerText = (msg.data["0"].unrealisedPnl / 100000000).toFixed(8);
                    }

                }
            }

            // if (msg.data[1] != null) {
            //
            //     if (msg.data[i].symbol.toString() === 'ETHUSD') {
            //         if (msg.data[i].markPrice != null && document.getElementById("markPriceSpanETHUSD") != msg.data[i].markPrice) {
            //             $(function () {
            //                 $("#markPriceTdETHUSD").delay(150).animate({
            //                     "background-color": "#ffeb79"
            //                 }, 350, function () {
            //                     $("#markPriceTdETHUSD").animate({
            //                         "background-color": "#fff"
            //                     }, 200);
            //                 });
            //             });
            //             document.getElementById("markPriceSpanETHUSD").innerText = msg.data[0].markPrice;
            //         }
            //         if (msg.data[i].maintMargin != null && document.getElementById("maintMarginSpanETHUSD") != msg.data[i].maintMargin) {
            //             $(function () {
            //                 $("#maintMarginTdETHUSD").delay(150).animate({
            //                     "background-color": "#ffeb79"
            //                 }, 350, function () {
            //                     $("#maintMarginTdETHUSD").animate({
            //                         "background-color": "#fff"
            //                     }, 200);
            //                 });
            //             });
            //             document.getElementById("maintMarginSpanETHUSD").innerText = (msg.data[0].maintMargin / 100000000).toFixed(8);
            //         }
            //
            //         if (msg.data[i].liquidationPrice != null && document.getElementById("liquidationPriceSpanETHUSD") != msg.data[i].liquidationPrice) {
            //             $(function () {
            //                 $("#liquidationPriceTdETHUSD").delay(150).animate({
            //                     "background-color": "#ffeb79"
            //                 }, 350, function () {
            //                     $("#liquidationPriceTdETHUSD").animate({
            //                         "background-color": "#fff"
            //                     }, 200);
            //                 });
            //             });
            //             document.getElementById("liquidationPriceSpanETHUSD").innerText = msg.data[0].liquidationPrice;
            //         }
            //
            //         if (msg.data[i].unrealisedRoePcnt != null && document.getElementById("unrealisedRoePcntSpanETHUSD").toString() != msg.data[i].unrealisedRoePcnt.toString()) {
            //             $(function () {
            //                 $("#unrealisedRoePcntTdETHUSD").delay(150).animate({
            //                     "background-color": "#ffeb79"
            //                 }, 350, function () {
            //                     $("#unrealisedRoePcntTdETHUSD").animate({
            //                         "background-color": "#fff"
            //                     }, 200);
            //                 });
            //             });
            //             document.getElementById("unrealisedRoePcntSpanETHUSD").innerText = (msg.data[0].unrealisedRoePcnt * 100).toFixed(2) + '%';
            //         }
            //
            //         if (msg.data[i].unrealisedPnl != null && document.getElementById("unrealisedPnlSpanETHUSD") != msg.data[i].unrealisedPnl) {
            //             $(function () {
            //                 $("#unrealisedPnlTdETHUSD").delay(150).animate({
            //                     "background-color": "#ffeb79"
            //                 }, 350, function () {
            //                     $("#unrealisedPnlTdETHUSD").animate({
            //                         "background-color": "#fff"
            //                     }, 200);
            //                 });
            //             });
            //             document.getElementById("unrealisedPnlSpanETHUSD").innerText = (msg.data[0].unrealisedPnl / 100000000).toFixed(8);
            //         }
            //     }
            // }

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