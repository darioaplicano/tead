/// Crear un RDD a partir de una colección local de scala "parallelize"
val xs = (1 to 10000).toList
val rdd = sc.parallelize(xs)

///Crear un RDD a partir de un fichero de texto del sistema de ficheros "textFile"
val rdd = sc.textFile("hdfs://host:9000/path/directory/*.gz")

///Leer todos los ficheros de un directorio y crea un RDD de pares clave-valor "wholeTextFile"
val rdd = sc.wholeTextFiles("path/to/my-data/*.txt")

///Leer pares clave-valor de un fichero secuencia y retorna un RDD de pares clave-valor "sequenceFile"
val rdd = sc.sequenceFile[String, String]("some-file")

///Contar el número de filas de un fichero "count"
//Crear el RDD
val lines = sc.textFile("C:\\spark\\LICENSE")
//Contar el número de líneas
val countLines = lines.count

///Contar las líneas que contienen la palabra BSD "contains"
//Extraer las líneas que contienen BSD
val linesBSD = lines.filter(line => line.contains("BSD"))
//Contar el número de líneas
val countLinesBSD = linesBSD.count

///Mostrar por patanlla en contido del RDD linesBSD "foreach"
linesBSD.foreach(lineBSD => println(lineBSD))

///Realizar un "map" y un "reduce" con el objetivo de hacer una transformación y generar un nuevo RDD
///Contar el tamaño total de todas las líneas del fichero
//Crear el RDD a través del "map"
val lengths = lines.map(line => line.length)
//Contar el número de líneas usando "reduce"
val totalLengths = lengths.reduce( (x,y) => x + y)

///Crear un RDD donde el tamaño de cada línea sea superio a 80 e imprimirlo "filter"
//Creando en nuevo RDD con las líneas grandes
val bigLines = lines.filter(line => line.length > 80)
//Contar el número del líneas
val countBigLines = bigLines.count
//Imprimir las líneas
bigLines.foreach(bigLine => println(bigLine))

///Contar el número de palabras que tienen el documento leído "flatMap"
//Crear el RDD que contiene una colección de palabras
val words = lines.map(line => line.split(" "))
//Unimos todos los RDDs creando un Array[String] "union"
val totalWords = words.reduce( (x,y) => x.union(y))
totalWords.length

//Otra forma de hacerlo usando "flatMap", se crean los RDDs y a la vez se unen en uno solo
val words = lines.flatMap(line => line.split(" "))
val totalWords = words.count
totalWords

///Usar "union" para generar un solo RDD concatenando dos RDDs
val linesFile1 = sc.parallelize(List("Lion", "Dolphin", "Whale"))
val linesFile2 = sc.parallelize(List("Shark", "Dolphin", "Whale"))
val union = linesFile1.union(linesFile2)
union.foreach(println)

///Usar "intersection" para generar un RDD que contenga únicamente los elementos de la intersección
///entre dos RDDs
val mammals = sc.parallelize(List("Lion", "Dolphin", "Whale"))
val marine = sc.parallelize(List("Shark", "Dolphin", "Whale"))
val seaMammalsIntersection = marine.intersection(mammals)
seaMammalsIntersection.foreach(println)

///Usar "subtract" para generar un RDD que contenga únicamente los elementos que están en el RDD fuente,
///pero no en el argumento
//Al primer RDD quitar los que están en el segundo RDD
val seaMammalsSubtract = marine.subtract(mammals)
seaMammalsSubtract.foreach(println)

///Usar "distinct" para obtener un RDD con los elementos que son distintos en otro RDD
val unique = union.distinct
unique.foreach(println)

///Agrupar los elementos de un RDD fuente deacuerdo con un criterio especificado "groupBy"
//Usar un case class para generar una colección

case class Client(name: String, age: Int, sex: String, cp: String)

val lines = sc.textFile("...")
val clients = lines.map { line => {
                            val split = line.split(",")
                            Client(split(0), split(1).toInt, split(2), split(3))
                            }
                        }
val groupByCP = clients.groupBy { client => client.cp}

///Usar "keyBy" para hacer un agrupamiento del RDD generando clave-valor

///usar "sortBy" para realizar el ordeamiento de un RDD por un valor que se considere
///El ordenamiento no funcion si hay elementos repetidos
val numeros = sc.parallelize(List(3,2, 4, 1, 5))
numeros.foreach(println)
val ordenados = numeros.sortBy(x => x, true)
ordenados.foreach(println)

