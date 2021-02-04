package chapter4

import java.util.Properties

import org.apache.flink.api.java.io.CsvInputFormat
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object DataSourceInputs_1 {
  //1．内置数据源
  //（1）文件数据源
  private val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
  //直接读取文本文件
  val textStream = env.readTextFile("C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\flink\\src\\main\\resources\\data\\words.txt")
  //制定CSVInputFormat读取CSV文件
  //  可以在readFile方法中指定文件读取类型（WatchType）、检测文件变换时间间隔（interval）、
  //  文件路径过滤条件（FilePathFilter）等参数，其中WatchType共分为两种模式——PROCESS_CONTINUOUSLY和PROCESS_ONCE模式。
  //  在PROCESS_CONTINUOUSLY模式下，一旦检测到文件内容发生变化，Flink会将该文件全部内容加载到Flink系统中进行处理。
  //  而在PROCESS_ONCE模式下，当文件内容发生变化时，只会将变化的数据读取至Flink中，在这种情况下数据只会被读取和处理一次。
  val csvStream = env.readFile(new CsvInputFormat[String](
    new Path("C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\flink\\src\\main\\resources\\data\\name.csv")) {
    override def fillRecord(out: String, objects: Array[AnyRef]): String = {
      return null
    }
  }, "C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\flink\\src\\main\\resources\\data\\name.csv")

  //  （2）Socket数据源
  val socketDataStream = env.socketTextStream("localhost", 9999)
  //  （3）集合数据源
  //  目前Flink支持从Java.util.Collection和java.util.Iterator序列中转换成DataStream数据集。这种方式非常适合调试Flink本地程序，
  //  但需要注意的是，集合内的数据结构类型必须要一致，否则可能会出现数据转换异常。
  //  通过fromElements从元素集合中创建DataStream数据集：
  val dataStream = env.fromElements(Tuple2(1L, 3L), (1L, 5L), (2L, 3L))
  //  通过fromCollection从数组转创建DataStream数据集：(java使用)
  //  2．外部数据源
  //  (1)Kafka Event DataSource
  private val properties = new Properties()
  properties.setProperty("bootstrap.servers", "master:9092,slave1:9092,slave2:9092")
  properties.setProperty("zookeeper.connect", "master:2181")
  properties.setProperty("group.id", "flink_test")
  //    ...
  //  （2）自定义数据源连接器
  //  可以通过实现SourceFunction定义单个线程的接入的数据接入器，也可以通过实现ParallelSource-Function接口或继承
  //  RichParallelSourceFunction类定义并发数据源接入器。
}
