import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntPair implements WritableComparable<IntPair> {
    private int first;
    private int second;

    public IntPair() {
    }

    public IntPair(int first, int second) {
        set(first,second);
    }

    public void set(int left,int right){
        first=left;
        second=right;
    }
    public int compareTo(IntPair o) {
        if (first!=o.first){
            return first<o.first?-1:1;
        }else if (second!=o.second){
            return second<o.second?-1:1;
        }else {
            return 0;
        }
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(first);
        out.writeInt(second);
    }

    public void readFields(DataInput in) throws IOException {
        first=in.readInt();
        second=in.readInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntPair intPair = (IntPair) o;
        return first == intPair.first &&
                second == intPair.second;
    }

    @Override
    public int hashCode() {
        return first*157+second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}
