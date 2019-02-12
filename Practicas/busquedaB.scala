import math._

def busquedaBR(arreglo: Array[Double], numero: Double):(Int, List[Double])={
    val mitad = floor(arreglo.length / 2).toInt
    val mitad1 = mitad+1

    if(arreglo.length == 1){
        if( arreglo(0) > numero){
            return (-1, List(numero, arreglo(0)))
        }else{
            return (-1, List(arreglo(0), numero))
        }
    }else{
        if(arreglo.length == 2){
            if(arreglo(mitad) < numero){
                return (-1, List(arreglo(1), numero))
            }else{
                if(arreglo(0) < numero){
                    return (-1, List(arreglo(0), arreglo(1)))
                }else{
                    return (-1, List(numero, arreglo(0)))
                }
            }
        }else{
            if(arreglo(mitad) < numero){
                if(arreglo(mitad1) > numero){
                        (-1, List(arreglo(mitad), arreglo(mitad1)))
                }else{
                    val arregloM = arreglo.filter(x => arreglo.indexOf(x) >= mitad)
                    busquedaBR(arregloM, numero)
                }
            }else{
                val arregloM = arreglo.filter(x => arreglo.indexOf(x) <= mitad)
                busquedaBR(arregloM, numero)
            }
        }
    }
}

def busqueda(arreglo: Array[Double], numero: Double):(Int, List[Double])={
    if(arreglo.isEmpty){
        return (-1, Nil)
    }else{
        if(arreglo.indexOf(numero) == (-1)){
            println(numero)
            busquedaBR(arreglo, numero)
        }else{
            return (arreglo.indexOf(numero)+1, Nil)
        }
    }
}

println()
println("Primer caso, buscar un elemento que se encuentra sobre el arreglo ordenado")
val arreglo = Array(1.2, 2.3, 3.2, 5.7, 7.8, 8.2)
busqueda(arreglo, 5.7)

println()
println("Segundo caso, buscar un elemento que no se encuentra sobre el arreglo ordenado")
busqueda(arreglo, 6)

println()
println("Tercer caso, buscar un elemento que no se encuentra sobre el arreglo ordenado")
busqueda(arreglo, 5.0)

println()
println("Cuarto caso, buscar un elemento que no se encuentra sobre el arreglo ordenado")
busqueda(arreglo, 8.0)

println()
println("Quinto caso, buscar un elemento que no se encuentra sobre el arreglo ordenado")
busqueda(arreglo, 2)

println()
println("Sexto caso, buscar un elemento que no se encuentra sobre el arreglo ordenado")
busqueda(arreglo, 0)

println()
println("Septimo caso, buscar un elemento que no se encuentra sobre el arreglo ordenado")
busqueda(arreglo, 9)

println()
println("Octavo caso, buscar un elemento que no se encuentra sobre el arreglo ordenado")
busqueda(arreglo, 10)

println()
println("Noveno caso, buscar un elemento que no se encuentra sobre el arreglo de un elemento")
val arreglo2 = Array(1.0)
busqueda(arreglo2, 0)

println()
println("Decimo primero caso, buscar un elemento que no se encuentra sobre el arreglo de un elemento")
val arreglo2 = Array(1.0)
busqueda(arreglo2, 2)

println()
println("Decimo segundo caso, buscar un elemento que no se encuentra sobre el arreglo vacío")
val arreglo2 = Array.empty[Double]
busqueda(arreglo2, 2)