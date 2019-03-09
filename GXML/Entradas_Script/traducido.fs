




var container = creararraydesdearchivo("Inicio.gxml");

var arrTexto = container[0].obtenerporEtiqueta("texto");

imprimir(arrTexto[0].y);


arrTexto[0].y = 77;

imprimir(arrTexto[0].y);

