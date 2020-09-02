
function registracija(e) {
    e.preventDefault();

    var box = document.getElementById("agree-term")

    if (box !== null && !box.checked) {

        window.alert("Morate se sloziti sa uslovima koriscenja!")
        return
    }


    document.getElementById("register-form").submit();


}