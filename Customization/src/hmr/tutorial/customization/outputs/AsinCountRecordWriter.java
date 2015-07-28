package hmr.tutorial.customization.outputs;


import hmr.tutorial.customization.codec.JsonCodex;
import hmr.tutorial.customization.models.AsinCountWritable;
import hmr.tutorial.customization.models.AsinWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Json based output layout.
 */
public class AsinCountRecordWriter extends RecordWriter<AsinWritable, AsinCountWritable> {
    private JsonCodex<AsinCountWritable> codex = new JsonCodex<AsinCountWritable>();
    private DataOutputStream outputStream;

    public AsinCountRecordWriter(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(AsinWritable asinWritable, AsinCountWritable asinCountWritable) throws IOException, InterruptedException {
        outputStream.writeUTF(asinWritable.getAsin().getAsin());
        outputStream.writeUTF(codex.encode(asinCountWritable));
    }

    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        IOUtils.closeStream(outputStream);
    }
}
