<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Path"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

        <div class="row mb-5">
            <div class="col-md-9 order-1">

                <div class="row align">
                    <div class="col-md-12 mb-5">
                        <div class="float-md-left">
                            <h2 class="text-black h5"><fmt:message key="products.all.sviProizvodi"/></h2>
                        </div>
                        
                        <div class="d-flex">
                            <div class="dropdown mr-1 ml-md-auto">
                                <c:if test="${!empty paramValues}">
                                <a href="http://localhost:8080/musicshop/product/all">
                                <button type="button" class="btn btn-white btn-sm px-4"><fmt:message key="products.all.resetujFiltere"/></button>
                                </a>
                                </c:if>
                            </div>
                            
                            
                            <div class="btn-group">
                                <button type="button" class="btn btn-white btn-sm dropdown-toggle px-4" id="dropdownMenuReference"
                                        data-toggle="dropdown"><fmt:message key="products.all.sortirajPo"/></button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuReference">
                                    <a class="dropdown-item" onclick="setGetParameter('orderby', 'name.asc')" href="#"><fmt:message key="products.all.nazivuAZ"/></a>                                    
                                    <a class="dropdown-item" onclick="setGetParameter('orderby', 'name.desc')" href="#"><fmt:message key="products.all.nazivuZA"/></a>
                                    <a class="dropdown-item" onclick="setGetParameter('orderby', 'price.asc')" href="#"><fmt:message key="products.all.ceniRastuce"/></a>
                                    <a class="dropdown-item" onclick="setGetParameter('orderby', 'price.desc')" href="#"><fmt:message key="products.all.ceniOpadajuce"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <c:if test="${requestScope.allProducts.size()==0}">
                    <div class="site-blocks-cover inner-page round-me-blur-me"style="background-repeat: no-repeat; background-size: cover; background-position: center"
                         data-aos="fade">
                        <a href="http://localhost:8080/musicshop/product/all" class="product-category-center404">
                            
                        <fmt:message key="products.all.nemaProizvoda"/>
                        </a>
                            
                        <div class="container">
                            <div class="row">
                                <img class="small-me" src="${pageContext.request.contextPath}/resursi/images/404.png">
                            </div>

                        </div>
                    </div>
                </c:if>
                <div class="row mb-5"> 
                    <!-- ovde ide for za proizvod -->
                    <c:forEach items="${requestScope.allProducts}" var="p">


                        <a href="${pageContext.request.contextPath}/product/${p.getProductId()}">
                        <div class="product-card roboto-font">
                            <div class="product-tumb">
                                <img src="${pageContext.request.contextPath}/resursi/images${p.getImgPath()}" alt="">
                            </div>
                            <div class="product-details">
                                <span class="product-catagory">${p.getCategory()}</span>
                                <h4><a href="${pageContext.request.contextPath}/product/${p.getProductId()}">${p.getProductName()} </a></h4>
                               
                                <div class="product-bottom-details">
                                    <div class="product-price">${p.getPrice()} €</div>
                                    <div class="product-links">
                                    </div>
                                </div>
                            </div>

                        </div>
</a>



                            
                            <!-- do ovde -->
                        </c:forEach>

                    </div>
                </div>



                <!-- filteri sa strane -->
                <div class="col-md-3 order-2 mb-5 mb-md-0">
                    <!--kategorije-->
                    <div class="border p-4 rounded mb-4">
                        <h3 class="mb-3 h6 text-uppercase text-black d-block"><fmt:message key="products.all.kategorije"/></h3>
                        <ul class="list-unstyled mb-0">
                            <c:forEach items="${kategorije}" var="k">

                                <li class="mb-1"><a onclick="setGetParameter('category', '${k}')" href="#" class="d-flex"><span>${k}</span> <span
                                            class="text-black ml-auto"></span></a></li>
                                    </c:forEach>
                        </ul>
                    </div>

                    <!--kategorije do ovde-->

                    <!--proizvodjaci-->
                    <div class="border p-4 rounded mb-4">
                        <h3 class="mb-3 h6 text-uppercase text-black d-block"><fmt:message key="products.all.proizvodjaci"/></h3>
                        <ul class="list-unstyled mb-0">
                            <c:forEach items="${proizvodjaci}" var="k">

                                <li class="mb-1"><a onclick="setGetParameter('manufacturer', '${k}')" href="#" class="d-flex"><span>${k}</span> <span
                                            class="text-black ml-auto"></span></a></li>
                                    </c:forEach>
                        </ul>
                    </div>
                    <!--proizvodjaci do ovde-->

                    <div class="border p-4 rounded mb-4">
                        <div class="mb-4">
                            <h3 class="mb-3 h6 text-uppercase text-black d-block"><fmt:message key="products.all.cena"/></h3>
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

    <%--<%@include file="../najpopularnijeKategorije.jsp" %>--%>


    <div class="site-section">
        <div class="container">
            <div class="title-section mb-5">
                <h2 class="text-uppercase"><span class="d-block">Najpopularnije</span> Kategorije</h2>
            </div>
            <div class="row align-items-stretch">
                <div class="col-lg-8">
                    <div class="product-item sm-height full-height bg-gray">
                        <a href="http://localhost:8080/musicshop/product/all?category=POJACALA" class="product-category">Pojačala <span>pogledaj više </span></a>
                        <img src="../resursi/images/Kategorija pojacala.png" alt="Image" class="img-fluid">
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="product-item sm-height bg-gray mb-4">
                        <a href="http://localhost:8080/musicshop/product/all?category=GITARE" class="product-category">Gitare <span>pogledaj više</span></a>
                        <img src="../resursi/images/123.png" alt="Image" class="img-fluid">
                    </div>

                    <div class="product-item sm-height bg-gray">
                        <a href="http://localhost:8080/musicshop/product/all?category=KLAVIJATURE" class="product-category">Klavijature <span>pogledaj više</span></a>
                        <img src="../resursi/images/Kategorija klavijature.png" alt="Image" class="img-fluid">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../footer.jsp" %>
