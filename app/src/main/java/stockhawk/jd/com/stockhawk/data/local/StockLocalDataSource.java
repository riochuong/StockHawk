package stockhawk.jd.com.stockhawk.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import stockhawk.jd.com.stockhawk.data.StockDataSource;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;
import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;
import timber.log.Timber;

/**
 * Created by chuondao on 3/5/17.
 */

public class StockLocalDataSource implements StockDataSource {

    private ContentResolver mContentResolver;

    private static StockLocalDataSource INSTANCE;

    /*prevent direct instantaniation */
    private StockLocalDataSource(ContentResolver mContentResolver) {
        this.mContentResolver = mContentResolver;
    }

    /*factory method to get instance */
    public static StockLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new StockLocalDataSource(contentResolver);
        }

        return INSTANCE;
    }


    @Override
    public void insertStocks(@NonNull List<StockModel> stocks, InsertStocksCallBacks callBacks) {

        if (stocks == null) {
            Timber.e("Provided and empty list of stocks for updating ! we can just ignore ");
            callBacks.onInsertError();
            return;
        }

        /* convert to content values array */
        List<ContentValues> cvs = new ArrayList<>();
        ContentValues[] cvas = new ContentValues[stocks.size()];
        for (StockModel stock : stocks) {
            cvs.add(stock.toContentValue());
        }
        /*convert to array*/
        cvs.toArray(cvas);

        int inserted = mContentResolver
                .bulkInsert(
                        StockContract.Quote.URI,
                        cvas);

        /* insert stocks and callback */
        if (inserted < 0) {
            callBacks.onInsertError();
        } else {
            callBacks.onStocksInserted();
        }
    }

    @Override
    public void deleteStock(@NonNull String symbol, DeleteStockCallBacks callBacks) {
        Uri uri = StockContract.Quote.makeUriForStock(symbol);
        int res = mContentResolver.delete(uri, null, null);
        if (res <= 0) {
            callBacks.onStockDeletedError();
        } else {
            callBacks.onStocksDeleted();
        }
    }

    @Override
    public void insertSingleStock(String symbol) {
        // No-op for now ..will insert to preferences and do refresh
    }

    @Override
    public void refreshStocks() {
        // No-op this will be determine by repository
    }

    @Override
    public void shedulePeriodicSync() {
        // No-op this will be handle by repository with remote data source
    }

    @Override
    public void getPortfolioStocks(OnGetPortfolioStockCallbacks cb) {
        Uri uri = StockContract.Quote.URI;
        List<StockModel> listStocks = new ArrayList<>();
        Cursor cursor = mContentResolver.query(uri, null, null, null, null);
        // process data here
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor != null) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    StockModel model = StockModel.from(cursor);
                    listStocks.add(model);
                }
            }

        }

        // callbacks
        if (listStocks.size() > 0){
            cb.onStocksReceived(listStocks);
        }
        else{
            cb.onStockRequestError();
        }

    }

    @Override
    public boolean isStockAvailable(String symbol) {

        Uri uri = StockContract.Quote.makeUriForStock(symbol);
        Cursor cursor = mContentResolver.query(uri, null,null,null,null);
        return (cursor.getCount() > 0);
    }


}
