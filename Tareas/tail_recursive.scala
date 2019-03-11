// - Modifica la función suma() implementándola usando recursión de cola.
// - ¿Se puede generalizar aún más y que una única función determine la suma
//   o productos de aplicar una función sobre los elementos de un intervalo
//   [a, b] = OP_{n=a..b{ f(n)?

def factorial(numero: Int): Int={
    def factorial_tr(numero: Int, resultado: Int):Int={
        if(numero == 0) 
            resultado
        else
            factorial_tr(numero-1, resultado*numero)
    }
    factorial_tr(numero,1)
}

def cubo(numero: Int): Int={
    numero*numero*numero
}

def identidad(numero: Int):Int={
    numero
}

// Primer enunciado, recursividad de cola
def suma1(f: Int => Int)(a: Int, b: Int): Int ={
    def iter(a: Int, result: Int): Int ={
        if (a == b)
            f(a)+result
        else
            iter(a+1, f(a)+result)
    }
    iter(a, 0)
}

def producto1(f: Int => Int)(a: Int, b: Int): Int ={
    def iter(a: Int, result: Int): Int ={
        if (a == b)
            f(a)*result
        else
            iter(a+1, f(a)*result)
    }
    iter(a, 1)
}

// Segundo enunciado, generalización de la sumatoria o productoria
def suma(a: Int, b: Int): Int = {a + b}
def producto(a: Int, b: Int): Int = {a * b}

def funcion(operacion: (Int, Int) => Int)(f: Int => Int)(a: Int, b:Int): Int ={
    def iter(a: Int, result: Int): Int ={
        if( a == b)
            operacion(result, f(a))
        else
            iter(a+1, operacion(result, f(a)))
    }
    iter(a,-operacion(-1,1))
}

val inicio = 1;
val fin = 3;

println("")
println("Sumas: ")
suma1(factorial)(inicio,fin)
suma1(identidad)(inicio,fin)
suma1(cubo)(inicio,fin)

println("")
println("Productos: ")
producto1(factorial)(inicio,fin)
producto1(identidad)(inicio,fin)
producto1(cubo)(inicio,fin)

println("")
println("Mediante la operacion de la suma: ")
funcion(suma)(factorial)(inicio,fin)
funcion(suma)(identidad)(inicio,fin)
funcion(suma)(cubo)(inicio,fin)

println("")
println("Mediante la operacion del producto: ")
funcion(producto)(factorial)(inicio,fin)
funcion(producto)(identidad)(inicio,fin)
funcion(producto)(cubo)(inicio,fin)