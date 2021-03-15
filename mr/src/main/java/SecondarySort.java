import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * 在map阶段，使用job.setInputFormatClass定义的InputFormat将输入的数据集分割成小数据块splites，
 * 同时InputFormat提供一个RecordReder的实现。本例子中使用的是TextInputFormat，他提供的RecordReder
 * 会将文本的字节偏移量作为key，这一行的文本作为value。这就是自定义Map的输入是<LongWritable, Text>
 * 的原因。然后调用自定义Map的map方法，将一个个<LongWritable, Text>对输入给Map的map方法。
 * 注意输出应该符合自定义Map中定义的输出<IntPair, IntWritable>。最终是生成一个List<IntPair, IntWritable>。
 * 在map阶段的最后，会先调用job.setPartitionerClass对这个List进行分区，每个分区映射到一个reducer。
 * 每个分区内又调用job.setSortComparatorClass设置的key比较函数类排序。可以看到，这本身就是一个二次排序。
 * 如果没有通过job.setSortComparatorClass设置key比较函数类，则使用key的实现的compareTo方法。
 * 在第一个例子中，使用了IntPair实现的compareTo方法，而在下一个例子中，专门定义了key比较函数类。
 *
 * 在reduce阶段，reducer接收到所有映射到这个reducer的map输出后，也是会调用job.setSortComparatorClass设置的
 * key比较函数类对所有数据对排序。然后开始构造一个key对应的value迭代器。这时就要用到分组，使用jobjob.setGroupingComparatorClass设置
 * 的分组函数类。只要这个比较器比较的两个key相同，他们就属于同一个组，它们的value放在一个value迭代器，而这个迭代器的key使用属于同一
 * 个组的所有key的第一个key。最后就是进入Reducer的reduce方法，reduce方法的输入是所有的（key和它的value迭代器）。同样注意输入与输出的
 * 类型必须与自定义的Reducer中声明的一致。
 */
public class SecondarySort {
    public static class Map extends Mapper<Object, Text,IntPair, IntWritable>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            int left=0;
            int right=0;
            if (tokenizer.hasMoreTokens()){
                left=Integer.parseInt(tokenizer.nextToken());
                if (tokenizer.hasMoreTokens()){
                    right=Integer.parseInt(tokenizer.nextToken());
                    context.write(new IntPair(left,right),new IntWritable(right));
                }
            }
        }
    }
    /*
     * 自定义分区函数类FirstPartitioner，根据 IntPair中的first实现分区
     */
    public static class FirstPartition extends Partitioner<IntPair,IntWritable>{
        public int getPartition(IntPair key, IntWritable value, int numPartitions) {
            return Math.abs(key.getFirst()*127)%numPartitions;
        }
    }
    /*
     * 自定义GroupingComparator类，实现分区内的数据分组
     */
    public static class GroupingComparator extends WritableComparator {
        public GroupingComparator() {
            super(IntPair.class,true);
        }

        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            IntPair ip1 = (IntPair) w1;
            IntPair ip2 = (IntPair) w2;
            int l = ip1.getFirst();
            int r = ip2.getFirst();
            return l==r?0:(l<r?-1:1);
        }
    }
    public static class Reduce extends Reducer<IntPair,IntWritable,Text,IntWritable>{
        @Override
        protected void reduce(IntPair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            for (IntWritable val:values){
                context.write(new Text(Integer.toString(key.getFirst())),val);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage:SecondarySort <in> <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "SecondarySort");
        job.setJarByClass(SecondarySort.class);
        job.setMapperClass(SecondarySort.Map.class);
        job.setReducerClass(SecondarySort.Reduce.class);

        job.setPartitionerClass(SecondarySort.FirstPartition.class);
        job.setGroupingComparatorClass(SecondarySort.GroupingComparator.class);

        job.setOutputKeyClass(IntPair.class);
        job.setOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
