package calllog;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * 在我们的场景中，我们需要收集呼叫日志详细信息。呼叫日志的信息包含。
 *
 * 主叫号码
 * 接收号码
 * 持续时间
 */
public class FakeLogReaderSpout implements IRichSpout {
    //Create instance for SpoutOutputCollector which passes tuples to bolt.
    private SpoutOutputCollector collector;
    private boolean completed = false;

    //Create instance for TopologyContext which contains topology data.
    private TopologyContext context;

    //Create instance for Random class.
    private Random randomGenerator = new Random();
    private Integer idx = 0;


    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.context=topologyContext;
        this.collector=spoutOutputCollector;
    }

    public void nextTuple() {
        ArrayList<String> mobileNumbers = new ArrayList<String>();
        mobileNumbers.add("1234123401");
        mobileNumbers.add("1234123402");
        mobileNumbers.add("1234123403");
        mobileNumbers.add("1234123404");
        Integer localIdx=0;
        while (localIdx++<100&&this.idx++<1000){
            String fromMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));
            String toMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));

            while(fromMobileNumber == toMobileNumber) {
                toMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));
            }

            Integer duration = randomGenerator.nextInt(60);
            this.collector.emit(new Values(fromMobileNumber, toMobileNumber, duration));
        }
    }
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("from","to","duration"));
    }
    public void close() {

    }

    public void activate() {

    }

    public void deactivate() {

    }


    public void ack(Object o) {

    }

    public void fail(Object o) {

    }


    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
    public boolean isDistributed() {
        return false;
    }
}
