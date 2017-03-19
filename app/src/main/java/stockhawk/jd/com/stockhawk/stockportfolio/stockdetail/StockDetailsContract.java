package stockhawk.jd.com.stockhawk.stockportfolio.stockdetail;


import stockhawk.jd.com.stockhawk.BasePresenter;
import stockhawk.jd.com.stockhawk.BaseView;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockHistoryModel;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;

/**
 * Created by chuondao on 3/13/17.
 */

public class StockDetailsContract {

    interface View extends BaseView<Presenter>{
        void setDataForChart(StockHistoryModel histModel);

        /* complete textviews with all neccessary data */
        void setStockCommonData(StockModel stock);

        /* set stock change color based on the price is up or down*/
        void setStockChangeColor(int color);

        /* complete textviews with all neccessary data */
        void setStockHistoryData(StockHistoryModel histModel);

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
