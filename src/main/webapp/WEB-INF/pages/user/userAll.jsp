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


<c:if test="${!empty users}">
    <div class="table-responsive">
        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
            <thead>
                <tr>
                    <th><fmt:message key="userId"/></th>
                    <th><fmt:message key="ime"/></th>
                    <th><fmt:message key="prezime"/></th>
                    <th><fmt:message key="adresa"/></th>
                    <th><fmt:message key="grad"/>, <fmt:message key="postanskiBroj"/></th>
                    <th><fmt:message key="kontaktTelefon"/></th>
                    <th><fmt:message key="email"/></th>
                    <th><fmt:message key="emailPotvrdjen"/></th>
                    <th><fmt:message key="orders.opcije"/></th>

                </tr>
            </thead>
            <tfoot>
                <tr>
                    <th><fmt:message key="userId"/></th>
                    <th><fmt:message key="ime"/></th>
                    <th><fmt:message key="prezime"/></th>
                    <th><fmt:message key="adresa"/></th>
                    <th><fmt:message key="grad"/>, <fmt:message key="postanskiBroj"/></th>
                    <th><fmt:message key="kontaktTelefon"/></th>
                    <th><fmt:message key="email"/></th>
                    <th><fmt:message key="emailPotvrdjen"/></th>
                    <th><fmt:message key="orders.opcije"/></th>

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
                                <i class="fas fa-user-edit"></i> <fmt:message key="users.viseOpcija"/>
                            </button>

                            <a href="${pageContext.request.contextPath}/admin/user/deleteUser/${u.userId}">
                                <button type="button" class="btn btn-danger" style="width: 115px;" onclick="return confirm('Are you sure you want to delete ${u.firstname} ${u.lastname}?')">
                                    <i class="fas fa-user-minus"></i> <fmt:message key="users.obrisi"/></button></a>
                        </td>
                    </tr>


                </c:forEach>
            </tbody>
        </table>
    </c:if>


    <%@include file="../footer.jsp" %>