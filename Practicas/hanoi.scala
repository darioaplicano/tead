//implementa la función hanoi() que dado un número de discos me diga el
//orden en el que se deben mover de un poste A hacia un poste C usando el
//poste B como auxiliar. Se supone que el número indica el tamaño, nunca
//pudiendo mover un disco más grande sobre otro más pequeño

def hanoi(discos: Int, origen: String, destino: String, auxiliar: String):Unit ={
    if(discos == 1){
        println("Mover del pivote "+origen+" al pivote "+destino+" ")
    }else{
        hanoi(discos-1,origen,auxiliar,destino)
        println("Mover del pivote "+origen+" al pivote "+destino+" ")
        hanoi(discos-1,auxiliar,destino,origen)
    }
}

println("")
println("Caso el caso de 1 discos")
hanoi(1, "A", "C","B")
println("")
println("Caso el caso de 2 discos")
hanoi(2, "A", "C","B")
println("")
println("Caso el caso de 3 discos")
hanoi(3, "A", "C","B")