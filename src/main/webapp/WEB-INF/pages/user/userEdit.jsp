<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../header.jsp" %>

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



<div class="site-section">
    <div class="container">
        <div class="row">         
            <div class="col-md-7">
                <h2 class="h3 mb-3 text-black"><fmt:message key="user.edit.izmenaKorisnika"/> "${u.firstname} ${u.lastname}"</h2>
                <p class="errorMsgTitle">${message}</p>

                <form:form action="${pageContext.request.contextPath}/admin/user/edit/saveEdited" method="post" modelAttribute="u">


                    <div class="p-3 p-lg-5 border">

                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="c_fname" class="text-black"><fmt:message key="ime"/><span class="text-danger">*</span></label>
                                <form:input  value="${u.firstname}" type="text" class="form-control" id="c_fname" name="c_fname" path="firstname"/>

                            </div>
                            <div class="col-md-6">
                                <label for="c_lname" class="text-black"><fmt:message key="prezime"/> <span class="text-danger">*</span></label>
                                <form:input  value="${u.lastname}" type="text" class="form-control" id="c_fname" name="c_fname" path="lastname" />

                                <!--<input type="text" class="form-control" id="c_lname" name="c_lname">-->
                            </div>
                        </div>
                                <form:hidden path="userId" />


                        <div class="form-group row">
                            <div class="col-md-12">
                                <label for="c_address" class="text-black"><fmt:message key="adresa"/> <span class="text-danger">*</span></label>
                                <!--<input type="text" class="form-control" id="c_address" name="c_address" placeholder="Ulica, broj i ulaz">-->

                                <form:input  value="${u.adress}" type="text" class="form-control" id="c_fname" name="c_fname" path="adress"/>

                            </div>
                        </div>



                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="c_state_country" class="text-black"><fmt:message key="grad"/> <span class="text-danger">*</span></label>
                                <!--<input type="text" class="form-control" id="c_state_country" name="c_state_country">-->
                                <form:input  value="${u.city}" type="text" class="form-control" id="c_fname" name="c_fname" path="city" />

                            </div>
                            <div class="col-md-6">
                                <label for="c_postal_zip" class="text-black"><fmt:message key="postanskiBroj"/> <span class="text-danger">*</span></label>
                                <!--<input type="text" class="form-control" id="c_postal_zip" name="c_postal_zip">-->
                                <form:input  value="${u.zip}" type="text" class="form-control" id="c_fname" name="c_fname" path="zip" />


                            </div>
                        </div>



                        <div class="form-group row mb-5">
                            <div class="col-md-6">
                                <label for="c_email_address" class="text-black"><fmt:message key="email"/> <span class="text-danger">*</span></label>
                                <form:input  value="${u.email}" type="text" class="form-control" id="c_fname" name="c_fname" path="email" />


                            </div>
                            <div class="col-md-6">
                                <label for="c_phone" class="text-black"><fmt:message key="kontaktTelefon"/> <span class="text-danger">*</span></label>
                                <form:input  value="${u.phoneNumber}" type="text" class="form-control" id="c_fname" name="c_fname" path="phoneNumber" />

                            </div>
                        </div>
                                 <div class="form-group row mb-5">
                            <div class="col-md-6">
                                <label for="c_phone" class="text-black">Role <span class="text-danger">*</span></label>
                                <fmt:message key="users.add.izaberiRole" var="role"/>
                                <form:select class="form-control" path="roleUser">
                                    <form:option value="" label="${role}" />
                                    <form:options items="${roleList}" />
                                </form:select>
                            </div>
                        </div>



                        <div class="form-group row mb-5">

                            <div class="col-md-6">
                                <button class="btn btn-primary btn-lg btn-block"><fmt:message key="user.edit.sacuvajIzmene"/></button>

                            </div>
                        </div>
                    </div>  
                </form:form>
            </div>
                <div class="col-md-5 ml-auto">
            <h2 class="h3 mb-3 text-black"><fmt:message key="user.edit.josAkcija"/></h2>
            <div class="p-4 border mb-3">
                <div class="block-5 mb-5" id="paddingGornji">
                    <button class="btn btn-primary btn-lg" onclick="window.location='${pageContext.request.contextPath}/admin/user/sendEmailConfirmation/${u.userId}'"><fmt:message key="user.edit.posaljiEmailZaPotvrduNaloga"/></button>
                    <br>
                    <br>
                    <button class="btn btn-primary btn-lg" onclick="window.location='${pageContext.request.contextPath}/admin/user/sendPasswordReset/${u.userId}'"><fmt:message key="user.edit.posaljiEmailZaResetLozinke"/></button>
                    <br>
                    <br>
                    <button class="btn btn-primary btn-lg" onclick="window.location='${pageContext.request.contextPath}/admin/orders/all/${u.userId}'"><fmt:message key="user.edit.pogledajSvePorudzbineZaKorisnika"/></button>
                </div>
                </div></div>       
        </div>
    </div>
</div>


<%@include file="../footer.jsp" %>