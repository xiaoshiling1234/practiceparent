package chapter3

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
object WordCount3_1 {
  def main(args: Array[String]): Unit = {
    //第一步：设定执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //其他方式
    //StreamExecutionEnvironment.getExecutionEnvironment
    //StreamExecutionEnvironment.createLocalEnvironment(5)
    //StreamExecutionEnvironment.createRemoteEnvironment("host",6021,5,"user/application.jar")
    //批处理
    //ExecutionEnvironment xxx其他不变
    //第二步：指定数据源地址，读取输入数据
    val text = env.readTextFile("file:///C:\\Users\\xiaobing\\IdeaProjects\\test\\practiceparent\\flink\\src\\main\\resources\\data\\words.txt")
    //第三步：对数据集指定转换操作逻辑
    val counts: DataStream[(String, Int)] =text.flatMap(_.toLowerCase.split(" "))
      .filter(_.nonEmpty)
      .map((_,1))
      // （1）根据字段位置指定（0）
      // （2）根据字段名称指定（“name”）
      // （3）根据key选择器 new KeySelector
      .keyBy(0)
      .sum(1)
    //第四步：指定计算结果输出位置
    counts.print()
    counts.writeAsText("out/WordCount3_1_out"+ System.currentTimeMillis().toString
    )
    //第五步：指定名称并触发流式任务
    env.execute("WordCount3_1")
  }
}
