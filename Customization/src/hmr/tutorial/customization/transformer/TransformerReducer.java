package hmr.tutorial.customization.transformer;


import hmr.tutorial.customization.codec.JsonCodex;
import hmr.tutorial.customization.models.Asin;
import hmr.tutorial.customization.models.AsinWritable;
import hmr.tutorial.customization.models.AsinCountWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * count asin number
 */
public class TransformerReducer extends Reducer<AsinWritable, IntWritable, AsinWritable, AsinCountWritable> {

    private JsonCodex<Asin> codex = new JsonCodex<Asin>();

    @Override
    protected void reduce(AsinWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable i : values) {
            sum += i.get();
        }

        AsinCountWritable asinCountWritable = new AsinCountWritable(key, sum);

        context.write(key, asinCountWritable);
    }
}
