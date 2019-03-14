var ventana_VentanaPrincipal=crearVentana("#000000",650,655,"VentanaPrincipal");
var Contenedor1_VentanaPrincipal=ventana_VentanaPrincipal.crearContenedor(600,600,"#ffffff",verdadero,10,5);
Contenedor1_VentanaPrincipal.crearTexto("Arial",14,"#f03405",20,10,verdadero,falso,"Haga clic en el siguiente boton para iniciar la evaluacion ");
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
ventana_VentanaPrincipal.alcargar();




