package stockhawk.jd.com.stockhawk.stockportfolio.widget;

import android.graphics.Color;

import java.util.List;

import stockhawk.jd.com.stockhawk.BasePresenter;
import stockhawk.jd.com.stockhawk.BaseView;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;

/**
 * Created by chuondao on 3/27/17.
 */

public class MyStockWidgetContract {

    interface View extends BaseView <Presenter>{
        void updateStockData(List<StockModel> stocks);
    }

    interface Presenter extends BasePresenter{
        void getStocksData();
    }

}
