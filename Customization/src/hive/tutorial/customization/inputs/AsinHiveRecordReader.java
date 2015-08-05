package hive.tutorial.customization.inputs;


import hmr.tutorial.customization.inputs.AsinIterator;
import hmr.tutorial.customization.models.Asin;
import hmr.tutorial.customization.models.AsinWritable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.RecordReader;

import java.io.IOException;

/**
 * just reuse the asinRecordReader
 */
public class AsinHiveRecordReader implements RecordReader<Text, AsinWritable> {

    private static Log logger = LogFactory.getLog(AsinHiveRecordReader.class);

    private AsinIterator iterator;
    long pos = 0;

    public AsinHiveRecordReader() {
        super();
    }

    public AsinHiveRecordReader(AsinIterator iterator) {
        this.iterator = iterator;
    }


    @Override
    public boolean next(Text key, AsinWritable value) throws IOException {
        if (iterator.hasNext()) {
            Asin asin = iterator.next();
            logger.info(asin);

            key.set(asin.getAsin());
            value.setAsin(asin);
            ++pos;
            return true;
        }
        return false;
    }

    @Override
    public Text createKey() {
        return new Text();
    }

    @Override
    public AsinWritable createValue() {
        return new AsinWritable();
    }

    @Override
    public long getPos() throws IOException {
        return pos;
    }

    @Override
    public void close() throws IOException {
        if (iterator != null)
            iterator.close();
    }

    @Override
    public float getProgress() throws IOException {
        return 0;
    }
}
