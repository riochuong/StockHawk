package stockhawk.jd.com.stockhawk.stockportfolio.model;

/**
 * This class represet a data point on the chart of stock price
 */

public class PriceDataPoint {

    private long mTimeStamp;
    private float mStockPrice;



    private String mDateFormat;

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


    public String getDateFormat() {
        return mDateFormat;
    }

    public void setDateFormat(String mDateFormat) {
        this.mDateFormat = mDateFormat;
    }


}
