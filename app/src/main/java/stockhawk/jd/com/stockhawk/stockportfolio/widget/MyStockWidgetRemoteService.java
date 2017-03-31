package stockhawk.jd.com.stockhawk.stockportfolio.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.data.local.StockLocalDataSource;
import stockhawk.jd.com.stockhawk.data.remote.StockRemoteDataSource;
import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;

/**
 * the remote View service controls data being show in the stock scrollable view
 */

public class MyStockWidgetRemoteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        // initialize presenters with required factories
        MyStockWidgetRemoteViewsFactory viewFactory = new MyStockWidgetRemoteViewsFactory(this);
        StockLocalDataSource stockLocalDataSource = StockLocalDataSource.getInstance(this.getContentResolver());
        StockRemoteDataSource remoteDataSource = StockRemoteDataSource.getInstance(this);
        PrefUtilsModel prefUtilsModel = PrefUtilsModel.getInstance(this);
        StockDataRepository repository = StockDataRepository.getInstance(stockLocalDataSource, remoteDataSource,
                                                                                        prefUtilsModel);
        // create presenter and set it to the view correctly
        MyStockWidgetPresenter presenter = new MyStockWidgetPresenter(repository,viewFactory);
        viewFactory.setPresenter(presenter);
        return viewFactory;
    }
}
