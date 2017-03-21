package stockhawk.jd.com.stockhawk.stockportfolio.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by chuondao on 3/18/17.
 */

public class StockHistoryModel {

    float max52wkHigh;
    float max52wkLow;
    float prevClose;
    float pctChange52wk;


    ArrayList<PriceDataPoint> data;

    public StockHistoryModel(ArrayList<PriceDataPoint> data) {
        this.data = data;

        // set 52wk high, low data
        Collections.sort(data, new StockPriceComparator());
        max52wkLow = data.get(0).getStockPrice();
        max52wkHigh = data.get(data.size() - 1).getStockPrice();

        // sort data based on timestamp
        Collections.sort(data, new StockTimestampComparator());
        prevClose = data.get(0).getStockPrice();
        pctChange52wk = calc52WkChangePct(
                data.get(0).getStockPrice(),data.get(data.size() - 1).getStockPrice());

    }

    /* return max price */
    public float get52Wkhigh(){
        return max52wkHigh;
    }

    public float get52WkLow(){
        return max52wkLow;
    }

    public float getPrevClose() {
        return prevClose;
    }

    public float getPctChange52wk() {
        return pctChange52wk;
    }

    private float calc52WkChangePct(float start, float end){
        return ((end - start) / start)  * 100;
    }

    public ArrayList<PriceDataPoint> getData() {
        return data;
    }
}
