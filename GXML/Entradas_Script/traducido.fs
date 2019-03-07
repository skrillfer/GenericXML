







/////////////////////////////////////////////////////////////////////////////////
///////////////////         Traduccion Ventana Inicio         ///////////////////
/////////////////////////////////////////////////////////////////////////////////


prueba();

funcion prueba()
{
	var p=nulo;
	Si(p!=nulo)
	{
		imprimir("p es nulo");
	}
	Si(verdadero<falso)
	{
		imprimir("verdadero es mayor a falso");
	}
}
funcion fibonacci(var n)
{
	
    si (n>1){
       retornar fibonacci(n-1) + fibonacci(n-2);  //función recursiva
    }
    sino si (n==1) {  // caso base
        retornar 1;
    }
    sino si (n==0){  // caso base
        retornar 0;
    }
    sino{ //error
        imprimir("Debes ingresar un tamaño mayor o igual a 1");
        retornar 1; 
    }
}

funcion factorial(var numero)
{
    si (numero==0){
      retornar 1;
    }
    sino{
     retornar numero * factorial(numero-1);
    }
}