///Devolver un RDD con una muestra de los elementos del RDD fuente "sample"
val numeros = sc.parallelize((1 to 100).toList)
val muestra = numeros.sample(true, 0.2)

///Devolver un nuevo RDD done el fuente está dividido en las particiones indicadas "repartition"
val numerosEnCuatroParticiones = numeros.repartition(4)

///Reducir el número de particiones de un RDD al indicado "coalesce"
val numerosEnUnaParticion = numerosEnCuatroParticiones.coalesce(1)

///Recoger todos los elementos de un RDD y devolverlo como un Array "collect"
val rdd = sc.parallelize((1 to 10000).toList)
val filtradaRdd = rdd filter { x => (x % 1000) == 0 }
val filtradaResultado = filtradaRdd.collect

///Usar "count" para devolver el número de elementos que tiene un RDD
val total = rdd.count

///Usar "countByValue" para devolver una colección clave-valor
val rdd = sc.parallelize(List(1, 2, 3, 4, 1, 2, 3, 1, 2, 1))
val conteo = rdd.countByValue

///Usar "first" para determinar el primer elemento de un RDD o colección
val rdd = sc.parallelize(List(10, 5, 3, 1))
val primeroRDD = rdd.first

///Usar "max" y "min" para determinar el mayor y menor de los números en un RDD
val rdd = sc.parallelize(List(2, 5, 3, 1))
val menor = rdd.min
val mayor = rdd.max

///Usar "take ( n )" para devolver los n primeros elementos de un RDD o array
val rdd = sc.parallelize(List(2, 5, 3, 1, 50, 100))
val tresPrimeros = rdd.take(3)

///Usar "takeOrdered(n)" y "top(n)" para devolver los n elementos más pequeños o más grandes respectivamente
val rdd = sc.parallelize(List(2, 5, 3, 1, 50, 100))
val TresMasPequenos = rdd.takeOrdered(3)
val TresMasGrandes = rdd.top(3)

///Usar "reduce" para realizar una operación sobre un RDD
val numbersRdd = sc.parallelize(List(2, 5, 3, 1))
val sum = numbersRdd.reduce ((x, y) => x + y)
val product = numbersRdd.reduce((x, y) => x * y)

///Usar "countByKey" para contar las repeticiones de cada clave en el RDD fuente y devolverlo en un map
val paresRdd = sc.parallelize(List(("a",1), ("b", 2), ("c", 3), ("a", 11),("b", 22), ("a", 1)))
val contarClaves = paresRdd.countByKey

///Usar "lookup" para devolver una secuencia de todos los valores asociados a esa clave en el RDD
val paresRdd = sc.parallelize(List(("a",1), ("b", 2), ("c", 3), ("a", 11),("b", 22), ("a", 1)))
val valores = paresRdd.lookup("a")
val suma = paresRdd.lookup("a").reduce((x,y) => x+y)

///Usar "mean","stdev" "sum", "variance" para devolver respectivamente la media, desviación estándar
///suma y varianza de los elementos numéricos del RDD fuente
//Devolver la media
val numerosRdd = sc.parallelize(List(2, 5, 3, 1))
val media = numerosRdd.mean
//Devoler la desviación estándar
val desvEst = numerosRdd.stdev
//Devolver la suma
val suma = numerosRdd.sum
valores.sum
//Devolver la varianza
val varianza = numerosRdd.variance

///Usar "saveAsTextFile" para guardar los elementos de un RDD en un fichero
//Primero se debe convertir en una cadena
val numerosRdd = sc.parallelize((1 to 10000).toList)
val filtradoRdd = numerosRdd.filter{ x => x % 1000 == 0}
filtradoRdd.saveAsTextFile("numeros-como-texto")

///Usar "saveAsObjectFile" para guardar los elementos de un RDD como un objeto
filtradoRdd.saveAsObjectFile("numeros-como-objeto")

///Usar "saveAsSequenceFile" para guardar los elementos de un RDD como un archivo de secuencias
val parejas = (1 to 10000).toList.map {x => (x, x*2)}
val parejasRdd = sc.parallelize(parejas)
val filtradoPRdd = parejasRdd.filter{ case (x, y)=> x % 1000 ==0}
filtradoPRdd.saveAsSequenceFile("pares-como-secuencia")
filtradoPRdd.saveAsTextFile("pares-como-texto")