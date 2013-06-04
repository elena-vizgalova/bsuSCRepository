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
        <link rel="shortcut icon" href="img/favicon.ico">
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
                    <a href="${siteURL}/home" class="bubble">
                        <fmt:message key="main_page"/>
                    </a>
                </div>

                <div class="headerWidget">
                    <a href="pages/login.jsp" class="bubble">
                        <fmt:message key="login_button_submit"/>
                    </a>
                </div>

                <a href="${siteURL}/home">
                    <img src="img/logo.png" id="logo" alt="Yummy Books logo">
                </a>

                <img src="img/logoText.png" id="logoText" alt="yummy books">

            </div>
            <div id="centerColumn">

                <h2>${sessionScope.greetingTitle}</h2>

                <p>${sessionScope.greetingMessage}</p>

            </div>
            <jsp:include page="jspf/footer.jspf" />