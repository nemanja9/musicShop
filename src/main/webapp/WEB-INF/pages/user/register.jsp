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
                        class="text-black">Registracija</strong></div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="column">
                    <div class="polovina">
                        <div class="signin-form">
                            <h2 class="form-title">Registracija</h2>
                            <p class="errorMsgTitle">${message}</p>
                            <c:if test="${not empty invalid}">
                                <p class="errorMsgTitle">${invalid}</p>
                            </c:if>
                            <form:form action="${pageContext.request.contextPath}/user/register_user" method="post" modelAttribute="userToRegister" class="register-form" id="register-form">
                                <div class="form-group">
                                    <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                        <form:input type="text" path="firstname" id="firstname" placeholder="Ime" />
                                </div>
                                <div><form:errors path="firstname" class="errorMsg"></form:errors></div>
                                <div class="form-group">
                                        <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                        <form:input type="text" path="lastname" id="lastname" placeholder="Prezime" />
                                </div>
                                <div><form:errors path="lastname" class="errorMsg"></form:errors></div>
                                <div class="form-group">
                                    <label for="email"><i class="zmdi zmdi-email"></i></label>
                                        <form:input type="text" path="email" id="email" placeholder="Email" />
                                </div>
                                <div><form:errors path="email" class="errorMsg"></form:errors></div>
                                <div class="form-group">
                                    <label for="phoneNumber"><i class="zmdi zmdi-smartphone-iphone"></i></label>
                                        <form:input type="text" path="phoneNumber" id="phoneNumber" placeholder="Kontakt telefon" />
                                </div>
                                <div><form:errors path="phoneNumber" class="errorMsg"></form:errors></div>
                                <div class="form-group">
                                    <label for="phoneNumber"><i class="zmdi zmdi-home"></i> </label>
                                    <form:input type="text" path="adress" id="adress" placeholder="Ulica, broj i ulaz" />
                                </div>
                                <div><form:errors path="adress" class="errorMsg"></form:errors></div>
                                <div class="form-group">
                                    <label for="phoneNumber"><i class="zmdi zmdi-map"></i></label>
                                        <form:input type="text" path="city" id="city" placeholder="Grad" />
                                </div>
                                <div><form:errors path="city" class="errorMsg"></form:errors></div>
                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                        <form:input type="password" path="password" id="password" placeholder="Lozinka" />
                                </div>
                                <div><form:errors path="password" class="errorMsg"></form:errors></div>
                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                        <form:input type="password" path="re_password" id="re_password" placeholder="Ponovite lozinku" />
                                </div>
                                <div><form:errors path="re_password" class="errorMsg"></form:errors></div>
                                <div class="form-group">
                                    <input type="checkbox" name="agree-term" id="agree-term" class="agree-term" />
                                    <label for="agree-term" class="label-agree-term"><span><span></span></span>Slažem se sa <a href="javascript:alert('Nema uslova koriscenja trenutno!');" class="term-service">uslovima korišćenja</a></label>
                                </div>
                                <div class="form-group form-button">
                                    <input type="submit" name="signup" id="signup" class="form-submit" value="Register"/>
                                </div>
                            </form:form>
                            <br>
                            <a href="${pageContext.request.contextPath}/user/login"><p>Već imate nalog? Prijavite se ovde</p></a>

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