//Implementa quick_sort para ordenar un vector que no esté ordenado

def quick_sortR(lista: List[Int]):List[Int]={
    if(lista.length == 1 | lista.isEmpty){
        lista
    }else{
        val ultimo = lista(lista.length-1)
        val repetidos = lista.filter( x => x == ultimo)
        val izquierda = quick_sortR(lista.filter( x => x < ultimo))
        val derecha = quick_sortR(lista.filter( x => x > ultimo))
        List(izquierda, repetidos, derecha).flatten
    }
}

val lista = List(6, 5, 3, 1, 8, 7, 4, 0, 9, 20, 21, 2, 1, 0)
quick_sortR(lista)