<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <%@include file="WEB-INF/pages/header.jsp" %>

<div class="site-blocks-cover" data-aos="fade">
      <div class="container">
        <div class="row">
          <div class="col-md-6 ml-auto order-md-2 align-self-start">
            <div class="site-block-cover-content">
              <h2 class="sub-title"><fmt:message key="index.noviDuvackiInstrumenti"/></h2>
              <h1><fmt:message key="index.letnjaRasprodaja"/></h1>
              <p><a href="${pageContext.request.contextPath}/product/all?category=DUVACKI" class="btn btn-black rounded-0"><fmt:message key="index.detaljnije" /></a></p>
            </div>
          </div>
          <div class="col-md-6 order-1 align-self-end">
            <img src="resursi/images/r.jpg" alt="Image" class="img-fluid">
          </div>
        </div>
      </div>
    </div>

    <%@include file="WEB-INF/pages/najpopularnijeKategorije.jsp" %>



    <div class="site-section">
      <div class="container">
        <div class="row">
          <div class="title-section mb-5 col-12">
            <h2 class="text-uppercase"><fmt:message key="index.najpopularnijiProizvodi"/></h2>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-4 col-md-6 item-entry mb-4">
            <a href="#" class="product-item md-height400px bg-gray d-block">
              <img src="resursi/images/instrumenti/violina.png" alt="Image" class="img-fluid" id="pojedinacanProizvod">
            </a>
            <h2 class="item-title"><a href="#">Violina</a></h2>
            <strong class="item-price">2 300.00 RSD</strong>
          </div>
          <div class="col-lg-4 col-md-6 item-entry mb-4">
            <a href="#" class="product-item md-height400px bg-gray d-block">
              <img src="resursi/images/instrumenti/harmonika.png" alt="Image" class="img-fluid" id="pojedinacanProizvod">
            </a>
            <h2 class="item-title"><a href="#">Harmonika</a></h2>
            <strong class="item-price"><del>5 600.00 RSD</del> 4 600 RSD</strong>
          </div>

          <div class="col-lg-4 col-md-6 item-entry mb-4">
            <a href="#" class="product-item md-height400px bg-gray d-block">
              <img src="resursi/images/instrumenti/ukulele.png" alt="Image" class="img-fluid">
            </a>
            <h2 class="item-title"><a href="#">Ukulele</a></h2>
            <strong class="item-price">1 200 RSD</strong>
          </div>



        </div>
      </div>
    </div>


    <div class="site-blocks-cover inner-page py-5" data-aos="fade" id="donjiCover">
      <div class="container">
        <div class="row">
          <div class="col-md-6 ml-auto order-md-2 align-self-start">
            <div class="site-block-cover-content">
              <h2 class="sub-title"></h2>
              <h1><fmt:message key="index.muzikaNasPokrece"/></h1>
              <p><a href="${pageContext.request.contextPath}/contact" class="btn btn-black rounded-0"><fmt:message key="index.pogledajVise"/></a></p>
            </div>
          </div>
          <div class="col-md-6 order-1 align-self-end">
            <img src="resursi/images/m 6.jpg" alt="Image" class="img-fluid">
          </div>
        </div>
      </div>
    </div>
<%@include file="../WEB-INF/pages/footer.jsp" %>

</html>