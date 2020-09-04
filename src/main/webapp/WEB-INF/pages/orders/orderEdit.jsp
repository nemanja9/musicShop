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
                <h2 class="h3 mb-3 text-black">Pregled porudzbine sa ID ${order.getOrderId()}</h2>
                <p class="errorMsgTitle">${message}</p>

                <form:form action="${pageContext.request.contextPath}/admin/order/submitStatusChange" method="post" modelAttribute="order">


                    <div class="p-3 p-lg-5 border">



                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="orderId" class="text-black">orderId<span class="text-danger">*</span></label>
                                <form:input  value="" type="text" class="form-control" id="orderId" name="orderId" path="orderId"  disabled="true"/>

                            </div>
                            <div class="col-md-6">
                                <label for="orderDate" class="text-black">orderDate <span class="text-danger">*</span></label>
                                <form:input value="" type="text" class="form-control" id="orderDate" name="orderDate" path="orderDate"  disabled="true"/>

                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="paymentId" class="text-black">paymentId<span class="text-danger">*</span></label>
                                <form:input  value="" type="text" class="form-control" id="paymentId" name="paymentId" path="paymentId"  disabled="true"/>

                            </div>
                            <div class="col-md-6">
                                <label for="c_lname" class="text-black">paidDate <span class="text-danger">*</span></label>
                                <form:input  value="${paidDate}" type="text" class="form-control" id="c_fname" name="c_fname" path="paidDate"  disabled="true"/>

                            </div>
                        </div>


                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="c_fname" class="text-black">token<span class="text-danger">*</span></label>
                                <form:input  value="${token}" type="text" class="form-control" id="c_fname" name="c_fname" path="token"  disabled="true"/>

                            </div>
                            <div class="col-md-6">
                                <label for="c_lname" class="text-black">Korisnik <span class="text-danger">*</span></label>
                                <form:input  value="${order.getUserDto().getFirstname()} ${order.getUserDto().getLastname()} " type="text" class="form-control" id="c_fname" name="c_fname" path="userDto.firstname" disabled="true"/>

                            </div>

                        </div>
                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="c_fname" class="text-black">Status<span class="text-danger">*</span></label>
                                <form:input  value="" type="text" class="form-control" id="c_fname" name="c_fname" path="orderStatus"  disabled="true"/>

                            </div>


                        </div>





                        <div class="form-group row mb-5">

                            <div class="col-md-6">

                            </div>
                        </div>


                    </div>  




                </form:form>



            </div>





            <div class="col-md-5 ml-auto">
                <h2 class="h3 mb-3 text-black"> <br> </h2>
                <div class="p-4 border mb-3">
                    <div class="block-5 mb-5" id="paddingGornji">


                        <!--prvi collapsable-->
                        <div class="border p-3 mb-5">
                            <h3 class="h6 mb-0"><a class="d-block" data-toggle="collapse" href="#collapsepaypal" role="button" aria-expanded="false" aria-controls="collapsepaypal">Stavke porudzbine</a></h3>

                            <div class="collapse" id="collapsepaypal">
                                <div class="py-2">

                                    <table class="table site-block-order-table mb-5">
                                        <thead>
                                        <th>Proizvod</th>
                                        <th>Iznos</th>
                                        </thead>
                                        <tbody>
                                            <!-- for iz carta za sve proizovode -->
                                            <c:forEach var="i" items="${order.orderItems}">
                                                <c:set var="subtotal" scope="session" value="${i.getQuantity()*i.getItemPrice()}" />
                                                <c:set var="total" scope="session" value="${total+subtotal}" />
                                                <tr>
                                                    <td>${i.getProduct().getProductName()} <strong class="mx-2">x</strong>   ${i.getQuantity()}</td>
                                                    <td><c:out value="${subtotal}" /> € </td>
                                                </tr>
                                                <!-- do ovde -->
                                            </c:forEach>
                                            <tr>
                                                <td class="text-black font-weight-bold"><strong>Ukupno za uplatu</strong></td>
                                                <td class="text-black font-weight-bold"><strong><c:out value="${total}" /> € </strong></td>
                                            </tr>
                                        </tbody>
                                    </table>


                                </div>
                            </div>
                        </div>
                        <!--prvi collapsable do ovde-->
                        <!--drugi collapsable--> 

                        <div class="border p-3 mb-3">
                            <h3 class="h6 mb-0"><a class="d-block" data-toggle="collapse" href="#collapsecheque" role="button" aria-expanded="false" aria-controls="collapsecheque">Promena statusa porudzbine</a></h3>

                            <div class="collapse" id="collapsecheque">
                                <div class="py-2">
                                    <br>
                                    <form method="post" action="${pageContext.request.contextPath}/admin/orders/updateStatus">
                                        <label for="novStatus">Izaberite nov status: </label>
                                        <select id="cars" name="novStatus">
                                            <option value="0">Neplaceno</option>
                                            <option value="1">Placeno</option>
                                            <option value="2">Spakovano</option>
                                            <option value="3">Poslato</option>
                                            <option value="4">Isporuceno</option>
                                        </select>
                                        <input type="hidden" value="${order.orderId}" id="id" name="id">
                                        <br>
                                        <br>
                                        <button type="submit" class="btn btn-warning" style="width: 115px;">Primeni</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <!--drugi collapsable do ovde-->


                    </div>
                </div></div>




        </div>
    </div>
</div>






<%@include file="../footer.jsp" %>