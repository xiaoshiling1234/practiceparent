package chapter4

import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.co.{CoFlatMapFunction, CoMapFunction}
import org.apache.flink.streaming.api.scala.{BroadcastConnectedStream, ConnectedStreams, DataStream, KeyedStream, SplitStream, StreamExecutionEnvironment}
import org.apache.flink.util.Collector

object DataSteamTransform_2 {
  def main(args: Array[String]): Unit = {
    //    1. Single-DataStream操作
    //    （1）Map [DataStream->DataStream]
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val dataStream: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))
    val mapStream1: DataStream[(String, Int)] = dataStream.map(t => (t._1, t._2 + 1))
    val mapStream2: DataStream[(String, Int)] = dataStream.map(new MapFunction[(String, Int), (String, Int)] {
      override def map(t: (String, Int)): (String, Int) = {
        (t._1, t._2 + 1)
      }
    })
    //    （2）FlatMap [DataStream->DataStream]
    //    （3）Filter [DataStream->DataStream]
    //    （4）KeyBy [DataStream->KeyedStream]
    val keyedStream: KeyedStream[(String, Int), Tuple] = dataStream.keyBy(0)
    //    （5）Reduce [KeyedStream->DataStream]
    //    滚动队第二个字段进行reduce相加求和
    val reduceStream: DataStream[(String, Int)] = keyedStream.reduce { (t1, t2) => (t1._1, t1._2 + t2._2) }
    //    （6）Aggregations[KeyedStream->DataStream]
    //    其实是将Reduce算子中的函数进行了封装，封装的聚合操作有sum、min、minBy、max、maxBy
    val sumStream: DataStream[(String, Int)] = keyedStream.sum(1)
    //滚动计算指定key的最小值
    val minStream: DataStream[(String, Int)] = keyedStream.min(1)
    //滚动计算指定key的最大值
    val maxStream: DataStream[(String, Int)] = keyedStream.max(1)
    //滚动计算指定key的最小值,返回最小值对应的元素
    val minByStream: DataStream[(String, Int)] = keyedStream.minBy(1)
    //滚动计算指定key的最大值,返回最大值对应的元素
    val maxByStream: DataStream[(String, Int)] = keyedStream.maxBy(1)
    //      2. Multi-DataStream操作
    //      （1）Union[DataStream ->DataStream]
    val dataStream1: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))
    val dataStream2: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))
    val dataStream3: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))
    //合并两个dataStream数据集
    val unionSteam = dataStream1.union(dataStream2)
    //合并多个dataStream数据集
    val allUnionStream: DataStream[(String, Int)] = dataStream1.union(dataStream2, dataStream3)
    //      （2）Connect, CoMap, CoFlatMap[DataStream->DataStream]
    //      对于ConnectedStreams类型的数据集不能直接进行类似Print()的操作，需要再转换成DataStream类型数据集
    //      Connect算子主要是为了合并两种或者多种不同数据类型的数据集，合并后会保留原来数据集的数据类型。
    //    CoMapFunction和CoFlaMapFunction中的两个方法，在Paralism>1的情况下，不会按照指定的顺序指定，因此有可能会影响输出数据的顺序和结果，这点用户在使用过程中需要注意。
    val dataStream11: DataStream[(String, Int)] = env.fromElements(("a", 3), ("d", 4), ("c", 2), ("c", 5), ("a", 5))
    val dataStream22: DataStream[Int] = env.fromElements(1, 2, 3, 4, 5)
    val connectedStream: ConnectedStreams[(String, Int), Int] = dataStream11.connect(dataStream22)
    val resultStream1: DataStream[(Int, String)] = connectedStream.map(new CoMapFunction[(String, Int), Int, (Int, String)] {
      //定义第一个数据集
      override def map1(in1: (String, Int)): (Int, String) = {
        (in1._2, in1._1)
      }

      override def map2(in2: Int): (Int, String) = {
        (in2, "default")
      }
    })

    val resultStream2: DataStream[(String, Int, Int)] = connectedStream.flatMap(new CoFlatMapFunction[(String, Int), Int, (String, Int, Int)] {
      //定义共享变量
      var number = 0

      override def flatMap1(in1: (String, Int), collector: Collector[(String, Int, Int)]): Unit = {
        collector.collect((in1._1, in1._2, number))
      }

      override def flatMap2(in2: Int, collector: Collector[(String, Int, Int)]): Unit = {
        number = in2
      }
    })
    //      通过指定的条件对两个数据集进行关联，然后产生相关性比较强的结果数据集。
    //通过keyby函数根据指定的key连接两个数据集
    //      通过使用keyby函数会将相同的key的数据路由在一个相同的Operator中
    //        val keyedConnect: ConnectedStreams[(String, Int), Int] = connectedStream.keyBy(1, 0)
    //通过broadcast关联两个数据集
    //      而BroadCast广播变量会在执行计算逻辑之前将dataStream2数据集广播到所有并行计算的Operator中，
    //      这样就能够根据条件对数据集进行关联，这其实也是分布式Join算子的基本实现方式。
    val broadcastConnect: BroadcastConnectedStream[(String, Int), Int] = dataStream11.connect(dataStream22.broadcast())

    //    （3）Split [DataStream->SplitStream] 只是添加了标记
    val splitedStream: SplitStream[(String, Int)] = unionSteam.split(t => if (t._2 % 2 == 0) Seq("even") else Seq("old"))
    //    （4）Select [SplitStream ->DataStream]
    //筛选偶数数据集
    val evenStream: DataStream[(String, Int)] = splitedStream.select("even")
    //筛选奇数数据集
    val oddStream: DataStream[(String, Int)] = splitedStream.select("odd")
    //筛选奇数和偶数
    val allStream: DataStream[(String, Int)] = splitedStream.select("even", "odd")
    //    （5）Iterate[DataStream->IterativeStream->DataStream]
    //    通过每一次的迭代计算，并将计算结果反馈到下一次迭代计算中(同时指定多个操作)
    val dataStream111 = env.fromElements(3, 1, 2, 1, 5, 0).map(t => t)
    //    Iterate算子适合于迭代计算场景，通过每一次的迭代计算，并将计算结果反馈到下一次迭代计算中。如下代码所示，
    //    调用dataStream的iterate()方法对数据集进行迭代操作，如果事件指标加1后等于2，则将计算指标反馈到下一次迭代的通道中，
    //    如果事件指标加1不等于2则直接输出到下游DataStream中。其中在执行之前需要对数据集做map处理主要目的是为了对数据分区根
    //    据默认并行度进行重平衡，在iterate()内参数类型为ConnectedStreams，然后调用ConnectedStreams的方法内分别执行两个map方法，
    //    第一个map方法执行反馈操作，第二个map函数将数据输出到下游数据集。
    //    (3, 1, 2, 1, 5)
    val iterated = dataStream111.iterate((input: ConnectedStreams[Int, String]) => {
      //分别定义两个map方法完成对输入ConnectedStreams数据集数据到处理
      val head = input.map(i => (i + 1).toString, s => s)
      (head.filter(_ == "2"), head.filter(_ != "2"))
    }, 1000) //1000指定最长迭代等待时间，单位为ms，超过该事件没有数据接入则终止迭代
    iterated.print()
    env.execute()
    //    3．物理分区操作
    //    （1）随机分区（RandomPartitioning）:[DataStream ->DataStream]
    val shuffleStream = dataStream.shuffle
    //    （2）Roundrobin Partitioning:[DataStream ->DataStream]
    val rebalanceStream = dataStream.rebalance
    //    （3）Rescaling Partitioning:[DataStream ->DataStream]
    //    和Roundrobin Partitioning一样，RescalingPartitioning也是一种通过循环的方式进行数据重平衡的分区策略。但是不同的是，
    //    当使用Roundrobin Partitioning时，数据会全局性地通过网络介质传输到其他的节点完成数据的重新平衡，
    //    而Rescaling Partitioning仅仅会对上下游继承的算子数据进行重平衡，具体的分区主要根据上下游算子的并行度决定。
    val rescaleSteam = dataStream.rescale
    //    （4）广播操作（Broadcasting）:[DataStream ->DataStream]
    //    广播策略将输入的数据集复制到下游算子的并行的Tasks实例中，下游算子中的Tasks可以直接从本地内存中获取广播数据集，不再依赖于网络传输。
    val broadCastStream = dataStream.broadcast()
    //    （5）自定义分区（CustomPartitioning）:[DataStream ->DataStream]
    //通过字段名称
    dataStream.partitionCustom(customPartitioner, "field_name")
    //通过字段索引
    dataStream.partitionCustom(customPartitioner, 0)
  }
}
