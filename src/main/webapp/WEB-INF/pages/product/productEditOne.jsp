<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../header.jsp" %>

<c:if test="${not empty uspeh}">
    <div id="myModal" class="modal">

        <div class="modal-content">
            <span class="close">&times;</span>
            <p>${uspeh}</p>
        </div>

    </div>
</c:if>

<div class="site-section">
    <div class="container">
        <div class="row">         
            <div class="col-sm-10">
                <h2 class="h3 mb-3 text-black"><fmt:message key="products.edit.one.izmenaProizvoda"/>"${product.productName}"</h2>
                <p class="errorMsgTitle">${message}</p>
                <div class="p-3 p-lg-5 border">
                    <form:form action="${pageContext.request.contextPath}/admin/product/edit/saveEdit" method="post" modelAttribute="product" enctype="multipart/form-data">
                        <div class="text-black"><fmt:message key="products.kategorija"/> </div>
                        <form:select class="form-control" path="category">

                            <fmt:message key="products.add.izaberiteKategoriju" var="kat"/>
                            <form:option value="" label="${kat}" />
                            <form:options items="${requestScope.categoryList}" />
                        </form:select>
                        <div><form:errors path="category" class="errorMsg"></form:errors></div>

                            <div class="text-black"><fmt:message key="products.naziv"/> </div>
                        <div>
                            <form:input class="form-control" value="${requestScope.productName}" type="text" path="productName" id="productName"/>
                            <div><form:errors path="productName" class="errorMsg"></form:errors></div>
                            </div>

                            <div class="text-black"><fmt:message key="products.cena"/> </div>
                        <div>
                            <form:input class="form-control" value="${requestScope.product.price}" type="text" path="price" id="price"/>
                            <div><form:errors path="price" class="errorMsg"></form:errors></div>
                            </div>

                            <div class="text-black"><fmt:message key="products.opis"/> </div>
                        <div>
                            <form:input class="form-control" type="text" value="${requestScope.product.description}" path="description" id="description"/>
                            <div><form:errors path="description" class="errorMsg"></form:errors></div>
                            </div>

                            <div class="text-black"><fmt:message key="products.proizvodjac"/> </div>
                        <div>
                            <form:input class="form-control" type="text" path="manufacturer" value="${requestScope.product.manufacturer}" id="manufacturer"/>
                            <div><form:errors path="manufacturer" class="errorMsg"></form:errors></div>
                            </div>

                        <form:hidden path="productId" value="${requestScope.product.productId}" id="productId"/>
                        <form:hidden path="imgPath" value="${requestScope.product.imgPath}" id="imgPath"/>
                        <div class="text-black"><fmt:message key="products.slika"/> </div>
                        <div>


                            <div class="upload-btn-wrapperr">
                                <button class="btn-upload-img"><fmt:message key="products.add.izaberiFajl"/></button>
                                <form:input id="img" path="img" type="file"/>
                            </div>

                            <div><form:errors path="img" class="errorMsg"></form:errors></div>
                            </div>
                            <br>
                            <button class="btn btn-primary btn-lg btn-block" id="save"><fmt:message key="products.add.sacuvaj"/></button>
                    </form:form>

                </div>

            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp" %>