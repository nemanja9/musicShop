<%-- 
    Document   : all
    Created on : Jul 5, 2020, 10:44:38 PM
    Author     : lj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>ALL USERS</h1>
        
        <div>
            <a href="<c:url value="/user/add"/>">Add new user</a>
        </div>
        <div>
            <a href="<c:url value="/index.jsp"/>">Back to home page</a>
        </div>
    </body>
</html>
