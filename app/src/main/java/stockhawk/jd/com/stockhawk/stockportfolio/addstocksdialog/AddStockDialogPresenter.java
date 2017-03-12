package stockhawk.jd.com.stockhawk.stockportfolio.addstocksdialog;

import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.util.NetworkUtilsModel;

/**
 * Created by chuondao on 3/12/17.
 */

public class AddStockDialogPresenter implements AddStockDiaglogContract.Presenter {

    AddStockDiaglogContract.View mView;
    StockDataRepository mRepository;
    NetworkUtilsModel mNetworkModel;


    public AddStockDialogPresenter(AddStockDiaglogContract.View mView,
                                   NetworkUtilsModel networkUtil,
                                   StockDataRepository repo) {
        this.mView = mView;
        this.mRepository = repo;
        this.mNetworkModel = networkUtil;
        mView.setPresenter(this);
    }

    @Override
    public void addStock(String symbol) {
        if (!mNetworkModel.networkUp()){
            mView.displayFailToAddStockDueToNetworkError(symbol);
        }
        /*insert to shared preference for now and wait for network */
        mRepository.insertSingleStock(symbol);
        /* close dialog */
        mView.dismissViewAfterAdd();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
