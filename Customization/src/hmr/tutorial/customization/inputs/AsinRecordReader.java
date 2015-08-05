package hmr.tutorial.customization.inputs;


import hmr.tutorial.customization.models.Asin;
import hmr.tutorial.customization.models.AsinWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * Read Key/Val from file. Using an external Iterator structure in this example.
 */
public class AsinRecordReader extends RecordReader<Text, AsinWritable> {

    private static Log logger = LogFactory.getLog(AsinRecordReader.class);

    private Text key = new Text();
    private AsinWritable value = new AsinWritable();
    private AsinIterator iterator;

    public AsinRecordReader() {
    }

    public AsinRecordReader(AsinIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) inputSplit;
        if (iterator == null)
            iterator = new AsinIterator(fileSplit.getPath(), taskAttemptContext.getConfiguration());
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (iterator.hasNext()) {
            Asin asin = iterator.next();
            logger.debug(asin);

            key.set(asin.getAsin());
            value = new AsinWritable(asin);
            return true;
        }
        return false;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public AsinWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    /**
     * TODO: this can be done by a two-pass method. Or you can give a rough estimation.
     */
    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {
        if (iterator != null)
            iterator.close();
    }
}
