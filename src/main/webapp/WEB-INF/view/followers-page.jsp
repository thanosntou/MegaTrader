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
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <title>Followers</title>
</head>
<body>
<div id="all" class="container-fluid">
    <div style="min-height: 100vh;" class="row">

        <%@ include file="side-bar.jsp" %>

            <div class="col-sm-10" style="background-color: #bac9d6">
                <div class="card" style="height: 100%; margin-top: 12px">
                    <div class="card-header" style="background-color: #eeeeee">
                        <span style="">Bitcoin Syndicate</span>
                    </div>
                    <div class="card-body">
                        <div class="row" style="margin: 20px 0px">
                            <h2>Followers <a href="${pageContext.request.contextPath}/user/news"><i class="fas fa-sync" class="button"></i></a></h2>
                        </div>
                        <br>
                        <div class="row">
                            <table class="table table-hover table-sm">
                                <thead class="thead bg-info">
                                    <tr>
                                        <th scope="col">User</th>
                                        <th scope="col">Status</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="temp" items="${followers}">
                                        <tr>
                                            <th scope="row"><a href="${pageContext.request.contextPath}/user/tx?follower=${temp.username}">${temp.username}</a></th>
                                            <td>

                                                    <%--<input type="hidden" name="symbol" value="XBTUSD"/>--%>


                                                    <c:if test="${temp.enabled}">
                                                        <span><i class="far fa-check-circle"></i></span>
                                                    </c:if>
                                                    <c:if test="${not temp.enabled}">
                                                        <span><i class="fas fa-minus"></i></span>
                                                    </c:if>
                                            </td>

                                            <c:if test="${temp.enabled}">
                                                <td>
                                                    <form:form action="${pageContext.request.contextPath}/user/disable" method="POST">
                                                        <input id="follower" type="hidden" name="follower" value="${temp.username}"/>

                                                        <input id="save-button" type="submit" class="btn btn-danger" value="Disable"/>
                                                    </form:form>
                                                </td>
                                            </c:if>

                                            <c:if test="${not temp.enabled}">
                                                <td>
                                                    <form:form action="${pageContext.request.contextPath}/user/enable" method="POST">
                                                        <input id="follower" type="hidden" name="follower" value="${temp.username}"/>

                                                        <input id="save-button" type="submit" class="btn btn-success" value="Enable"/>
                                                    </form:form>
                                                </td>

                                            </c:if>

                                        </tr>
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
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
</body>
</html>