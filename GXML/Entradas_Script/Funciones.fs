
funcion MensajeBienvenida(var Nombre){
	Imprimir("Bienvenido al registro" + Nombre + " de Creator XML, porfavor ingrese todos sus datos");
}

funcion MensajeDespedida(var NombreCompleto){
	Imprimir("Gracias por registrarse con nosotros " + NombreCompleto);
}

funcion ackermann(var m, var n) {
    si (m == 0) {
        retornar (n + 1);
    } sino si (m > 0 && n == 0) {
        retornar ackermann(m - 1, 1);
    } sino {
        retornar ackermann(m - 1, ackermann(m, n - 1));
    }
}

