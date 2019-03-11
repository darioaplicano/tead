// Directorio con los datos y conjunto de datos
val PATH="C:\\"
val DATA="car.data"

val lines = sc.textFile(PATH + DATA)
val nonEmpty = lines.filter(_.nonEmpty)
val parsed = nonEmpty.map(line => line.split(","))
parsed.first()

//Crear un esquema de acuerdo a la estructura de las filas del RDD dentro del documento
import org.apache.spark.sql.types.{StructType,StructField,StringType,DoubleType}
val carSchema = StructType(Array(
StructField("coste_compra",StringType,true),
StructField("coste_mantenimiento",StringType,true),
StructField("puertas",StringType,true),
StructField("personas",StringType,true),
StructField("maletero",StringType,true),
StructField("seguridad",StringType,true),
StructField("clase",StringType,true)
))

//Creamo un dataFrame con columnas originales
import org.apache.spark.sql.Row

val carDF = spark.createDataFrame(parsed.map(Row.fromSeq(_)),carSchema)
//Lo examinamos
carDF.show(5)

//Crear la columna features a través de "VectorAssembler"
//Crear la columna label usando "StringIndexer"

//Usando StringIndexer
import org.apache.spark.sql.DataFrame
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.StringIndexerModel
def indexStringColumns(df:DataFrame, cols:Array[String]):DataFrame ={
    var newdf = df
    for(col <- cols) {
        val si = new StringIndexer().setInputCol(col).setOutputCol(col+"-num")
        val sm:StringIndexerModel = si.fit(newdf)
        newdf = sm.transform(newdf).drop(col)
        newdf = newdf.withColumnRenamed(col+"-num", col)
    }
    newdf
}
val carDFnumeric = indexStringColumns(carDF, Array("coste_compra", "coste_mantenimiento", "puertas", "personas", "maletero","seguridad"))

//Lo examinamos
carDFnumeric.show(10)

//Aplicando OneHotEncoderEstimator
//Ningún algoritmo pueda usar el orden a los indices de las clases
import org.apache.spark.ml.feature.OneHotEncoderEstimator
import org.apache.spark.ml.feature.OneHotEncoderModel
def oneHotEncodeColumns(df:DataFrame, cols:Array[String]):DataFrame ={
    var newdf = df
    val tmpcols = cols.map(x => x+"onehot")
    val onehotenc = new OneHotEncoderEstimator().setInputCols(cols).setOutputCols(tmpcols)
    val model=onehotenc.fit(df)
    newdf=model.transform(df)
    for(c <- cols) {
        newdf = newdf.drop(c)
        newdf = newdf.withColumnRenamed(c+"-onehot", c)
    }
    newdf
}
val carDFhot = oneHotEncodeColumns(carDFnumeric, Array("coste_compra","coste_mantenimiento", "puertas", "personas", "maletero", "seguridad"))
//Lo examinamos
carDFhot.show(10)

//Crear la columna features con VectoresAssambler
import org.apache.spark.ml.feature.VectorAssembler
val va = new VectorAssembler().setOutputCol("features").setInputCols(carDFhot.columns.diff(Array("clase")))
val carDFlpoints = va.transform(carDFhot).select("features","clase")

// Lo examinamos
carDFlpoints.show(10)


//Crear el label utilizando la columan de clase
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.StringIndexerModel

val indiceClase= new StringIndexer().setInputCol("clase").setOutputCol("label")
val carFeaturesLabelDF = indiceClase.fit(carDFlpoints).transform(carDFlpoints).drop("clase")

//Lo examinamos
carFeaturesLabelDF.show(10)

//Entrenamos el clasificador
// Realizamos una partición aleatoria de los datos 66% para entrenamiento, 34% para prueba
val dataSplits = carFeaturesLabelDF.randomSplit(Array(0.66, 0.34))
val trainCarDF = dataSplits(0)
val testCarDF = dataSplits(1)

