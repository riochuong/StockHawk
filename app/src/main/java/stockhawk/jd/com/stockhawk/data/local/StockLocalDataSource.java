package stockhawk.jd.com.stockhawk.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import stockhawk.jd.com.stockhawk.data.StockDataSource;
import stockhawk.jd.com.stockhawk.displaystocks.model.StockModel;

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
    public void insertStocks(@NonNull List<StockModel> stocks, InsertStocksCallBacks callBacks) {

        /* convert to content values array */
        List<ContentValues> cvs = new ArrayList<>();
        ContentValues [] cvas = new ContentValues [stocks.size()] ;
        for (StockModel stock: stocks){
            cvs.add(stock.toContentValue());
        }
        /*convert to array*/
        cvs.toArray(cvas);

        int inserted = mContentResolver
                .bulkInsert(
                        StockContract.Quote.URI,
                        cvas);

        /* insert stocks and callback */
        if (inserted <= 0){
            callBacks.onInsertError();
        }
        else{
            callBacks.onStocksInserted();
        }
    }

    @Override
    public void deleteStock(@NonNull StockModel stock, DeleteStockCallBacks callBacks) {
        Uri uri = StockContract.Quote.makeUriForStock(stock.getSymbol());
        int res = mContentResolver.delete(uri,null,null);
        if (res <= 0){
            callBacks.onStockDeletedError();
        }
        else{
            callBacks.onStocksDeleted();
        }
    }

    @Override
    public void insertSingleStock(StockModel stock, InsertSingleStockCallBacks callbacks) {
        Uri uri = StockContract.Quote.URI;
        Uri res =  mContentResolver.insert(uri,stock.toContentValue());
        /*check for error*/
        if (res == null){
            callbacks.onInsertSingleStockError();
        }else{
            callbacks.onInsertSingleStockCompleted();
        }
    }

    @Override
    public void fetchStocks(@NonNull Context ctx) {
        // NO-op as fetchstocks will be call straight through network api
    }


}
