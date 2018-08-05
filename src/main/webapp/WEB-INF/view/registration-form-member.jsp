<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
      <title>Bio-Union</title>
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!--       -->
      
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register-form-member.css">
        <style>
            .error,#passFail{color: red;}
        </style>
    </head> 

    <body>
        <%@include file="myNavbar.jsp" %>
        
        <div class="card h-100">
            <div class="card-header w-100">
                Sign Up as a Member
            </div>
            <div class="card-body">
                <div class="row">
                    
                        <form:form action="${pageContext.request.contextPath}/register/memberRegistrationForm" modelAttribute="member" class="form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-15">
                                    <div>
                                        <!-- Check for registration error -->
                                        <c:if test="${registrationError != null}">
                                            <div class="alert alert-danger col-xs-offset-1 col-xs-10">${registrationError}</div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- User id -->        
                            <form:hidden path="id" />
                                    
                            <!-- User name -->
                            <form:label path="username">Username *</form:label><form:errors path="username" cssClass="error" />
                            <div style="margin-bottom: 25px" class="input-group">
				            <span class="input-group-addon"><i class="fas fa-address-card"></i></span>
                                <form:input path="username" placeholder="username" class="form-control" />
                            </div>

                            <!-- Password -->
                            Password * <span id="passFail">${lathos}</span>
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
                                <input type="password" name="pass" placeholder="password" class="form-control" />    
                            </div>
                                        
                            <!-- Password Confirmation-->
                            Password Confirmation*
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"></span> 
                                <input type="password" name="confirmPass" placeholder="confirm password" class="form-control" />
                            </div>
                                                
                            <!-- Email -->
                            <form:label path="email">Email *</form:label><form:errors path="email" cssClass="error" />
                            <div style="margin-bottom: 25px" class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon1">@</span>
                                </div>
                                <form:input path="email" placeholder="email" class="form-control" />      
                            </div>
                                                
                            <!-- first name -->
                            <form:label path="firstName">First name *</form:label><form:errors path="firstName" cssClass="error" />
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-pencil "></i></span> 
                                <form:input path="firstName" placeholder="first name" class="form-control" />     
                            </div> 
        
                            <!-- last name -->
                            <form:label path="lastName">Last name *</form:label><form:errors path="lastName" cssClass="error" />
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-pencil "></i></span> 
                                <form:input path="lastName" placeholder="last name" class="form-control" />     
                            </div>
                                        
                                                
                            <!-- hidden ssn -->
                            <form:label path="ssn">Ssn *</form:label><form:errors path="ssn" cssClass="error" />
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-pencil "></i></span> 
                                <form:input path="ssn" placeholder="ssn" class="form-control" />
                                        
                            </div>

                                    <!-- Register Button -->
                            <div style="margin-top: 10px" class="form-group">						
                                <div class="col-sm-6 controls">
                                    <button type="submit" class="btn btn-primary">Register</button>
                                </div>
                            </div>   
                            <!-- hidden register option -->
                            <input type="hidden" value="member" name="option"/>
                        </form:form>         
                    </div>	
                </div>
            </div>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>

