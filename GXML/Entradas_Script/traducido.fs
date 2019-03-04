

/////////////////////////////////////////////////////////////////////////////////
///////////////////         Traduccion Ventana Inicio         ///////////////////
/////////////////////////////////////////////////////////////////////////////////


var numero1 = 1000;
funcion metodo1()
{
    imprimir(numero1);
}

var Ven_Inicio = CrearVentana("#ff0000",500,500,"VentanaUno");

var Cont1_Inicio = Ven_Inicio.CrearContenedor(200, 400, "#00FF00", verdadero, 20, 20);



////////////////// Valores de Nombre
cont1_inicio.CrearTexto("Arial", 14, "#000000", 10, 20, falso, falso, "Nombre");
cont1_inicio.CrearCajaTexto(30, 150, "Arial", 14, "#000000", 70, 20, falso, falso, "Ingrese aqui su nombre", "CTNombre");

////////////////// Valores de Correo
cont1_inicio.CrearTexto("Arial", 14, "#000000", 10, 50, falso, falso, "Correo");
cont1_inicio.CrearCajaTexto(30, 150, "Arial", 14, "#000000", 70, 50, falso, falso, "Ingrese aqui su correo", "CTCorreo");



/////////////////// Contenedor ContBtn
var ContBtn_Inicio = Ven_Inicio.CrearContenedor(100, 200, "#FFFF00", falso, 10, 220);


/////////////////// Boton enviar
Var btnIngresar_Inicio = ContBtn_Inicio.CrearBoton("Arial", 12, "#000000", 25, 30, metodo1(), "Ingresar", 50, 70);

btnIngresar_Inicio.AlClic(metodo1());

