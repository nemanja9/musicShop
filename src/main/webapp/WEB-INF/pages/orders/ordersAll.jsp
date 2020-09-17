<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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






<c:if test="${!empty orders}">
    <div class="table-responsive">
        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
            <thead>
                <tr>
                    <th><fmt:message key="orders.id"/></th>
                    <th><fmt:message key="orders.status"/></th>
                    <th><fmt:message key="orders.datumPorudzbine"/></th>
                    <th><fmt:message key="orders.datumPlacanja"/></th>
                    <th><fmt:message key="orders.datumSlanja"/></th>
                    <th><fmt:message key="orders.idPlacanja"/></th>
                    <th><fmt:message key="orders.tokenPlacanja"/></th>
                    <th><fmt:message key="orders.korisnik"/></th>
                    <th><fmt:message key="orders.stavke"/></th>
                    <th><fmt:message key="orders.ukupanIznos"/></th>
                    <th><fmt:message key="orders.opcije"/></th>
                </tr>
            </thead>
            <tfoot>
                <tr>
                    <th><fmt:message key="orders.id"/></th>
                    <th><fmt:message key="orders.status"/></th>
                    <th><fmt:message key="orders.datumPorudzbine"/></th>
                    <th><fmt:message key="orders.datumPlacanja"/></th>
                    <th><fmt:message key="orders.datumSlanja"/></th>
                    <th><fmt:message key="orders.idPlacanja"/></th>
                    <th><fmt:message key="orders.tokenPlacanja"/></th>
                    <th><fmt:message key="orders.korisnik"/></th>
                    <th><fmt:message key="orders.stavke"/></th>
                    <th><fmt:message key="orders.ukupanIznos"/></th>
                    <th><fmt:message key="orders.opcije"/></th>
                </tr>
            </tfoot>
            <tbody>
                <c:forEach items="${orders}" var="o">
                    <tr>
                        <td>${o.getOrderId()}</td>
                        <td>${o.getOrderStatus()}</td>
                        <td>${o.getOrderDate()}</td>
                        <td>${o.getPaidDate()}</td>
                        <td>${o.getShippedDate()}</td>
                        <td>${o.getPaymentId()}</td>
                        <td>${o.getToken()}</td>
                        <td>${o.getUserDto().getFirstname()}  ${o.getUserDto().getLastname()}</td>

                        <td>
                            <c:set var="total" scope="session" value="0" />
                            <c:forEach items="${o.getOrderItems()}" var="i">

                                <c:set var="subTotal" scope="session" value="${i.getQuantity()*i.getItemPrice()}" />

                                ${i.getProduct().getProductName()} [${i.getQuantity()}] x ${i.getItemPrice()} € =  <c:out value="${subTotal}" /> €<br>
                                <c:set var="total" scope="session" value="${total+subTotal}" />
                            </c:forEach>

                        </td>
                        <td>
                            <fmt:formatNumber type = "number" 
                                  maxFractionDigits = "2" value = "${total}" />
                                    €
                            
                            
                            <!-- <c:out value="${total}" /> € </td>-->


                        <td style="text-align:center">
                            <a href="${pageContext.request.contextPath}/admin/orders/edit/${o.getOrderId()}">
                            <button type="button" class="btn btn-warning" onclick="" style="width: 115px;"> <i class="fas fa-user-minus"></i> <fmt:message key="orders.izmeniStatus"/></button>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/orders/deleteOrder/${o.getOrderId()}">
                                <button type="button" class="btn btn-danger" style="width: 115px;" onclick="return confirm('Ako obrišete ovu porudžbinu, obrisaće se i sve njene stavke. Da li ste sigurni?')">
                                    <i class="fas fa-user-minus"></i> <fmt:message key="orders.obrisi"/></button></a>


                        </td>
                   </tr>


                </c:forEach>
            </tbody>
        </table>
    </c:if>


    <%@include file="../footer.jsp" %>