//Creamos la instancia del modelo deseado "estimator"
import org.apache.spark.ml.classification.DecisionTreeClassifier

// Creamos una instancia de DecisionTreeClassifier */
val DTcar = new DecisionTreeClassifier()

// Fijamos parametros del modelo */
val impureza = "entropy"
val maxProf = 3
val maxBins = 5
DTcar.setImpurity(impureza)
DTcar.setMaxDepth(maxProf)
DTcar.setMaxBins(maxBins)

// Entrenamos el modelo */ Tiene que tener una columna features y label
//Arbol de decisión ya creado
val DTcarModel = DTcar.fit(trainCarDF)

// Predecimos la clase de los ejemplos de prueba */
val predictionsAndLabelsDF = DTcarModel.transform(testCarDF).select("prediction", "label")
predictionsAndLabelsDF.show

//Generamos el arbol
DTcarModel.toDebugString

//Ahora evaluamos con MLlib
//Hay que convertir el DF a RDD
// Necesitamos un RDD de predicciones y etiquetas: es Mllib */
val predictionsAndLabelsRDD=predictionsAndLabelsDF.rdd.map(row => (row.getDouble(0), row.getDouble(1)))

//Ahora calculando la tasa de error
// Importamos de Mllib */
import org.apache.spark.mllib.evaluation.MulticlassMetrics
// Creamos una instancia de Metrics */
val metrics = new MulticlassMetrics(predictionsAndLabelsRDD)
// Calculamos la tasa de acierto */
val acierto = metrics.accuracy
// Calculamos el error */
val error = 1 - acierto

// Guardamos el modelo */
//DTcarModel.save( PATH + “DTcarModelML")
DTcarModel.write.overwrite().save(PATH + "DTcarModelML")




/// -------------- SEGUNDA PARTE ---------------- ///
//Configure an ML pipeline, which consists of three stages: tokenizer, hashingTF, and lr.
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.Pipeline

val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")

val hashingTF = new HashingTF().setNumFeatures(1000).setInputCol(tokenizer.getOutputCol).setOutputCol("features")

val lr = new LogisticRegression().setMaxIter(10).setRegParam(0.01)

val pipeline = new Pipeline().setStages(Array(tokenizer, hashingTF, lr))

//Luego entrenamos el pipeline
//No lo utilizamos más adelante
//val model = pipeline.fit(trainCarDF)

//Creamos una isntancia de CrossValidator
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
val crossval = new CrossValidator().setEstimator(pipeline).setEvaluator(new BinaryClassificationEvaluator)

//Creamos la rejilla
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}
val paramGrid = new ParamGridBuilder().addGrid(hashingTF.numFeatures, Array(10, 100,1000)).addGrid(lr.regParam, Array(0.1, 0.01)).build()

crossval.setEstimatorParamMaps(paramGrid)
crossval.setNumFolds(2)

//Obtenemos el modelo
val trainingDF=training.toDF
val cvModel = crossval.fit(trainingDF)

//Obtener el meor modelo de regresión logística con la mejor configuración de parámetros para el grid
val lrModel = cvModel.bestModel

// Prepare test documents, which are unlabeled.
val test = sparkContext.parallelize(Seq(
  Document(4L, "spark i j k"),
  Document(5L, "l m n"),
  Document(6L, "mapreduce spark"),
  Document(7L, "apache hadoop")))

//Hay que convertir el test en un DF y no en una estructura de tipo double
val testDF=test.toDF

//Hacemos la predicción en el test de documentos usando el mejor modelo encontrado
cvModel.transform(testDF).select('id, 'text, 'probability, 
'prediction).collect().foreach { case Row(id: Long, text: String,
probability: Any, prediction: Double) => println("(" + id + ", " +
text + ") --> probability=" + probability + ", prediction=" +
prediction) }

//Evaluamos
val bceval = new BinaryClassificationEvaluator()
val auc = bceval.evaluate(lrModel.transform(trainingDF))