var ventana_VentanaPrincipal=crearVentana("#000000",650,655,"VentanaPrincipal");
var Contenedor1_VentanaPrincipal=ventana_VentanaPrincipal.crearContenedor(600,600,"#ffffff",verdadero,10,5);
Contenedor1_VentanaPrincipal.crearTexto("Arial",14,"#000000",20,10,verdadero,falso,"Haga clic en el siguiente boton para iniciar la evaluacion ");
var btnEvaluacion_Contenedor1_VentanaPrincipal=Contenedor1_VentanaPrincipal.crearboton("ARIAL",14,"#ffffff",60,100,cargar_VentanaAritmetica(),"",75,150);
btnEvaluacion_Contenedor1_VentanaPrincipal.crearTexto("Arial",14,"#000000",10,10,falso,falso,"Iniciar Evaluacion ");

btnEvaluacion_Contenedor1_VentanaPrincipal.alclic(Bienvenido());
btnEvaluacion_Contenedor1_VentanaPrincipal.alclic(cargar_VentanaAritmetica());

funcion cargar_VentanaAritmetica(){
ventana_VentanaAritmetica.alcargar();
}
Contenedor1_VentanaPrincipal.crearTexto("Arial",14,"#000000",10,175,falso,verdadero,"Haga clic en el siguiente boton para iniciar el area de reportes ");
var btnReportes_Contenedor1_VentanaPrincipal=Contenedor1_VentanaPrincipal.crearboton("ARIAL",14,"#ffffff",60,250,cargar_VentanaReportes(),"Iniciar Reportes ",75,150);
btnReportes_Contenedor1_VentanaPrincipal.alclic(BienvenidoReporte());
btnReportes_Contenedor1_VentanaPrincipal.alclic(cargar_VentanaReportes());

funcion cargar_VentanaReportes(){
ventana_VentanaReportes.alcargar();
}
var btnEnviar_Contenedor1_VentanaPrincipal=Contenedor1_VentanaPrincipal.crearboton("ARIAL",14,"#0000FF",60,350,"","Sin funcionalidad ",75,150);
btnEnviar_Contenedor1_VentanaPrincipal.alclic(guardarContenedor1_VentanaPrincipal());
funcion guardarContenedor1_VentanaPrincipal(){
ventana_VentanaPrincipal.creararraydesdearchivo();
}
btnEnviar_Contenedor1_VentanaPrincipal.alclic(EnviarSinFuncionalidad());
//importar("/FuncionesEvaluacion.fs");
var ventana_VentanaAritmetica=crearVentana("#2E2EFE",1000,740,"VentanaAritmetica");
var ContenedorAritmeticas_VentanaAritmetica=ventana_VentanaAritmetica.crearContenedor(700,700,"#ffffff",verdadero,10,10);
ContenedorAritmeticas_VentanaAritmetica.crearTexto("times new roman",18,"#000000",10,0,verdadero,verdadero,"Bienvenido a la prueba de Aritmetica , responda las siguientes preguntas ");
ContenedorAritmeticas_VentanaAritmetica.crearTexto("Arial",14,"#000000",10,50,falso,falso,"Ingrese su Nombre : ");
ContenedorAritmeticas_VentanaAritmetica.crearCajaTexto(25,200,"Arial",14,"#000000",140,85,falso,falso,"Ingrese aqui su nombre ","CTNombre");
ContenedorAritmeticas_VentanaAritmetica.crearTexto("Arial",14,"#000000",10,90,falso,falso,"Ingrese la potencia de 5 a la 5 : ");
ContenedorAritmeticas_VentanaAritmetica.crearControlNumerico(50,100,5000,0,130,175,"0","CPotencia");
var btnPotencia_ContenedorAritmeticas_VentanaAritmetica=ContenedorAritmeticas_VentanaAritmetica.crearboton("ARIAL",14,"#ffffff",250,175,"","Ver Respuesta ",50,100);

