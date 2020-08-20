function qtyMinus(){
    

  const form = document.createElement('form');
  form.method = "post";
  form.action = "";

  for (const key in params) {
    if (params.hasOwnProperty(key)) {
      const hiddenField = document.createElement('input');
      hiddenField.type = 'hidden';
      hiddenField.name = key;
      hiddenField.value = params[key];

      form.appendChild(hiddenField);
    }
  }

  document.body.appendChild(form);
  form.submit();
    
}


function qtyPlus(){
    alert(sessionStorage.getItem("SessionData"));
    
}