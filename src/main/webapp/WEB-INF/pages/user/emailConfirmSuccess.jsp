<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Login</title>
        <!-- Font Icon -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/fonts/material-icon/css/material-design-iconic-font.min.css">
        <!-- Main css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/login.style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/bootstrap.min.css">
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
        <div class="custom-border-bottom py-3" id="backHome">
            <div>
                <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}"><fmt:message key="nav.pocetna"/></a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black"> <b> <fmt:message key="nav.potvrdaEmailAdrese"/></b></strong></div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="center-email-conf">
                    <img src="${pageContext.request.contextPath}/resursi/images/ok.png" alt="" class="potvrdjenEmailSlika">
                    <br>
                    <h2 class="display-3 text-black"><fmt:message key="emailConfSucces.succes"/></h2>         
                    <div class="asdasdasd">
                        <a href="${pageContext.request.contextPath}/user/login">
                            <input type="button" name="login" id="login" class="form-submit-email-conf" value="<fmt:message key="emailConf.prijavljivanje"/>" />
                        </a>
                    </div>
                    
                </div>
            </div>
            <!-- JS -->
            <script src="${pageContext.request.contextPath}/resursi/jquery/login.jquery.min.js"></script>
            <script src="${pageContext.request.contextPath}/resursi/js/login.js"></script>
    </body>

</html>