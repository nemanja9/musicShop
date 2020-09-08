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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/modal.css">

    </head>

    <body>


        <!--OD MODAL-->
         <c:if test="${not empty uspeh}">
        <div id="myModal" class="modal">

            <div class="modal-content">
                <span class="close">&times;</span>
                <p>${uspeh}</p>
            </div>

        </div>
         </c:if>
        <!--DO MODAL-->



        <div class="custom-border-bottom py-3" id="backHome">
            <div>
                <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}"><fmt:message key="nav.pocetna"/></a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">Login</strong></div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="column">
                    <img src="${pageContext.request.contextPath}/resursi/images/login.jpg" alt="">
                </div>
                <div class="column">
                    <div class="polovina">
                        <div class="signin-form">
                            <h2 class="form-title"><fmt:message key="login.prijavljivanje"/></h2>
                            <p class="errorMsgTitle">${message}</p>
                            <c:if test="${not empty invalid}">
                                <p  class="errorMsgTitle">${invalid}</p>
                            </c:if>
                            <form:form action="${pageContext.request.contextPath}/user/login_user" method="post" modelAttribute="userToLogin" class="register-form" id="login-form">
                                <div class="form-group">
                                    <label for="your_name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                        <form:input type="text" name="email" id="email" path = "email" placeholder="Email" />
                                    <div><form:errors path="email" class="errorMsg"></form:errors></div>
                                    </div>
                                    <fmt:message key='login.lozinka' var="lozinka"/>
                                    <div class="form-group">
                                        <label for="your_pass"><i class="zmdi zmdi-lock"></i></label>
                                        <form:input type="password" name="password" id="password" path="password" placeholder="${lozinka}" />
                                    <div><form:errors path="password" class="errorMsg"></form:errors></div>
                                    </div>
                                    <div class="form-group">

                                    </div>
                                    <div class="form-group form-button">
                                        <input type="submit" name="login" id="login" class="form-submit" value="<fmt:message key='login.prijavljivanje'/>" />
                                    </div>
                            </form:form>
                            <br>
                            <a href="${pageContext.request.contextPath}/user/register"><p><fmt:message key="login.nemateNalog"/></p></a>
                            <a href="${pageContext.request.contextPath}/user/resetPassword"><p><fmt:message key="login.zaboravljenaLozinka"/></p></a>

                        </div>
                    </div>
                    <div id="signUpInstead">
                    </div>

                </div>
            </div>

            <!-- JS -->
            <script src="${pageContext.request.contextPath}/resursi/jquery/login.jquery.min.js"></script>
            <script src="${pageContext.request.contextPath}/resursi/js/login.js"></script>
            <script src="${pageContext.request.contextPath}/resursi/js/modal.js"></script>
    </body>

</html>