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
    <%--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">--%>
    <title>Trade Panel</title>
</head>
<body>
    <div id="all" class="container-fluid" style="width: 100%">
        <%--main content--%>
        <div style="height: 100vh;" class="row">

            <%@ include file="side-bar.jsp" %>

            <%----------------------------------------------------------------------main bar--------------------------------------------------------------------------%>
            <div class="col-sm-10">
                <br><br>
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
                <br><br><br>

                <%-- main bar --%>
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
                </ul>

                <div class="tab-content" id="pills-tabContent">

                <%--------------------------------------------------------------- LIMIT TAB ------------------------------------------------------------------------------- --%>
                <div class="tab-pane fade show active" id="pills-limit" role="tabpanel" aria-labelledby="pills-limit-tab">
                    <form:form action="${pageContext.request.contextPath}/trade/order" method="POST" id="limit-form" oninput="x.value=parseInt(a.value)">
                        <div class="row">
                            <div class="col-sm-1">

                            </div>
                            <div class="col-sm-1">
                                <input type="text" name="ordType" value="Limit" hidden /><br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-1">
                                Client
                            </div>
                            <div class="col-sm-1">
                                <select name="client">
                                    <option value="testnet">Testnet</option>
                                    <option value="bitmex">Bitmex</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Contract
                            </div>
                            <div class="col-sm-1">
                                <input type="text" name="symbol" value="${symbol}" disabled/>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Quantity
                            </div>
                            <div class="col-sm-1">
                                <input type="number" name="orderQty"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-1">
                                Limit Price
                            </div>
                            <div class="col-sm-1">
                                <input type="number" step="${priceStep}" name="price"/>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Side
                            </div>
                            <div class="col-sm-1">
                                <select name="side">
                                    <option value="Buy">Buy</option>
                                    <option value="Sell">Sell</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Leverage
                            </div>
                            <div class="col-sm-2">
                                <input type="range" id="a" name="leverage" value="0" min="0" max="${maxLeverage}"> <output name="x" for="a"></output>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">

                            </div>
                            <div class="col-sm-1">
                                <input type="submit" value="Place Order" /><br>
                            </div>
                        </div>
                        <input type="text" name="symbol" value="${symbol}" hidden />
                    </form:form>

                </div>

                <%----------------------------------------------- MARKET TAB ------------------------------------------------------------------------------- --%>
                <div class="tab-pane fade" id="pills-market" role="tabpanel" aria-labelledby="pills-market-tab">
                    <form:form action="${pageContext.request.contextPath}/trade/order" method="POST" id="market-form" oninput="x.value=parseInt(a2.value)">
                        <div class="row">
                            <div class="col-sm-1">

                            </div>
                            <div class="col-sm-1">
                                <input type="text" name="ordType" value="Market" hidden /><br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-1">
                                Client
                            </div>
                            <div class="col-sm-1">
                                <select name="client">
                                    <option value="testnet">Testnet</option>
                                    <option value="bitmex">Bitmex</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Contract
                            </div>
                            <div class="col-sm-1">
                                <input type="text" name="symbol" value="${symbol}" disabled/>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Quantity
                            </div>
                            <div class="col-sm-1">
                                <input type="number" name="orderQty"/>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Side
                            </div>
                            <div class="col-sm-1">
                                <select name="side">
                                    <option value="Buy">Buy</option>
                                    <option value="Sell">Sell</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Leverage
                            </div>
                            <div class="col-sm-2">
                                <input type="range" id="a2" name="leverage" value="0" min="0" max="${maxLeverage}"> <output name="x" for="a"></output>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">

                            </div>
                            <div class="col-sm-1">
                                <input type="submit" value="Place Order" /><br>
                            </div>
                        </div>
                        <input type="text" name="symbol" value="${symbol}" hidden />
                    </form:form>
                </div>

                    <%------------------------------------------ LIMIT TAB ------------------------------------------------------------------------------- --%>
                    <div class="tab-pane fade" id="pills-stopMarket" role="tabpanel" aria-labelledby="pills-stopMarket-tab">
                    <form:form action="${pageContext.request.contextPath}/trade/order" method="POST" id="stop-form" oninput="x.value=parseInt(a3.value)">
                        <div class="row">
                            <div class="col-sm-1">

                            </div>
                            <div class="col-sm-1">
                                <input type="text" name="ordType" value="Stop" hidden /><br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-1">
                                Client
                            </div>
                            <div class="col-sm-1">
                                <select name="client">
                                    <option value="testnet">Testnet</option>
                                    <option value="bitmex">Bitmex</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Contract
                            </div>
                            <div class="col-sm-1">
                                <input type="text" name="symbol" value="${symbol}" disabled/>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Quantity
                            </div>
                            <div class="col-sm-1">
                                <input type="number" name="orderQty"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-1">
                                Stop Price
                            </div>
                            <div class="col-sm-1">
                                <input type="number" step="${priceStep}" name="stopPx"/>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Side
                            </div>
                            <div class="col-sm-1">
                                <select name="side">
                                    <option value="Buy">Buy</option>
                                    <option value="Sell">Sell</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">
                                Leverage
                            </div>
                            <div class="col-sm-2">
                                <input type="range" id="a3" name="leverage" value="0" min="0" max="${maxLeverage}"> <output name="x" for="a"></output>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-1">

                            </div>
                            <div class="col-sm-1">
                                <input type="submit" value="Place Order" /><br>
                            </div>
                        </div>
                        <input type="text" name="symbol" value="${symbol}" hidden />
                        <input type="text" name="execInst" value="Close,LastPrice" hidden /><br><br>
                    </form:form>
                </div>
            </div>
            <br>
            <div>
                <h4>-- Active Orders --</h4>
                <table class="table table-hover table-sm">
                    <tbody>
                    <c:forEach var="tempMap" items="${openOrders}">
                        <%--<c:if test="${tempMap['ordStatus'] == 'New'}" >--%>
                        <tr>
                            <th scope="row">${tempMap['symbol']}</th>
                            <td>${tempMap['ordStatus']}</td>
                            <td>${tempMap['side']}</td>
                            <td>${tempMap['ordType']}</td>
                            <td>${tempMap['orderQty']}</td>
                            <td>${tempMap['price']}</td>
                            <td>${tempMap['stopPx']}</td>
                            <td>${tempMap['transactTime']}</td>
                        </tr>
                        <%--</c:if>--%>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <br>
            <div>
                <h4>-- Positions --</h4>
                <table class="table table-hover table-sm">
                    <thead class="thead bg-info">
                    <tr>
                        <th scope="col">symbol</th>
                        <%--<th scope="col">commission</th>--%>
                        <th scope="col">leverage</th>
                        <th scope="col">isOpen</th>
                        <%--<th scope="col">rebalancedPnl</th>--%>
                        <%--<th scope="col">prevRealisedPnl</th>--%>
                        <%--<th scope="col">prevUnrealisedPnl</th>--%>
                        <%--<th scope="col">openingQty</th>--%>
                        <%--<th scope="col">openingCost</th>--%>
                        <th scope="col">unrealisedPnl</th>
                        <%--<th scope="col">execQty</th>--%>
                        <th scope="col">execCost</th>
                        <th scope="col">currentQty</th>
                        <th scope="col">currentCost</th>
                        <%--<th scope="col">currentComm</th>--%>
                        <%--<th scope="col">realisedCost</th>--%>
                        <th scope="col">unrealisedCost</th>
                        <%--<th scope="col">grossOpenCost</th>--%>
                        <%--<th scope="col">grossOpenPremium</th>--%>
                        <%--<th scope="col">grossExecCost</th>--%>
                        <th scope="col">markPrice</th>
                        <th scope="col">posMargin</th>
                        <th scope="col">maintMargin</th>
                        <th scope="col">realisedPnl</th>
                        <th scope="col">unrealisedGrossPnl</th>
                        <%--<th scope="col">unrealisedTax</th>--%>

                        <%--<th scope="col">unrealisedPnlPcnt</th>--%>
                        <th scope="col">unrealisedRoePcnt</th>
                        <%--<th scope="col">simpleQty</th>--%>
                        <%--<th scope="col">simpleCost</th>--%>
                        <%--<th scope="col">avgCostPrice</th>--%>
                        <th scope="col">avgEntryPrice</th>
                        <%--<th scope="col">breakEvenPrice</th>--%>
                        <%--<th scope="col">marginCallPrice</th>--%>
                        <th scope="col">liquidationPrice</th>
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
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="tempMap" items="${positions}">
                        <%--<c:if test="${tempMap['ordStatus'] == 'New'}" >--%>
                        <tr>
                            <th scope="row">${tempMap['symbol']}</th>
                            <%--<td>${tempMap['commission']}</td>--%>
                            <td>${tempMap['leverage']}</td>
                            <td>${tempMap['isOpen']}</td>
                            <%--<td>${tempMap['rebalancedPnl']}</td>--%>
                            <%--<td>${tempMap['prevRealisedPnl']}</td>--%>
                            <%--<td>${tempMap['prevUnrealisedPnl']}</td>--%>
                            <%--<td>${tempMap['openingQty']}</td>--%>
                            <%--<td>${tempMap['openingCost']}</td>--%>
                            <td>${tempMap['unrealisedPnl']}</td>
                            <%--<td>${tempMap['execQty']}</td>--%>
                            <td>${tempMap['execCost']}</td>
                            <td>${tempMap['currentQty']}</td>
                            <td>${tempMap['currentCost']}</td>
                            <%--<td>${tempMap['currentComm']}</td>--%>
                            <%--<td>${tempMap['realisedCost']}</td>--%>
                            <td>${tempMap['unrealisedCost']}</td>
                            <%--<td>${tempMap['grossOpenCost']}</td>--%>
                            <%--<td>${tempMap['grossOpenPremium']}</td>--%>
                            <%--<td>${tempMap['grossExecCost']}</td>--%>
                            <td>${tempMap['markPrice']}</td>
                            <td>${tempMap['posMargin']}</td>
                            <td>${tempMap['maintMargin']}</td>
                            <td>${tempMap['realisedPnl']}</td>
                            <td>${tempMap['unrealisedGrossPnl']}</td>
                            <%--<td>${tempMap['unrealisedTax']}</td>--%>

                            <%--<td>${tempMap['unrealisedPnlPcnt']}</td>--%>
                            <td>${tempMap['unrealisedRoePcnt']}</td>
                            <%--<td>${tempMap['simpleQty']}</td>--%>
                            <%--<td>${tempMap['simpleCost']}</td>--%>
                            <%--<td>${tempMap['avgCostPrice']}</td>--%>
                            <td>${tempMap['avgEntryPrice']}</td>
                            <%--<td>${tempMap['breakEvenPrice']}</td>--%>
                            <%--<td>${tempMap['marginCallPrice']}</td>--%>
                            <td>${tempMap['liquidationPrice']}</td>
                            <%--<td>${tempMap['timestamp']}</td>--%>
                            <%--<td>${tempMap['lastPrice']}</td>--%>
                            <%--<td>${tempMap['lastValue']}</td>--%>
                        </tr>
                        <%--</c:if>--%>
                        
                    </c:forEach>
                    </tbody>
                </table>
            </div><br>

            </div>

        </div>
    </div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
    (function () {
        function checkTime(i) {
            return (i < 10) ? "0" + i : i;
        }

        function startTime() {
            var today = new Date(),
                h = checkTime(today.getHours()),
                m = checkTime(today.getMinutes()),
                s = checkTime(today.getSeconds());
            document.getElementById('time').innerHTML = h + ":" + m + ":" + s;
            t = setTimeout(function () {
                startTime()
            }, 500);
        }
        startTime();
    })();
</script>
</body>
</html>
