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
              <h2 class="sub-title">Novi duvački instrumenti</h2>
              <h1>Letnja rasprodaja</h1>
              <p><a href="${pageContext.request.contextPath}/product/all" class="btn btn-black rounded-0">Detaljnije</a></p>
            </div>
          </div>
          <div class="col-md-6 order-1 align-self-end">
            <img src="resursi/images/r.jpg" alt="Image" class="img-fluid">
          </div>
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
              <a href="http://localhost:8080/musicshop/product/all?category=POJAČALA" class="product-category">Pojačala <span>pogledaj više </span></a>
              <img src="resursi/images/Kategorija pojacala.png" alt="Image" class="img-fluid">
            </div>
          </div>
          <div class="col-lg-4">
            <div class="product-item sm-height bg-gray mb-4">
              <a href="http://localhost:8080/musicshop/product/all?category=GITARE" class="product-category">Gitare <span>pogledaj više</span></a>
              <img src="resursi/images/123.png" alt="Image" class="img-fluid">
            </div>

            <div class="product-item sm-height bg-gray">
              <a href="http://localhost:8080/musicshop/product/all?category=KLAVIJATURE" class="product-category">Klavijature <span>pogledaj više</span></a>
              <img src="resursi/images/Kategorija klavijature.png" alt="Image" class="img-fluid">
            </div>
          </div>
        </div>
      </div>
    </div>



    <div class="site-section">
      <div class="container">
        <div class="row">
          <div class="title-section mb-5 col-12">
            <h2 class="text-uppercase">Popularni proizvodi</h2>
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
              <h1>Muzika nas pokreće</h1>
              <p><a href="${pageContext.request.contextPath}/contact" class="btn btn-black rounded-0">Pogledaj više</a></p>
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