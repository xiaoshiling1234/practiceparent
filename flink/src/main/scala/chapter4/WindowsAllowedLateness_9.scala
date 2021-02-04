package chapter4

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.{DataStream, OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

class WindowsAllowedLateness_9 {
  def main(args: Array[String]): Unit = {
    //延时数据处理
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val inputStream: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))
    //使用Scala Lambada表达式定义Windows ReduceFunction
    val lateOutputLag=OutputTag[(String,Int)]("late")
    val result = inputStream
      .assignAscendingTimestamps(_._2)
      .keyBy(0)
      //指定窗口类型
      .window(SlidingEventTimeWindows.of(Time.hours(1), Time.minutes(10)))
      .allowedLateness(Time.seconds(2))
      .sideOutputLateData(lateOutputLag)
      //指定聚合函数逻辑，将根据ID将第二个字段求和
      .reduce { (v1, v2) => (v1._1, v1._2 + v2._2) }

    val lateStream = result.getSideOutput(lateOutputLag)


  }

}
