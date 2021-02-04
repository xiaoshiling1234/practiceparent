package chapter4

import org.apache.flink.api.common.functions.AggregateFunction

class MyAverageAggregate extends AggregateFunction[(String,Long),(Long,Long),Double]{
  //定义createAccumulator为两个参数的元祖
  override def createAccumulator(): (Long, Long) = (0L,0L)
  //定义输入数据累加到accumulator的逻辑
  override def add(in: (String, Long), acc: (Long, Long)): (Long, Long) = (acc._1+in._2,acc._2+1L)
  //根据累加器合并的逻辑
  override def getResult(acc: (Long, Long)): Double = {
    acc._1/acc._2
  }
  //merge方法定义合并accumulator的逻辑
  override def merge(acc: (Long, Long), acc1: (Long, Long)): (Long, Long) = {
    (acc._1+acc1._1,acc._2+acc1._2)
  }
}
