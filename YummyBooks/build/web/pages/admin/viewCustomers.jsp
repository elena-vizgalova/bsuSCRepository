<%-- 
    Document   : viewCustomers
    Created on : Feb 7, 2013, 1:47:14 PM
    Author     : Len
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
    <c:set var="pageName" value="view_customers_page"/>
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

            <c:if test="${!empty customersList}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="8"><fmt:message key="customers"/></th>
                    </tr>

                    <tr class="tableHeading">
                        <td><span class="smallText"><fmt:message key="customer_id"/></span></td>
                        <td><span class="smallText"><fmt:message key="customer_name"/></span></td>
                        <td><span class="smallText"><fmt:message key="customer_username"/></span></td>
                        <td><span class="smallText"><fmt:message key="email"/></span></td>
                        <td><span class="smallText"><fmt:message key="phone"/></span></td>
                        <td><span class="smallText"><fmt:message key="address"/></span></td>
                        <td><span class="smallText"><fmt:message key="is_in_black_list"/></span></td>
                        <td><span class="smallText"><fmt:message key="black_list_button_label"/></span></td>
                    </tr>

                    <c:forEach var="customer" items="${sessionScope.customersList}" varStatus="iter">

                        <tr class="${((iter.index % 2) == 1) ? 'lightGreen' : 'lightYellow'} tableRow">
                            <c:set var="customerLoginInfo" value="${customer.customerLoginInfo}"/>

                            <td><span class="smallText">${customer.id}</span></td>
                            <td><span class="smallText">${customer.name}</span></td>
                            <td><span class="smallText">${customerLoginInfo.username}</span></td>
                            <td><span class="smallText">${customer.email}</span></td>
                            <td><span class="smallText">+${customer.phone}</span></td>
                            <td><span class="smallText">${customer.cityRegion}, ${customer.address}</span></td>
                            <td>
                                <c:choose>
                                    <c:when test="${customerLoginInfo.isInBlackList}">
                                        +
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>  
                            </td>
                            <td>
                                <form name="blackListButton" method="POST" action="Controller">
                                    <input type="hidden" name="command" value="blackListChange" />

                                    <input type="hidden"
                                           name="username"
                                           value="${customerLoginInfo.username}">

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
