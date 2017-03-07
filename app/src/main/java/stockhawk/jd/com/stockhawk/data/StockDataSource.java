package stockhawk.jd.com.stockhawk.data;

/**
 * Created by chuondao on 3/5/17.
 */

import android.content.ContentValues;
import android.support.annotation.NonNull;

import stockhawk.jd.com.stockhawk.displaystocks.model.Stock;

/**
 * This is the main entry point for accessing data.
 * Callbacks are provided to inform users of data loading error.
 */
public interface StockDataSource {

    /**
     * Call backs for get stocks from data source
     */
    interface InsertStocksCallBacks {
        void onStocksInserted();
        void onInsertError();
    }

    /**
     * Call backs for get stocks from data source
     */
    interface DeleteStockCallBacks {
        void onStocksInserted();
        void onInsertError();
    }


    void insertStocks(@NonNull ContentValues[] stocks, InsertStocksCallBacks callBacks);

    void deleteStock(@NonNull Stock stock, DeleteStockCallBacks callBacks);

}
