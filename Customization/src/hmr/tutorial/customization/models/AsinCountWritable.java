package hmr.tutorial.customization.models;


import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class AsinCountWritable implements Writable {
    private AsinWritable asinWritable;
    private int count;

    public AsinCountWritable() {
    }

    public AsinCountWritable(AsinWritable asinWritable, int count) {
        this.asinWritable = asinWritable;
        this.count = count;
    }

    public AsinWritable getAsinWritable() {
        return asinWritable;
    }

    public void setAsinWritable(AsinWritable asinWritable) {
        this.asinWritable = asinWritable;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        asinWritable.write(dataOutput);
        dataOutput.writeDouble(count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        asinWritable.readFields(dataInput);
        count = dataInput.readInt();
    }
}
