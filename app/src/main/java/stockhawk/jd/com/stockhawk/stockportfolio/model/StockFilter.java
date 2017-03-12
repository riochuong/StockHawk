package stockhawk.jd.com.stockhawk.stockportfolio.model;

/**
 * Created by chuondao on 3/7/17.
 */

public class StockFilter {

    private StockFilterType mStockFilterType;

    public StockFilterType getStockFilterType() {
        return mStockFilterType;
    }

    public StockFilter(StockFilterType mStockFilterType) {
        this.mStockFilterType = mStockFilterType;
    }
}
