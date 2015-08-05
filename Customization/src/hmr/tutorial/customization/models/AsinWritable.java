package hmr.tutorial.customization.models;


import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * This demos how to read/write particular type.
 * Use another writable to read/write customized types.
 */
public class AsinWritable implements WritableComparable<AsinWritable> {
    private Asin asin;

    public AsinWritable() {
    }

    public AsinWritable(Asin asin) {
        this.asin = asin;
    }

    public void setAsin(Asin asin) {
        this.asin = asin;
    }

    public Asin getAsin() {
        return asin;
    }

    @Override
    public int compareTo(AsinWritable o) {
        return this.asin.compareTo(o.getAsin());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(asin.getAsin());
        out.writeDouble(asin.getPrice());
        out.writeInt(asin.getDph());

        DoubleArrayWritable doubleArrayWritable = new DoubleArrayWritable();
        doubleArrayWritable.setData(asin.getFcsts());
        doubleArrayWritable.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        String asin = in.readUTF();
        double price = in.readDouble();
        int dph = in.readInt();

        DoubleArrayWritable doubleArrayWritable = new DoubleArrayWritable();
        doubleArrayWritable.readFields(in);
        double[] fcst = doubleArrayWritable.getData();

        this.asin = new Asin(asin, price, dph, fcst);
    }

    @Override
    public String toString() {
        return asin.toString();
    }

    public static AsinWritable read(DataInput in) throws IOException {
        AsinWritable asinWritable = new AsinWritable();
        asinWritable.readFields(in);
        return asinWritable;
    }
}
