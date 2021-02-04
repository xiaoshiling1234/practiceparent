package chapter4

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.{EventTimeSessionWindows, GlobalWindows, ProcessingTimeSessionWindows, SlidingEventTimeWindows, SlidingProcessingTimeWindows, TumblingEventTimeWindows, TumblingProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.time.Time

object Windows_5 {
  def main(args: Array[String]): Unit = {
    //Windows Assigner：指定窗口的类型，定义如何将数据流分配到一个或多个窗口；
    //Windows Trigger：指定窗口触发的时机，定义窗口满足什么样的条件触发计算；
    //Evictor：用于数据剔除；
    //Lateness：标记是否处理迟到数据，当迟到数据到达窗口中是否触发计算；
    //Output Tag：标记输出标签，然后在通过getSideOutput将窗口中的数据根据标签输出；
    //Windows Funciton：定义窗口上数据处理的逻辑，例如对数据进行sum操作。
    //  1. Keyed和Non-Keyed窗口
    //  如果是Non-Keyed类型，则调用WindowsAll()方法来指定Windows Assigner
    //  2. Windows Assigner
    //    （1）滚动窗口
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val inputStream: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))
    val tumblingEventTimeWindows = inputStream.keyBy(0)
      //通过使用TumblingEventTimeWindows定义Event Time滚动窗口
      .window(TumblingEventTimeWindows.of(Time.seconds(10)))
      //      .process(...)//定义窗口函数
      .sum(1)
    val tumblingProcessingTimeWindows = inputStream.keyBy(0)
      //通过使用TumblingEventTimeWindows定义Event Time滚动窗口
      .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
      //      .process(...)//定义窗口函数
      .sum(1)

    //窗口时间类型根据time characteristic确定
    val timeWindows = inputStream.keyBy(0)
      .timeWindow(Time.seconds(1))
      //      .process(...)//定义窗口函数
      .sum(1)
    //    （2）滑动窗口
    val slidingEventTimeWindows = inputStream.keyBy(0)
      .window(SlidingEventTimeWindows.of(Time.hours(1), Time.minutes(10)))
      //      .process(...)//定义窗口函数
      .sum(1)
    val slidingProcessingTimeWindows = inputStream.keyBy(0)
      .window(SlidingProcessingTimeWindows.of(Time.hours(1), Time.minutes(10)))
      //      .process(...)//定义窗口函数
      .sum(1)
    //    （3）会话窗口
    //    只需要定义session gap，来规定不活跃数据的时间上限即可
    val eventTimeSessionWindows = inputStream.keyBy(0)
      .window(EventTimeSessionWindows.withGap(Time.milliseconds(10)))
      //      .process(...)//定义窗口函数
      .sum(1)
    val processingTimeSessionWindows = inputStream.keyBy(0)
      .window(ProcessingTimeSessionWindows.withGap(Time.milliseconds(10)))
      //      .process(...)//定义窗口函数
      .sum(1)
    //    动态Session Gap
    //    （4）全局窗口
    //    全局窗口（Global Windows）将所有相同的key的数据分配到单个窗口中计算结果，窗口没有起始和结束时间，
    //    窗口需要借助于Triger来触发计算，如果不对Global Windows指定Triger，窗口是不会触发计算的
    val globalWindows = inputStream.keyBy(0)
      .window(GlobalWindows.create())
      //      .process(...)//定义窗口函数
      .sum(1)
    env.execute()
  }


}
