package stockhawk.jd.com.stockhawk.stockportfolio.displaystocks;

import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.data.LoaderProvider;
import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.data.local.StockLocalDataSource;
import stockhawk.jd.com.stockhawk.data.remote.StockRemoteDataSource;
import stockhawk.jd.com.stockhawk.util.NetworkUtilsModel;
import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;

public class DisplayMyStocksActivity extends AppCompatActivity {

    /* status of the view */
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_my_stocks_activity);
        ButterKnife.bind(this);
        // Create elements for MVP
        StockLocalDataSource stockLocalDataSource = StockLocalDataSource.getInstance(getContentResolver());
        StockRemoteDataSource remoteDataSource = StockRemoteDataSource.getInstance(this);
        PrefUtilsModel prefUtilsModel = PrefUtilsModel.getInstance(this);
        StockDataRepository stockDataRepository = StockDataRepository.getInstance(stockLocalDataSource,
                remoteDataSource, prefUtilsModel);
        NetworkUtilsModel networkUtilsModel = NetworkUtilsModel.getInstance(this);
        LoaderProvider loaderProvider = new LoaderProvider(this);
        LoaderManager loaderManager = getSupportLoaderManager();

        DisplayMyStockFragment fragment = (DisplayMyStockFragment) getSupportFragmentManager().findFragmentById(R.id
                .main_fragment);

        DisplayMyStockPresenter presenter = new DisplayMyStockPresenter(fragment,stockDataRepository,loaderProvider,
                loaderManager,
                networkUtilsModel);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /*Chain the call to route it to fragment*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
