<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div class="bg-light py-3">
    <div class="container">
        <div class="row">
            <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}">Početna</a> <span class="mx-2 mb-0">/</span> <a href="${pageContext.request.contextPath}/cart">Korpa</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">Potvrda porudžbine</strong></div>
        </div>
    </div>
</div>

<div class="site-section">
    <div class="container">
<form:form method="post" action="${pageContext.request.contextPath}/PayPal/openPayPal"  modelAttribute="orderDto">
        <div class="row">
            <!--deo za podatke-->

            <div class="col-md-6 mb-5 mb-md-0">
                

                    <h2 class="h3 mb-3 text-black">Vaši podaci</h2>
                    <div class="p-3 p-lg-5 border">

                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="c_fname" class="text-black">Ime<span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.firstname}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.firstname" />

                            </div>
                            <div class="col-md-6">
                                <label for="c_lname" class="text-black">Prezime <span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.lastname}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.lastname" />

                                <!--<input type="text" class="form-control" id="c_lname" name="c_lname">-->
                            </div>
                        </div>



                        <div class="form-group row">
                            <div class="col-md-12">
                                <label for="c_address" class="text-black">Adresa <span class="text-danger">*</span></label>
                                <!--<input type="text" class="form-control" id="c_address" name="c_address" placeholder="Ulica, broj i ulaz">-->

                                <form:input  value="${sessionScope.loginUser.adress}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.adress" />

                            </div>
                        </div>



                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="c_state_country" class="text-black">Grad <span class="text-danger">*</span></label>
                                <!--<input type="text" class="form-control" id="c_state_country" name="c_state_country">-->
                                <form:input  value="${sessionScope.loginUser.city}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.city" />

                            </div>
                            <div class="col-md-6">
                                <label for="c_postal_zip" class="text-black">Poštanski broj <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="c_postal_zip" name="c_postal_zip">

                            </div>
                        </div>

                        <div class="form-group">
                            <label for="c_country" class="text-black">Država <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" value="Srbija" id="c_fname" name="c_fname" disabled="true">
                        </div>

                        <div class="form-group row mb-5">
                            <div class="col-md-6">
                                <label for="c_email_address" class="text-black">Email adresa <span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.email}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.email"/>


                            </div>
                            <div class="col-md-6">
                                <label for="c_phone" class="text-black">Kontakt telefon <span class="text-danger">*</span></label>
                                <form:input  value="${sessionScope.loginUser.phoneNumber}" type="text" class="form-control" id="c_fname" name="c_fname" path="UserDto.phoneNumber"/>

                            </div>
                        </div>
                    </div>
                </div>
                <!--podaci do ovde-->
            
            <div class="col-md-6">

                <c:set var="total" scope="session" value="0" />

                <div class="row mb-5">
                    <div class="col-md-12">
                        <h2 class="h3 mb-3 text-black">Vaša porudžbina</h2>
                        <div class="p-3 p-lg-5 border">
                            <table class="table site-block-order-table mb-5">
                                <thead>
                                <th>Proizvod</th>
                                <th>Iznos</th>
                                </thead>
                                <tbody>
                                    <!-- for iz carta za sve proizovode -->
                                    <c:forEach var="i" items="${sessionScope.cart}">
                                        <c:set var="subtotal" scope="session" value="${i.getQuantity()*i.getProduct().getPrice()}" />
                                        <c:set var="total" scope="session" value="${total+subtotal}" />
                                        <tr>
                                            <td>${i.getProduct().getProductName()} <strong class="mx-2">x</strong>   ${i.getQuantity()}</td>
                                            <td><c:out value="${subtotal}" /> RSD </td>
                                        </tr>
                                        <!-- do ovde -->
                                    </c:forEach>
                                    <tr>
                                        <td class="text-black font-weight-bold"><strong>Ukupno za uplatu</strong></td>
                                        <td class="text-black font-weight-bold"><strong><c:out value="${total}" /> RSD </strong></td>
                                    </tr>
                                </tbody>
                            </table>

                            <div class="border p-3 mb-3">
                                <h3 class="h6 mb-0"><a class="d-block" data-toggle="collapse" href="#collapsebank" role="button" aria-expanded="false" aria-controls="collapsebank">Plaćanje pouzećem</a></h3>

                                <div class="collapse" id="collapsebank">
                                    <div class="py-2">
                                        <p class="mb-0">Iznos za uplatu plaćate kuriru kada vam isporuči robu.</p>
                                    </div>
                                </div>
                            </div>



                            <div class="border p-3 mb-5">
                                <h3 class="h6 mb-0">
                                    <a class="d-block" data-toggle="collapse" href="#collapsepaypal" role="button" aria-expanded="false" aria-controls="collapsepaypal">Plaćanje preko Paypal</a></h3>

                                <div class="collapse" id="collapsepaypal">
                                    <div class="py-2">
                                        <p class="mb-0">Nakon klika na dugnme 'Završi porudžbinu' bićete preusmereni na sajt Paypal da izvršite uplatu</p>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <button class="btn btn-primary btn-lg btn-block">Završi porudžbinu</button>
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