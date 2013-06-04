<%-- 
    Document   : welcome_page
    Created on : Feb 7, 2013, 12:05:22 PM
    Author     : Len
--%>

<%--
    Document   : confirmation
    Created on : Sep 9, 2009, 12:20:30 AM
    Author     : tgiunipero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="cust_tag" uri="/WEB-INF/tlds/taglib.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <c:set var="siteURL" value="http://localhost:8084/YummyBooks"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <link rel="shortcut icon" href="img/favicon.ico">
        <link rel="shortcut icon" href="../img/favicon.ico">
        <title>Yummy Books</title>
    </head>
    <c:set var="pageName" value="admin_welcome"/>
    <body>
        <div id="main">
            <div id="header">
                <c:set var="userRequestLanguage" value="${ pageContext.request.locale.language }" scope="session" />
                <cust_tag:LanguageHelperTag variableName="language"/>
                <fmt:setLocale value="${language}"/>
                <fmt:setBundle basename="resource.messages" />
                
                <div class="headerWidget">
                    <form name="viewCustomersForm" method="POST" action="Controller">
                        <input type="hidden" name="command" value="viewCustomers" />
                            <button type="submit" class="link">
                                <span><fmt:message key="view_customers" /></span>
                            </button>
                    </form>
                </div>

                <div class="headerWidget">
                    <form name="viewOrdersForm" method="POST" action="Controller">
                        <input type="hidden" name="command" value="viewOrders" />
                            <button type="submit" class="link">
                                <span><fmt:message key="view_orders" /></span>
                            </button>
                    </form>
                </div>

                <a href="${siteURL}">
                    <img src="img/logo.png" id="logo" alt="Yummy Books logo">
                </a>

                <img src="img/logoText.png" id="logoText" alt="yummy books">

            </div>
            <div id="centerColumn">

                <h2>${sessionScope.greetingTitle}</h2>

                <p>${sessionScope.greetingMessage}</p>

            </div>
            <jsp:include page="jspf/footer.jspf" />
