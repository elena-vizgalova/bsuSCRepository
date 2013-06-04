<%--
    Document   : cart
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
    <c:set var="pageName" value="cart_page"/>
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

                        <%-- <cust_tag:LoadCart variableName="cart"></cust_tag:LoadCart> --%>
                        
                    <c:if test="${!empty cart && cart.booksNumber != 0}">
                        <div class="headerWidget">
                            <a href="checkout.jsp" class="bubble">
                                <fmt:message key="checkout_label"/>
                            </a>
                        </div>
                    </c:if>

                </div>
                <a href="../index.jsp">
                    <img src="../img/logo.png" id="logo" alt="Yummy Books logo">
                </a>

                <img src="../img/logoText.png" id="logoText" alt="yummy books">

            </div>
            
            <div id="singleColumn">

                <c:choose>
                    <c:when test="${ 1<= cart.booksNumber }">
                        <p><fmt:message key="cart_contains_label"/>${cart.booksNumber}.</p>
                    </c:when>
                    <c:otherwise>
                        <p><fmt:message key="empty_cart_label"/></p>
                    </c:otherwise>
                </c:choose>

                <div id="actionBar">
                    
                    <c:if test="${!empty cart && cart.booksNumber != 0}">

                        <c:url var="urlClearCart" value="clearCart">
                            <c:param name="clear" value="true"/>
                        </c:url>

                        <a href="${urlClearCart}" class="bubble hMargin"><fmt:message key="clear_cart"/></a>
                    </c:if>

                    <cust_tag:ContinueShopping variableName="continueShoppingURL"/>
                    <c:url var="urlContinue" value="${continueShoppingURL}"/>
                    
                    <a href="${urlContinue}" class="bubble hMargin">
                        <fmt:message key="continue_shopping_label"/>
                    </a>

                    <c:if test="${!empty cart && cart.booksNumber != 0}">
                        <a href="checkout.jsp" class="bubble hMargin">
                            <fmt:message key="checkout_label"/>
                        </a>
                    </c:if>
                        
                </div>

                <c:if test="${!empty cart && cart.booksNumber != 0}">

                    <table id="cartTable">

                        <tr class="header">
                        <th><fmt:message key="book_label"/></th>
                        <th><fmt:message key="book_name_label"/></th>
                        <th><fmt:message key="price_label"/></th>
                        <th><fmt:message key="quantity_label"/></th>
                        </tr>

                        <c:forEach var="bookQuantity" items="${cart.bookQuantity}" varStatus="iter">

                            <c:set var="book" value="${bookQuantity.key}"/>

                            <tr class="${((iter.index % 2) == 0) ? 'lightYellow' : 'lightGreen'}">
                                <td>
                                    <img src="../img/books/${book.id}.jpg"
                                         alt="${book.name}">
                                </td>

                                <td>${book.name}</td>

                            <td>
                            <fmt:formatNumber type="currency" currencySymbol="$" value="${cartItem.total}"/>
                            <br>
                            <span class="smallText">(
                                <fmt:formatNumber type="currency" currencySymbol="$" value="${book.price}"/>
                                / <fmt:message key="one_book_price"/> )</span>
                            </td>

                            <td>
                                <form name="updateCart" method="POST" action="Controller">
                                    <input type="hidden" name="command" value="updateCart" />
                                    
                                    <input type="hidden"
                                           name="bookID"
                                           value="${book.id}">
                                    
                                    <input type="text"
                                           name="booksQuantity"
                                           id="quantity"
                                           maxlength="2"
                                           size="2"
                                           value="${bookQuantity.value}"
                                           style="margin:5px">
                                    
                                    <input type="submit"
                                           name="submit"
                                           value="<fmt:message key='update_cart'/>">
                                </form>
                            </td>
                            </tr>
                            <tr>
                            <p class="error">
                                ${error}
                            </p>
                            </tr>

                        </c:forEach>

                    </table>

                </c:if>
            </div>
            <jsp:include page="jspf/footer.jspf"/>