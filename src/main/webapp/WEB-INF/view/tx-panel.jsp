<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
                        <h2>Transaction History <a href="${pageContext.request.contextPath}/user/tx"></a></h2>
                    </div>
                    <div class="row">
                        <h3>Bitmexcallbot Orders <c:if test="${not empty userTX}"> - <span style="background-color: yellow"> ${userTX}</span></c:if></h3>
                    </div>
                    <div class="row">
                        <table class="table table-hover table-sm">
                            <thead class="thead bg-info">
                                <tr>
                                    <th scope="col">Symbol</th>
                                    <th scope="col">Side</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Stop Price</th>
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
                                    <c:if test="${fn:contains(tempMap['text'], 'Syndicate')}">
                                        <tr>
                                            <th scope="row">${tempMap['symbol']}</th>
                                            <td>${tempMap['side']}</td>
                                            <td>${tempMap['price']}</td>
                                            <td>${tempMap['stopPx']}</td>
                                            <td>${tempMap['ordType']}</td>
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
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>