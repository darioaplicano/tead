import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils

val PATH ="C:\\"
val file="sample_multiclass_classification_data.txt"

val data = MLUtils.loadLibSVMFile(sc,PATH+file)

// Split data into training (60%) and test (40%)
val Array(training, test) = data.randomSplit(Array(0.6, 0.4), seed = 11L)
training.cache()

import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS
// Run training algorithm to build the model
val model = new LogisticRegressionWithLBFGS().setNumClasses(3).run(training)

import org.apache.spark.mllib.regression.LabeledPoint
// Compute raw scores on the test set
val predictionAndLabels = test.map { case
LabeledPoint(label, features) => val prediction =
model.predict(features) 
(prediction, label) }

import org.apache.spark.mllib.evaluation.MulticlassMetrics
// Instantiate metrics object
val metrics = new MulticlassMetrics(predictionAndLabels)
// Confusion matrix
println("Confusion matrix:")
println(metrics.confusionMatrix)

// Overall Statistics
val accuracy = metrics.accuracy
println("Summary Statistics")
println(s"Accuracy = $accuracy") 

// Precision by label
val labels = metrics.labels
labels.foreach { l => println(s"Precision($l) = " +
metrics.precision(l)) } 

// Recall by label
labels.foreach { l => println(s"Recall($l) = " +
metrics.recall(l)) }

// False positive rate by label
labels.foreach { l => println(s"FPR($l) = " +
metrics.falsePositiveRate(l)) } 

// F-measure by label
labels.foreach { l => println(s"F1-Score($l) = " +
metrics.fMeasure(l)) }

// Weighted stats
println(s"Weighted precision: ${metrics.weightedPrecision}")
println(s"Weighted recall: ${metrics.weightedRecall}")
println(s"Weighted F1 score: ${metrics.weightedFMeasure}") 
println(s"Weighted false positive rate:${metrics.weightedFalsePositiveRate}")