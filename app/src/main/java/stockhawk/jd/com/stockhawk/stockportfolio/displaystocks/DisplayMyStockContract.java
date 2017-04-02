package stockhawk.jd.com.stockhawk.stockportfolio.displaystocks;

import java.util.List;

import stockhawk.jd.com.stockhawk.BasePresenter;
import stockhawk.jd.com.stockhawk.BaseView;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;

/**
 * Created by chuondao on 3/4/17.
 */

public class DisplayMyStockContract {


    interface View extends BaseView<Presenter> {
        // update regular stokcs
        void updateStockDataDisplay(List<StockModel> stock);
        void updateInvestmentIndices(List<StockModel> stock);
        void displayFailToRefreshDuetoNetworkError();
    }

    interface Presenter extends BasePresenter{
        void refreshStockData();
        void deleteStockSymbol(String symbol);
    }
}

