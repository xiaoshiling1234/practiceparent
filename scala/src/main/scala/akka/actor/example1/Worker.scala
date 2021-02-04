package akka.actor.example1

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Worker  extends Actor {

  def doHi(): Unit ={
    println("我是 Worker，我接收到了 akka.actor.example1.Master 的 hi 的消息");
  }


  override def preStart(): Unit = {
    // 实现的是给 akka.actor.example1.Master 发送消息  地址

    val workerActor = context.actorSelection("akka.tcp://MasterActorSystem@localhost:6790/user/MasterActor")
    workerActor ! "hello"
  }

  override def receive: Receive = {
    case "hi"=>{
      doHi()
    }
  }
}
object Worker{
  def main(args: Array[String]): Unit = {
    val str=
      """
        |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
        |akka.remote.netty.tcp.hostname = localhost
      """.stripMargin

    val conf = ConfigFactory.parseString(str)
    val actorSystem = ActorSystem("WorkerActorSystem", conf)
    actorSystem.actorOf(Props(new Worker()), "WorkerActor")
  }
}
