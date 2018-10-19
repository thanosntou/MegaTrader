<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/copy.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <title>Settings</title>
</head>
<body>
<div id="all" class="container-fluid">
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news/" role="tab" aria-controls="v-pills-news" aria-selected="false"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard/" role="button" aria-controls="v-pills-trades" aria-selected="false"><i class="fas fa-exchange-alt"></i> Dashboard</a>                        <a class="nav-link" id="v-pills-copy-tab" href="${pageContext.request.contextPath}/copy" role="button" aria-controls="v-pills-copy" aria-selected="false"><i class="fas fa-chart-line"></i> Copy Trader</a>
                        <a class="nav-link" href="#v-pills-th" role="tab" aria-controls="v-pills-th" aria-selected="false"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link" href="#v-pills-wallet" role="tab" aria-controls="v-pills-wallet" aria-selected="false"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link active" href="${pageContext.request.contextPath}/user/settings" role="tab" aria-controls="v-pills-settings" aria-selected="true"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/trade/" role="button" aria-controls="v-pills-trades" aria-selected="false"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/" role="button" aria-controls="v-pills-admin" aria-selected="false"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                        </security:authorize>
                    </div>
                </div>
            </div>
        </div>


        <%-- main bar --%>
        <div class="col-sm-10" style="background-color: #bac9d6">
            <div class="card" style="height: 100%; margin-top: 12px">
                <div class="card-header" style="background-color: #eeeeee">
                    <span style="">Bitcoin Syndicate</span>
                </div>

                <div class="card-body">
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
                                ${user.apiKey}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-1">
                                API Secret:
                            </div>
                            <div class="col-sm-11">
                                ${user.apiSecret}
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
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>