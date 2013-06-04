<%-- 
    Document   : index
    Created on : Jan 29, 2013, 4:41:28 AM
    Author     : Len
--%>
<%@page import="sun.misc.GC"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="cust_tag" uri="/WEB-INF/tlds/taglib.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <link rel="shortcut icon" href="img/favicon.ico">
        <title>Yummy Books</title>
    </head>
    <c:set var="pageName" value="index_page" scope="request"/>
    <body>
        <c:set var="userRequestLanguage" value="${ pageContext.request.locale.language }" scope="session" />
        <cust_tag:LanguageHelperTag variableName="language"/>
        <fmt:setLocale value="${language}"/>
        <fmt:setBundle basename="resource.messages" />
        <div id="main">
            <div id="header">
                <div id="widgetBar">
                    <div class="headerWidget">
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

                    <c:if test="${!empty login_info}">
                        <c:if test="${!empty cart && cart.booksNumber != 0}">
                            <div class="headerWidget">
                                <a href="pages/checkout.jsp" class="bubble">
                                    <fmt:message key="checkout_label"/>
                                </a>
                            </div>
                        </c:if>
                    </c:if>
                    
                    <c:if test="${!empty login_info}">
                        <div class="headerWidget">
                            <a href="pages/cart.jsp" class="bubble">
                                <fmt:message key="cart_button"/>
                            </a>
                        </div>
                    </c:if>

                    <c:choose>
                        <c:when test="${empty login_info}">
                            <div class="headerWidget">
                                <a href="pages/login.jsp" class="bubble">
                                    <fmt:message key="login_button_submit"/>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="headerWidget">
                                <form name="logoutForm" method="POST" action="Controller">
                                    <input type="hidden" name="command" value="logout" />
                                        <button type="submit" class="link">
                                            <span><fmt:message key="logout_button_submit" /></span>
                                        </button>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <a href="index.jsp">
                    <img src="img/logo.png" id="logo" alt="Yummy Books logo">
                </a>

                <img src="img/logoText.png" id="logoText" alt="yummy books">

            </div>

            <div id="indexLeftColumn">
                <div id="welcomeText">
                    <p> <cust_tag:GreetingTag/> </p>
                    <fmt:message key="about_yummybooks"/>
                </div>
            </div>
            <div id="indexRightColumn">
                <%-- <cust_tag:CategoryTag variableName="categories"></cust_tag:CategoryTag> --%>
                <c:forEach var="category" items="${categories}">
                    <div class="categoryBox">
                        <a href="<c:url value='pages/category?${category.id}'/>">
                            <span class="categoryLabel"></span>
                            <span class="categoryLabelText"><fmt:message key="${category.name}" /></span>

                            <img src="${initParam.categoryImagePath}${category.name}.png"
                                 alt="${category.name}" class="categoryImage"/>
                        </a>
                    </div>
                </c:forEach>
            </div>
            <jsp:include page="pages/jspf/footer.jspf"/>