btnPotencia_ContenedorAritmeticas_VentanaAritmetica.alclic(VerPotencia(5,5));
ContenedorAritmeticas_VentanaAritmetica.crearTexto("Arial",14,"#000000",10,200,falso,falso,"Ingrese el Factorial de 7 : ");
ContenedorAritmeticas_VentanaAritmetica.crearControlNumerico(50,100,6000,0,130,275,"0","CFactorial");
var btnFactorial_ContenedorAritmeticas_VentanaAritmetica=ContenedorAritmeticas_VentanaAritmetica.crearboton("ARIAL",14,"#ffffff",250,275,"","Ver Respuesta ",50,100);

btnFactorial_ContenedorAritmeticas_VentanaAritmetica.alclic(VerFactorial(7));
ContenedorAritmeticas_VentanaAritmetica.crearTexto("Arial",14,"#000000",10,305,falso,falso,"Ingrese el numero invertido de 351230347 : ");
ContenedorAritmeticas_VentanaAritmetica.crearControlNumerico(50,100,100,0,130,375,"0","CInvertido");
var btnInvertido_ContenedorAritmeticas_VentanaAritmetica=ContenedorAritmeticas_VentanaAritmetica.crearboton("ARIAL",14,"#ffffff",250,375,"","Ver Respuesta ",50,100);

btnInvertido_ContenedorAritmeticas_VentanaAritmetica.alclic(VerInvertido(351230347));
ContenedorAritmeticas_VentanaAritmetica.crearTexto("Arial",14,"#000000",10,410,falso,falso,"Ingrese el mcd de 240 , 506 con 10 : ");
ContenedorAritmeticas_VentanaAritmetica.crearControlNumerico(50,100,100,0,130,475,"0","CMCD");
var btnMCD_ContenedorAritmeticas_VentanaAritmetica=ContenedorAritmeticas_VentanaAritmetica.crearboton("ARIAL",14,"#ffffff",250,475,"","Ver Respuesta ",50,100);

btnMCD_ContenedorAritmeticas_VentanaAritmetica.alclic(VerMCD(240,506,10));
ContenedorAritmeticas_VentanaAritmetica.crearTexto("Arial",14,"#000000",10,515,falso,falso,"Ingrese el Fibonacci de 19 : ");
ContenedorAritmeticas_VentanaAritmetica.crearControlNumerico(50,100,6000,0,130,575,"0","CFibonacci");
var btnFibonacci_ContenedorAritmeticas_VentanaAritmetica=ContenedorAritmeticas_VentanaAritmetica.crearboton("ARIAL",14,"#000000",250,575,"","Ver Respuesta ",50,100);

btnFibonacci_ContenedorAritmeticas_VentanaAritmetica.alclic(VerFibonacci(19));
var ContEnviarAritmetica_VentanaAritmetica=ventana_VentanaAritmetica.crearContenedor(100,200,"#ffffff",falso,10,800);
var btnEnviar_ContEnviarAritmetica_VentanaAritmetica=ContEnviarAritmetica_VentanaAritmetica.crearboton("ARIAL",14,"#0000FF",40,20,cargar_VentanaHistoria(),"Contestar ",50,100);
btnEnviar_ContEnviarAritmetica_VentanaAritmetica.alclic(guardarContEnviarAritmetica_VentanaAritmetica());

