package hive.tutorial.customization.inputs;


import hmr.tutorial.customization.exception.CustomizationException;
import hmr.tutorial.customization.inputs.AsinIterator;
import hmr.tutorial.customization.models.AsinWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.io.HiveInputFormat;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hive is still using old mapred apis....
 * Hadoop is already 2.7.1, man....
 */
public class AsinHiveInputFormat extends HiveInputFormat<Text, AsinWritable> {
    private static Log logger = LogFactory.getLog(AsinHiveInputFormat.class);

    @Override
    public InputSplit[] getSplits(JobConf job, int numSplits) throws IOException {
        Path[] inputPaths = FileInputFormat.getInputPaths(job);
        if (inputPaths.length == 0) {
            logger.warn(">>> No paths provided");
            return new InputSplit[0];
        }

        List<Path> pathList = new ArrayList<Path>();
        for (Path path : inputPaths) {
            FileSystem fileSystem = path.getFileSystem(job);
            if (!fileSystem.getFileStatus(path).isDirectory()) {
                throw new IOException("Path [" + path + "] is not a directory!");
            }
            FileStatus[] children = fileSystem.listStatus(path);
            for (FileStatus child : children) {
                Path childPath = child.getPath();
                pathList.add(childPath);
                logger.info(">>> loading path: " + childPath);
            }
        }
        List<InputSplit> inputSplits = new ArrayList<InputSplit>();
        for (Path path : pathList) {
            inputSplits.add(new FileSplit(path, 0, 0, job));
        }
        return inputSplits.toArray(new InputSplit[inputSplits.size()]);
    }

    @Override
    protected void init(JobConf job) {
        logger.info(">>> init AsinHiveInputFormat");
        super.init(job);
    }

    @Override
    public RecordReader getRecordReader(InputSplit split, JobConf job, Reporter reporter) throws IOException {

        if (split instanceof FileSplit) {
            FileSplit fileSplit = (FileSplit) split;
            Path path = fileSplit.getPath();
            AsinIterator iterator = new AsinIterator(path, job);
            return new AsinHiveRecordReader(iterator);
        } else {
            throw new CustomizationException("Not supported file split type");
        }
    }
}
