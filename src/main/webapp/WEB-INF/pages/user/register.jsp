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
                                    <label for="email"><i class="zmdi zmdi-email"></i></label>
                                        <form:input type="text" path="email" id="email" placeholder="Email" />
                                </div>
                                <div><form:errors path="email" class="errorMsg"></form:errors></div>

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
                                        <input type="checkbox" name="agree-term" id="agree-term" class="agree-term custom-control-label" onclick="checkbox()"/>
                                        <label for="agree-term" class="label-agree-term">Slažem se sa <a href="javascript:alert('Nema uslova koriscenja trenutno!');" class="term-service">uslovima korišćenja</a></label>
                                    </div>
                                    <div class="form-group form-button">
                                        <input type="submit" name="signup" id="signup" class="form-submit" value="Register" onclick="registracija(event)"/>
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
                <script src="${pageContext.request.contextPath}/resursi/js/modal.js"></script>

                </body>
                </html>