def insertarR(lista: List[Int], cabeza: Int, numero: Int, agregado: Boolean):List[List[AnyVal]]={
    if(lista.isEmpty){
        if(numero > cabeza){
            List(List(true),cabeza::numero::lista)
        }else{
            List(List(true),numero::cabeza::lista)
        }
    }else{
        if(lista.forall(x => x > cabeza)){
            val p = insertarR(lista.tail,lista.head,numero, false)
            val lista2 = p(1)
            if(lista2.head == lista.head){
                List(List(true),cabeza::lista2)
            }else{
                if(cabeza > lista2.head){
                    List(List(true),lista2.head::cabeza::lista2.tail)
                }else{
                    List(List(true),cabeza::lista2)
                }
            }
        }
        else{
            println("La lista no está ordenada")
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


val lista = List()
insertar(lista,3)
