package stockhawk.jd.com.stockhawk.stockportfolio.stockdetail;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import stockhawk.jd.com.stockhawk.data.LoaderProvider;
import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.stockportfolio.displaystocks.DisplayMyStockContract;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockFilter;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockFilterType;
import stockhawk.jd.com.stockhawk.util.NetworkUtilsModel;

/**
 * Created by chuondao on 3/13/17.
 */

public class StockDetailsPresenter implements StockDetailsContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {

    private StockDetailsContract.View mView;
    private StockDataRepository mRepository;
    private LoaderProvider mLoaderProvider;
    private LoaderManager mLoaderManager;
    private NetworkUtilsModel mNetworkUtilsModel;

    private static final int LOAD_STOCK_DATA_SPEC = 702;
    private static final String STOCK_SYMBOL = "stock_symbol";

    public StockDetailsPresenter(StockDetailsContract.View mView,
                                 StockDataRepository mRepository,
                                 LoaderProvider mLoaderProvider,
                                 LoaderManager mLoaderManager,
                                 NetworkUtilsModel mNetworkUtilsModel) {
        this.mView = mView;
        this.mRepository = mRepository;
        this.mLoaderProvider = mLoaderProvider;
        this.mLoaderManager = mLoaderManager;
        this.mNetworkUtilsModel = mNetworkUtilsModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }



    //////// LOADERS SPECIFIC METHODS /////////
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // really weird if it happens
        if (id != LOAD_STOCK_DATA_SPEC){
            throw new IllegalArgumentException("Wrong loader id received...must be weird");
        }
        String symbol = args.getString(STOCK_SYMBOL);
        if (symbol == null){
            throw new IllegalArgumentException("Cannot find symbol from bundle");
        }
        StockFilter filter = new StockFilter(StockFilterType.ONE_SPECIFIC_STOCK,symbol);
        return mLoaderProvider.createFilteredTasksLoader(filter);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void getStockData(String symbol) {
        Bundle data = new Bundle();
        data.putString(STOCK_SYMBOL,symbol);
        mLoaderManager.initLoader(LOAD_STOCK_DATA_SPEC,data,this).forceLoad();
    }
}
