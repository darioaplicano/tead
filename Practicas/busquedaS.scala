//Implementa de forma recursiva la búsqueda secuencial en
//una lista de String, de tal forma que devuelva la posición
//donde se encuentra el elemento. Si no está puedes elegir entre
//devolver un número negativo o bien devolver una tupla (existe:
//Boolean, pos: Int) donde si existe == false, no tiene sentido el
//valor de pos posición donde está.

def busquedaR(lista:List[String], elemento:String, indice:Int):Int={
    if(lista.isEmpty){
        return -1
    }else{
        if(lista.head == elemento){
            return indice
        }else{
            busquedaR(lista.tail,elemento,indice+1)
        }
    }
}

def busqueda(lista: List[String], elemento: String):Int={
    if(lista.isEmpty){
        return -1
    }else{
        busquedaR(lista,elemento,0)
    }
}

println("")
println("Primer caso, enviando una lista vacía")
val lista = List()
busqueda(lista,"profesor")

println("")
println("Segundo caso, enviando una lista que no tiene el elemento")
val lista1 = List("maestro", "catedratico", "pedagogo", "experto", "disertador", "educador", "docente")
busqueda(lista1,"profesor")

println("")
println("Segundo caso, enviando una lista que no tiene el elemento")
val lista2 = List("maestro", "catedratico", "pedagogo", "experto", "profesor", "disertador", "educador", "docente")
busqueda(lista2,"profesor")