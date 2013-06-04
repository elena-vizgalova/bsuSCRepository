<%-- 
    Document   : viewOrder
    Created on : Feb 7, 2013, 12:40:30 PM
    Author     : Elena Vizgalova
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
        <link rel="stylesheet" type="text/css" href="../../css/style.css">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="shortcut icon" href="img/favicon.ico">
        <link rel="shortcut icon" href="../../img/favicon.ico">
        <link rel="shortcut icon" href="../img/favicon.ico">
        <title>Yummy Books</title>
    </head>
    <c:set var="pageName" value="view_orders_page"/>
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
            </div>

            <c:if test="${!empty sessionScope.ordersList}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="8"><fmt:message key="order_label"/></th>
                    </tr>

                    <tr class="tableHeading">
                        <td><span class="smallText"><fmt:message key="order_id"/></span></td>
                        <td><span class="smallText"><fmt:message key="customer_id"/></span></td>
                        <td><span class="smallText"><fmt:message key="is_payed"/></span></td>
                        <td><span class="smallText"><fmt:message key="is_confirmed"/></span></td>
                        <td><span class="smallText"><fmt:message key="book_id"/> : <fmt:message key="quantity"/></span></td>
                        <td><span class="smallText"><fmt:message key="total_cost"/></span></td>
                        <td><span class="smallText"><fmt:message key="date"/></span></td>
                        <td><span class="smallText"><fmt:message key="is_payed_button"/></span></td>
                    </tr>

                    <c:forEach var="order" items="${sessionScope.ordersList}" varStatus="iter">

                        <c:set var="customer" value="${order.customer}"/>
                        <tr class="${((iter.index % 2) == 1) ? 'lightGreen' : 'lightYellow'} tableRow">

                            <td><span class="smallText">${order.id}</span></td>
                            <td><span class="smallText">${customer.id}</span></td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.isPayed}">
                                        +
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.isConfirmed}">
                                        +
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <table id="bookQuantity">
                                    <c:forEach var="oneBookQuantity" items="${order.bookQuantity}" varStatus="iter">
                                        <tr>
                                            ${oneBookQuantity.key} : ${oneBookQuantity.value}
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                            <td>
                                <fmt:formatNumber type="currency"
                                                  currencySymbol="$"
                                                  value="${order.total}"/>
                            </td>

                            <td>
                                <fmt:formatDate value="${order.dateCreated}"
                                                type="both"
                                                dateStyle="short"
                                                timeStyle="short"/>
                            </td>
                            <td>
                                <form name="isPayedButton" method="POST" action="Controller">
                                    <input type="hidden" name="command" value="isPayedChange" />

                                    <input type="hidden"
                                           name="orderID"
                                           value="${order.id}">
                                    <input type="hidden"
                                           name="isPayed"
                                           value="${order.isPayed}">

                                    <input type="submit"
                                           name="submit"
                                           value="<fmt:message key='do_it_button'/>">
                                </form>
                            </td>
                        </tr>

                    </c:forEach>

                </table>

            </c:if>
            <div id="footer">
                <br>
                <hr id="footerDivider">
                <p id="footerText" class="reallySmallText">
                    &nbsp;&nbsp;&copy;&nbsp;&nbsp;
                    2013 Yummy Books</p>
            </div>
        </div>
    </body>
</html>
