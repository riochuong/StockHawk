package stockhawk.jd.com.stockhawk.stockportfolio.model;

/**
 * This class represet a data point on the chart of stock price
 */

public class PriceDataPoint {

    private float mTimeStamp;
    private float mStockPrice;

    public PriceDataPoint(float mTimeStamp, float mStockPrice) {
        this.mTimeStamp = mTimeStamp;
        this.mStockPrice = mStockPrice;
    }
}
