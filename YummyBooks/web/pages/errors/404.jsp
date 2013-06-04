<%-- 
    Document   : 404
    Created on : Feb 2, 2013, 9:57:14 PM
    Author     : Elena Vizgalova
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="cust_tag" uri="/WEB-INF/tlds/taglib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
    <head>
        <c:set var="siteURL" value="http://localhost:8084/YummyBooks"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="shortcut icon" href="../../img/favicon.ico">
        <link rel="shortcut icon" href="../img/favicon.ico">
        <title>404 X_X</title>
    </head>
    <c:set var="pageName" value="error_page"/>
    <body>
        <div id="main">
            <div id="header">
                <div id="widgetBar">
                    <c:set var="userRequestLanguage" value="${ pageContext.request.locale.language }" scope="session" />
                    <cust_tag:LanguageHelperTag variableName="language"/>
                    <fmt:setLocale value="${language}"/>
                    <fmt:setBundle basename="resource.messages" />
                </div>
                <a href="${siteURL}/index.jsp">
                    <img src="${siteURL}/img/logo.png" id="logo" alt="Yummy Books logo">
                </a>

                <img src="${siteURL}/img/logoText.png" id="logoText" alt="yummy books">

            </div>
            <form name="loginForm" method="POST" action="${siteURL}/Controller">
                <input type="hidden" name="command" value="logination" />

                <p class="error">
                    <fmt:message key="no_page_error"/>
                    <br>
                    <fmt:message key="go_to_main_page"/>
                </p>

            </form>
            <jsp:include page="../jspf/footer.jspf"/>
