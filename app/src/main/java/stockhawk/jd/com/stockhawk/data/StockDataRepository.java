package stockhawk.jd.com.stockhawk.data;

import android.support.annotation.NonNull;

import java.util.List;

import stockhawk.jd.com.stockhawk.data.remote.StockRemoteDataSource;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;
import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;

/**
 * Created by chuondao on 3/7/17.
 */

public class StockDataRepository implements  StockDataSource {

    private static final String TAG  = "StockDataRepo";
    StockDataSource mStockLocalDataSource;
    StockRemoteDataSource mRemoteDataSource;
    PrefUtilsModel mPrefUtilsModel;

    private StockDataRepository(StockDataSource mStockLocalDataSource,
                                StockRemoteDataSource mStockRemoteDataSource,
                                PrefUtilsModel mPrefUtils) {
        this.mStockLocalDataSource = mStockLocalDataSource;
        this.mRemoteDataSource = mStockRemoteDataSource;
        this.mPrefUtilsModel = mPrefUtils;
    }



    private static StockDataRepository INSTANCE;


    @Override
    public void insertStocks(@NonNull List<StockModel> stocks, InsertStocksCallBacks callBacks) {
        /*only insert stock to local DB */
        mStockLocalDataSource.insertStocks(stocks, callBacks);
    }

    @Override
    public void deleteStock(@NonNull String symbol, final DeleteStockCallBacks callBacks) {

        /*delete stock from local database */
        mStockLocalDataSource.deleteStock(symbol, new DeleteStockCallBacks() {
            @Override
            public void onStocksDeleted() {
                callBacks.onStocksDeleted();
            }

            @Override
            public void onStockDeletedError() {
                callBacks.onStockDeletedError();
            }
        });

        /*resync data*/
        mPrefUtilsModel.removeStock(symbol);
    }

    @Override
    public void insertSingleStock(String symbol) {
        if (symbol != null && symbol.trim() != ""){
            mPrefUtilsModel.addStock(symbol);
            mRemoteDataSource.refreshStocks();
        }
    }


    @Override
    public void refreshStocks() {
        mRemoteDataSource.refreshStocks();
    }

    @Override
    public void shedulePeriodicSync() {
        mRemoteDataSource.shedulePeriodicSync();
    }

    @Override
    public void getPortfolioStocks(final OnGetPortfolioStockCallbacks cb) {
        this.mStockLocalDataSource.getPortfolioStocks(new OnGetPortfolioStockCallbacks() {
            @Override
            public void onStocksReceived(List<StockModel> stocks) {
                cb.onStocksReceived(stocks);
            }

            @Override
            public void onStockRequestError() {
                // try to refresh stocks and recall get portfolio
               mRemoteDataSource.refreshStocks();
               mStockLocalDataSource.getPortfolioStocks(cb);
            }
        });
    }

    public static StockDataRepository getInstance(StockDataSource localDataSource,
                                                  StockRemoteDataSource remoteDataSource,
                                                  PrefUtilsModel prefUtils){
        if (INSTANCE == null){
            INSTANCE = new StockDataRepository(localDataSource, remoteDataSource, prefUtils);
        }

        return INSTANCE;
    }
}
