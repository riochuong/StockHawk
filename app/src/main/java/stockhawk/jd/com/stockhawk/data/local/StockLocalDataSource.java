package stockhawk.jd.com.stockhawk.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import stockhawk.jd.com.stockhawk.data.StockDataSource;

/**
 * Created by chuondao on 3/5/17.
 */

public class StockLocalDataSource implements  StockDataSource{


    private ContentResolver mContentResolver;

    private static StockLocalDataSource INSTANCE;

    /*prevent direct instantaniation */
    private StockLocalDataSource(ContentResolver mContentResolver) {
        this.mContentResolver = mContentResolver;
    }

    /*factory method to get instance */
    public static StockLocalDataSource getInstance(@NonNull ContentResolver contentResolver){
        if (INSTANCE == null){
            INSTANCE = new StockLocalDataSource(contentResolver);
        }

        return INSTANCE;
    }


    @Override
    public void insertStocks(@NonNull ContentValues[] stocks, InsertStocksCallBacks callBacks) {
        int inserted = mContentResolver
                .bulkInsert(
                        StockContract.Quote.URI,
                        stocks);

        /* insert stocks and callback */
        if (inserted <= 0){
            callBacks.onInsertError();
        }
        else{
            callBacks.onStocksInserted();
        }
    }

    @Override
    public void deleteStock(@NonNull String stockId, DeleteStockCallBacks callBacks) {

    }


}
