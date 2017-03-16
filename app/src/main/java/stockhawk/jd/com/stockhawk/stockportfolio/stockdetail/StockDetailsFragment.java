package stockhawk.jd.com.stockhawk.stockportfolio.stockdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.stockportfolio.model.PriceDataPoint;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;

/**
 * Created by chuondao on 3/13/17.
 */

public class StockDetailsFragment extends Fragment implements  StockDetailsContract.View{

    @BindView(R.id.line_chart)
    LineChart stockLineChart;

    @BindView(R.id.stock_symbol_text)
    TextView stockSymbol;

    @BindView(R.id.stock_full_name)
    TextView stockName;

    @BindView(R.id.stock_changes)
    TextView stockChanges;

    @BindView(R.id.stock_price)
    TextView stockPrice;

    @BindView(R.id.stock_volume)
    TextView stockVolume;

    StockDetailsContract.Presenter mPresenter;

    private static final String STOCK_SYMBOL = "stock_symbol";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.display_my_stock_fragment,container,false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }

    public static StockDetailsFragment newInstance(String stockSymbol){

        StockDetailsFragment frag = new StockDetailsFragment();

        Bundle data = new Bundle();

        data.putString(STOCK_SYMBOL, stockSymbol);

        frag.setArguments(data);
        return frag;
    }

    @Override
    public void setPresenter(StockDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setDataForChart(ArrayList<PriceDataPoint> data) {

    }

    @Override
    public void setStockCommonData(StockModel stock) {

    }
}
