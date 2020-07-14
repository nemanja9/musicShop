function setGetParameter(paramName, paramValue) {
    var pocetni = "http://localhost:8080/musicshop/product/all";
    var pocetniPort = pocetni.split("http://localhost:")[1].split("/")[0];

    var url = window.location.href;
    var portStvarni =  url.split("http://localhost:")[1].split("/")[0];
    pocetni = pocetni.replace(pocetniPort,portStvarni)



    if (url == pocetni) {
        url = url + "?" + paramName + "=" + paramValue;
        window.location.href = url;
    }
     var ostatak = url.split("http://localhost:"+portStvarni+"/musicshop/product/all?")[1];

    var pronadjen = false;

    var niz = ostatak.split("&");
    if (niz.length == 1 & niz[0].includes(paramName)) {
        niz[0] = paramName + "=" + paramValue;
        url = pocetni + "?" + niz[0];
        window.location.href = url;
        pronadjen = true;
        window.location.href = url;

        return;
    }
    else {
        for (let i = 0; i < niz.length; i++) {
            if (niz[i].includes(paramName)) {
                url = url.replace(niz[i], paramName + "=" + paramValue);
                pronadjen = true
                window.location.href = url;
            }
        }
        if (pronadjen == false){
            url= url + "&"+ paramName+"="+paramValue;
            window.location.href = url;

        }
    }
}