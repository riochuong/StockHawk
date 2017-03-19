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

        /* this one should be called when new stock added but we can not get the
        * data both from DB and Network. Might just show an empty view
        * */
        void displayNetworkError();

    }

    interface Presenter extends BasePresenter{
        /*retreive data related to the requested stock*/
        void getStockData(String symbol);


    }
}
