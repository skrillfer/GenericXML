var contenido = CrearArrayDesdeArchivo("ventana1.gdato");
contenido.map(ImprimirGanadores);

funcion ImprimirGanadores(item){
	Imprimir(item.CTNombre);
	Imprimir(item.CTPregunta);
	Imprimir(item.CAckerman);
}




