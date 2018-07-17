
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FORGET IT</title>
    </head>
    <body>
        <h1>GET THE FUCK OUT. TOP SECRET</h1>
        <hr>
        <p>
            You are not authorized, mate.. stay away
        </p>
        
            <h2>Submitted File</h2>
            <table>
                <tr>
                    <td>OriginalFileName:</td>
                    <td>${file.originalFilename}</td>
                </tr>
                <tr>
                    <td>Type:</td>
                    <td>${file.contentType}</td>
                </tr>
            </table>
                    ${info}<br><br>
                    ${info2}<br>
        
        <hr>
        <a href="${pageContext.request.contextPath}/" >Back to Home Page</a>
    </body>
</html>
