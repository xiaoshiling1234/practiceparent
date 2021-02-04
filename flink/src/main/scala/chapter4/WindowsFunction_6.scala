package chapter4

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object WindowsFunction_6 {
  def main(args: Array[String]): Unit = {
    //    Flink中提供了四种类型的Window Function，
    //    分别为ReduceFunction、AggregateFunction、FoldFunction以及ProcessWindowFunction。
    //    四种类型的Window Fucntion按照计算原理的不同可以分为两大类，
    //    一类是增量聚合函数，对应有ReduceFunction、AggregateFunction和FoldFunction；
    //    另一类是全量窗口函数，对应有ProcessWindowFunction
    //    1. ReduceFunction
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val inputStream: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))
    //使用Scala Lambada表达式定义Windows ReduceFunction
    val reduceWindowStream1 = inputStream
      .assignAscendingTimestamps(_._2)
      .keyBy(0)
      //指定窗口类型
      .window(SlidingEventTimeWindows.of(Time.hours(1), Time.minutes(10)))
      //指定聚合函数逻辑，将根据ID将第二个字段求和
      .reduce { (v1, v2) => (v1._1, v1._2 + v2._2) }

    //使用Class实现接口的方式表达式定义Windows ReduceFunction
    val reduceWindowStream2 = inputStream
      .assignTimestampsAndWatermarks(
        new BoundedOutOfOrdernessTimestampExtractor[(String, Int)](Time.seconds(10)) {
          override def extractTimestamp(t: (String, Int)): Long = {
            t._2
          }
        }
      )
      .keyBy(_._1)
      .timeWindow(Time.seconds(10),Time.seconds(5))
      //定义ReduceFunction实现类
      .reduce(new ReduceFunction[(String, Int)] {
        override def reduce(t1: (String, Int), t2: (String, Int)): (String, Int) = {
          (t1._1, t1._2 + t2._2)
        }
      })
    reduceWindowStream2.print()
//    //    2. AggregateFunction
//    //    定义Windows AggregateFunction并在DataStream中使用
//    val aggregateWindowStream = inputStream.keyBy(_._1)
//      .window(SlidingEventTimeWindows.of(Time.hours(1), Time.minutes(10)))
//      //指定聚合函数逻辑，将根据ID将第二个字段求平均值，未知错误
//      .aggregate(new MyAverageAggregate)


//    3. FoldFunction
//    FoldFunction定义了如何将窗口中的输入元素与外部的元素合并的逻辑，

//    FoldFunction已经在Flink DataStream API中被标记为@Deprecated，也就是说很可能会在未来的版本中移除，
//    Flink建议用户使用AggregateFunction来替换使用FoldFunction。

    val foldWindowStream=inputStream
      .assignAscendingTimestamps(_._2)
      .keyBy(_._1)
    //指定窗口类型
      .window(SlidingEventTimeWindows.of(Time.hours(1),Time.minutes(10)))
      //    将“flink”字符串添加到inputStream数据集中所有元素第二个字段上，并将结果输出到下游DataStream中
      .fold("flink"){(acc,v)=>acc+v._2}

    foldWindowStream.print()

//    4. ProcessWindowFunction
//    统计更复杂的指标可能需要依赖于窗口中所有的数据元素，或需要操作窗口中的状态数据和窗口元数据

    val staticProcessFunction=inputStream
      .assignAscendingTimestamps(_._2)
      .keyBy(_._1)
      .timeWindow(Time.seconds(10))
      .process(new StaticProcessFunction)
    staticProcessFunction.print()
    env.execute()
//    5. Incremental Aggregation和ProcessWindowsFunction整合

  }
}
