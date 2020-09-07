<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="bg-light py-3">
    <div class="container">
        <div class="row">
            <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}">Početna</a> <span
                    class="mx-2 mb-0">/</span> <strong class="text-black">Korpa</strong></div>
        </div>
    </div>
</div>

<div class="site-section">
    <div class="container">
        <div class="row mb-5">
            <form class="col-md-12" method="post">
                <div class="site-blocks-table">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th class="product-thumbnail">Proizvod</th>
                                <th class="product-name">Naziv</th>
                                <th class="product-price">Cena</th>
                                <th class="product-quantity">Količina</th>
                                <th class="product-total">Ukupno</th>
                                <th class="product-remove">Brisanje</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="total" scope="session" value="0" />
                            <!-- ovde ide for za cart -->
                            <c:forEach var="i" items="${sessionScope.cart}">
                                <tr>
                                    <td class="product-thumbnail">
                                        <img
                                            src="${pageContext.request.contextPath}/resursi/images${i.getProduct().getImgPath()}"
                                            alt="Image" class="img-fluid">
                                    </td>
                                    <td class="product-name">
                                        <h2 class="h5 text-black">${i.getProduct().getProductName()}</h2>
                                    </td>

                                    <td>${i.getProduct().getPrice()} </td>
                                    <td style="text-align: center;max-width: 60px">



                                        <div class="input-group mb-3" style="max-width: 120px;">
                                            <div class="input-group-prepend">
                                                <a href="${pageContext.request.contextPath}/cart/minus?id=${i.getProduct().getProductId()}">
                                                <button class="btn btn-outline-primary js-btn-minus" type="button">&minus;</button>
                                                </a>
                                            </div>
                                                <input type="text" class="form-control text-center" value="${i.getQuantity()}" disabled="true" placeholder="" aria-label="Example text with button addon" aria-describedby="button-addon1">
                                            <div class="input-group-append">
                                                <a href="${pageContext.request.contextPath}/cart/plus?id=${i.getProduct().getProductId()}">
                                                <button class="btn btn-outline-primary js-btn-plus" type="button">&plus;</button>
                                                </a>
                                            </div>
                                        </div>
                                    </td>
                                    <c:set var="subtotal" scope="session" value="${i.getQuantity()*i.getProduct().getPrice()}" />
                                    <c:set var="total" scope="session" value="${total+subtotal}" />
                                    <td>
                                        <c:out value="${subtotal}" /> € </td>
                                    <td>


                                        <form:form method="post" action="${pageContext.request.contextPath}/cart" modelAttribute="orderItemDto">
                                            <form:hidden value="${i.getProduct().getProductId()}" path="product.productId" />
                                            <button class="btn btn-primary height-auto btn-sm">X</button>
                                        </form:form>


                                    </td>
                                </tr>

                            </c:forEach>

                            <!-- do ovde -->


                        </tbody>
                    </table>
                </div>
            </form>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="row mb-5">

                    <div class="col-md-6">
                        <a href="${pageContext.request.contextPath}/product/all">
                            <button class="btn btn-outline-primary btn-sm btn-block">Nastavi kupovinu</button></a>
                    </div>
                </div>
                <div class="row">


                </div>
            </div>
            <div class="col-md-6 pl-5">
                <div class="row justify-content-end">
                    <div class="col-md-7">
                        <div class="row">
                            <div class="col-md-12 text-right border-bottom mb-5">
                                <h3 class="text-black h4 text-uppercase">Za uplatu</h3>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <span class="text-black">Ukupno</span>
                            </div>
                            <div class="col-md-6 text-right">
                                <strong class="text-black">
                                    <c:out value="${total}" /> €</strong>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <button class="btn btn-primary btn-lg btn-block"
                                        onclick="window.location = '${pageContext.request.contextPath}/cart/checkout'" <c:if test = "${sessionScope.cart == null || sessionScope.cart.size() < 1}">disabled="true"</c:if>>Nastavi</button>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

                                        
                                        <!--odavde ide footer, ne stavljamo ga preko importa jer nam ne trebaju svi js fajlovi-->

<footer class="site-footer custom-border-top">
    <div class="container">


        <div class="copyright text-center my-auto" id="">
            <span>Copyright &copy; AIN Music 2020</span>
        </div>



</footer>

</div>

<script src="${pageContext.request.contextPath}/resursi/js/get.js"></script>      

<script src="${pageContext.request.contextPath}/resursi/js/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/resursi/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resursi/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resursi/js/owl.carousel.min.js"></script>



<script src="${pageContext.request.contextPath}/resursi/js/modal.js"></script>




</body>

</html>

