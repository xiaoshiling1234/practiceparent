package chapter4

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
class TaskChain_10 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //关闭全局作业链
    env.disableOperatorChaining()
    val dataStream=env.fromElements(1,2,3,4,5,1,2,3)
    //创建的链条只对当前的操作符和之后的操作符有效
    val ds1=dataStream.filter(_%2==0).map((_,1)).startNewChain().map((_._2+1))
    //警用局部链条
    val ds2=dataStream.map((_,1)).disableChaining()

    //slots资源组
    dataStream.filter(_%2==0).slotSharingGroup("name")
  }
}
