
/////////////////////////////////////////////////////////////////////////////////
///////////////////         Traduccion Ventana Inicio         ///////////////////
/////////////////////////////////////////////////////////////////////////////////

var Ven_Inicio = CrearVentana("#ff0000",500,500,"VentanaUno");

var Cont1_Inicio = Ven_Inicio.CrearContenedor(400, 400, "#00FF00", falso, 20, 20);



////////////////// Valores de Nombre
cont1_inicio.CrearTexto("Arial", 14, "#000000", 10, 20, falso, falso, "Nombre");
cont1_inicio.CrearCajaTexto(30, 150, "Arial", 14, "#000000", 70, 20, falso, falso, "Ingrese aqui su nombre", "CTNombre");

////////////////// Valores de Correo
cont1_inicio.CrearTexto("Arial", 14, "#000000", 10, 50, falso, falso, "Correo");
cont1_inicio.CrearCajaTexto(30, 150, "Arial", 14, "#000000", 70, 50, falso, falso, "Ingrese aqui su correo", "CTCorreo");

