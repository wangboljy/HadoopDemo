package hmr.tutorial.customization.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;

/**
 * I may use this model somewhere. It could be imported.
 */
public class Asin implements Comparable<Asin> {
    public String asin;
    public double price;
    public int dph;
    public double[] fcsts;

    /**
     * dummy constructor required by ObjectMapper
     */
    public Asin() {
    }

    public Asin(String asin, double price, int dph, double[] fcsts) {
        this.asin = asin;
        this.price = price;
        this.dph = dph;
        this.fcsts = fcsts;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDph() {
        return dph;
    }

    public void setDph(int dph) {
        this.dph = dph;
    }

    public double[] getFcsts() {
        return fcsts;
    }

    public void setFcsts(double[] fcsts) {
        this.fcsts = fcsts;
    }

    @Override
    public int compareTo(Asin o) {
        return this.asin.compareTo(o.asin);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("asin", asin)
                .append("price", price)
                .append("dph", dph)
                .append("fcsts", fcsts)
                .toString();

    }

    // IMPORTANT: The following code is added to support deserialization. I'm using reflection to hack.
    public Object getData(String key) throws NoSuchFieldException, IllegalAccessException {
        if (key.equalsIgnoreCase("fcsts")) {
            double data[] = (double[]) this.getClass().getDeclaredField(key).get(this);
            return Arrays.toString(data);
        }
        return this.getClass().getDeclaredField(key).get(this);
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        double fcsts[] = {1, 2, 3};
        System.out.println(Arrays.toString(fcsts));

        Asin asin = new Asin("1234567890", 11.95, 101, fcsts);
        asin.getData("asin");
    }
}