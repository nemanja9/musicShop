<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Path"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<!--OD MODAL-->
<c:if test="${not empty sessionScope.nedostajuciPodaci}">
    <div id="myModal" class="modal">

        <div class="modal-content">
            <span class="close">&times;</span>
            <p>${sessionScope.nedostajuciPodaci}</p>
        </div>

    </div>
</c:if>
<!--DO MODAL-->



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
                <h2 class="h3 mb-3 text-black"><fmt:message key="profile.vasiPodaci"/></h2>
                <p>${msg}</p>

                <div class="p-3 p-lg-5 border">
                    <s:form action="${pageContext.request.contextPath}/user/edit/submitPersonalChanges" method="post" modelAttribute="izmenjenKorisnik">


                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="c_fname" class="text-black"><fmt:message key="ime"/><span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.firstname}" type="text" class="form-control" id="c_fname" name="c_fname" path="firstname" />
                                <div><form:errors path="firstname" class="errorMsg"></form:errors></div>
                                </div>
                                <div class="col-md-6">
                                    <label for="c_lname" class="text-black"><fmt:message key="prezime"/> <span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.lastname}" type="text" class="form-control" id="c_fname" name="c_fname" path="lastname" />
                                <div><form:errors path="lastname" class="errorMsg"></form:errors></div>
                                    <!--<input type="text" class="form-control" id="c_lname" name="c_lname">-->
                                </div>
                            </div>



                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="c_address" class="text-black"><fmt:message key="adresa"/> <span class="text-danger">*</span></label>
                                    <!--<input type="text" class="form-control" id="c_address" name="c_address" placeholder="Ulica, broj i ulaz">-->

                                <form:input  value="${sessionScope.loginUser.adress}" type="text" class="form-control" id="c_fname" name="c_fname" path="adress" />
                                <div><form:errors path="adress" class="errorMsg"></form:errors></div>
                                </div>
                            </div>



                            <div class="form-group row">
                                <div class="col-md-6">
                                    <label for="c_state_country" class="text-black"><fmt:message key="grad"/> <span class="text-danger">*</span></label>
                                    <!--<input type="text" class="form-control" id="c_state_country" name="c_state_country">-->
                                <form:input  value="${sessionScope.loginUser.city}" type="text" class="form-control" id="c_fname" name="c_fname" path="city" />
                                <div><form:errors path="city" class="errorMsg"></form:errors></div>
                                </div>
                                <div class="col-md-6">
                                    <label for="c_postal_zip" class="text-black"><fmt:message key="postanskiBroj"/> <span class="text-danger">*</span></label>
                                    <!--<input type="text" class="form-control" id="c_postal_zip" name="c_postal_zip">-->
                                <form:input  value="${sessionScope.loginUser.zip}" type="text" class="form-control" id="c_fname" name="c_fname" path="zip" />
                                <div><form:errors path="zip" class="errorMsg"></form:errors></div>

                                </div>
                            </div>

                            <div class="form-group">
                                <label for="c_country" class="text-black"><fmt:message key="drzava"/> <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" value="Srbija" id="c_fname" name="c_fname" disabled="true">
                            </div>

                            <div class="form-group">

                                <label for="c_phone" class="text-black"><fmt:message key="kontaktTelefon"/> <span class="text-danger">*</span></label>
                            <form:input  value="${sessionScope.loginUser.phoneNumber}" type="text" class="form-control" id="c_fname" name="c_fname" path="phoneNumber"/>
                            <div><form:errors path="phoneNumber" class="errorMsg"></form:errors></div>
                            </div>

                            <button class="btn btn-primary btn-lg btn-block"><fmt:message key="profile.sacuvajPodatke"/></button>
                        <form:hidden value="${sessionScope.loginUser.email}" path="email" id="email"/>
                        <form:hidden value="${sessionScope.loginUser.password}" path="password" id="password"/>
                        <form:hidden value="${sessionScope.loginUser.roleUser}" path="roleUser" id="roleUser"/>
                        <form:hidden value="${sessionScope.loginUser.emailConfirmed}" path="emailConfirmed" id="emailConfirmed"/>
                    </s:form>
                </div>
            </div>
            <div class="col-md-5 ml-auto">
                <h2 class="h3 mb-3 text-black"><fmt:message key="profile.podaciNaloga"/></h2>
                <div class="p-4 border mb-3">
                    <div class="block-5 mb-5" id="paddingGornji">

                        <!--PROMENI EMAIL-->
                        <s:form action="${pageContext.request.contextPath}/user/edit/submitEmail" method="post" modelAttribute="izmenjenKorisnik">

                            <div class="border p-3 mb-3">
                                <h3 class="h6 mb-0"><a class="d-block"  data-toggle="collapse" href="#collapsebank" role="button" aria-expanded="false" aria-controls="collapsebank"><fmt:message key="profile.promeniEmail"/></a></h3>
                                <div class="collapse" id="collapsebank">
                                    <div class="py-2">
                                        <div class="col-md-10">
                                            <label for="c_email_address" class="text-black">Nova Email adresa <span class="text-danger">*</span></label>
                                            <form:input  value="${sessionScope.loginUser.email}" type="text" class="form-control" id="c_fname" name="c_fname" path="email"/>
                                            <br>
                                            <button class="btn btn-primary btn-lg btn-block"><fmt:message key="profile.sacuvajEmail"/></button>

                                        </div>
                                        <br>

                                        <p class="mb-0"><fmt:message key="profile.emailText"/>
                                        </p>
                                        <br>
                                    </div>
                                </div>
                            </div>
                        </s:form>
                        <!--PROMENI EMAIL DO OVDE-->      


                        <!--PROMENI SIFRU-->
                        <s:form action="${pageContext.request.contextPath}/user/edit/submitPassword" method="post" modelAttribute="izmenjenaSifra">
                            <div class="border p-3 mb-3">
                                <h3 class="h6 mb-0"><a class="d-block" data-toggle="collapse" href="#collapsecheque" role="button" aria-expanded="false" aria-controls="collapsecheque"><fmt:message key="profile.promeniLozinku"/></a></h3>
                                <div class="collapse" id="collapsecheque">
                                    <br>

                                    <div class="py-2">
                                        <div class="col-md-10">
                                            <label for="c_passwrd" class="text-black"><fmt:message key="profile.novaLozinka"/><span class="text-danger">*</span></label>
                                            <form:input type="password" class="form-control" id="c_fname" name="c_fname" path="password"/>
                                            <div class="errMsg"><form:errors path="password" class="errorMsg"></form:errors></div>
                                                <br>
                                                <label for="c_email_address" class="text-black"><fmt:message key="profile.potvrditeLozinku"/><span class="text-danger">*</span></label>
                                            <form:input type="password" class="form-control" id="c_fname" name="c_fname" path="re_password"/>
                                            <div class="errMsg"><form:errors path="re_password" class="errorMsg "></form:errors></div>
                                                <br>
                                                <button class="btn btn-primary btn-lg btn-block"><fmt:message key="profile.sacuvajLozinku"/> </button>

                                            </div>
                                            <br>
                                            <p class="mb-0"><fmt:message key="profile.lozinkaText"/></p>
                                        </div>
                                    </div>
                                </div>


                        </s:form>
                        <!--PROMENI SIFRU DO OVDE-->



                    </div>

                </div>


            </div>


        </div>
    </div>

</div>
</div>



<%@include file="../footer.jsp" %>






