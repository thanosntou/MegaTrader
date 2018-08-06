<%--
  Created by IntelliJ IDEA.
  User: Athan
  Date: 8/6/2018
  Time: 7:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/management-panel.css">--%>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <title>Email Form</title>
</head>
<body>
    <div class="container">
        <div class="row">

            <form:form action="${pageContext.request.contextPath}/email/send" method="POST" class="form-horizontal" >

                <div class="container">
                    <h2>Message</h2>
                    <input type="text" id="message" name="message" value=""/>
                </div>

                <div style="margin-top: 10px; text-align: right;" class="form-group">
                    <div class="col-sm-6 controls">
                        <button type="submit" class="btn btn-success">Proceed</button>
                    </div>
                </div>
            </form:form>

            <%--<form action="${pageContext.request.contextPath}/email/send" method="POST" >--%>
                <%--<div class="form-group">--%>
                    <%--<label for="form1">Message To All</label>--%>
                    <%--<input type="text" class="form-control" id="form1" rows="6" />--%>
                <%--</div>--%>
                <%--<br>--%>
                <%--<div class="col-sm-6 controls">--%>
                    <%--<input type="submit" class="btn btn-primary">Register</input>--%>
                <%--</div>--%>
            <%--</form>--%>
        </div>


    </div>









</body>
</html>
