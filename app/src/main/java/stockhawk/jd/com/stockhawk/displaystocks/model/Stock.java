package stockhawk.jd.com.stockhawk.displaystocks.model;

import android.database.Cursor;

import stockhawk.jd.com.stockhawk.data.local.StockContract;

/**
 * Created by chuondao on 3/7/17.
 */

public class Stock {


    private final String mSymbol;

    private final String mPrice;

    private final String mAbsoluteChange;

    private final String mPercentageChange;

    private final String mHistory;

    public Stock(String mSymbol, String mPrice, String mAbsoluteChange, String mPercentageChange, String mHistory) {
        this.mSymbol = mSymbol;
        this.mPrice = mPrice;
        this.mAbsoluteChange = mAbsoluteChange;
        this.mPercentageChange = mPercentageChange;
        this.mHistory = mHistory;
    }

    /**
     * return a stock object from a cursor
     * @param cursor
     * @return stock object
     */
    public static Stock from (Cursor cursor){
        String symbol = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_SYMBOL));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_PRICE));
        String absolutePerc = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_PERCENTAGE_CHANGE));
        String percChange = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_PERCENTAGE_CHANGE));
        String history = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_HISTORY));
        return new Stock(symbol,price,absolutePerc,percChange,history);
    }

    public String getSymbol() {
        return mSymbol;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getAbsoluteChange() {
        return mAbsoluteChange;
    }

    public String getPercentageChange() {
        return mPercentageChange;
    }

    public String getHistory() {
        return mHistory;
    }
}
