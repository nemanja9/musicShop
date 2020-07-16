<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib  uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="../header.jsp" %>

<div class="bg-light py-3">
    <div class="container">
        <div class="row">
            <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}">Početna</a> <span class="mx-2 mb-0">/</span> <a href="${pageContext.request.contextPath}/product/all">Proizvodi</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">${requestScope.productDto.getProductName()}</strong></div>
        </div>
    </div>
</div>  

<div class="site-section">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <div class="item-entry">
                    <a href="#" class="product-item md-height bg-NOgray d-block">
                        <img src="${pageContext.request.contextPath}/resursi/images${requestScope.productDto.getImgPath()}" alt="Image" class="img-fluid">

                    </a>

                </div>

            </div>
            <div class="col-md-6">
                <!--<p>${message}</p>-->
                <h2 class="text-black">${requestScope.productDto.getProductName()}</h2>
                <p> <b>OPIS:</b><br>
                ${requestScope.productDto.getDescription()}
                </p>
                <p class="mb-4"><b>PROIZVOĐAĆ:</b><br>
                                ${requestScope.productDto.getManufacturer()}

                </p>
                <p><strong class="text-primary h4">${requestScope.productDto.getPrice()} €</strong></p>
                <form:form action="${pageContext.request.contextPath}/cart/add" method="post" modelAttribute="orderItemDto">

                    <div class="mb-5">
                        <div class="input-group mb-3" style="max-width: 120px;">
                            <div class="input-group-prepend">
                                <button class="btn btn-outline-primary js-btn-minus" type="button">&minus;</button>
                            </div>

                            <form:input class="form-control text-center" value="1" type="text" path="quantity" aria-label="Example text with button addon" aria-describedby="button-addon1" id="quantity"/>
                            <div class="input-group-append">

                                <button class="btn btn-outline-primary js-btn-plus" type="button">&plus;</button>
                            </div>
                        </div>
                        <form:hidden value="${requestScope.productDto.getProductName()}" path="product.productName"/>
                        <form:hidden value="${requestScope.productDto.getProductId()}" path="product.productId"/>
                        <form:hidden value="${requestScope.productDto.getImgPath()}" path="product.imgPath"/>
                        <form:hidden value="${requestScope.productDto.getPrice()}" path="product.price"/>
                        <form:hidden value="${requestScope.productDto.getDescription()}" path="product.description"/>
                        <form:hidden value="${requestScope.productDto.getManufacturer()}" path="product.manufacturer"/>
                        <button class="buy-now btn btn-sm height-auto px-4 py-3 btn-primary" id="save">Dodaj u korpu</button>
                    </form:form> 


                </div>
            </div>
        </div>
    

    <%@include file="../footer.jsp" %>