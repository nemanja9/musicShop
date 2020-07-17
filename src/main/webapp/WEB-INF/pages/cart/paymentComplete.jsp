<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>



<div class="bg-light py-3">
      <div class="container">
        <div class="row">
          <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}">Početna</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">Porudžbina primljena</strong></div>
        </div>
      </div>
    </div>  

    <div class="site-section">
      <div class="container">
        <div class="row">
          <div class="col-md-12 text-center">
            <span class="icon-check_circle display-3 text-success"></span>
            <h2 class="display-3 text-black">Hvala na porudžbini!</h2>
            <p class="lead mb-5">Uskoro možete očekivati naručene prozvode na vašoj adresi
            </p>
            <p><a href="${pageContext.request.contextPath}/product/all" class="btn btn-sm height-auto px-4 py-3 btn-primary">Nazad na sve proizvode</a></p>
          </div>
        </div>
      </div>
    </div>


<%@include file="../footer.jsp" %>