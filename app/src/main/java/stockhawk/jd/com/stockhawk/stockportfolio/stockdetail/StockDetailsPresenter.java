package stockhawk.jd.com.stockhawk.stockportfolio.stockdetail;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by chuondao on 3/13/17.
 */

public class StockDetailsPresenter implements StockDetailsContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {



    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }



    //////// LOADERS SPECIFIC METHODS /////////
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
