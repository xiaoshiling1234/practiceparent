package chapter4

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows
import org.apache.flink.streaming.api.windowing.time.Time
object WindowsTrigger_7 {
  /**
   * 数据接入窗口后，窗口是否触发WindowFunciton计算，取决于窗口是否满足触发条件，每种类型的窗口都有对应的窗口触发机制，
   * 保障每一次接入窗口的数据都能够按照规定的触发逻辑进行统计计算
   * 以下对Flink自带的窗口触发器进行分类整理，用户可以根据需要选择合适的触发器：
   * ❑EventTimeTrigger：通过对比Watermark和窗口EndTime确定是否触发窗口，如果Watermark的时间大于Windows EndTime则触发计算，否则窗口继续等待；
   * ❑ProcessTimeTrigger：通过对比ProcessTime和窗口EndTime确定是否触发窗口，如果窗口Process Time大于Windows EndTime则触发计算，否则窗口继续等待；
   * ❑ContinuousEventTimeTrigger：根据间隔时间周期性触发窗口或者Window的结束时间小于当前EventTime触发窗口计算；
   * ❑ContinuousProcessingTimeTrigger：根据间隔时间周期性触发窗口或者Window的结束时间小于当前ProcessTime触发窗口计算；
   * ❑CountTrigger：根据接入数据量是否超过设定的阈值确定是否触发窗口计算；
   * ❑DeltaTrigger：根据接入数据计算出来的Delta指标是否超过指定的Threshold，判断是否触发窗口计算；
   * ❑PurgingTrigger：可以将任意触发器作为参数转换为Purge类型触发器，计算完成后数据将被清理。
   * *
   * 如果已有Trigger无法满足实际需求，用户也可以继承并实现抽象类Trigger自定义触发器，FlinkTrigger接口中共有如下方法需要复写，
   * 然后在DataStream API中调用trigger方法传入自定义Trigger。
   * ❑OnElement()：针对每一个接入窗口的数据元素进行触发操作；
   * ❑OnEventTime()：根据接入窗口的EventTime进行触发操作；
   * ❑OnProcessTime()：根据接入窗口的ProcessTime进行触发操作；
   * ❑OnMerge()：对多个窗口进行Merge操作，同时进行状态的合并；
   * ❑Clear()：执行窗口及状态数据的清除方法。
   * 在自定义触发器时，判断窗口触发方法返回的结果有如下类型，分别是CONTINUE、FIRE、PURGE、FIRE_AND_PURGE。
   * 其中CONTINUE代表当前不触发计算，继续等待；FIRE代表触发计算，但是数据继续保留；PURGE代表窗口内部数据清除，
   * 但不触发计算；FIRE_AND_PURGE代表触发计算，并清除对应的数据；
   **/
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val inputStream: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))
    //使用Scala Lambada表达式定义Windows ReduceFunction
    val reduceWindowStream = inputStream
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[(String, Int)](Time.seconds(2)) {
        override def extractTimestamp(element: (String, Int)): Long = {
          element._2
        }
      })
      .keyBy(0)
      //指定窗口类型
      .window(EventTimeSessionWindows.withGap(Time.milliseconds(10)))
      .trigger(ContinuousEventTimeTrigger.of(Time.seconds(5)))
      //指定聚合函数逻辑，将根据ID将第二个字段求和
      .reduce { (v1, v2) => (v1._1, v1._2 + v2._2) }
    reduceWindowStream.print()
    env.execute()
  }

}
