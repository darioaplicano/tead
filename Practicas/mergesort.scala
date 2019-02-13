def mergeR(izquierda:List[Int], derecha:List[Int]):List[Int]={
    if(izquierda.isEmpty){
        derecha
    }else{
        if(derecha.isEmpty){
            izquierda
        }else{
            if(izquierda.head <= derecha.head){
                izquierda.head::mergeR(izquierda.tail, derecha)
            }else{
                derecha.head::mergeR(izquierda, derecha.tail)
            }
        }
    }
}

def mergesort(lista: List[Int]):List[Int]={
    if (lista.length == 1){
        lista
    }else{
        val mitad = (lista.length/2).toInt
        val derecha = mergesort(lista.slice(mitad,lista.length))
        val izquierda = mergesort(lista.slice(0,mitad))
        mergeR(izquierda, derecha)
    }
}

val lista = List(6, 5, 3, 1, 8, 7, 4, 0, 9, 20, 21, 2, 1, 0)
mergesort(lista)