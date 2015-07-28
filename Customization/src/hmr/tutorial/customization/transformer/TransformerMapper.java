package hmr.tutorial.customization.transformer;


import hmr.tutorial.customization.models.AsinWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * count asin number
 */
public class TransformerMapper extends Mapper<Text, AsinWritable, AsinWritable, IntWritable> {
    private IntWritable ONE = new IntWritable(1);

    @Override
    protected void map(Text key, AsinWritable value, Context context) throws IOException, InterruptedException {
        context.write(value, ONE);
    }
}
