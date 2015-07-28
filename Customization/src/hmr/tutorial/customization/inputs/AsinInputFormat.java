package hmr.tutorial.customization.inputs;

import hmr.tutorial.customization.exception.CustomizationException;
import hmr.tutorial.customization.models.AsinWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * Input format for Asin record. This will create a recordReader.
 * <p/>
 * We can direct how to construct a recordReader. Here I can put a Iterator or let the RecordReader decide its behavior.
 */
public class AsinInputFormat extends FileInputFormat<Text, AsinWritable> {
    private static Log logger = LogFactory.getLog(AsinInputFormat.class);

    @Override
    public RecordReader<Text, AsinWritable> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        if (inputSplit instanceof FileSplit) {
            FileSplit fileSplit = (FileSplit) inputSplit;
            Path path = fileSplit.getPath();
            Configuration configuration = taskAttemptContext.getConfiguration();

            AsinIterator iterator = new AsinIterator(path, configuration);

            logger.info(">>> AsinInputFormat is ready");
            return new AsinRecordReader(iterator);
            // return new AsinRecordReader();
        } else {
            logger.error(">>> input split is not file split");
            throw new CustomizationException("input split is not file split");
        }
    }

    /**
     * if not splitable, a file will be a mapper
     */
    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }
}
