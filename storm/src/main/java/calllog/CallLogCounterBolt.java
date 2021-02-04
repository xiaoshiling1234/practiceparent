package calllog;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * 呼叫日志创建者bolt接收呼叫日志元组。
 * 呼叫日志元组具有主叫方号码，接收方号码和呼叫持续时间。
 * 此bolt通过组合主叫方号码和接收方号码简单地创建一个新值。
 * 新值的格式为“来电号码 - 接收方号码”，
 * 并将其命名为新字段“呼叫”。完整的代码如下。
 */
public class CallLogCounterBolt implements IRichBolt {
    Map<String,Integer> counterMap;
    private OutputCollector collector;
    public void prepare(Map conf, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector=outputCollector;
        this.counterMap=new HashMap<String, Integer>();
    }

    public void execute(Tuple tuple) {
        String call = tuple.getString(0);
        if (!counterMap.containsKey(call)){
            counterMap.put(call, 1);
        }else {
            int c = counterMap.get(call) + 1;
            counterMap.put(call,c);
        }
        collector.ack(tuple);
    }

    public void cleanup() {
        for (String key:counterMap.keySet()){
            System.out.println("---------------"+key+":"+counterMap.get(key));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("call"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
