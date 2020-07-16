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
                      <!--<td>{o.getUserDto().getFirstname()+ " " + o.getUserDto().getLastname()}</td>-->
                      <td>X</td>
                      <!--obrisati kom i dodati $ kada se resi prob-->
                      <td>
                          <c:set var="total" scope="session" value="0" />
                          <c:forEach items="${o.getOrderItems()}" var="i">
                              ${i.getProduct().getProductName()} x ${i.getQuantity()} <br>
                              <c:set var="total" scope="session" value="${total+i.getQuantity()*i.getProduct().getPrice()}" />
                          </c:forEach>
                          
                        </td>
                      <td><c:out value="${total}" /> EUR </td></td>

                    
                      <td style="text-align:center">
                          <!--<button type="button" class="btn btn-warning" style="width: 115px;" data-toggle="modal" onClick="window.location='${pageContext.request.contextPath}/product/edit/${p.productId}'"><i class="fas fa-user-edit"></i> Izmeni</button>-->
                      <a href="${pageContext.request.contextPath}/orders/deleteOrder/${o.getOrderId()}"><button type="button" class="btn btn-danger" style="width: 115px;" onclick="return confirm('Ako obrišete ovu porudžbinu, obrisaće se i sve njene stavke. Da li ste sigurni?')">
                              <i class="fas fa-user-minus"></i> Obriši</button></a></td>
                    </tr>
                    
                    
                      </c:forEach>
                  </tbody>
                </table>
              </c:if>


<%@include file="../footer.jsp" %>