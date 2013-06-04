<%-- 
    Document   : login
    Created on : Feb 2, 2013, 11:26:34 PM
    Author     : Len
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <link rel="shortcut icon" href="../img/favicon.ico">
        <title>Yummy Books</title>
    </head>
    <c:set var="pageName" value="login_page"/>
    <body>
        <div id="main">
            <div id="header">
                <div id="widgetBar">

                    <div class="headerWidget">
                        <c:set var="userRequestLanguage" value="${ pageContext.request.locale.language }" scope="session" />
                        <cust_tag:LanguageHelperTag variableName="language"/>
                        <fmt:setLocale value="${language}"/>
                        <fmt:setBundle basename="resource.messages" />
                        <c:choose>
                            <c:when test="${language eq 'ru'}">
                                <c:out value="русский | "/>
                                <c:url var="url" value="chooseLanguage">
                                    <c:param name="language" value="en"/>
                                    <c:param name="pageName" value="${pageName}"/>
                                </c:url>
                                <div class="bubble"><a href="${url}">english</a></div>
                            </c:when>
                            <c:otherwise>
                                <c:out value="english | "/>
                                <c:url var="url" value="chooseLanguage">
                                    <c:param name="language" value="ru"/>
                                    <c:param name="pageName" value="${pageName}"/>
                                </c:url>
                                <div class="bubble"><a href="${url}">русский</a></div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="headerWidget">
                        <a href="../home" class="bubble">
                            <fmt:message key="main_page"/>
                        </a>
                    </div>

                </div>
                <a href="../index.jsp">
                    <img src="../img/logo.png" id="logo" alt="Yummy Books logo">
                </a>

                <img src="../img/logoText.png" id="logoText" alt="yummy books">

            </div>

            <div id="loginBox">
                <form name="loginForm" method="POST" action="../Controller">
                    <input type="hidden" name="command" value="logination" />

                    <strong><fmt:message key="login"/>: </strong>
                    <input type="text" name="login" size="30" id="login"><br/>

                    <strong><fmt:message key="password"/>: </strong>
                    <input type="password" name="password" size="30" id="password"><br/>

                    <fmt:message key="login_button_submit" var="buttonValue" />
                    <p><input type="submit" value="${buttonValue}"></p>
                </form>
            </div>

            <jsp:include page="jspf/footer.jspf"/>