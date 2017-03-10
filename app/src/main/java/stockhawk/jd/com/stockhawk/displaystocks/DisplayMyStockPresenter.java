package stockhawk.jd.com.stockhawk.displaystocks;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

import stockhawk.jd.com.stockhawk.data.LoaderProvider;
import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.data.remote.QuoteSyncJob;
import stockhawk.jd.com.stockhawk.displaystocks.model.StockModel;
import stockhawk.jd.com.stockhawk.displaystocks.model.StockFilter;
import stockhawk.jd.com.stockhawk.displaystocks.model.StockFilterType;

/**
 * Created by chuondao on 3/4/17.
 */

public class DisplayMyStockPresenter implements DisplayMyStockContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {

    private DisplayMyStockContract.View mView;
    private StockDataRepository mRepository;
    private LoaderProvider mLoaderProvider;
    private LoaderManager mLoaderManager;


    public DisplayMyStockPresenter(DisplayMyStockContract.View mView,
                                   StockDataRepository mRepository,
                                   LoaderProvider mLoaderProvider,
                                   LoaderManager mLoaderManager) {
        this.mView = mView;
        this.mRepository = mRepository;
        this.mLoaderProvider = mLoaderProvider;
        this.mLoaderManager = mLoaderManager;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createFilteredTasksLoader(new StockFilter(StockFilterType.ALL_PERSONAL_STOCKS));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            List<StockModel> stockList = new ArrayList<>();
            if (data != null){
                /*convert to list*/
                for (int i = 0; i < data.getCount() ; i++) {
                    if (data.moveToPosition(i)){
                        stockList.add(StockModel.from(data));
                    }
                }
                mView.updateStocksData(stockList);
            }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void insertStock() {

    }

    @Override
    public void fetchStocksData() {
        mRepository.fetchStocks();
    }
}
