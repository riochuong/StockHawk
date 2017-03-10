package stockhawk.jd.com.stockhawk.data;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import stockhawk.jd.com.stockhawk.data.remote.QuoteSyncJob;
import stockhawk.jd.com.stockhawk.displaystocks.model.StockModel;

/**
 * Created by chuondao on 3/7/17.
 */

public class StockDataRepository implements  StockDataSource{

    StockDataSource mStockLocalDataSource;

    private StockDataRepository(StockDataSource mStockLocalDataSource) {
        this.mStockLocalDataSource = mStockLocalDataSource;
    }



    private static StockDataRepository INSTANCE;


    @Override
    public void insertStocks(@NonNull List<StockModel> stocks, InsertStocksCallBacks callBacks) {

    }

    @Override
    public void deleteStock(@NonNull StockModel stock, final DeleteStockCallBacks callBacks) {

        /*delete stock from locat database */
        mStockLocalDataSource.deleteStock(stock, new DeleteStockCallBacks() {
            @Override
            public void onStocksDeleted() {
                callBacks.onStocksDeleted();
            }

            @Override
            public void onStockDeletedError() {
                callBacks.onStockDeletedError();
            }
        });

    }

    @Override
    public void insertSingleStock(StockModel stock, final InsertSingleStockCallBacks callbacks) {
        mStockLocalDataSource.insertSingleStock(stock, new InsertSingleStockCallBacks() {
            @Override
            public void onInsertSingleStockCompleted() {
                callbacks.onInsertSingleStockCompleted();
            }

            @Override
            public void onInsertSingleStockError() {
                callbacks.onInsertSingleStockError();
            }
        });
    }

    @Override
    public void fetchStocks(Context ctx) {
        QuoteSyncJob.syncImmediately(ctx);
    }

    public static StockDataRepository getInstance(StockDataSource localDataSource){
        if (INSTANCE == null){
            INSTANCE = new StockDataRepository(localDataSource);
        }

        return INSTANCE;
    }
}
