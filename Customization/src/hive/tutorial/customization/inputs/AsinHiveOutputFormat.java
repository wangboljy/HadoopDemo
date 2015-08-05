package hive.tutorial.customization.inputs;


import hmr.tutorial.customization.models.AsinWritable;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.FileSinkOperator;
import org.apache.hadoop.hive.ql.io.HiveOutputFormat;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.util.Progressable;

import java.io.IOException;
import java.util.Properties;

/**
 * Dummy hive output format. We don't need to write anything
 */
public class AsinHiveOutputFormat implements HiveOutputFormat<Text, AsinWritable> {
    @Override
    public FileSinkOperator.RecordWriter getHiveRecordWriter(JobConf jc, Path finalOutPath, Class<? extends Writable> valueClass, boolean isCompressed, Properties tableProperties, Progressable progress) throws IOException {
        return null;
    }

    @Override
    public RecordWriter<Text, AsinWritable> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress) throws IOException {
        return null;
    }

    @Override
    public void checkOutputSpecs(FileSystem ignored, JobConf job) throws IOException {

    }
}
