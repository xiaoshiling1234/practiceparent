package chapter4

import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

class StaticProcessFunction extends ProcessWindowFunction[(String,Int),
  (String,Long,Long,Long,Long,Long),String,TimeWindow]{
  override def process(key: String,
                       context: Context,
                       elements: Iterable[(String, Int)],
                       out: Collector[(String, Long, Long, Long, Long, Long)]): Unit = {
    val sum=elements.map(_._2).sum
    val min=elements.map(_._2).min
    val max=elements.map(_._2).max
    val avg=sum/elements.size
    val windowEnd=context.window.getEnd
    out.collect((key,sum,min,max,avg,windowEnd))
  }
}
