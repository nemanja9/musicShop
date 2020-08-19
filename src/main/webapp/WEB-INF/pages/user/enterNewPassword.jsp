<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Registracija</title>

        <!-- Font Icon -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/fonts/material-icon/css/material-design-iconic-font.min.css">

        <!-- Main css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/login.style.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resursi/css/bootstrap.min.css">

    </head>
    <body>
        <div class="custom-border-bottom py-3" id="backHome">
            <div>
                <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}">Početna</a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">Promena lozinke</strong></div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="column">
                    <div class="polovina">
                        <div class="signin-form">
                            <h2 class="form-title">Unesite novu lozinku</h2>
                            <p class="errorMsgTitle">${message}</p>
                            <c:if test="${not empty invalid}">
                                <p class="errorMsgTitle">${invalid}</p>
                            </c:if>
                            <form:form action="${pageContext.request.contextPath}/user/enterNewPassword/submit" method="post" modelAttribute="userToChangePass" class="register-form" id="register-form">
                               
                               
                              
                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                        <form:input type="password" path="password" id="password" placeholder="Nova lozinka" />
                                </div>
                                <div><form:errors path="password" class="errorMsg"></form:errors></div>
                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                        <form:input type="password" path="re_password" id="re_password" placeholder="Ponovite lozinku" />
                                        <form:hidden path="email" id="email" />
                                </div>
                                <div><form:errors path="re_password" class="errorMsg"></form:errors></div>
                                <div class="form-group form-button">
                                    <input type="submit" name="signup" id="signup" class="form-submit" value="Promeni lozinku"/>
                                </div>
                            </form:form>
                            <br>

                        </div>
                    </div>
                    <div id="signUpInstead">
                    </div>

                </div>
                <div class="column">
                    <img src="${pageContext.request.contextPath}/resursi/images/signup.jpg" alt="">
                </div>

                <!-- JS -->
                <script src="${pageContext.request.contextPath}/resursi/jquery/login.jquery.min.js"></script>
                <script src="${pageContext.request.contextPath}/resursi/js/login.js"></script>
                </body>
                </html>