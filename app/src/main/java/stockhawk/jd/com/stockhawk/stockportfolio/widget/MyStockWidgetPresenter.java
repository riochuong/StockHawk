package stockhawk.jd.com.stockhawk.stockportfolio.widget;

import android.util.Log;

import java.util.List;

import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.data.StockDataSource;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;

/**
 * Created by chuondao on 3/27/17.
 */

public class MyStockWidgetPresenter implements MyStockWidgetContract.Presenter{

    StockDataRepository stockDataRepository;
    MyStockWidgetContract.View view;

    public MyStockWidgetPresenter(StockDataRepository stockDataRepository, MyStockWidgetContract.View view) {
        this.stockDataRepository = stockDataRepository;
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    @Override
    public void getStocksData() {
            stockDataRepository.getPortfolioStocks(new StockDataSource.OnGetPortfolioStockCallbacks() {
                @Override
                public void onStocksReceived(List<StockModel> stocks) {
                    view.updateStockData(stocks);
                }

                @Override
                public void onStockRequestError() {
                    view.updateStockData(null);
                }
            });
    }
}
