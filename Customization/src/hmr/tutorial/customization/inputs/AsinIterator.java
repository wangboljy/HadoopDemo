package hmr.tutorial.customization.inputs;


import hmr.tutorial.customization.codec.JsonCodex;
import hmr.tutorial.customization.exception.CustomizationException;
import hmr.tutorial.customization.models.Asin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.util.Iterator;

/**
 * Iterate through a file. Each line is a json string. Each json string represents an Asin object
 */
public class AsinIterator implements Iterator<Asin>, Closeable {

    private static Log logger = LogFactory.getLog(AsinIterator.class);

    private BufferedReader reader = null;
    private Asin asin = null;
    private String line = null;
    private JsonCodex<Asin> codex = new JsonCodex<Asin>();

    public AsinIterator(Path path, Configuration configuration) throws IOException {
        InputStream inputStream = FileSystem.get(configuration).open(path);
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public boolean hasNext() {
        try {
            line = reader.readLine();
            if (line == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error(e);
            throw new CustomizationException("AsinIterator failure", e);
        }
    }

    @Override
    public Asin next() {
        if (line == null) {
            return null;
        }

        try {
            asin = codex.decode(line, Asin.class);
        } catch (IOException e) {
            logger.error(e);
            throw new CustomizationException("AsinIterator failure", e);
        }

        return asin;
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeStream(reader);
    }

}

