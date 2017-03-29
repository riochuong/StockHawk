package stockhawk.jd.com.stockhawk.data;

/**
 * Created by chuondao on 3/5/17.
 */

import android.support.annotation.NonNull;

import java.util.List;

import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;

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
        void onStocksDeleted();
        void onStockDeletedError();
    }

    interface OnGetPortfolioStockCallbacks {
        void onStocksReceived(List<StockModel> stocks);
        void onStockRequestError();
    }


    void insertStocks(@NonNull List<StockModel> stocks, InsertStocksCallBacks callBacks);

    void deleteStock(@NonNull String symbol, DeleteStockCallBacks callBacks);

    /* add a single stock to db*/
    void insertSingleStock(String symbol);

    /*Asynchronous call to update stocks */
    void refreshStocks();

    /* schedule periodic sync for stocks*/
    void shedulePeriodicSync();

    /* retreived stocks */
    void getPortfolioStocks(OnGetPortfolioStockCallbacks callbacks);

}
