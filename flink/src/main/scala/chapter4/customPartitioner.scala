package chapter4

import org.apache.flink.api.common.functions.Partitioner


object customPartitioner extends Partitioner[String]{
  //获取随机数
  private val r = scala.util.Random
  override def partition(key: String, numPartitions: Int): Int = {
    //定义分区策略
    if(key.contains("flink")) 0 else r.nextInt(numPartitions)
  }
}