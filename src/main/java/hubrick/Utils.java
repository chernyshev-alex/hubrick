package hubrick;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;

public class Utils {
    
    public static <T> Function<List<T>, Double> median(ToDoubleFunction mapper) {
        return (List<T> ls) -> {
            DoubleStream ds = ls.stream().mapToDouble(mapper).sorted();
            return (ls.size() % 2) ==0 
                    ? ds.skip(ls.size() / 2 -1).limit(2).average().getAsDouble()
                    : ds.skip(ls.size() / 2).findFirst().getAsDouble();
        };
    }
        
    /**
     * Calculate persentile
     * 
     * @param pv - persentile , example 95.0
     * @param values data
     * @return persentile value
     */
    public static double persentile(double pv, double[] values) {
        int len = values.length;
        double pos = pv * (len +1) /100;
        double fpos = Math.floor(pos);
        int intpos = (int) fpos;
        double dif = pos - fpos;
        double[] sorted = new double[len];
        System.arraycopy(values, 0, sorted, 0, len);
        Arrays.sort(sorted);
        if (pos < 1) return sorted[0];
        else if (pos >= len) return sorted[len-1];
        
        double low = sorted[intpos -1];
        double up = sorted[intpos];
        return low + dif * (up - low);
    } 
    
}
