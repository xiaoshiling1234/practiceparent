package chapter4

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.api.common.state.ReducingStateDescriptor
import org.apache.flink.api.common.typeinfo.Types
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.Trigger.TriggerContext
import org.apache.flink.streaming.api.windowing.triggers.{Trigger, TriggerResult}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow

class ContinuousEventTimeTrigger(interval: Long) extends Trigger[Object, TimeWindow] {
  //重定义Java.lang.Long类型为JLong类型
  private type JLong = java.lang.Long
  //实现函数，求取2个时间戳的最小值
  private val min = new ReduceFunction[JLong] {
    override def reduce(t: JLong, t1: JLong): JLong = {
      Math.min(t, t1)
    }
  }
  private val stateDesc = new ReducingStateDescriptor[JLong](
    "trigger-time", min, Types.LONG)

  def clearTimerForState(ctx: TriggerContext) = {
    val timestamp = ctx.getPartitionedState(stateDesc).get()
    if (timestamp != null) {
      ctx.deleteEventTimeTimer(timestamp)
    }
  }

  //处理接入的元素，每次都会被调用
  override def onElement(element: Object, timestamp: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
    //如果当前的Watermark超过窗口的结束时间，则清除定时器内容，直接触发窗口计算
    if (window.maxTimestamp() <= ctx.getCurrentWatermark) {
      clearTimerForState(ctx: TriggerContext)
      TriggerResult.FIRE
    } else {
      //否則將窗口的结束时间注册给EventTime定时器
      ctx.registerEventTimeTimer(window.maxTimestamp())
      //获取当前分区状态中的时间戳
      val fireTimestamp = ctx.getPartitionedState(stateDesc)
      //如果第一次执行，则对元素的timestamp进行floor操作，取整后加上传入的实例变量interval.得到下一次触发时间并注册，
      // 添加到状态中
      if (fireTimestamp.get() == null) {
        val start = timestamp - (timestamp % interval)
        val nextFireTimestamp = start + interval
        ctx.registerEventTimeTimer(nextFireTimestamp)
        fireTimestamp.add(nextFireTimestamp)
      }
      //此时继续等待
      TriggerResult.CONTINUE
    }
  }

  //时间概念类型不选择ProcessTime.不会基于processing Time触发，直接返回CONTINUE
  override def onProcessingTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
    TriggerResult.CONTINUE
  }

  //当Watermark超过注册时间时，就会执行onEventTime方法
  override def onEventTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
    //如果事件时间等于maxTimestamp时间，则清空状态数据，并触发计算
    if (time == window.maxTimestamp()) {
      clearTimerForState(ctx)
      TriggerResult.FIRE
    } else {
      //否則，获取状态中的值(maxTimestamp和nextFireTimestamp的最小值)
      val fireTimestamp = ctx.getPartitionedState(stateDesc)
      //如果状态中的值等于事件时间，则清除定时器时间戳，注册下一个interval的时间戳，并触发窗口计算
      if (fireTimestamp.get() == time) {
        fireTimestamp.add(time + interval)
        ctx.registerEventTimeTimer(time + interval)
        TriggerResult.FIRE
      } else {
        TriggerResult.CONTINUE
      }
    }
  }

  override def clear(window: TimeWindow, ctx: Trigger.TriggerContext): Unit = {
    ctx.deleteEventTimeTimer(window.maxTimestamp())
    val fireTimestamp = ctx.getPartitionedState(stateDesc)
    val timestamp = fireTimestamp.get()
    if (timestamp != null) {
      ctx.deleteEventTimeTimer(timestamp)
      fireTimestamp.clear()
    }
  }

  override def toString: String = {
    s"ContinuousEventTimeTrigger($interval)"
  }
}

object ContinuousEventTimeTrigger {
  def of(interval: Time) = new ContinuousEventTimeTrigger(interval.toMilliseconds)
}
