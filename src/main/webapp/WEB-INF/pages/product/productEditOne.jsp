<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../header.jsp" %>

<div class="site-section">
    <div class="container">
        <div class="row">         
            <div class="col-sm-10">
                <h2 class="h3 mb-3 text-black">Izmena proizvoda "${product.productName}"</h2>
                <p>${message}</p>
                <div class="p-3 p-lg-5 border">
                    <form:form action="${pageContext.request.contextPath}/product/edit/save" method="post" modelAttribute="product" enctype="multipart/form-data">
                        <div class="text-black">Kategorija proizvoda </div>
                        <form:select class="form-control" path="category">
                            <form:option value="" label="Izaberite kategoriju" />
                            <form:options items="${requestScope.categoryList}" />
                        </form:select>
                        <div><form:errors path="category"></form:errors></div>

                            <div class="text-black">Product name </div>
                            <div>
                            <form:input class="form-control" value="${requestScope.productName}" type="text" path="productName" id="productName"/>
                            <div><form:errors path="productName"></form:errors></div>
                            </div>
                            
                            <div class="text-black">Product price </div>
                            <div>
                            <form:input class="form-control" value="${requestScope.product.price}" type="text" path="price" id="price"/>
                            <div><form:errors path="price"></form:errors></div>
                            </div>
                            
                            <div class="text-black">Product description </div>
                            <div>
                            <form:input class="form-control" type="text" value="${requestScope.product.description}" path="description" id="description"/>
                            <div><form:errors path="description"></form:errors></div>
                            </div>
                            
                            <div class="text-black">Product manufacturer </div>
                            <div>
                            <form:input class="form-control" type="text" path="manufacturer" value="${requestScope.product.manufacturer}" id="manufacturer"/>
                            <div><form:errors path="manufacturer"></form:errors></div>
                            </div>
                            
                            <form:hidden path="productId" value="${requestScope.product.productId}" id="productId"/>
                            <form:hidden path="imgPath" value="${requestScope.product.imgPath}" id="imgPath"/>
                            <div class="text-black">Product img </div>
                            <div>


                                <div class="upload-btn-wrapperr">
                                    <button class="btn-upload-img">Upload a file</button>
                                <form:input id="img" path="img" type="file"/>
                            </div>

                            <div><form:errors path="img"></form:errors></div>
                            </div>
                            <br>
                            <button class="btn btn-primary btn-lg btn-block" id="save">Sacuvaj izmene</button>
                    </form:form>

                </div>

            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp" %>