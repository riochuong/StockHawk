package stockhawk.jd.com.stockhawk.stockportfolio.addstocksdialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.data.local.StockLocalDataSource;
import stockhawk.jd.com.stockhawk.data.remote.StockRemoteDataSource;
import stockhawk.jd.com.stockhawk.util.NetworkUtilsModel;
import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;

/**
 * Created by chuondao on 3/12/17.
 */

public class AddStockDiaglogFragment extends DialogFragment implements AddStockDiaglogContract.View  {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.dialog_stock)
    EditText stock;
    AddStockDiaglogContract.Presenter mPresenter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View custom = inflater.inflate(R.layout.add_stock_dialog, null);

        ButterKnife.bind(this, custom);

//        stock.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                addStock();
//                return true;
//            }
//        });
        builder.setView(custom);

        builder.setMessage(getString(R.string.dialog_title));

        builder.setPositiveButton(getString(R.string.dialog_add),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String symbol = String.valueOf(stock.getText());
                        mPresenter.addStock(symbol);
                    }
                });
        builder.setNegativeButton(getString(R.string.dialog_cancel), null);

        Dialog dialog = builder.create();

        Window window = dialog.getWindow();

        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        /*set presenter here */
        StockLocalDataSource stockLocalDataSource = StockLocalDataSource.getInstance(getActivity().getContentResolver());

        StockRemoteDataSource remoteDataSource = StockRemoteDataSource.getInstance(getActivity());

        PrefUtilsModel prefUtilsModel = PrefUtilsModel.getInstance(getActivity());

        NetworkUtilsModel networkUtilsModel = NetworkUtilsModel.getInstance(getActivity());

        StockDataRepository stockDataRepository = StockDataRepository.getInstance(stockLocalDataSource,
                remoteDataSource, prefUtilsModel);

        AddStockDialogPresenter presenter = new AddStockDialogPresenter(this,networkUtilsModel, stockDataRepository);

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.stop();
    }

    @Override
    public void displayFailToAddStockDueToNetworkError(String symbol) {
        String message = getString(R.string.toast_stock_added_no_connectivity, symbol);
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }



    @Override
    public void setPresenter(AddStockDiaglogContract.Presenter presenter) {
            this.mPresenter = presenter;
    }

    @Override
    public void dismissViewAfterAdd() {
        dismissAllowingStateLoss();
    }
}
