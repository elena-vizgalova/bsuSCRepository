<%--
    Document   : confirmation
    Created on : Sep 9, 2009, 12:20:30 AM
    Author     : tgiunipero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <link rel="shortcut icon" href="../img/favicon.ico">
        <title>Yummy Books</title>
    </head>
    <body>
        <div id="main">
            <div id="header">
                <c:set var="userRequestLanguage" value="${ pageContext.request.locale.language }" scope="session" />
                <cust_tag:LanguageHelperTag variableName="language"/>
                <fmt:setLocale value="${language}"/>
                <fmt:setBundle basename="resource.messages" />
                
                <div class="headerWidget">
                    <form name="logoutForm" method="POST" action="../Controller">
                        <input type="hidden" name="command" value="logout" />
                            <button type="submit" class="link">
                                <span><fmt:message key="logout_button_submit" /></span>
                            </button>
                    </form>
                </div>

                <div class="headerWidget">
                    <a href="../index.jsp" class="bubble">
                        <fmt:message key="main_page"/>
                    </a>
                </div>

                <a href="../index.jsp">
                    <img src="../img/logo.png" id="logo" alt="Yummy Books logo">
                </a>

                <img src="../img/logoText.png" id="logoText" alt="yummy books">

            </div>
            <div id="singleColumn">

                <p id="confirmationText">
                    <fmt:message key="order_accepted"/>
                    <br><br>
                    <fmt:message key="order_info"/>
                </p>

            </div>
            <jsp:include page="jspf/footer.jspf" />