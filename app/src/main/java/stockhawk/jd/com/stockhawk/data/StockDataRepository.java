package stockhawk.jd.com.stockhawk.data;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import stockhawk.jd.com.stockhawk.displaystocks.model.Stock;

/**
 * Created by chuondao on 3/7/17.
 */

public class StockDataRepository implements  StockDataSource{

    private StockDataRepository(StockDataSource mStockLocalDataSource) {
        this.mStockLocalDataSource = mStockLocalDataSource;
    }

    StockDataSource mStockLocalDataSource;

    private static StockDataRepository INSTANCE;


    @Override
    public void insertStocks(@NonNull ContentValues[] stocks, InsertStocksCallBacks callBacks) {

    }

    @Override
    public void deleteStock(@NonNull Stock stock, DeleteStockCallBacks callBacks) {

    }

    public static StockDataRepository getInstance(StockDataSource localDataSource){
        if (INSTANCE == null){
            INSTANCE = new StockDataRepository(localDataSource);
        }

        return INSTANCE;
    }
}
