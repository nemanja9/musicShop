<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div class="bg-light py-3">
    <div class="container">
        <div class="row">
            <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}"><fmt:message key="nav.pocetna"/></a> <span class="mx-2 mb-0">/</span> 
                <a href="${pageContext.request.contextPath}/cart"><fmt:message key="nav.korpa"/></a> <span class="mx-2 mb-0">/</span> <strong class="text-black">
                    <fmt:message key="nav.potvrdaPorudzbine"/></strong></div>
        </div>
    </div>
</div>

<div class="site-section">
    <div class="container">
        <form:form method="post" id="formaCartCheckout" action="${pageContext.request.contextPath}/PayPal/openPayPal"  modelAttribute="orderDto" onsubmit="return plati(event)">
            <div class="row">
                <!--deo za podatke-->

                <div class="col-md-6 mb-5 mb-md-0">


                    <h2 class="h3 mb-3 text-black"><fmt:message key="cart.checkout.vasiPodaci"/></h2>
                    <div class="p-3 p-lg-5 border">

                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="c_fname" class="text-black"><fmt:message key="ime"/><span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.firstname}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.firstname" disabled="true"/>

                            </div>
                            <div class="col-md-6">
                                <label for="c_lname" class="text-black"><fmt:message key="prezime"/> <span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.lastname}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.lastname" disabled="true"/>

                                <!--<input type="text" class="form-control" id="c_lname" name="c_lname">-->
                            </div>
                        </div>



                        <div class="form-group row">
                            <div class="col-md-12">
                                <label for="c_address" class="text-black"><fmt:message key="adresa"/> <span class="text-danger">*</span></label>
                                <!--<input type="text" class="form-control" id="c_address" name="c_address" placeholder="Ulica, broj i ulaz">-->

                                <form:input  value="${sessionScope.loginUser.adress}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.adress" disabled="true"/>

                            </div>
                        </div>



                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="c_state_country" class="text-black"><fmt:message key="grad"/> <span class="text-danger">*</span></label>
                                <!--<input type="text" class="form-control" id="c_state_country" name="c_state_country">-->
                                <form:input  value="${sessionScope.loginUser.city}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.city" disabled="true"/>

                            </div>
                            <div class="col-md-6">
                                <label for="c_postal_zip" class="text-black"><fmt:message key="postanskiBroj"/> <span class="text-danger">*</span></label>
                                <!--<input type="text" class="form-control" id="c_postal_zip" name="c_postal_zip">-->
                                <form:input  value="${sessionScope.loginUser.zip}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.zip" disabled="true"/>


                            </div>
                        </div>

                        <div class="form-group">
                            <label for="c_country" class="text-black"><fmt:message key="drzava"/> <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" value="Srbija" id="c_fname" name="c_fname" disabled="true">
                        </div>

                        <div class="form-group row mb-5">
                            <div class="col-md-6">
                                <label for="c_email_address" class="text-black"><fmt:message key="email"/> <span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.email}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.email" disabled="true"/>


                            </div>
                            <div class="col-md-6">
                                <label for="c_phone" class="text-black"><fmt:message key="kontaktTelefon"/> <span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.phoneNumber}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.phoneNumber" disabled="true"/>

                            </div>
                                </div>
                                
                                
                                
                                 <div class="form-group row mb-5">
                            
                            <div class="col-md-6">
                                    <a href="${pageContext.request.contextPath}/user/profile" class="btn-lg btn-block"><fmt:message key="cart.checkout.izmeniPodatke"/> ></a>
                                
                            </div>
                                </div>
                                
                                
                            </div>
                        </div>
                <!--podaci do ovde-->

                <div class="col-md-6">

                    <c:set var="total" scope="session" value="0" />

                    <div class="row mb-5">
                        <div class="col-md-12">
                            <h2 class="h3 mb-3 text-black"><fmt:message key="cart.checkout.vasaPorudzbina"/></h2>
                            <div class="p-3 p-lg-5 border">
                                <table class="table site-block-order-table mb-5">
                                    <thead>
                                    <th><fmt:message key="products.proizvod"/></th>
                                    <th><fmt:message key="cart.checkout.iznos"/></th>
                                    </thead>
                                    <tbody>
                                        <!-- for iz carta za sve proizovode -->
                                        <c:forEach var="i" items="${sessionScope.cart}">
                                            <c:set var="subtotal" scope="session" value="${i.getQuantity()*i.getItemPrice()}" />
                                            <c:set var="total" scope="session" value="${total+subtotal}" />
                                            <tr>
                                                <td>${i.getProduct().getProductName()} <strong class="mx-2">x</strong>   ${i.getQuantity()}</td>
                                                <td><c:out value="${subtotal}" /> € </td>
                                            </tr>
                                            <!-- do ovde -->
                                        </c:forEach>
                                        <tr>
                                            <td class="text-black font-weight-bold"><strong><fmt:message key="cart.zaUplatu"/></strong></td>
                                            <!--<td class="text-black font-weight-bold"><strong><c:out value="${total}" /> € </strong></td>-->
                                            <td class="text-black font-weight-bold">
                                            
                                            <strong class="text-black">
                                    <!-- <c:out value="${total}" /> €</strong> -->
                                    <fmt:formatNumber type = "number" 
                                  maxFractionDigits = "2" value = "${total}" />
                                    €</strong>
                                    </td>
                                        </tr>
                                    </tbody>
                                </table>

                                <div class="border p-3 mb-3">

                                    <p style="color: black"><b><fmt:message key="cart.checkout.izaberiteNacinPlacanja"/></b></p>              

                                    <form class="formRadioButtons">
                                        <div class="inputGroupRadioButtons">
                                            <input id="radio1" name="radio" type="radio"/>
                                            <label for="radio1"><fmt:message key="cart.checkout.pouzecem"/></label>
                                        </div>
                                        <div class="inputGroupRadioButtons">
                                            <input id="radio2" name="radio" type="radio"/>
                                            <label for="radio2"><fmt:message key="cart.checkout.paypal"/></label>
                                        </div>
                                    </form>
                                </div>


                                <div class="form-group">
                                    <button class="btn btn-primary btn-lg btn-block"><fmt:message key="cart.checkout.zavrsiPorudzbinu"/></button>
                                </form:form>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- </form> -->
    </div>
</div>

<%@include file="../footer.jsp" %>