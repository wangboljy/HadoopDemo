package hmr.tutorial.customization.models;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * read/write double array
 */
public class DoubleArrayWritable implements Writable {
    private double[] data;

    public DoubleArrayWritable() {
    }

    public DoubleArrayWritable(double[] data) {
        this.data = data;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        int len = 0;
        if (data != null) {
            len = data.length;
        }

        out.writeInt(len);
        for (int i = 0; i < len; ++i) {
            out.writeDouble(data[i]);
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        int len = in.readInt();
        data = new double[len];

        for (int i = 0; i < len; ++i) {
            data[i] = in.readDouble();
        }
    }
}
