package chapter4

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011

object DataSink_3 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val personStream = env.fromElements(("xiaobing", 18), ("doudou", 19))
    //    1．基本数据输出 文件输出、客户端输出、Socket网络端口等
    personStream.writeAsCsv("file:///C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\out\\person.csv", writeMode = WriteMode.OVERWRITE)
    personStream.writeAsText("file:///C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\out\\person.txt")
//    personStream.writeToSocket("localhost", 9999, new SimpleStringSchema())

//    2．第三方数据输出
  val kafkaProducer=new FlinkKafkaProducer011[String](
    "localhost:9092",
    "kafka-topic",
    new SimpleStringSchema
  )
    val wordStream=env.fromElements("Alex","Peter")
    wordStream.addSink(kafkaProducer)
    env.execute()
  }
}
