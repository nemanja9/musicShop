<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../header.jsp" %>





<div class="site-section">
    <div class="container">
        <div class="row">         
            <div class="col-sm-10">
                <h2 class="h3 mb-3 text-black">Dodadavanje novog proizvoda</h2>
                <p class="errorMsgTitle">${message}</p>
                <div class="p-3 p-lg-5 border">
                    <form:form action="${pageContext.request.contextPath}/product/save" method="post" modelAttribute="productDto" enctype="multipart/form-data">
                        <div class="text-black">Kategorija proizvoda </div>


                        <form:select class="form-control" path="category">
                            <form:option value="" label="Izaberite kategoriju" />
                            <form:options items="${categoryList}" />
                        </form:select>
                        <div><form:errors path="category" class="errorMsg"></form:errors></div>

                            <div class="text-black">Naziv </div>
                            <div>
                            <form:input class="form-control" type="text" path="productName" id="productName"/>
                            <div><form:errors path="productName" class="errorMsg" ></form:errors></div>
                            </div>
                            
                            <div class="text-black">Cena </div>
                            <div>
                            <form:input class="form-control" type="text" path="price" id="price"/>
                            <div><form:errors path="price" class="errorMsg"></form:errors></div>
                            </div>
                            
                            <div class="text-black">Opis </div>
                            <div>
                            <form:input class="form-control" type="text" path="description" id="description"/>
                            <div><form:errors path="description" class="errorMsg"></form:errors></div>
                            </div>
                            
                            <div class="text-black">Proizvođač </div>
                            <div>
                            <form:input class="form-control" type="text" path="manufacturer" id="manufacturer"/>
                            <div><form:errors path="manufacturer" class="errorMsg"></form:errors></div>
                            </div>
                            
                            
                            
                            <div class="text-black">Slika </div>
                            <div>


                                <div class="upload-btn-wrapperr">
                                    <button class="btn-upload-img">Izaberite fajl</button>
                                <form:input id="img" path="img" type="file"/>
                            </div>

                            <div><form:errors path="img" class="errorMsg"></form:errors></div>
                            </div>
                            <br>
                            <button class="btn btn-primary btn-lg btn-block" id="save">Sačuvaj</button>
                    </form:form>

                </div>

            </div>
        </div>
    </div>
</div>






<%@include file="../footer.jsp" %>