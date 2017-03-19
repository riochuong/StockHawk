package stockhawk.jd.com.stockhawk.stockportfolio.model;

import java.util.Comparator;

/**
 * Created by chuondao on 3/18/17.
 */

public class StockPriceComparator implements Comparator<PriceDataPoint> {
    @Override
    public int compare(PriceDataPoint o1, PriceDataPoint o2) {
        if (o1.getStockPrice() < o2.getStockPrice()){
            return -1;
        }

        if (o1.getStockPrice() > o2.getStockPrice()){
            return 1;
        }

        return 0;
    }

}
