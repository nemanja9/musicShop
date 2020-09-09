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
                        class="text-black"><fmt:message key="nav.resetLozinke"/></strong></div>
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
                            <h2 class="form-title"><fmt:message key="resetPassword.resetovanjeLozinke"/></h2>
                            <br>
                            
                          <c:if test="${not empty invalid}">
                                <p  class="errorMsgTitle">${invalid}</p>
                            </c:if>
                                <form method="post" action="${pageContext.request.contextPath}/user/resetPassword">
                                <div class="form-group">
                                    <label for="your_name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                        <input type="text" name="email" id="email" placeholder="Email" />
                                    
                                    
                                   
                                    <div class="form-group form-button">
                                        <input type="submit" name="login" id="login" class="form-submit" value="<fmt:message key="resetPassword.posaljiEmail"/>" />
                                    </div>
                            <br>
                            <a href="${pageContext.request.contextPath}/user/login"><p><fmt:message key="resetPassword.nazadNaPrijavljivanje"/></p></a>

                        </div>
                            </form>
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