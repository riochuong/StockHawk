package stockhawk.jd.com.stockhawk.displaystocks;

import java.util.List;

import stockhawk.jd.com.stockhawk.BasePresenter;
import stockhawk.jd.com.stockhawk.BaseView;
import stockhawk.jd.com.stockhawk.displaystocks.model.StockModel;

/**
 * Created by chuondao on 3/4/17.
 */

public class DisplayMyStockContract {


    interface View extends BaseView<Presenter> {
        void updateStocksData (List<StockModel> stock);
    }

    interface Presenter extends BasePresenter{
        void insertStock();
        void fetchStocksData();
    }
}
