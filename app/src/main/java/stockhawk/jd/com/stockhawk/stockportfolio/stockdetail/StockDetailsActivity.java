package stockhawk.jd.com.stockhawk.stockportfolio.stockdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;

import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.data.LoaderProvider;
import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.data.local.StockLocalDataSource;
import stockhawk.jd.com.stockhawk.data.remote.StockRemoteDataSource;
import stockhawk.jd.com.stockhawk.util.NetworkUtilsModel;
import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;

/**
 * Created by chuondao on 3/14/17.
 */

public class StockDetailsActivity extends AppCompatActivity {

    public static final String STOCK_SYMBOL = "stock_symbol";
    private static final String MOVIE_DETAIL_FRAG = "movie_detail_frag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_detail_activity_layout);

        String symbol = getIntent().getStringExtra(STOCK_SYMBOL);

        // no symbol passed in ..just finish the activity
        if (symbol == null) {
            this.finish();
        }
        LoaderManager lm = getSupportLoaderManager();
        StockLocalDataSource stockLocalDataSource = StockLocalDataSource.getInstance(getContentResolver());
        StockRemoteDataSource remoteDataSource = StockRemoteDataSource.getInstance(this);
        PrefUtilsModel prefUtilsModel = PrefUtilsModel.getInstance(this);
        StockDataRepository stockDataRepository = StockDataRepository.getInstance(stockLocalDataSource,
                remoteDataSource, prefUtilsModel);
        NetworkUtilsModel networkUtilsModel = NetworkUtilsModel.getInstance(this);
        LoaderProvider loaderProvider = new LoaderProvider(this);
        LoaderManager loaderManager = getSupportLoaderManager();
        //create the view
        StockDetailsFragment fragment = StockDetailsFragment.newInstance(symbol);
        // now we can create the presenter
        StockDetailsPresenter presenter = new StockDetailsPresenter(fragment, stockDataRepository, loaderProvider,
                loaderManager, networkUtilsModel);

        fragment.setPresenter(presenter);
        // replace the framelayout holder with the Fragments
        getSupportFragmentManager().beginTransaction().replace(
                R.id.stock_detail_fragment, fragment, MOVIE_DETAIL_FRAG
        ).commit();


    }
}
