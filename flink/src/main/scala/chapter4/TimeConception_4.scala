package chapter4

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.windowing.time.Time

object TimeConception_4 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //在系统中指定EventTime
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    //    4.2.2 EventTime和Watermark
    //Flink会将用读取进入系统的最新事件时间减去固定的时间间隔作为Watermark，该时间间隔为用户外部配置的支持最大延迟到达
    // 的时间长度，也就是说理论上认为不会有事件超过该间隔到达，否则就认为是迟到事件或异常事件。
    //    1．指定Timestamps与生成Watermarks
    //    1）在Source Function中直接定义Timestamps和Watermarks
    val input = List(("a", 1L, 1), ("b", 1L, 1), ("b", 3L, 1), ("d", 3L, 1))
    val source = env.addSource(
      new SourceFunction[(String, Long, Int)] {
        override def run(sourceContext: SourceFunction.SourceContext[(String, Long, Int)]): Unit = {
          input.foreach(value => {
            //调用collectWithTimestamp增加Event Time抽取
            //生成EventTime时间戳
            sourceContext.collectWithTimestamp(value, value._2)
            //调用emitWatermark,创建Watermark,最大延时设定为1
            sourceContext.emitWatermark(new Watermark(value._2 - 1))
          })
          //设定默认Watermark
          sourceContext.emitWatermark(new Watermark(Long.MaxValue))
        }

        override def cancel(): Unit = {}
      }
    )
    //    （2）通过Flink自带的Timestamp Assigner指定Timestamp和生成Watermark
    //    如果用户使用了Flink已经定义的外部数据源连接器，就不能再实现SourceFuncton接口来生成流式数据以及相应的Event Time和Watermark，
    //    这种情况下就需要借助Timestamp Assigner来管理数据流中的Timestamp元素和Watermark。
    //    Periodic Watermarks是根据设定时间间隔周期性地生成Watermarks,
    //    Punctuated Watermarks是根据接入数据的数量生成

    //    Periodic Watermark Assigner，一种为升序模式，会将数据中的Timestamp根据指定字段提取，并用当前的Timestamp作为最新的Watermark，
    //    这种Timestamp Assigner比较适合于事件按顺序生成，没有乱序事件的情况；另外一种是通过设定固定的时间间隔来指定Watermark落后
    //    于Timestamp的区间长度，也就是最长容忍迟到多长时间内的数据到达系统。

    //    1）使用Ascending Timestamp Assigner指定Timestamps和Watermarks
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val input1 = env.fromCollection(List(("a", 1L, 1), ("b", 2L, 1), ("b", 3L, 1), ("d", 3L, 1)))
    //使用系统默认Ascending分配时间信息和Watermark
    val withTimestampAndWatermarks: DataStream[(String, Long, Int)] = input1.assignAscendingTimestamps(t => t._2)
    val result = withTimestampAndWatermarks.keyBy(0)
      .timeWindow(Time.seconds(10)).sum("_2")
//    result.print()

    //    2）使用固定时延间隔的Timestamp Assigner指定Timestamps和Watermarks
    val withTimestampAndWatermarks1 = input1.assignTimestampsAndWatermarks(
      new BoundedOutOfOrdernessTimestampExtractor[(String, Long, Int)](Time.seconds(10)) {
        override def extractTimestamp(t: (String, Long, Int)): Long = {
          t._2
        }
      }
    )
    val result2 = withTimestampAndWatermarks1.keyBy(0)
      .timeWindow(Time.seconds(10)).sum("_3")
    result2.print()
    env.execute()
  }
}
