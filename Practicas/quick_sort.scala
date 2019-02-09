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

println("")
println("Primer caso, una lista vacia")
val lista = List()
println("Resultado")
quick_sort(lista)

println("")
println("Segundo caso, una lista de un elemento")
val lista2 = List(1)
println("Resultado")
quick_sort(lista2)

println("")
println("Tercer caso, una lista de dos elementos ordenados")
val lista3 = List(1,4, 5, 7, 9)
println("Resultado")
quick_sort(lista3)

println("")
println("Tercer caso, una lista de dos elementos desordenados")
val lista3 = List(3,7,8,5,2,1,9,5,4)
println("Resultado")
quick_sort(lista3)