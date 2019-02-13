//Implementa quick_sort para ordenar un vector que no esté ordenado

def ordenarR(lista: List[Int], cabeza: Int):List[Int]={
    if(lista.isEmpty){
        List(cabeza)
    }else{
        if(cabeza < lista.head){
            return cabeza::lista
        }else{
            lista.head::ordenarR(lista.tail,cabeza)
        }
    }
}

def recorridoR(lista: List[Int], cabeza:Int):List[Int]={
    if(lista.isEmpty){
        List(cabeza)
    }else{
        val lista2 = recorridoR(lista.tail, lista.head)
        if(lista2.forall(x => cabeza < x)){
            return cabeza::lista2
        }else{
            ordenarR(lista2,cabeza)
        }
    }
}

def quick_sort(lista: List[Int]):List[Int]={
    if(lista.isEmpty){
        return lista
    }else{
        recorridoR(lista.tail,lista.head)
    }
}

val lista = List(6, 5, 3, 1, 8, 7, 4, 0, 9, 20, 21, 2, 1, 0)
quick_sort(lista)