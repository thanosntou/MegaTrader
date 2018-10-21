<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/trade-panel.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <title>Trade Panel</title>
</head>
<body>
    <div id="all" class="container-fluid">
        <div style="min-height: 100vh;" class="row">

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

                        <div class="row">
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
                        <br>
                        <br>
                        <%--signal line form--%>
                        <form:form action="${pageContext.request.contextPath}/trade/signal" method="POST" oninput="x.value=parseInt(a.value)">
                            <div class="row">
                                <div class="col-sm-2">
                                    <div class="row">
                                        Contract
                                    </div>
                                    <div class="row">
                                        <input type="hidden" name="symbol" value="${symbol}" >
                                        <input type="text" value="${symbol}" disabled >
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="row">
                                        Side
                                    </div>
                                    <div class="row">
                                        <select name="side">
                                            <option value="Buy">Long</option>
                                            <option value="Sell">Short</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="row">
                                        Leverage
                                    </div>
                                    <div class="row">
                                        <input type="range" id="a" name="leverage" value="0" min="0" max="${maxLeverage}"> <output name="x" for="a"></output>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="row">
                                        Stop Loss
                                    </div>
                                    <div class="row">
                                        <input type="text" name="stopLoss" >
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="row">
                                        Profit Trigger
                                    </div>
                                    <div class="row">
                                        <input type="text" name="profitTrigger" >
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <br>
                                    <div class="row">
                                        <input type="submit" value="Create Signal" ><br>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                        <br>
                        <br>

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
                                            <c:if test="${activeOrders.size() > 1}">
                                                <form:form action="${pageContext.request.contextPath}/trade/order/cancelAll" method="POST">
                                                    <%--<input type="hidden" value="${tempMap['orderID']}" name="orderID"/>--%>
                                                    <input type="submit" class="btn btn-danger" value="Cancel All"/>
                                                </form:form>
                                            </c:if>

                                        </th>
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
                                        <td>
                                            <form:form action="${pageContext.request.contextPath}/trade/order/cancel" method="POST">
                                                <input type="hidden" value="${tempMap['orderID']}" name="orderID"/>
                                                <input type="submit" class="btn btn-danger" value="Cancel"/>
                                            </form:form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <br>
                        <div class="row">
                            <h3>-- Open Positions --</h3>
                        </div>
                        <div class="row">
                            <table class="table table-hover table-sm">
                                <thead class="thead bg-info">
                                <tr>
                                    <th scope="col">Symbol</th>
                                    <th scope="col">Size</th>
                                    <th scope="col">Entry Price</th>
                                    <th scope="col">Mark Price</th>
                                    <th scope="col">Liq. Price</th>
                                    <th scope="col">Margin</th>
                                    <th scope="col">Unrealised Pnl</th>
                                    <th scope="col">Roe %</th>
                                    <th scope="col">Realised Pnl</th>
                                    <%--<th scope="col">commission</th>--%>
                                    <th scope="col">leverage</th>
                                    <th scope="col">isOpen</th>
                                    <%--<th scope="col">rebalancedPnl</th>--%>
                                    <%--<th scope="col">prevRealisedPnl</th>--%>
                                    <%--<th scope="col">prevUnrealisedPnl</th>--%>
                                    <%--<th scope="col">openingQty</th>--%>
                                    <%--<th scope="col">openingCost</th>--%>
                                    <%--<th scope="col">execQty</th>--%>
                                    <th scope="col">execCost</th>
                                    <th scope="col">currentCost</th>
                                    <%--<th scope="col">currentComm</th>--%>
                                    <%--<th scope="col">realisedCost</th>--%>
                                    <th scope="col">unrealisedCost</th>
                                    <%--<th scope="col">grossOpenCost</th>--%>
                                    <%--<th scope="col">grossOpenPremium</th>--%>
                                    <%--<th scope="col">grossExecCost</th>--%>
                                    <th scope="col">posMargin</th>
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
                                    <th scope="col">
                                        <%--<c:if test="${activeOrders.size() > 1}">--%>
                                            <%--<form:form action="${pageContext.request.contextPath}/trade/order/cancelAll" method="POST">--%>
                                                <%--&lt;%&ndash;<input type="hidden" value="${tempMap['orderID']}" name="orderID"/>&ndash;%&gt;--%>
                                                <%--<input type="submit" class="btn btn-danger" value="Cancel All"/>--%>
                                            <%--</form:form>--%>
                                        <%--</c:if>--%>
                                    </th>
                                    <th scope="col">
                                        <%--<c:if test="${activeOrders.size() > 1}">--%>
                                            <%--<form:form action="${pageContext.request.contextPath}/trade/order/cancelAll" method="POST">--%>
                                                <%--&lt;%&ndash;<input type="hidden" value="${tempMap['orderID']}" name="orderID"/>&ndash;%&gt;--%>
                                                <%--<input type="submit" class="btn btn-danger" value="Cancel All"/>--%>
                                            <%--</form:form>--%>
                                        <%--</c:if>--%>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="tempMap" items="${positions}">
                                    <c:if test="${tempMap['isOpen'] == 'true'}" >
                                    <tr>
                                        <th scope="row">${tempMap['symbol']}</th>
                                        <td>${tempMap['currentQty']}</td>
                                        <td>${tempMap['avgEntryPrice']}</td>
                                        <td>${tempMap['markPrice']}</td>
                                        <td>${tempMap['liquidationPrice']}</td>
                                        <td>${tempMap['maintMargin']}</td>
                                        <td>${tempMap['unrealisedPnl']}</td>
                                        <td>${tempMap['unrealisedRoePcnt']}</td>
                                        <td>${tempMap['realisedPnl']}</td>
                                            <%--<td>${tempMap['commission']}</td>--%>
                                        <td>${tempMap['leverage']}</td>
                                        <td>${tempMap['isOpen']}</td>
                                            <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                                            <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                                            <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                                            <%--<td>${tempMap['openingQty']}</td>--%>
                                            <%--<td>${tempMap['openingCost']}</td>--%>
                                            <%--<td>${tempMap['execQty']}</td>--%>
                                        <td>${tempMap['execCost']}</td>
                                        <td>${tempMap['currentCost']}</td>
                                            <%--<td>${tempMap['currentComm']}</td>--%>
                                            <%--<td>${tempMap['realisedCost']}</td>--%>
                                        <td>${tempMap['unrealisedCost']}</td>
                                            <%--<td>${tempMap['grossOpenCost']}</td>--%>
                                            <%--<td>${tempMap['grossOpenPremium']}</td>--%>
                                            <%--<td>${tempMap['grossExecCost']}</td>--%>
                                        <td>${tempMap['posMargin']}</td>
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
                                        <td>
                                            <form:form action="${pageContext.request.contextPath}/trade/position/close" method="POST">
                                                <input type="hidden" name="symbol" value="${symbol}"/>
                                                <input type="number" step="0.5" name="limitPrice"/>
                                                <input type="submit" class="btn btn-danger" value="Close"/>
                                            </form:form>

                                        </td>
                                        <td>
                                            <form:form action="${pageContext.request.contextPath}/trade/position/market" method="POST">
                                                <input type="hidden" name="symbol" value="${symbol}"/>
                                                <input type="submit" class="btn btn-danger" value="Market"/>
                                            </form:form>
                                        </td>
                                    </tr>
                                    </c:if>

                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="row">

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