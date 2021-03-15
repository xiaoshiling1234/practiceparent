import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class FindFriend {
    /**
     * A B C D E F
     * B A C D E
     * C A B E
     * D A B E
     * E A B C D
     * F A
     */
    public static class changeMapper extends Mapper<Object, Text,Text,Text>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            Text owner= new Text();
            Text outKey = new Text();
            Set set=new TreeSet<String>();
            owner.set(itr.nextToken());
            while (itr.hasMoreTokens()){
                set.add(itr.nextToken());
            }
            String[] friends = new String[set.size()];
            friends = (String[]) set.toArray(friends);
            for (int i=0;i<friends.length;i++){
                for (int j=i+1;j<friends.length;j++){
                    String outPutKey=friends[i]+friends[j];
                    System.out.println(outPutKey);
                    outKey.set(outPutKey);
                    System.out.println(outPutKey);
                    context.write(outKey,owner);
                }
            }
        }
    }
    public static class FindReducer extends Reducer<Text,Text,Text,Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String commonFriends="";
            for (Text val:values){
                commonFriends+=val;
            }
            context.write(key,new Text(commonFriends));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage:findFriend <in> <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "findFriend");
        job.setJarByClass(FindFriend.class);
        job.setMapperClass(FindFriend.changeMapper.class);
        job.setReducerClass(FindFriend.FindReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
