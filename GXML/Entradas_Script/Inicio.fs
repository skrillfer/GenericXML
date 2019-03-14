importar("/Funciones.fs");

//---------------------------
//---------------------------
//---------------------ventana_164
//---------------------------
//---------------------------
var ventana_164 = crearventana(nulo,nulo,nulo,"Inicio");
//Valores de contenedor_101
var contenedor_101 = ventana_164.crearcontenedor(200,200,nulo,nulo,10,10);

//Valores de texto_36
var texto_36 = contenedor_101.creartexto(nulo,nulo,nulo,10,20,nulo,nulo,"Nombre");

//Valores de texto_62
var texto_62 = contenedor_101.crearcajatexto(10,100,nulo,nulo,nulo,40,20,nulo,nulo,"Ingrese aqui su nombre","CTNombre");

//Valores de texto_74
var texto_74 = contenedor_101.creartexto(nulo,nulo,nulo,10,50,nulo,nulo,"Correo");

//Valores de texto_99
var texto_99 = contenedor_101.crearcajatexto(10,100,nulo,nulo,nulo,40,50,nulo,nulo,"Ingrese aqui su correo","CTCorreo");

//Valores de contenedor_162
var contenedor_162 = ventana_164.crearcontenedor(100,200,nulo,nulo,10,220);

//Valores de boton_137
var boton_137 = contenedor_162.crearboton(nulo,nulo,nulo,25,30,nulo,"Ingresar",70,50);

funcion boton_137_enviar(){
	ventana_164.creararraydesdearchivo(); 
}

//Valores de boton_160
var boton_160 = contenedor_162.crearboton(nulo,nulo,nulo,125,30,"Registrar","Registrar",70,50);

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
var texto_68 = contenedor_184.crearcajatexto(10,100,nulo,nulo,nulo,40,20,nulo,nulo,"Ingrese aqui su nombre","CTNombre");

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
var contenedor_226 = ventana_228.crearcontenedor(100,200,nulo,nulo,10,320);

//Valores de boton_223
var boton_223 = contenedor_226.crearboton(nulo,nulo,nulo,75,30,"Inicio","Registrar",70,40);

funcion boton_223_enviar(){
	ventana_228.creararraydesdearchivo(); 
}

ventana_164.alcargar();

