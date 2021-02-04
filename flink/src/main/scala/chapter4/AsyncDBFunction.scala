package chapter4

import org.apache.flink.streaming.api.scala.async.{AsyncFunction, ResultFuture}


class AsyncDBFunction extends AsyncFunction[String,(String,String)]{

  /**
   * 在使用Flink处理流式数据的过程中，会经常和外部系统进行数据交互。通常情况下在Flink中可以通过RichMapFunction来创建外
   * 部数据库系统的Client连接，然后通过Client连接将数据元素写入外部存储系统中或者从外部存储系统中读取数据。考虑到连接外
   * 部系统的网络等因素，这种同步查询和操作数据库的方式往往会影响整个函数的处理效率，用户如果想提升应用的处理效率，就必
   * 须考虑增加算子的并行度，这将导致大量的资源开销。Flink在1.2版本中引入了Asynchronous I/O，能够支持通过异步方式连接外
   * 部存储系统，以提升Flink系统与外部数据库交互的性能及吞吐量，但前提是数据库本身需要支持异步客户端。
   */
  override def asyncInvoke(input: String, resultFuture: ResultFuture[(String, String)]): Unit = ???
}
