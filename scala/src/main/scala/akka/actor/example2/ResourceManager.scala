package akka.actor.example2

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable

class ResourceManager(var resourceManagerHostName:String, var resourceManagerPort:Int) extends Actor{
  /**
   * 定义一个Map,接受MyNodeManager的注册信息，key是主机名，
   * value是NodeManagerInfo对象，里面存储主机名、CPU和内存信息
   * */
  var registerMap=new mutable.HashMap[String,NodeManagerInfo]()
  /**
   * 定义一个Set,接受MyNodeManager的注册信息，key是主机名，
   * value是NodeManagerInfo对象，里面存储主机名、CPU和内存信息
   * 实际上和上面的Map里面存档内容一样，容易变历，可以不用写，主要是模仿后面Spark里面的内容
   * 方便到时理解Spark源码
   * */
  var registerSet=new mutable.HashSet[NodeManagerInfo]()

  override def preStart(): Unit = {
    import scala.concurrent.duration._
    import context.dispatcher
    //定时检测
    context.system.scheduler.schedule(0 millis, 5000 millis, self,CheckTimeOut)
  }

  override def receive: Receive = {
    //匹配到NodeManager的注册信息进行对应处理
    case NodeManagerRegisterMsg(nodeManagerID,cpu,memory)=>{
      //将注册信息实例化为一个NodeManagerInfo对象
      val registerMsg=new NodeManagerInfo(nodeManagerID,cpu,memory)
      //将注册信息存储到registerMap和registerSet里面，key是主机名，value是NodeManagerInfo对象
      registerMap.put(nodeManagerID,registerMsg)
      registerSet += registerMsg
      //注册成功之后，反馈个MyNodeManager一个成功的信息
      sender()!new RegisterFeedbackMsg("注册成功!" + resourceManagerHostName+":"+resourceManagerPort)
    }
    case HeartBeat(nodeManagerID)=>{
      //获取当前时间
      val time:Long = System.currentTimeMillis()
      //根据nodeManagerID获取NodeManagerInfo对象
      val info = registerMap(nodeManagerID)
      info.lastHeartBeatTime=time
      //更新registerMap和registerSet里面nodeManagerID对应的NodeManagerInfo对象信息(最后一次心跳时间)
      registerMap(nodeManagerID)=info
      registerSet+=info
    }
    //检测超时，对超时的数据从集合中删除
    case CheckTimeOut=>{
      var time = System.currentTimeMillis()
      registerSet.filter(nm=>time-nm.lastHeartBeatTime>10000)
        .foreach(deadnm=>{
          registerSet-=deadnm
          registerMap.remove(deadnm.nodeManagerID)
        })
      println("当前注册成功的节点数：" + registerMap.size)
    }
  }
}
object ResourceManager{
  def main(args: Array[String]): Unit = {
    /**
     * 传参：
     *   ResourceManager的主机地址、端口号
     * */
    val RM_HOSTNAME = args(0)
    val RM_PORT = args(1).toInt

    val str:String =
      """
        |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
        |akka.remote.netty.tcp.hostname =localhost
        |akka.remote.netty.tcp.port=19888
      """.stripMargin
    val conf = ConfigFactory.parseString(str)
    val actorSystem = ActorSystem(Conf.RMAS,conf)
    actorSystem.actorOf(Props(new ResourceManager(RM_HOSTNAME,RM_PORT)),Conf.RMA)
  }
}
