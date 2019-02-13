//Implementa quick_sort para ordenar un vector que no esté ordenado

def repetidosR(repetidos: Int, ultimo: Int):List[Int]={
    if(repetidos == 1){
        List(ultimo)
    }else{
        ultimo::repetidosR((repetidos-1),ultimo)
    }
}

def quick_sortR(lista: List[Int]):List[Int]={
    if(lista.length == 1 | lista.isEmpty){
        lista
    }else{
        val ultimo = lista(lista.length-1)
        val repetidos = lista.count(_==ultimo)
        val izquierda = quick_sortR(lista.filter( x => x < ultimo))
        val derecha = quick_sortR(lista.filter( x => x > ultimo))
        List(izquierda, repetidosR(repetidos, ultimo), derecha).flatten
    }
}

val lista = List(6, 5, 3, 1, 8, 7, 4, 0, 9, 20, 21, 2, 1, 0)
quick_sortR(lista)