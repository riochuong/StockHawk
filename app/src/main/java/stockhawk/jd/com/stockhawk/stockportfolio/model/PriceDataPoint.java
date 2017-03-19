package stockhawk.jd.com.stockhawk.stockportfolio.model;

/**
 * This class represet a data point on the chart of stock price
 */

public class PriceDataPoint {

    private long mTimeStamp;
    private float mStockPrice;

    public PriceDataPoint(long mTimeStamp, float mStockPrice) {
        this.mTimeStamp = mTimeStamp;
        this.mStockPrice = mStockPrice;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public float getStockPrice() {
        return mStockPrice;
    }


}
