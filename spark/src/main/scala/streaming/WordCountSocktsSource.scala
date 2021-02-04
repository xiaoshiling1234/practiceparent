package streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WordCountSocktsSource {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf,Seconds(5))

    val lines = ssc.socketTextStream("localhost",9999)

    val words = lines.flatMap(_.split(" "))

    val pairs = words.map(word=>(word,1))

    val wordCounts = pairs.reduceByKey(_+_)

    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
