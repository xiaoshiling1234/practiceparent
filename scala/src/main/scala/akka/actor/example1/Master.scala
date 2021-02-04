package akka.actor.example1

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Master extends Actor{
  def doHello(): Unit ={
    println("我是 akka.actor.example1.Master, 我接收到了 Worker 的 hello 的消息")
  }


  /**
   * 其实就是一个死循环 : 接收消息
   * while(true)
   */
  override def receive: Receive = {
    case "hello"=>{
      doHello()
      //sender()   ! "hi"  给 sender() 发送一个 hi的消息
      sender()! "hi"
    }
  }
}
object Master{
  def main(args: Array[String]): Unit = {
    val str=
      """
        |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
        |akka.remote.netty.tcp.hostname = localhost
        |akka.remote.netty.tcp.port = 6790
      """.stripMargin

    val conf = ConfigFactory.parseString(str)
    val actorSystem = ActorSystem("MasterActorSystem",conf)
    // 创建并启动 actor   def actorOf(props: Props, name: String): ActorRef
    //new akka.actor.example1.Master() 会导致主构造函数会运行！！
    actorSystem.actorOf(Props(new Master()),"MasterActor")
  }
}