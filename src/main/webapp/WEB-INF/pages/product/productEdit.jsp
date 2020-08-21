<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <c:if test="${!empty allProducts}">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                      <th>ID</th>
                      <th>Naziv</th>
                      <th>Cena</th>
                      <th>Slika</th>
                      <th>Kategorija</th>
                      <th>Opis</th>
                      <th>Proizvodjac</th>
                      <th>Opcije</th>
                    </tr>
                    </thead>
                     <tfoot>
                    <tr>
                      <th>ID</th>
                      <th>Naziv</th>
                      <th>Cena</th>
                      <th>Slika</th>
                      <th>Kategorija</th>
                      <th>Opis</th>
                      <th>Proizvodjac</th>
                      <th>Opcije</th>
                    </tr>
                  </tfoot>
                  <tbody>
                      <c:forEach items="${allProducts}" var="p">
                            <tr>
                      <td>${p.productId}</td>
                      <td>${p.productName}</td>
                      <td>${p.price}</td>
                      <td><img src="${pageContext.request.contextPath}/resursi/images${p.getImgPath()}" alt="Image" class="img-fluid"></td>
                      <td>${p.category}</td>
                      <td>${p.manufacturer}</td>
                      <td>${p.description}</td>
                    
                      <td style="text-align:center">
                          <button type="button" class="btn btn-warning" style="width: 115px;" data-toggle="modal" onClick="window.location='${pageContext.request.contextPath}/admin/product/edit/${p.productId}'"><i class="fas fa-user-edit"></i> Izmeni</button>
                      <a href="${pageContext.request.contextPath}/admin/product/deleteProduct/${p.productId}"><button type="button" class="btn btn-danger" style="width: 115px;" onclick="return confirm('Are you sure you want to delete this product?')">
                              <i class="fas fa-user-minus"></i> Obriši</button></a></td>
                    </tr>
                    
                    
                      </c:forEach>
                  </tbody>
                </table>
              </c:if>


<%@include file="../footer.jsp" %>