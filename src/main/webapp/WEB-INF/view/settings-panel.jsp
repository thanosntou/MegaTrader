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

        <%@ include file="side-bar.jsp" %>

        <%-- main bar --%>
        <div class="col-sm-10" style="background-color: #bac9d6">
            <div class="card" style="height: 100%; margin-top: 12px">
                <div class="card-header" style="background-color: #eeeeee">
                    <span style="">Bitcoin Syndicate</span>
                </div>

                <div class="card-body">
                    <div class="row" style="margin: 20px 0px">
                        <h2>Settings <a href="${pageContext.request.contextPath}/user/settings"><i class="fas fa-sync" class="button"></i></a></h2>
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