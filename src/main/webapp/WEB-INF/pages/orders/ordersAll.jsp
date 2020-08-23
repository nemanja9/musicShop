<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>








<c:if test="${!empty orders}">
    <div class="table-responsive">
        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
            <thead>
                <tr>
                    <th>ID porudžbine</th>
                    <th>Datum porudžbine</th>
                    <th>Datum plaćanja</th>
                    <th>Datum slanja</th>
                    <th>ID plaćanja</th>
                    <th>Token plaćanja</th>
                    <th>Korisnik</th>
                    <th>Stavke</th>
                    <th>Ukupan iznos</th>
                    <th>Opcije</th>
                </tr>
            </thead>
            <tfoot>
                <tr>
                    <th>ID porudžbine</th>
                    <th>Datum porudžbine</th>
                    <th>Datum plaćanja</th>
                    <th>Datum slanja</th>
                    <th>ID plaćanja</th>
                    <th>Token plaćanja</th>
                    <th>Korisnik</th>
                    <th>Stavke</th>
                    <th>Ukupan iznos</th>
                    <th>Opcije</th>
                </tr>
            </tfoot>
            <tbody>
                <c:forEach items="${orders}" var="o">
                    <tr>
                        <td>${o.getOrderId()}</td>
                        <td>${o.getOrderDate()}</td>
                        <td>${o.getPaidDate()}</td>
                        <td>${o.getShippedDate()}</td>
                        <td>${o.getPaymentId()}</td>
                        <td>${o.getToken()}</td>
                        <td>${o.getUserDto().getFirstname()}  ${o.getUserDto().getLastname()}</td>

                        <td>
                            <c:set var="total" scope="session" value="0" />
                            <c:forEach items="${o.getOrderItems()}" var="i">

                                <c:set var="subTotal" scope="session" value="${i.getQuantity()*i.getProduct().getPrice()}" />

                                ${i.getProduct().getProductName()}[${i.getQuantity()}] x ${i.getProduct().getPrice()} € =  <c:out value="${subTotal}" /> €<br>
                                <c:set var="total" scope="session" value="${total+subTotal}" />
                            </c:forEach>

                        </td>
                        <td><c:out value="${total}" /> € </td>


                        <td style="text-align:center">
                            <button type="button" class="btn btn-warning" onclick="" style="width: 115px;" data-toggle="modal" data-target="#myModal${o.getOrderId()}"> <i class="fas fa-user-minus"></i> izmeni status</button>
                            <a href="${pageContext.request.contextPath}/adminn/orders/deleteOrder/${o.getOrderId()}"><button type="button" class="btn btn-danger" style="width: 115px;" onclick="return confirm('Ako obrišete ovu porudžbinu, obrisaće se i sve njene stavke. Da li ste sigurni?')">
                                    <i class="fas fa-user-minus"></i> Obriši</button></a>


                            <!--OD MODAL-->
                            <div class="modal fade" id="myModal${o.getOrderId()}" role="dialog">
                                <div class="modal-dialog">

                                    <!-- Modal content-->
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Modal Header</h4>
                                        </div>
                                        <div class="modal-body">
                                            <p>Some text in the modal.</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <!--DO MODAL-->


                        </td>
                    </tr>


                </c:forEach>
            </tbody>
        </table>
    </c:if>


    <%@include file="../footer.jsp" %>