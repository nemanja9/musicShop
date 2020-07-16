<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

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
                        <form action="#" method="post">
                            <input type="text" class="form-control" placeholder="Pretraga...">
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
                                        <a href="${pageContext.request.contextPath}">Početna</a>
                                    </li>
                                    <li class="has-children <c:if test="${fn:startsWith(currrentAdress, 'product')}">active </c:if>>">
                                        <a href="<c:url value="/product/all"/>">Proizvodi</a>
                                        <ul class="dropdown">
                                            <li><a href="${pageContext.request.contextPath}/product/all">Svi proizvodi</a></li>
                                            <li><a href="${pageContext.request.contextPath}/product/add">Dodavanje proizvoda</a></li>
                                            <li><a href="${pageContext.request.contextPath}/product/edit">Izmena proizvoda</a></li>

                                        </ul>
                                    </li>

                                    <li class="<c:if test="${fn:startsWith(currrentAdress, 'contact')}">active  </c:if>>">
                                        <a href="<c:url value="/contact"/>">Kontakt</a></li>
                                    <li class="has-children">
                                        <a>Korisnička zona</a>
                                        <ul class="dropdown">
                                            <c:if test="${empty sessionScope.loginUser}">
                                                <li><a href="${pageContext.request.contextPath}/user/login">Prijava</a></li>
                                                <li><a href="${pageContext.request.contextPath}/user/register">Registracija</a></li>
                                                </c:if>
                                                <c:if test="${not empty sessionScope.loginUser}">
                                                <li><a href="#">Moje porudžbine</a></li>
                                               
                                                
                                                <li><a href="${pageContext.request.contextPath}/user/logout">Odjavi se</a></li>
                                                
                                               
                                                </c:if>

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
                            <img src="${pageContext.request.contextPath}/resursi/images/language.png" alt="">
                            <div>Srpski</div>
                            <span class="arrow_carrot-down"></span>
                            <ul>
                                <li><a href="#">Srpski</a></li>
                                <li><a href="#">English</a></li>
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