package stockhawk.jd.com.stockhawk.stockportfolio.stockdetail;

import java.util.ArrayList;

import stockhawk.jd.com.stockhawk.BasePresenter;
import stockhawk.jd.com.stockhawk.BaseView;
import stockhawk.jd.com.stockhawk.stockportfolio.model.PriceDataPoint;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;

/**
 * Created by chuondao on 3/13/17.
 */

public class StockDetailsContract {

    interface View extends BaseView<Presenter>{
        void setDataForChart(ArrayList<PriceDataPoint> data);

        /* complete textviews with all neccessary data */
        void setStockCommonData(StockModel stock);

    }

    interface Presenter extends BasePresenter{
        /*retreive data related to the requested stock*/
        void getStockData(String symbol);
    }
}
