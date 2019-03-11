//Implementa de forma recursiva la búsqueda binaria en un 
//vector de números reales de doble precisión. La función
//debe devolver al igual que antes un número >= 0 que indica
//dónde está el elemento en el vector y un número negativo si el
//elemento no está, pero debería estar en esa posición.
//Opcionalmente, puedes devolver también una tupla.

def busquedaB(lista: List[Double], elemento: Double):(Int, Int) ={
    def busquedaBR(lista: List[Double], posicion: Int, movimiento: Boolean):(Int, Int) = {
        if(lista.isEmpty)
            (-1, if(movimiento == false) posicion - 1 else posicion)
        else{
            val mitad = (lista.length/2).toInt
            if(elemento > lista(mitad)){
                val listab = lista.filter(x => lista.indexOf(x) > mitad)
                busquedaBR(listab, posicion + (listab.length/2).toInt + 1, true)
            }
            else
                if(elemento < lista(mitad)){
                    val listab = lista.filter(x => lista.indexOf(x) < mitad)
                    busquedaBR(listab, posicion - (listab.length/2).toInt, false)
                }
                else
                    (if(mitad == 0 & movimiento == false) posicion - 1 else posicion, -1)
        }
    }
    busquedaBR(lista, (lista.length/2).toInt, true)
}

val lista = List(1, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59.0)
busquedaB(lista, 0)
busquedaB(lista, 1)
busquedaB(lista, 3)
busquedaB(lista, 19)
busquedaB(lista, 23)
busquedaB(lista, 29)
busquedaB(lista, 53)
busquedaB(lista, 59)
busquedaB(lista, 60)
busquedaB(lista, 22)
