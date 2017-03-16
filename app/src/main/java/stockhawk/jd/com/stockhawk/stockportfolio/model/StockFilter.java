package stockhawk.jd.com.stockhawk.stockportfolio.model;

/**
 * Created by chuondao on 3/7/17.
 */

public class StockFilter {

    private StockFilterType mStockFilterType;
    /* if this is null -- get all available stocks */
    private String mSymbol;

    public StockFilterType getStockFilterType() {
        return mStockFilterType;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public StockFilter(StockFilterType mStockFilterType, String symbol) {
        this.mStockFilterType = mStockFilterType;
        this.mSymbol = symbol;
    }


}
