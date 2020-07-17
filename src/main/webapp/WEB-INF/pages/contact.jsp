<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>

<div class="site-blocks-cover inner-page"
      style="background-image: url('${pageContext.request.contextPath}/resursi/images/hero_2.jpg'); background-repeat: no-repeat; background-size: cover; background-position: center"
      data-aos="fade">
      <div class="container">
        <div class="row">

        </div>
      </div>
    </div>

    <div class="custom-border-bottom py-3">
      <div class="container">
        <div class="row">
          <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}">Početna</a> <span class="mx-2 mb-0">/</span> <strong
              class="text-black">Kontakt</strong></div>
        </div>
      </div>
    </div>


    <div class="site-section">
      <div class="container">
        <div class="row">         
          <div class="col-md-7">
            <h2 class="h3 mb-3 text-black">Kontaktirajte nas</h2>
            <form action="" method="post" id="formaContact" onsubmit="return kontaktirajteNas(event)">

              <div class="p-3 p-lg-5 border">
                <div class="form-group row">
                  <div class="col-md-6">
                    <label for="c_fname" class="text-black">Ime <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="c_fname" name="c_fname">
                  </div>
                  <div class="col-md-6">
                    <label for="c_lname" class="text-black">Prezime <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="c_lname" name="c_lname">
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-md-12">
                    <label for="c_email" class="text-black">Email <span class="text-danger">*</span></label>
                    <input type="email" class="form-control" id="c_email" name="c_email" placeholder="">
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-md-12">
                    <label for="c_subject" class="text-black">Naslov </label>
                    <input type="text" class="form-control" id="c_subject" name="c_subject">
                  </div>
                </div>

                <div class="form-group row">
                  <div class="col-md-12">
                    <label for="c_message" class="text-black">Poruka </label>
                    <textarea name="c_message" id="c_message" cols="30" rows="7" class="form-control"></textarea>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-lg-12">
                    <input type="submit" class="btn btn-primary btn-lg btn-block" value="Pošalji poruku">
                  </div>
                </div>
              </div>
            </form>
          </div>
          <div class="col-md-5 ml-auto">
            <h2 class="h3 mb-3 text-black">O nama</h2>
            <div class="p-4 border mb-3">
              <div class="block-5 mb-5" id="paddingGornji">
                <!-- <h3 class="footer-heading mb-4">Kontakt podaci</h3> -->
                <ul class="list-unstyled">
                  
                  <li class="phone"><a href="tel://23923929210">+381 11 689 901</a></li>
                  <li class="email">office@AINmusic.com</li>
                  <li class="address">Jove Ilića 154, Voždovac</li>
                </ul>
              </div>
             
              <!-- mapa -->
              <div>
                <iframe width="390" height="400" frameborder="0" src="https://www.bing.com/maps/embed?h=400&w=500&cp=44.772914090533554~20.47477203853014&lvl=17&typ=d&sty=r&src=SHELL&FORM=MBEDV8" scrolling="no">
                </iframe>
                <div style="white-space: nowrap; text-align: center; width: 500px; padding: 6px 0;">
                   <a id="largeMapLink" target="_blank" href="https://www.bing.com/maps?cp=44.772914090533554~20.47477203853014&amp;sty=r&amp;lvl=17&amp;FORM=MBEDLD">Veća mapa</a> &nbsp; | &nbsp;
                   <a id="dirMapLink" target="_blank" href="https://www.bing.com/maps/directions?cp=44.772914090533554~20.47477203853014&amp;sty=r&amp;lvl=17&amp;rtp=~pos.44.772914090533554_20.47477203853014____&amp;FORM=MBEDLD">Navigacija</a>
               </div>
           </div>
              <!-- mapa -->
            </div>
           

          </div>
        </div>
      </div>
    </div>

<%@include file="footer.jsp" %>