funcion cargar_VentanaHistoria(){
ventana_VentanaHistoria.alcargar();
}
funcion guardarContEnviarAritmetica_VentanaAritmetica(){
ventana_VentanaAritmetica.creararraydesdearchivo();
}
btnEnviar_ContEnviarAritmetica_VentanaAritmetica.alclic(cargar_VentanaHistoria());
var ventana_VentanaHistoria=crearVentana("#2E2EFE",1000,740,"VentanaHistoria");
var ContenedorHistoria_VentanaHistoria=ventana_VentanaHistoria.crearContenedor(700,700,"#ffffff",verdadero,10,10);
ContenedorHistoria_VentanaHistoria.crearTexto("times new roman",18,"#000000",10,0,verdadero,verdadero,"Bienvenido a la prueba de Historia , responda las siguientes preguntas ");
ContenedorHistoria_VentanaHistoria.crearTexto("Arial",14,"#000000",10,40,falso,falso,"Ingrese su Nombre : ");
ContenedorHistoria_VentanaHistoria.crearCajaTexto(25,200,"Arial",14,"#000000",145,75,falso,falso,"Ingrese aqui su nombre ","CTNombre");
ContenedorHistoria_VentanaHistoria.crearTexto("Arial",14,"#000000",10,100,falso,falso,"Ingrese el paisaje de la foto ");
ContenedorHistoria_VentanaHistoria.crearDesplegable(50,100,["Playa ","Luna ","Selva ","Desierto ","Oceano "],20,200,"","CDPaisaje1");
ContenedorHistoria_VentanaHistoria.crearImagen("playa.jpg",200,150,175,250);
var btnPlaya_ContenedorHistoria_VentanaHistoria=ContenedorHistoria_VentanaHistoria.crearboton("ARIAL",14,"#ffffff",500,200,"","Ver Respuesta ",50,100);

btnPlaya_ContenedorHistoria_VentanaHistoria.alclic(Paisaje(10));
ContenedorHistoria_VentanaHistoria.crearTexto("Arial",14,"#000000",10,300,falso,falso,"Ingrese el paisaje de la foto ");
ContenedorHistoria_VentanaHistoria.crearDesplegable(50,100,["Playa ","Luna ","Selva ","Desierto ","Oceano "],20,410,"","CDPaisaje2");
ContenedorHistoria_VentanaHistoria.crearImagen("luna.jpg",200,350,175,250);
var btnLuna_ContenedorHistoria_VentanaHistoria=ContenedorHistoria_VentanaHistoria.crearboton("ARIAL",14,"#ffffff",500,410,"","Ver Respuesta ",50,100);

btnLuna_ContenedorHistoria_VentanaHistoria.alclic(Paisaje(20));
ContenedorHistoria_VentanaHistoria.crearTexto("Arial",14,"#000000",10,500,falso,falso,"Ingrese el paisaje de la foto ");
ContenedorHistoria_VentanaHistoria.crearDesplegable(50,100,["Playa ","Luna ","Selva ","Desierto ","Oceano "],20,610,"","CDPaisaje3");
ContenedorHistoria_VentanaHistoria.crearImagen("selva.jpg",200,550,175,250);
var btnSelva_ContenedorHistoria_VentanaHistoria=ContenedorHistoria_VentanaHistoria.crearboton("ARIAL",14,"#ffffff",500,610,"","Ver Respuesta ",50,100);

btnSelva_ContenedorHistoria_VentanaHistoria.alclic(Paisaje(30));
ContenedorHistoria_VentanaHistoria.crearTexto("Arial",14,"#000000",10,700,falso,falso,"Ingrese el paisaje de la foto ");
ContenedorHistoria_VentanaHistoria.crearDesplegable(50,100,["Playa ","Luna ","Selva ","Desierto ","Oceano "],20,810,"","CDPaisaje4");
ContenedorHistoria_VentanaHistoria.crearImagen("Desierto.jpg",200,750,175,250);
var btnDesierto_ContenedorHistoria_VentanaHistoria=ContenedorHistoria_VentanaHistoria.crearboton("ARIAL",14,"#ffffff",500,810,"","Ver Respuesta ",50,100);

btnDesierto_ContenedorHistoria_VentanaHistoria.alclic(Paisaje(40));
ContenedorHistoria_VentanaHistoria.crearTexto("Arial",14,"#000000",10,900,falso,falso,"Ingrese el paisaje de la foto ");
ContenedorHistoria_VentanaHistoria.crearDesplegable(50,100,["Playa ","Luna ","Selva ","Desierto ","Oceano "],20,1010,"","CDPaisaje5");
ContenedorHistoria_VentanaHistoria.crearImagen("oceano.jpg",200,950,175,250);
var btnOceano_ContenedorHistoria_VentanaHistoria=ContenedorHistoria_VentanaHistoria.crearboton("ARIAL",14,"#ffffff",500,1010,"","Ver Respuesta ",50,100);

