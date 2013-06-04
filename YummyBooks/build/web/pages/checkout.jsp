<%--
    Document   : checkout
    Created on : Jun 9, 2010, 3:59:32 PM
    Author     : tgiunipero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="cust_tag" uri="/WEB-INF/tlds/taglib.tld"%>

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
        <c:set var="pageName" value="checkout_page"/>
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
                        <a href="../index.jsp" class="bubble">
                            <fmt:message key="main_page"/>
                        </a>
                    </div>

                </div>
                <a href="../index.jsp">
                    <img src="../img/logo.png" id="logo" alt="Yummy Books logo">
                </a>

                <img src="../img/logoText.png" id="logoText" alt="yummy books">

            </div>

            <div id="centerColumn">

                <h2><fmt:message key="order_label"/></h2>

                <p><fmt:message key="order_text"/></p>

                <form name="checkout" method="POST" action="Controller">
                    <input type="hidden" name="command" value="checkout" />

                    <table id="checkoutTable">

                        <tr>
                            <td>
                                <font color="red">${nameError}</font><br/>
                                <label for="name"><fmt:message key="customer_name"/>:</label>
                            </td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="45"
                                       id="customer_name"
                                       name="customer_name"
                                       value="${customer.name}">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <font color="red">${emailError}</font><br/>
                                <label for="email"><fmt:message key="email"/>:</label>
                            </td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="45"
                                       id="customer_email"
                                       name="customer_email"
                                       value="${customer.email}">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <font color="red">${phoneError}</font><br/>
                                <label for="phone"><fmt:message key="phone"/>:</label>
                            </td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="16"
                                       id="customer_phone"
                                       name="customer_phone"
                                       value="${customer.phone}">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <font color="red">${addressError}</font><br/>
                                <label for="address"><fmt:message key="address"/>:</label>
                            </td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="45"
                                       id="customer_address"
                                       name="customer_address"
                                       value="${customer.address}">

                                <br>
                        </tr>
                        <tr>
                            <td>
                                <font color="red">${cityRegionError}</font><br/>
                                <label for="city_region"><fmt:message key="city_region"/>:</label>
                            </td>
                            <td class="inputField">
                                <input type="text"
                                       size="31"
                                       maxlength="16"
                                       id="city_region"
                                       name="city_region"
                                       value="${customer.cityRegion}">
                            </td>
                        </tr>

                        <tr>
                            <td></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="<fmt:message key="send_order"/>"></td>
                        </tr>

                    </table>

                </form>

                <div id="infoBox">
                    <div id="priceBox">
                        <fmt:message key="order_total"/> ${cart.totalCost}
                        <br><fmt:message key="order_info"/>
                    </div>
                </div>
            </div>
            <jsp:include page="jspf/footer.jspf"/>