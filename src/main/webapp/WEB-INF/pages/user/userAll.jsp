<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${!empty users}">
    <div class="table-responsive">
        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Ime</th>
                    <th>Prezime</th>
                    <th>Adresa</th>
                    <th>Grad, ZIP</th>
                    <th>Telefon</th>
                    <th>E posta</th>
                    <th>E posta potvrdjena</th>
                    <th>Opcije</th>
                </tr>
            </thead>
            <tfoot>
                <tr>
                    <th>ID</th>
                    <th>Ime</th>
                    <th>Prezime</th>
                    <th>Adresa</th>
                    <th>Grad, ZIP</th>
                    <th>Telefon</th>
                    <th>E posta</th>
                    <th>E posta potvrdjena</th>
                    <th>Opcije</th>
                </tr>
            </tfoot>
            <tbody>
                <c:forEach items="${users}" var="u">
                    <tr>
                        <td>${u.userId}</td>
                        <td>${u.firstname}</td>
                        <td>${u.lastname}</td>
                        <td>${u.adress}</td>
                        <td>${u.city}, ${u.zip}</td>
                        <td>${u.phoneNumber}</td>
                        <td>${u.email}</td>
                        <td><c:if test="${u.emailConfirmed == 1}">True</c:if> <c:if test="${u.emailConfirmed == 0}">False</c:if></td>

                        <td style="text-align:center">
                            <button type="button" class="btn btn-warning" style="width: 115px;" data-toggle="modal" 
                                        onClick="window.location = '${pageContext.request.contextPath}/admin/user/edit/${u.userId}'">
                                <i class="fas fa-user-edit"></i> vise opcija
                            </button>
                         
                            <a href="${pageContext.request.contextPath}/admin/user/deleteUser/${u.userId}">
                                <button type="button" class="btn btn-danger" style="width: 115px;" onclick="return confirm('Are you sure you want to delete this user?')">
                                    <i class="fas fa-user-minus"></i> Obriši</button></a>
                        </td>
                    </tr>


                </c:forEach>
            </tbody>
        </table>
    </c:if>


    <%@include file="../footer.jsp" %>