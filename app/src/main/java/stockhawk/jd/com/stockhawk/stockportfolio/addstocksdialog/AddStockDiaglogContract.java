package stockhawk.jd.com.stockhawk.stockportfolio.addstocksdialog;

import stockhawk.jd.com.stockhawk.BasePresenter;
import stockhawk.jd.com.stockhawk.BaseView;

/**
 * Created by chuondao on 3/12/17.
 */

public class AddStockDiaglogContract {

    private AddStockDiaglogContract() {}

    interface View extends BaseView<Presenter>{
        void dismissViewAfterAdd();
        void displayFailToAddStockDueToNetworkError(String symbol);
    }

    interface  Presenter extends BasePresenter{
        void addStock(String symbol);
        boolean checkStock(String symbol);
    }
}
