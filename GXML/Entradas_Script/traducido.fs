/*var container = leerGxml("Inicio.gxml");

var ventana1   = container[0];

var control    = container.obtenerpornombre("CTCorreo","Inicio");
imprimir("Tipo:"+control.tipo);
*/

var arr1={arreglo:[1,2,3,4,5,6,7]};
var xx = 100;


var fix = arr1.arreglo.map(print);

imprimir("el valor de xx:"+xx);

imprimir("fix[0]="+fix[0]);
funcion print(item)
{
    imprimir(item);
    xx+=2;
    retornar xx;
}

