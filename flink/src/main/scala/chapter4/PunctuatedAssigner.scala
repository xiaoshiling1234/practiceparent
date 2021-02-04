package chapter4

import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks
import org.apache.flink.streaming.api.watermark.Watermark

class PunctuatedAssigner extends AssignerWithPunctuatedWatermarks[(String,Long,Int)]{

  //定义Watermark生成逻辑
  override def checkAndGetNextWatermark(lastElement: (String, Long, Int), extractedTimestamp: Long): Watermark = {
    if (lastElement._3==0) new Watermark(extractedTimestamp) else null
  }

  //定义抽取Timestamp逻辑
  override def extractTimestamp(element: (String, Long, Int), perviousElementTimestamp: Long): Long = {
    element._2
  }
}
