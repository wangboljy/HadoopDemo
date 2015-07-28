package hmr.tutorial.customization.transformer;


import hmr.tutorial.customization.inputs.AsinInputFormat;
import hmr.tutorial.customization.models.AsinCountWritable;
import hmr.tutorial.customization.models.AsinWritable;
import hmr.tutorial.customization.outputs.AsinCountOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Transformer {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        if (args.length != 2) {
            System.out.println(" usage: arg[0] is input path, arg[1] is output path");
            return;
        }


        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "transformer");

        job.setJarByClass(Transformer.class);

        job.setInputFormatClass(AsinInputFormat.class);
        job.setMapperClass(TransformerMapper.class);
        job.setMapOutputKeyClass(AsinWritable.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputFormatClass(AsinCountOutputFormat.class);
        job.setReducerClass(TransformerReducer.class);
        job.setOutputKeyClass(AsinWritable.class);
        job.setOutputValueClass(AsinCountWritable.class);



        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        /**
         * delete output path if already exists
         */
        FileSystem fs = FileSystem.get(conf);
        fs.delete(new Path(args[1]), true);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
