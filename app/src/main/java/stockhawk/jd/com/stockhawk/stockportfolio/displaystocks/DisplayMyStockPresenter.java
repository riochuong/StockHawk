package stockhawk.jd.com.stockhawk.stockportfolio.displaystocks;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import stockhawk.jd.com.stockhawk.data.LoaderProvider;
import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.data.StockDataSource;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockFilter;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockFilterType;
import stockhawk.jd.com.stockhawk.util.NetworkUtilsModel;
import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;
import timber.log.Timber;

/**
 * Created by chuondao on 3/4/17.
 */

public class DisplayMyStockPresenter implements DisplayMyStockContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {

    private DisplayMyStockContract.View mView;
    private StockDataRepository mRepository;
    private LoaderProvider mLoaderProvider;
    private LoaderManager mLoaderManager;
    private NetworkUtilsModel mNetworkUtilsModel;
    private PrefUtilsModel mPrefUtilsModel;

    private static final int LOADER_ID = 541;

    private static  HashMap<String, Integer>INDICES = new HashMap<>();

    static{

        // initialize the map of investment indices
        INDICES.put("^GSPC",1); // s&p
        INDICES.put("^IXIC",2); // nasdaq
    }

    public DisplayMyStockPresenter(DisplayMyStockContract.View mView,
                                   StockDataRepository mRepository,
                                   LoaderProvider mLoaderProvider,
                                   LoaderManager mLoaderManager,
                                   NetworkUtilsModel mNetworkUtils,
                                   PrefUtilsModel prefUtilsModel
                                   ) {
        this.mView = mView;
        this.mRepository = mRepository;
        this.mLoaderProvider = mLoaderProvider;
        this.mLoaderManager = mLoaderManager;
        this.mNetworkUtilsModel = mNetworkUtils;
        this.mPrefUtilsModel = prefUtilsModel;
        mView.setPresenter(this);

    }

    @Override
    public void start() {
        /* refresh stock data first */
        refreshStockData();
        mLoaderManager.initLoader(LOADER_ID,null,this).forceLoad();
        /*then schedule periodic sync*/
        mRepository.shedulePeriodicSync();
    }

    @Override
    public void stop() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createFilteredTasksLoader(new StockFilter(StockFilterType.ALL_PERSONAL_STOCKS, null));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            List<StockModel> stockList = new ArrayList<>();
            List<StockModel> indicesList = new ArrayList<>();

            if (data != null){
                /*convert to list*/
                for (int i = 0; i < data.getCount() ; i++) {
                    if (data.moveToPosition(i)){
                        StockModel model = StockModel.from(data);
                        if (INDICES.get(model.getSymbol()) != null){
                            indicesList.add(model);
                        }
                        else{
                            stockList.add(model);
                        }
                    }
                }
                // use view contract method to update stock data
                mView.updateStockDataDisplay(stockList);
                mView.updateInvestmentIndices(indicesList);
            }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mView.updateStockDataDisplay(null);
    }


    @Override
    public void refreshStockData() {
        if (!mNetworkUtilsModel.networkUp()){
            mView.displayFailToRefreshDuetoNetworkError();
        }
        mRepository.refreshStocks();
    }

    @Override
    public void deleteStockSymbol(String symbol) {
        mRepository.deleteStock(symbol, new StockDataSource.DeleteStockCallBacks() {
            @Override
            public void onStocksDeleted() {
                Timber.d("Stock is removed completely from DB");
                mPrefUtilsModel.sendDataUpdateBroadcast();
            }

            @Override
            public void onStockDeletedError() {
                Timber.d("failed to delete stock from DB");
            }
        });
    }

}
