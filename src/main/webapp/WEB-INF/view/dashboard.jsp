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
                                    <div class="card" style="width: 25rem;">
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">Balance: ${user.wallet.balance / 100000000} BTC</li>
                                            <li class="list-group-item">Earned: ${earned / 100000000} BTC</li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="card" style="width: 25rem;">
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">Bitmex Balance: ${walletBalance / 100000000} BTC</li>
                                            <li class="list-group-item">Active Balance for Orders: ${availableMargin / 100000000} BTC</li>
                                            <li class="list-group-item">Active on orders: ${activeBalance} (${activeBalance / 100000000} BTC)</li>

                                        </ul>
                                    </div>
                                </div>

                            </div>
                            <br>
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
                                            <%--<th scope="col">Email</th>--%>
                                            <%--<th scope="col">Order Status</th>--%>
                                            <%--<th scope="col">Order Type</th>--%>
                                            <%--<th scope="col">Order Qty</th>--%>
                                            <%--<th scope="col">Price</th>--%>
                                            <%--<th scope="col">Stop Price</th>--%>
                                            <%--<th scope="col">Transaction Time</th>--%>
                                            <%--<th scope="col">--%>
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
                                            <%--<td>${temp.email}</td>--%>
                                            <%--<td>${temp.}</td>--%>
                                            <%--<td>${temp['ordType']}</td>--%>
                                            <%--<td>${temp['orderQty']}</td>--%>
                                            <%--<td>${temp['price']}</td>--%>
                                            <%--<td>${temp['stopPx']}</td>--%>
                                            <%--<td>${temp['transactTime']}</td>--%>
                                        </tr>
                                        <%--</c:if>--%>

                                    </c:forEach>
                                    </tbody>
                                </table>
                            <br>
                            <br>
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
