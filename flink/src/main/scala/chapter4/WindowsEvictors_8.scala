package chapter4

class WindowsEvictors_8 {
  /**
   * Evictors是Flink窗口机制中一个可选的组件，其主要作用是对进入WindowFuction前后的数据进行剔除处理，
   * Flink内部实现CountEvictor、DeltaEvictor、TimeEvitor三种Evictors。在Flink中Evictors通过调用DataStreamAPI中evictor()
   * 方法使用，且默认的Evictors都是在WindowsFunction计算之前对数据进行剔除处理。
   * ❑CountEvictor：保持在窗口中具有固定数量的记录，将超过指定大小的数据在窗口计算前剔除；
   * ❑DeltaEvictor：通过定义DeltaFunction和指定threshold，并计算Windows中的元素与最新元素之间的Delta大小，如果超过threshold则将当前数据元素剔除；
   * ❑TimeEvictor：通过指定时间间隔，将当前窗口中最新元素的时间减去Interval，然后将小于该结果的数据全部剔除，其本质是将具有最新时间的数据选择出来，删除过时的数据。
   */
}
