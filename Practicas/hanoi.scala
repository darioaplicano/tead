def hanoi(discos: Int, origen: String, destino: String, auxiliar: String):Unit ={
    if(discos == 1){
        println("Mover del pivote "+origen+" al pivote "+destino+" ")
    }else{
        hanoi(discos-1,origen,auxiliar,destino)
        println("Mover del pivote "+origen+" al pivote "+destino+" ")
        hanoi(discos-1,auxiliar,destino,origen)
    }
}