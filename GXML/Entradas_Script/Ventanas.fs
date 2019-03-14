importar("/Funciones.fs");

//---------------------------
//---------------------------
//---------------------ventana_228
//---------------------------
//---------------------------
var ventana_228 = crearventana(nulo,nulo,nulo,"Registrar");
ventana_228.alcargar(MensajeBienvenida("Julio "));
ventana_228.alcerrar(MensajeDespedida("Julio" + " Arango"));
//Valores de contenedor_184
var contenedor_184 = ventana_228.crearcontenedor(300,200,nulo,nulo,10,10);

//Valores de texto_42
var texto_42 = contenedor_184.creartexto(nulo,nulo,nulo,10,20,nulo,nulo,"Nombre");

//Valores de texto_68
var texto_68 = contenedor_184.crearcajatexto(100,100,nulo,nulo,nulo,40,20,nulo,nulo,"Ingrese aqui su nombre","CTNombre");
/*
//Valores de texto_80
var texto_80 = contenedor_184.creartexto(nulo,nulo,nulo,10,50,nulo,nulo,"Edad");

//Valores de numerico_108
var numerico_108 = contenedor_184.crearcontrolnumerico(10,50,nulo,18,40,50,"18","CEdad");

//Valores de texto_120
var texto_120 = contenedor_184.creartexto(nulo,nulo,nulo,10,80,nulo,nulo,"Descripcion");

//Valores de textoarea_145
var textoarea_145 = contenedor_184.crearareatexto(100,100,nulo,nulo,nulo,40,80,nulo,nulo,"Ingrese aqui la descripcion de     su registro","CADescripcion");

//Valores de texto_157
var texto_157 = contenedor_184.creartexto(nulo,nulo,nulo,10,200,nulo,nulo,"Correo");

//Valores de texto_182
var texto_182 = contenedor_184.crearcajatexto(10,100,nulo,nulo,nulo,40,200,nulo,nulo,"Ingrese aqui su correo","CTCorreo");

//Valores de contenedor_226
var contenedor_226 = ventana_228.crearcontenedor(100,200,nulo,nulo,10,320);*/

ventana_228.alcargar();


