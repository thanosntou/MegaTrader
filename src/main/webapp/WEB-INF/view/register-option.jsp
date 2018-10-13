<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register-option.css">
        <title>Register</title>
    </head>
    <body>
        
        <nav class="navbar navbar-inverse">
            
        <div class="container">
        <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"></button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">MegaTrader</a>
        </div> 
        </div>
        </nav>
        
<!--        <nav class="navbar navbar-inverse navbar-fixed-top"id="it">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    </button>


                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <li class=" active"><button class="btn btn-outline-success and-all-other-classes" style="margin-top: 9px; margin-right: 100px; margin-left: -100px; background-color: #888; color: #303030;font-weight: 900;"><a href="#myPage" style="background-color:inherit;color: #303030">BioUnion</a></button></li>
                    </ul>

                </div>
            </div>
        </nav>-->
        <div>
            <form:form action="${pageContext.request.contextPath}/register/showRegistrationForm" method="GET" class="form-horizontal" >
            
            <div class="container">
                <h2>You can register as:</h2>
                <ul>
                    <li>
                        <input type="radio" id="customer" name="selector" value="customer">
                        <label for="customer">Customer</label>
                        <div class="check"></div>
                    </li>
                
                    <li>
                    <input type="radio" id="member" name="selector" value="member">
                    <label for="member">Trader</label>
                    <div class="check"><div class="inside"></div></div>
                    </li>
                </ul>
            </div>
            
            <div style="margin-top: 10px; text-align: right;" class="form-group">						
                <div class="col-sm-6 controls">
                <button type="submit" class="btn btn-success">Proceed</button>
		</div>
            </div>
        </form:form>
        </div>
        
    </body>
</html>
