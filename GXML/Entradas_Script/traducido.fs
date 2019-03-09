

var container = leerGxml("Inicio.gxml");

var arrTexto = container.obtenerporEtiqueta("texto");

imprimir(arrTexto[0].y);


arrTexto[0].y = 77;

imprimir(arrTexto[0].y);

