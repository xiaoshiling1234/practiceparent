package akka.actor.example2

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

class NodeManager (resourceManagerHostName:String,resourceManagerPort:Int,cpu:Int,memory:Int) extends Actor {
  //MyNodeManager的UUID
  var nodeManagerID:String = _
  var rmref:ActorSelection = _

  override def preStart(): Unit = {
    //获取MyResourceManager的Actor的引用
    rmref = context.actorSelection(s"akka.tcp://${Conf.RMAS}@${resourceManagerHostName}:${resourceManagerPort}/user/${Conf.RMA}")
    //生成随机的UUID
    nodeManagerID = UUID.randomUUID().toString
    /**
     * 向MyResourceManager发送注册信息
     * */
    rmref ! NodeManagerRegisterMsg(nodeManagerID,cpu,memory)
  }
//进行消息匹配
  override def receive: Receive = {
    case RegisterFeedbackMsg(feedbackMsg)=>{
      /**
       * initialDelay: FiniteDuration, 多久以后开始执行
       * interval:     FiniteDuration, 每隔多长时间执行一次
       * receiver:     ActorRef, 给谁发送这个消息
       * message:      Any  发送的消息是啥
       */
      //定时任务需要导入的工具包
      import scala.concurrent.duration._
      import context.dispatcher
      //定时向自己发送信息
      context.system.scheduler.schedule(0 millis, 3000 millis, self, SendMessage)
    }
    //匹配到SendMessage信息之后做相应处理
    case SendMessage => {
      //向MyResourceManager发送心跳信息
      rmref ! HeartBeat(nodeManagerID)
      println(Thread.currentThread().getId + ":" + System.currentTimeMillis())
    }
  }
}
object NodeManager{
  def main(args: Array[String]): Unit = {
    /**
     * 传参：
     *   NodeManager的主机地址、端口号、CPU、内存
     *   ResourceManager的主机地址、端口号
     * */
    val NM_HOSTNAME = args(0)
    val NM_PORT = args(1)
    val NM_CPU:Int = args(2).toInt
    val NM_MEMORY:Int = args(3).toInt

    val RM_HOSTNAME = args(4)
    val RM_PORT = args(5).toInt

    val str:String =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = ${NM_HOSTNAME}
         |akka.remote.netty.tcp.port = ${NM_PORT}
      """.stripMargin

    val conf: Config = ConfigFactory.parseString(str)
    val actorSystem = ActorSystem(Conf.NMAS,conf)
    actorSystem.actorOf(Props(new NodeManager(RM_HOSTNAME,RM_PORT,NM_CPU,NM_MEMORY)),Conf.NMA)
  }
}
