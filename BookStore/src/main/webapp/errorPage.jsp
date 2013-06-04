<%-- 
    Document   : errorPage
    Created on : Jun 4, 2013, 2:38:40 PM
    Author     : Elena Vizgalova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error!</title>
    </head>
    <body>
        <h1><c:out value="${sessionScope.errorMessage}"/></h1>
    </body>
</html>
