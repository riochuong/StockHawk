package stockhawk.jd.com.stockhawk.stockportfolio.model;

import java.util.Comparator;

/**
 * StockPriceComparator helps to sort the data based on price
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
