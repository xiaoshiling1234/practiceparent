package chapter4

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.watermark.Watermark

class PeriodicAssigner extends AssignerWithPeriodicWatermarks[(String,Long,Int)]{
  val maxOutOfOrderness=1000L//1秒时延设定，表示在1秒以内的数据延时有效，超过一秒的数据被认定为迟到数据
  var currentMaxTimestamp:Long=_
  override def getCurrentWatermark: Watermark = {
    new Watermark(currentMaxTimestamp-maxOutOfOrderness)
  }

  override def extractTimestamp(event: (String, Long, Int), previousEventsTimestamp: Long): Long = {
    val currentTimestamp=event._2
    //对比当前的事件时间和历史最大事件时间，将最新的时间赋值给currentMaxTimestamp变量
    if (currentMaxTimestamp==None){
      currentMaxTimestamp=currentTimestamp
    }
    if( currentTimestamp>currentMaxTimestamp ){
      currentMaxTimestamp=currentTimestamp
    }
    currentTimestamp
  }
}
