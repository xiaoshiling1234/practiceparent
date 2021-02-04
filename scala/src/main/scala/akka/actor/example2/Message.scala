package akka.actor.example2
//NodeManager注册信息
case class NodeManagerRegisterMsg(val nodeManagerID:String, var cpu:Int, var memory:Int)
//ResourceManager接收到注册信息成功之后的返回信息
case class RegisterFeedbackMsg(val feedbackMsg: String)
//NodeManager的心跳信息
case class HeartBeat(val nodeManagerID:String)
//NodeManager注册信息
class NodeManagerInfo(val nodeManagerID:String, var cpu:Int, var memory:Int){
  //定义一个属性，存储上一次的心跳时间
  var lastHeartBeatTime:Long = _
}

case object SendMessage
case object CheckTimeOut