btnOceano_ContenedorHistoria_VentanaHistoria.alclic(Paisaje(50));
var ContEnviarHistoria_VentanaHistoria=ventana_VentanaHistoria.crearContenedor(100,200,"#ffffff",falso,10,800);
var btnEnviar_ContEnviarHistoria_VentanaHistoria=ContEnviarHistoria_VentanaHistoria.crearboton("ARIAL",14,"#0000FF",15,15,cargar_VentanaIngles(),"Contestar ",50,150);
btnEnviar_ContEnviarHistoria_VentanaHistoria.alclic(guardarContEnviarHistoria_VentanaHistoria());

funcion cargar_VentanaIngles(){
ventana_VentanaIngles.alcargar();
}
funcion guardarContEnviarHistoria_VentanaHistoria(){
ventana_VentanaHistoria.creararraydesdearchivo();
}
btnEnviar_ContEnviarHistoria_VentanaHistoria.alclic(cargar_VentanaIngles());
var ventana_VentanaIngles=crearVentana("#2E2EFE",1000,740,"VentanaIngles");
var ContenedorIngles_VentanaIngles=ventana_VentanaIngles.crearContenedor(700,700,"#ffffff",verdadero,10,10);
ContenedorIngles_VentanaIngles.crearTexto("times new roman",18,"#000000",10,0,verdadero,verdadero,"Bienvenido a la prueba de Ingles , responda las siguientes preguntas ");
ContenedorIngles_VentanaIngles.crearTexto("Arial",14,"#000000",10,45,falso,falso,"Ingrese su Nombre : ");
ContenedorIngles_VentanaIngles.crearCajaTexto(25,200,"Arial",14,"#000000",150,80,falso,falso,"Ingrese aqui su nombre ","CTNombre");
//ContenedorIngles_VentanaIngles.crearReproductor("Ackermann.mp3",450,50,falso,50,100,);
ContenedorIngles_VentanaIngles.crearTexto("Arial",14,"#000000",10,100,falso,falso,"What algorithm is the audio talking about ? ");
ContenedorIngles_VentanaIngles.crearCajaTexto(25,250,"Arial",14,"#000000",10,250,falso,falso,"Ingrese aqui su respuesta ","CTPregunta");
var btnPregunta_ContenedorIngles_VentanaIngles=ContenedorIngles_VentanaIngles.crearboton("ARIAL",14,"#ffffff",300,240,"","Ver Respuesta ",50,100);

btnPregunta_ContenedorIngles_VentanaIngles.alclic(Pregunta("Tipo"));
ContenedorIngles_VentanaIngles.crearTexto("Arial",14,"#000000",10,290,falso,falso,"Ingrese el ackerman de 3 , 11 : ");
ContenedorIngles_VentanaIngles.crearControlNumerico(50,100,100,0,100,375,"0","CAckerman");
var btnAckerman_ContenedorIngles_VentanaIngles=ContenedorIngles_VentanaIngles.crearboton("ARIAL",14,"#ffffff",300,375,"","Ver Respuesta ",50,100);

btnAckerman_ContenedorIngles_VentanaIngles.alclic(Pregunta("Resultado"));
var ContEnviarIngles_VentanaIngles=ventana_VentanaIngles.crearContenedor(100,200,"#ffffff",falso,50,750);
var btnEnviar_ContEnviarIngles_VentanaIngles=ContEnviarIngles_VentanaIngles.crearboton("ARIAL",14,"#0000FF",15,15,cargar_VentanaLogica(),"Contestar ",50,150);
btnEnviar_ContEnviarIngles_VentanaIngles.alclic(guardarContEnviarIngles_VentanaIngles());

funcion cargar_VentanaLogica(){
ventana_VentanaLogica.alcargar();
}
funcion guardarContEnviarIngles_VentanaIngles(){
ventana_VentanaIngles.creararraydesdearchivo();
}
btnEnviar_ContEnviarIngles_VentanaIngles.alclic(cargar_VentanaLogica());
ventana_VentanaPrincipal.alcargar();
































