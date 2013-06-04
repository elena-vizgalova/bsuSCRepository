<%--
    Document   : category
    Created on : Jun 9, 2010, 3:59:32 PM
    Author     : tgiunipero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="cust_tag" uri="/WEB-INF/tlds/taglib.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <c:set var="pageName" value="category_page" scope="request"/>
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
                        <%--<cust_tag:LoadCart variableName="cart"></cust_tag:LoadCart> --%>
                        <c:if test="${!empty cart && cart.booksNumber != 0}">
                            <div class="headerWidget">
                                <a href="checkout.jsp" class="bubble">
                                    <fmt:message key="checkout_label"/>
                                </a>
                            </div>
                        </c:if>
                    </c:if>
                    
                    <c:if test="${!empty login_info}">
                        <div class="headerWidget">
                            <a href="cart.jsp" class="bubble">
                                <fmt:message key="cart_button"/>
                            </a>
                        </div>
                    </c:if>

                    <c:choose>
                        <c:when test="${empty login_info}">
                            <div class="headerWidget">
                                <a href="login.jsp" class="bubble">
                                    <fmt:message key="login_button_submit"/>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="headerWidget">
                                <form name="logoutForm" method="POST" action="../Controller">
                                    <input type="hidden" name="command" value="logout" />
                                        <button type="submit" class="link">
                                            <span><fmt:message key="logout_button_submit" /></span>
                                        </button>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>

                <a href="../index.jsp">
                    <img src="../img/logo.png" id="logo" alt="Yummy Books logo">
                </a>

                <img src="../img/logoText.png" id="logoText" alt="yummy books">

            </div>
                            <%--<cust_tag:CategoryTag variableName="categories"></cust_tag:CategoryTag> --%>
            <div id="categoryLeftColumn">

                <c:forEach var="category" items="${categories}">

                    <c:choose>
                        <c:when test="${category.name == selectedCategory.name}">
                            <div class="categoryButton" id="selectedCategory">
                                <span class="categoryText">
                                    <fmt:message key="${category.name}"/>
                                </span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value='category?${category.id}'/>" class="categoryButton">
                                <span class="categoryText">
                                    <fmt:message key="${category.name}"/>
                                </span>
                            </a>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>

            </div>

            <div id="categoryRightColumn">
                <p id="categoryTitle"><fmt:message key="${selectedCategory.name}" /></p>

                <table id="bookTable">

                    <c:forEach var="book" items="${categoryBooks}" varStatus="iter">

                        <tr class="${( (iter.index % 2) == 0 ) ? 'lightYellow' : 'lightGreen'}">
                            <td>
                                <img src="../img/books/${book.id}.jpg"
                                     alt="<fmt:message key='${book.name}'/>">
                            </td>

                            <td>
                                ${book.name}
                                <p align="left">
                                    <br/>
                                    <span class="smallText">
                                        ${book.description}
                                        <br/><br/><fmt:message key="author_label"/>: ${book.authors}
                                        <br/><br/><fmt:message key="language_label"/>: ${book.language}
                                    </span>
                                </p>
                                <p align="right">
                                    <br/><fmt:message key="price_label"/>: <fmt:formatNumber type="currency" currencySymbol="$" value="${book.price}"/>
                                    <br/></p>
                                <form name="addToCartForm" method="POST" action="Controller">
                                    <input type="hidden" name="command" value="addToCart" />
                                    
                                    <input type="hidden"
                                           name="bookID"
                                           value="${book.id}">
                                    <p align="right">
                                        <input type="submit"
                                               name="submit"
                                               value="<fmt:message key='add_to_cart'/>">
                                    </p>
                                    <c:if test="${bookIDError == book.id}">
                                        <p class="error" align="center">
                                            <c:out value="${error}"/>
                                        </p>
                                    </c:if>
                                </form>
                            </td>
                        </tr>

                    </c:forEach>
                </table>
            </div>
        </div>
        <jsp:include page="jspf/footer.jspf"/>
