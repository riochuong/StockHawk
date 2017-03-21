package stockhawk.jd.com.stockhawk.stockportfolio.model;

import java.util.Comparator;

/**
 * StockTimestampComparator helps to sort data based on timestamp
 */

public class StockTimestampComparator implements Comparator<PriceDataPoint> {
    @Override
    public int compare(PriceDataPoint o1, PriceDataPoint o2) {
        if (o1.getTimeStamp() < o2.getTimeStamp()){
            return -1;
        }

        if (o1.getTimeStamp() > o2.getTimeStamp()){
            return 1;
        }

        return 0;
    }

}
