package stockhawk.jd.com.stockhawk.stockportfolio.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import stockhawk.jd.com.stockhawk.data.local.StockContract;

/**
 * Created by chuondao on 3/7/17.
 */

public class StockModel implements  Comparable<StockModel>{


    private final String mSymbol;

    private final String mPrice;

    private final String mAbsoluteChange;

    private final String mPercentageChange;

    private final String mHistory;

    private final String mVolumeAvg;

    private final String mName;

    private static final int ONE_BILL = 1 * 1000 * 1000 * 1000;
    private static final int ONE_MILL = 1 * 1000 * 1000;
    private static final int ONE_K  = 1 * 1000;

    private static final String VOLUME_FORMAT = "AVG.VOL: %.2f";

    public StockModel(String mSymbol, String mPrice, String mAbsoluteChange, String mPercentageChange, String
            mHistory, String mVolume, String name) {
        this.mSymbol = mSymbol;
        this.mPrice = mPrice;
        this.mAbsoluteChange = mAbsoluteChange;
        this.mPercentageChange = mPercentageChange;
        this.mHistory = mHistory;
        this.mVolumeAvg = mVolume;
        this.mName = name;
    }

    /**
     * return a stock object from a cursor
     * @param cursor
     * @return stock object
     */
    public static StockModel from (Cursor cursor){
        String symbol = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_SYMBOL));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_PRICE));
        String absolutePerc = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_ABSOLUTE_CHANGE));
        String percChange = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_PERCENTAGE_CHANGE));
        String history = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_HISTORY));
        String volume = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_VOLUME_AVG));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(
                StockContract.Quote.COLUMN_NAME));
        return new StockModel(symbol,price,absolutePerc,percChange,history,volume,name);
    }


    public static StockModel from (ContentValues cv){
        String symbol = cv.getAsString(StockContract.Quote.COLUMN_SYMBOL);
        String price =cv.getAsString(StockContract.Quote.COLUMN_PRICE);
        String absolutePerc = cv.getAsString(StockContract.Quote.COLUMN_ABSOLUTE_CHANGE);
        String percChange = cv.getAsString(StockContract.Quote.COLUMN_PERCENTAGE_CHANGE);
        String history = cv.getAsString(StockContract.Quote.COLUMN_HISTORY);
        String volume = cv.getAsString(StockContract.Quote.COLUMN_VOLUME_AVG);
        String name = cv.getAsString(StockContract.Quote.COLUMN_NAME);
        return new StockModel(symbol,price,absolutePerc,percChange,history,volume,name);
    }


    public ContentValues toContentValue(){
        ContentValues cv = new ContentValues();
        cv.put(StockContract.Quote.COLUMN_SYMBOL,this.mSymbol);
        cv.put(StockContract.Quote.COLUMN_PRICE,this.mPrice);
        cv.put(StockContract.Quote.COLUMN_ABSOLUTE_CHANGE,this.mAbsoluteChange);
        cv.put(StockContract.Quote.COLUMN_PERCENTAGE_CHANGE,this.mPercentageChange);
        cv.put(StockContract.Quote.COLUMN_HISTORY,this.mHistory);
        cv.put(StockContract.Quote.COLUMN_VOLUME_AVG,this.mVolumeAvg);
        cv.put(StockContract.Quote.COLUMN_NAME,this.mName);
        return cv;
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

    /*do a little work here to have volume nice formatting*/
    public String getVolume() {

        float volumeDigit = Float.parseFloat(mVolumeAvg);
        float ret;
        // BILLION MAGNITUDE
        if (volumeDigit > ONE_BILL){
            ret = volumeDigit / ONE_BILL;
            return String.format(VOLUME_FORMAT,ret)+"B";
        }

        // MILLION MAGNITUDE
        if (volumeDigit > ONE_MILL){
            ret = volumeDigit / ONE_MILL;
            return String.format(VOLUME_FORMAT,ret)+"M";
        }

        // THOUSANDS MAGNITUDE
        if (volumeDigit > ONE_K){
            ret = volumeDigit / ONE_K;
            return String.format(VOLUME_FORMAT,ret)+"K";
        }

        return "AVG.VOL: "+mVolumeAvg;

    }

    public String getName() {return mName;}

    /*symbol comparision to sort alphabetical*/
    @Override
    public int compareTo(@NonNull StockModel o) {
        return this.mSymbol.compareTo(o.getSymbol());
    }



}
