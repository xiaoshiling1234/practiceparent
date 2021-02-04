import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
    conf.setAppName("wordCount")
    conf.setMaster("local")

    val sc: SparkContext = new SparkContext(conf)
    val rdd: RDD[String] = sc.textFile(this.getClass.getClassLoader.getResource("word.txt").getPath)

    rdd.flatMap(_.split(" "))
      .map((_,1))
      .countByKey()
//      .foreach(println)
//      .reduceByKey(_+_)
//      .collect()
//      .foreach(println)

  }
}
