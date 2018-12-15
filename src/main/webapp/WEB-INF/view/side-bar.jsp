<html>
<head>
</head>
<body>
<%--------------------------------------------------------------------// side bar---------------------------------------------------------------------------------%>
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
            <c:choose>
                <c:when test = "${page == 'news'}">
                    <div class="nav flex-column nav-pills nav-fill" role="tablist" aria-orientation="vertical" >
                        <a class="nav-link active" href="${pageContext.request.contextPath}/user/news" role="button"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard/" role="button" aria-selected="true"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/copy/" role="button"><i class="fas fa-chart-line"></i> Follow a Trader</a>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/followers" role="button"><i class="fas fa-chart-line"></i> Followers</a>
                        </security:authorize>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/tx" role="button"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/wallet" role="button"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/settings" role="button"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/trade/" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                        </security:authorize>
                    </div>
                </c:when>
                <c:when test = "${page == 'dashboard'}">
                    <div class="nav flex-column nav-pills nav-fill" role="tablist" aria-orientation="vertical" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news" role="button"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link active" href="${pageContext.request.contextPath}/dashboard" role="button"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/copy" role="button"><i class="fas fa-chart-line"></i> Follow a Trader</a>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/followers" role="button"><i class="fas fa-chart-line"></i> Followers</a>
                        </security:authorize>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/tx" role="button"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/wallet" role="button"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/settings" role="button"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/trade" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                        </security:authorize>
                    </div>
                </c:when>
                <c:when test = "${page == 'copy'}">
                    <div class="nav flex-column nav-pills nav-fill" role="tablist" aria-orientation="vertical" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news" role="button"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard" role="button" aria-selected="true"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/copy" role="button"><i class="fas fa-chart-line"></i> Follow a Trader</a>
                        </security:authorize>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/tx" role="button"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/wallet" role="button"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/settings" role="button"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/trade" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                        </security:authorize>
                    </div>
                </c:when>
                <c:when test = "${page == 'followers'}">
                    <div class="nav flex-column nav-pills nav-fill" role="tablist" aria-orientation="vertical" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news" role="button"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard" role="button" aria-selected="true"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/copy" role="button"><i class="fas fa-chart-line"></i> Follow a Trader</a>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/user/followers" role="button"><i class="fas fa-chart-line"></i> Followers</a>
                        </security:authorize>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/tx" role="button"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/wallet" role="button"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/settings" role="button"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/trade" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                        </security:authorize>
                    </div>
                </c:when>
                <c:when test = "${page == 'tx'}">
                    <div class="nav flex-column nav-pills nav-fill" role="tablist" aria-orientation="vertical" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news" role="button"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard" role="button" aria-selected="true"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/copy" role="button"><i class="fas fa-chart-line"></i> Follow a Trader</a>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/followers" role="button"><i class="fas fa-chart-line"></i> Followers</a>
                        </security:authorize>
                        <a class="nav-link active" href="${pageContext.request.contextPath}/user/tx" role="button"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/wallet" role="button"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/settings" role="button"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/trade" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                        </security:authorize>
                    </div>
                </c:when>
                <c:when test = "${page == 'wallet'}">
                    <div class="nav flex-column nav-pills nav-fill" role="tablist" aria-orientation="vertical" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news" role="button"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard" role="button" aria-selected="true"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/copy" role="button"><i class="fas fa-chart-line"></i> Follow a Trader</a>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/followers" role="button"><i class="fas fa-chart-line"></i> Followers</a>
                        </security:authorize>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/tx" role="button"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link active" href="${pageContext.request.contextPath}/user/wallet" role="button"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/settings" role="button"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/trade" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                        </security:authorize>
                    </div>
                </c:when>
                <c:when test = "${page == 'settings'}">
                    <div class="nav flex-column nav-pills nav-fill" role="tablist" aria-orientation="vertical" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news" role="button"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard" role="button" aria-selected="true"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/copy" role="button"><i class="fas fa-chart-line"></i> Follow a Trader</a>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/followers" role="button"><i class="fas fa-chart-line"></i> Followers</a>
                        </security:authorize>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/tx" role="button"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/wallet" role="button"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link active" href="${pageContext.request.contextPath}/user/settings" role="button"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/trade" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                        </security:authorize>
                    </div>
                </c:when>
                <c:when test = "${page == 'trade'}">
                    <div class="nav flex-column nav-pills nav-fill" role="tablist" aria-orientation="vertical" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news" role="button"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard" role="button" aria-selected="true"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/copy" role="button"><i class="fas fa-chart-line"></i> Follow a Trader</a>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/followers" role="button"><i class="fas fa-chart-line"></i> Followers</a>
                        </security:authorize>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/tx" role="button"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/wallet" role="button"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/settings" role="button"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/trade" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                        </security:authorize>
                    </div>
                </c:when>
                <c:when test = "${page == 'admin'}">
                    <div class="nav flex-column nav-pills nav-fill" role="tablist" aria-orientation="vertical" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news" role="button"><i class="fa fa-newspaper-o" style="content: 'f1ea';"><span class="icon">&#xf1ea;</span></i> News</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard" role="button" aria-selected="true"><i class="fas fa-exchange-alt"></i> Dashboard</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/copy" role="button"><i class="fas fa-chart-line"></i> Follow a Trader</a>
                        </security:authorize>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/tx" role="button"><i class="fas fa-history"></i> Transaction History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/wallet" role="button"><i class="fas fa-wallet"></i> Wallet</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/settings" role="button"><i class="fas fa-cogs"></i> Settings</a>
                        <security:authorize access="hasAnyRole('ADMIN', 'TRADER')">
                            <a class="nav-link" href="${pageContext.request.contextPath}/trade" role="button"><i class="fas fa-exchange-alt"></i> Trade</a>
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/admin" role="button"><i class="fas fa-exchange-alt"></i> Admin Panel</a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/logins" role="button"><i class="fas fa-exchange-alt"></i> Logins</a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/deposits" role="button"><i class="fas fa-exchange-alt"></i> Deposits</a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/tx" role="button"><i class="fas fa-exchange-alt"></i> TX</a>
                        </security:authorize>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
