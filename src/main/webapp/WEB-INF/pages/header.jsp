<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>AIN Music store</title>
        <meta charset="utf-8">

        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Mukta:300,400,700">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/fonts/icomoon/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/magnific-popup.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/jquery-ui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/owl.carousel.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/owl.theme.default.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/aos.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/style.css">
        <script src="${pageContext.request.contextPath}/resursi/js/i18n.js"></script>


    </head>

    <body>
    <hi>
        <c:set var="currrentAdress" scope="session" value="${requestScope['javax.servlet.forward.request_uri'].split(\"/musicshop/\")[1]}"/>



    </hi>
    <div class="site-wrap">
        <div class="site-navbar bg-white py-2">

            <div class="search-wrap">
                <div class="container">
                    <a href="#" class="search-close js-search-close"><span class="icon-close2"></span></a>
                    <form action="${pageContext.request.contextPath}/product/all" method="get">
                        <input type="text" id="productName" name="productName" class="form-control" placeholder="<fmt:message key="header.pretraga" />">
                    </form>
                </div>
            </div>

            <div class="container">
                <div class="d-flex align-items-center justify-content-between">
                    <div class="logo">
                        <div class="site-logo">
                            <a href="${pageContext.request.contextPath}" class="js-logo-clone">ain music</a>
                        </div>
                    </div>
                    <div class="main-nav d-none d-lg-block">
                        <nav class="site-navigation text-right text-md-center" role="navigation">
                            <ul class="site-menu js-clone-nav d-none d-lg-block">
                                <li class=" <c:if test="${empty currrentAdress}">active </c:if>>">
                                    <a href="${pageContext.request.contextPath}"><fmt:message key="header.pocetna" /></a>
                                </li>
                                <li class="has-children <c:if test="${fn:startsWith(currrentAdress, 'product')}">active </c:if>>">
                                    <a href="<c:url value="/product/all"/>"><fmt:message key="header.proizvodi" /></a>
                                    <ul class="dropdown">
                                        <li><a href="${pageContext.request.contextPath}/product/all"><fmt:message key="header.sviProizvodi" /></a></li>
                                        <li><a href="${pageContext.request.contextPath}/product/add"><fmt:message key="header.dodavanjeProizvoda" /></a></li>
                                        <li><a href="${pageContext.request.contextPath}/product/edit"><fmt:message key="header.izmenaProizvoda" /></a></li>

                                    </ul>
                                </li>

                                <li class="<c:if test="${fn:startsWith(currrentAdress, 'contact')}">active  </c:if>>">
                                    <a href="<c:url value="/contact"/>"><fmt:message key="header.kontakt" /></a></li>
                                <li class="has-children">
                                    <a><fmt:message key="header.korisnickaZona" /></a>
                                    <ul class="dropdown">
                                        <c:if test="${empty sessionScope.loginUser}">
                                            <li><a href="${pageContext.request.contextPath}/user/login"><fmt:message key="header.prijava" /></a></li>
                                            <li><a href="${pageContext.request.contextPath}/user/register"><fmt:message key="header.registracija" /></a></li>
                                            </c:if>
                                            <c:if test="${not empty sessionScope.loginUser}">



                                            <li><a href="${pageContext.request.contextPath}/user/logout"><fmt:message key="header.odjaviSe" /></a></li>
                                            <li><a href="${pageContext.request.contextPath}/user/profile"><fmt:message key="header.profil" /></a></li>


                                        </c:if>
                                        <li><a href="${pageContext.request.contextPath}/orders/all"><fmt:message key="header.mojePorudzbine" /></a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    <div class="icons">
                        <a href="#" class="icons-btn d-inline-block js-search-open"><span class="icon-search"></span></a>
                        <a href="${pageContext.request.contextPath}/cart" class="icons-btn d-inline-block bag">
                            <span class="icon-shopping-bag" <c:if test="${fn:startsWith(currrentAdress, 'cart')}"> id="inCart" </c:if> ></span>
                            <c:if test = "${sessionScope.cart.size() > 0 }">
                                <span class="number"><c:out value="${sessionScope.cart.size()}" /></span>
                            </c:if>
                        </a>
                    </div>
                    <div class="header__top__right__language">
                        <c:set var="context" value="${pageContext.request.contextPath}" />
                        <span class="icon-globe">         </span>
                        <div>
                            <c:if test="${fn:startsWith(cookie.lang.value, 'srb')}">
                                <fmt:message key="jezik.srpski" />
                            </c:if>
                            <c:if test="${fn:startsWith(cookie.lang.value, 'en')}">
                                <fmt:message key="jezik.engleski" />
                            </c:if>
                        </div>
                        <span class="arrow_carrot-down"></span>
                        <ul>
                            <li><a href="javascript:setLanguageSrb();"> <fmt:message key="jezik.srpski" /> </a></li>
                            <li><a href="javascript:setLanguageEn();"> <fmt:message key="jezik.engleski" /> </a></li>
                            
                        </ul>
                    </div>



                    <div id="signedInUser">
                        <c:if test="${not empty sessionScope.loginUser}">
                            ${sessionScope.loginUser.firstname} ${sessionScope.loginUser.lastname}
                        </c:if>
                    </div>
                </div>
            </div>
        </div>