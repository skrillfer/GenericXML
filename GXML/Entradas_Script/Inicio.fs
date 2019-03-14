importar("/Funciones.fs");

//---------------------------
//---------------------------
//---------------------ventana_167
//---------------------------
//---------------------------
var ventana_167 = crearventana(nulo,nulo,nulo,"Inicio");
//Valores de contenedor_101
var contenedor_101 = ventana_167.crearcontenedor(200,200,nulo,nulo,10,10);

//Valores de texto_36
var texto_36 = contenedor_101.creartexto(nulo,nulo,nulo,10,20,nulo,nulo,"Nombre");

//Valores de texto_62
var texto_62 = contenedor_101.crearcajatexto(10,100,nulo,nulo,nulo,40,20,nulo,nulo,"Ingrese aqui su nombre","CTNombre");

//Valores de texto_74
var texto_74 = contenedor_101.creartexto(nulo,nulo,nulo,10,50,nulo,nulo,"Correo");

//Valores de texto_99
var texto_99 = contenedor_101.crearcajatexto(10,100,nulo,nulo,nulo,40,50,nulo,nulo,"Ingrese aqui su correo","CTCorreo");

//Valores de contenedor_165
var contenedor_165 = ventana_167.crearcontenedor(100,200,nulo,nulo,10,220);

//Valores de boton_140
var boton_140 = contenedor_165.crearboton(nulo,nulo,nulo,25,30,nulo,"Ingresar",70,50);

boton_140.alclic(boton_140_OnClick());

funcion boton_140_OnClick(){

	boton_140_enviar();
	prueba();
	
}

funcion boton_140_enviar(){
	ventana_167.creararraydesdearchivo(); 
}

//Valores de boton_163
var boton_163 = contenedor_165.crearboton(nulo,nulo,nulo,125,30,"Registrar","Registrar",70,50);

boton_163.alclic(boton_163_OnClick());

funcion boton_163_OnClick(){
	
}

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

boton_223.alclic(boton_223_OnClick());

funcion boton_223_OnClick(){

	boton_223_enviar();	
}

funcion boton_223_enviar(){
	ventana_228.creararraydesdearchivo(); 
}

ventana_167.alcargar();

