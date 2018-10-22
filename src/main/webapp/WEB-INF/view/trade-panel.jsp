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

                    <%------------------------------------------ Stop Market TAB ------------------------------------------------------------------------------- --%>
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
            <br>

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
