<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Path"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>


<div class="site-section">
    <div class="container">

        <div class="row mb-5">
            <div class="col-md-9 order-1">

                <div class="row align">
                    <div class="col-md-12 mb-5">
                        <div class="float-md-left">
                            <h2 class="text-black h5">Svi proizvodi</h2>
                        </div>
                        <div class="d-flex">
                            <div class="dropdown mr-1 ml-md-auto">
                            </div>
                            <div class="btn-group">
                                <button type="button" class="btn btn-white btn-sm dropdown-toggle px-4" id="dropdownMenuReference"
                                        data-toggle="dropdown">Sortirtaj po</button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuReference">
                                    <a class="dropdown-item" onclick="setGetParameter('orderby', 'name.asc')" href="#">Nazivu A-Z</a>                                    
                                    <a class="dropdown-item" onclick="setGetParameter('orderby', 'name.desc')" href="#">Nazivu Z-A</a>
                                    <a class="dropdown-item" onclick="setGetParameter('orderby', 'price.asc')" href="#">Ceni, rastuće</a>
                                    <a class="dropdown-item" onclick="setGetParameter('orderby', 'price.desc')" href="#">Ceni, opadajuće</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mb-5">

                    <!-- ovde ide for za proizvod -->
                    <c:forEach items="${requestScope.allProducts}" var="p">
                        <div class="col-lg-6 col-md-6 item-entry mb-4">
                            <a href="${pageContext.request.contextPath}/product/${p.getProductId()}" class="product-item md-height bg-gray d-block">
                                <img src="http://localhost:8080/musicshop/resursi/images/${p.getProductId()}.png" alt="Image" class="img-fluid">
                            </a>
                            <h2 class="item-title"><a href="#">${p.getProductName()}</a></h2>
                            <strong class="item-price">${p.getPrice()} $</strong>
                        </div>
                        <!-- do ovde -->
                    </c:forEach>

                </div>
            </div>

            <!-- filteri sa strane -->
            <div class="col-md-3 order-2 mb-5 mb-md-0">
                <!--kategorije-->
                <div class="border p-4 rounded mb-4">
                    <h3 class="mb-3 h6 text-uppercase text-black d-block">Kategorije</h3>
                    <ul class="list-unstyled mb-0">
                        <c:forEach items="${kategorije}" var="k">

                            <li class="mb-1"><a onclick="setGetParameter('category', '${k}')" href="#" class="d-flex"><span>${k}</span> <span
                                        class="text-black ml-auto">(2,220)</span></a></li>
                                </c:forEach>
                    </ul>
                </div>
                
                <!--kategorije do ovde-->
                
                <!--proizvodjaci-->
                 <div class="border p-4 rounded mb-4">
                    <h3 class="mb-3 h6 text-uppercase text-black d-block">Proizvođači</h3>
                    <ul class="list-unstyled mb-0">
                        <c:forEach items="${proizvodjaci}" var="k">

                            <li class="mb-1"><a onclick="setGetParameter('manufacturer', '${k}')" href="#" class="d-flex"><span>${k}</span> <span
                                        class="text-black ml-auto">(2,220)</span></a></li>
                                </c:forEach>
                    </ul>
                </div>
                <!--proizvodjaci do ovde-->

                <div class="border p-4 rounded mb-4">
                    <div class="mb-4">
                        <h3 class="mb-3 h6 text-uppercase text-black d-block">Filtriraj po ceni</h3>
                        <div id="slider-range" class="border-primary"></div>
                        <input type="text" name="text" id="amount" class="form-control border-0 pl-0 bg-white" disabled="" />
                        <div id="najmanjaCena" style="display: none">132</div>
                        <div id="najvecaCena" style="display: none">${najvecaCena}</div>
                    </div>
                </div>
            </div>
            <!-- kraj filtera -->
        </div>

    </div>
</div>

<div class="site-section">
    <div class="container">
        <div class="title-section mb-5">
            <h2 class="text-uppercase"><span class="d-block">Najpopularnije</span> Kategorije</h2>
        </div>
        <div class="row align-items-stretch">
            <div class="col-lg-8">
                <div class="product-item sm-height full-height bg-gray">
                    <a href="#" class="product-category">Pojacala <span>25 proizvoda</span></a>
                    <img src="${pageContext.request.contextPath}/resursi/images/Kategorija pojacala.png" alt="Image" class="img-fluid">
                </div>
            </div>
            <div class="col-lg-4">
                <div class="product-item sm-height bg-gray mb-4">
                    <a href="#" class="product-category">Gitare <span>15 proizvoda</span></a>
                    <img src="${pageContext.request.contextPath}/resursi/images/123.png" alt="Image" class="img-fluid">
                </div>

                <div class="product-item sm-height bg-gray">
                    <a href="#" class="product-category">Klavijature <span>19 proizvoda</span></a>
                    <img src="${pageContext.request.contextPath}/resursi/images/Kategorija klavijature.png" alt="Image" class="img-fluid">
                </div>
            </div>
        </div>
    </div>
</div>


<div>
    <a href="<c:url value="/product/add"/>">Add new product</a>
</div>
<div>
    <a href="<c:url value="/index.jsp"/>">Back to home page</a>
</div>

<%@include file="../footer.jsp" %>
