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
                    <a href="#" class="product-item md-height bg-gray d-block">
                        <img src="${pageContext.request.contextPath}/resursi/images${requestScope.productDto.getImgPath()}" alt="Image" class="img-fluid">

                    </a>

                </div>

            </div>
            <div class="col-md-6">
                <!--<p>${message}</p>-->
                <h2 class="text-black">${requestScope.productDto.getProductName()}</h2>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Pariatur, vitae, explicabo? Incidunt facere, natus soluta dolores iusto! Molestiae expedita veritatis nesciunt doloremque sint asperiores fuga voluptas, distinctio, aperiam, ratione dolore.</p>
                <p class="mb-4">Ex numquam veritatis debitis minima quo error quam eos dolorum quidem perferendis. Quos repellat dignissimos minus, eveniet nam voluptatibus molestias omnis reiciendis perspiciatis illum hic magni iste, velit aperiam quis.</p>
                <p><strong class="text-primary h4">${requestScope.productDto.getPrice()} $</strong></p>
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
    </div>

    <div class="site-section block-3 site-blocks-2">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7 site-section-heading text-center pt-4">
                    <h2>Najnoviji proizvodi</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 block-3">
                    <div class="nonloop-block-3 owl-carousel">
                        <div class="item">
                            <div class="item-entry">
                                <a href="#" class="product-item md-height bg-gray d-block">
                                    <img src="${pageContext.request.contextPath}/resursi/images/model_1.png" alt="Image" class="img-fluid">
                                </a>
                                <h2 class="item-title"><a href="#">Smooth Cloth</a></h2>
                                <strong class="item-price"><del>$46.00</del> $28.00</strong>
                                <div class="star-rating">
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="item-entry">
                                <a href="#" class="product-item md-height bg-gray d-block">
                                    <img src="${pageContext.request.contextPath}/resursi/images/prod_3.png" alt="Image" class="img-fluid">
                                </a>
                                <h2 class="item-title"><a href="#">Blue Shoe High Heels</a></h2>
                                <strong class="item-price"><del>$46.00</del> $28.00</strong>

                                <div class="star-rating">
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="item-entry">
                                <a href="#" class="product-item md-height bg-gray d-block">
                                    <img src="${pageContext.request.contextPath}/resursi/images/model_5.png" alt="Image" class="img-fluid">
                                </a>
                                <h2 class="item-title"><a href="#">Denim Jacket</a></h2>
                                <strong class="item-price"><del>$46.00</del> $28.00</strong>

                                <div class="star-rating">
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                </div>

                            </div>
                        </div>
                        <div class="item">
                            <div class="item-entry">
                                <a href="#" class="product-item md-height bg-gray d-block">
                                    <img src="${pageContext.request.contextPath}/resursi/images/prod_1.png" alt="Image" class="img-fluid">
                                </a>
                                <h2 class="item-title"><a href="#">Leather Green Bag</a></h2>
                                <strong class="item-price"><del>$46.00</del> $28.00</strong>
                                <div class="star-rating">
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="item-entry">
                                <a href="#" class="product-item md-height bg-gray d-block">
                                    <img src="${pageContext.request.contextPath}/resursi/images/model_7.png" alt="Image" class="img-fluid">
                                </a>
                                <h2 class="item-title"><a href="#">Yellow Jacket</a></h2>
                                <strong class="item-price">$58.00</strong>
                                <div class="star-rating">
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                    <span class="icon-star2 text-warning"></span>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="../footer.jsp" %>