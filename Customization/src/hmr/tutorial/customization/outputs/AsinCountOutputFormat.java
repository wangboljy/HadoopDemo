package hmr.tutorial.customization.outputs;


import hmr.tutorial.customization.models.AsinCountWritable;
import hmr.tutorial.customization.models.AsinWritable;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


public class AsinCountOutputFormat extends FileOutputFormat<AsinWritable, AsinCountWritable> {

    @Override
    public RecordWriter<AsinWritable, AsinCountWritable> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        Path outputPath = this.getDefaultWorkFile(job, ".json");
        FileSystem fs = outputPath.getFileSystem(job.getConfiguration());
        FSDataOutputStream outputStream = fs.create(outputPath);
        return new AsinCountRecordWriter(outputStream);
    }
}
