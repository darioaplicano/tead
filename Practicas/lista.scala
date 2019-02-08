//Debo insertar un entero en una lista de enteros, que puede estar vacía.
//Si la lista de origen no está ordenada, debe devolver falso y la lista vacía.
//Si está ordenada, debe devolver cierto y la nueva lista con el elemento
//ordenado correctamente.
def insertarR(lista: List[Int], cabeza: Int, numero: Int, agregado: Boolean):List[List[AnyVal]]={
    if(lista.isEmpty){
        if(numero > cabeza){
            List(List(true),cabeza::numero::lista)
        }else{
            List(List(true),numero::cabeza::lista)
        }
    }else{
        if(lista.forall(x => x > cabeza)){
            val resultado = insertarR(lista.tail,lista.head,numero, false)
            if(resultado(0).head == true){
                val lista2 = resultado(1)
                val cabezaLista2 = lista2.head.asInstanceOf[Number].longValue
                if(lista2.head == lista.head){
                    List(List(true),cabeza::lista2)
                }else{
                    if(cabeza > cabezaLista2){
                        List(List(true),lista2.head::cabeza::lista2.tail)
                    }else{
                        List(List(true),cabeza::lista2)
                    }
                }
            }else{
                List(List(false),Nil)
            }
        }
        else{
            println("La lista no esta ordenada")
            List(List(false),Nil)
        } 
    }
}

def insertar(lista: List[Int], numero: Int):List[List[AnyVal]]={
    if(lista.isEmpty){
        List(List(true),numero::lista)
    }else{
        insertarR(lista.tail,lista.head,numero,false)
    }
}

println("")
println("Primer caso, agregar el numero 3 a una lista vacia")
val lista = List()
insertar(lista,3)

println("")
println("Segundo caso, agregar el numero 3 a una lista desordenada")
val lista2 = List(1,5,2,4,6)
insertar(lista2,3)

println("")
println("Tercer caso, agregar el numero 3 a una lista ordenada")
val lista3 = List(1,2,4,5,6)
insertar(lista3,3)