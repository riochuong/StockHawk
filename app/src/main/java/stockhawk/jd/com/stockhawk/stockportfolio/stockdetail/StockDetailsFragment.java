package stockhawk.jd.com.stockhawk.stockportfolio.stockdetail;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.stockportfolio.model.PriceDataPoint;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockPriceComparator;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockTimestampComparator;

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

    private static final String DATA_SET_LABEL = "line_chart_label";

    private static final String STOCK_SYMBOL = "stock_symbol";

    /*animation time for the plot*/
    private static final int ANIMATION_DURATION = 1000 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.stock_detail_fragment_layout,container,false);

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
    public void onResume() {
        super.onResume();
        if (mPresenter != null){
            mPresenter.getStockData(getArguments().getString(STOCK_SYMBOL));
        }
    }

    @Override
    public void setPresenter(StockDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setDataForChart(ArrayList<PriceDataPoint> data) {
        ArrayList<Entry> values = constructEntryist(data);
        LineData lineData = null;
        if (stockLineChart.getData() == null){
            // set marker first
            MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);
            mv.setChartView(stockLineChart); // For bounds control
            stockLineChart.setMarker(mv); // Set the marker to the chart
            // initialize data
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(getNewLineDataSet(values));
            lineData = new LineData(dataSets);
            stockLineChart.setData(lineData);
        } else{
            lineData = stockLineChart.getData();
            LineDataSet set = (LineDataSet) lineData.getDataSetByIndex(0);
            set.setValues(values);
        }
        Pair<Float,Float> pricesMaxMin = getStockMaxMinPrice(data);
        // add the limit line to the plot
        setStockLimitLine(pricesMaxMin.first,pricesMaxMin.second);
        // set animation
        stockLineChart.animateXY(ANIMATION_DURATION, ANIMATION_DURATION);

        Legend l = stockLineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        stockLineChart.getData().notifyDataChanged();
        stockLineChart.notifyDataSetChanged();
    }


    /**
     *
     * @param data
     * @return
     */
    private Pair<Float,Float> getStockMaxMinPrice(ArrayList<PriceDataPoint> data){

        // sort data by price
        Collections.sort(data, new StockPriceComparator());
        float maxPrice = data.get(data.size() - 1).getStockPrice();
        float minPrice = data.get(0).getStockPrice();
        return new Pair(minPrice,maxPrice);
    }

    /**
     * helper to generate values data for fragment
     * @param data
     * @return
     */
    private ArrayList<Entry> constructEntryist(ArrayList<PriceDataPoint> data){
        ArrayList<Entry> values = new ArrayList<>();
        // going through and add data to list
        // first sort the data
        Collections.sort(data,new StockTimestampComparator());
        for (PriceDataPoint item : data){
            values.add (new Entry(item.getTimeStamp(),item.getStockPrice()));
        }

        return values;
    }


    private void setStockLimitLine(float minPrice, float maxPrice){
        if (stockLineChart == null){
            return;
        }
        // set upper bound
        LimitLine ll1 = new LimitLine(maxPrice, getContext().getString(R.string.highest_price));
//        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), getContext().getString(R.string
//                .type_face_font));
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        stockLineChart.getXAxis().setDrawLabels(false);
 //       ll1.setTypeface(tf);

        // set lower bound
        LimitLine ll2 = new LimitLine(minPrice, getContext().getString(R.string.lowest_price));
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        //ll2.setTypeface(tf);

        YAxis leftAxis = stockLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        stockLineChart.getAxisRight().setEnabled(false);
    }

    /**
     * construct line data set with specific effects
     * @param values
     * @return
     */
    private LineDataSet getNewLineDataSet(ArrayList<Entry> values){
        LineDataSet set = new LineDataSet(values, DATA_SET_LABEL);
        set.enableDashedLine(10f, 5f, 0f);
        set.enableDashedHighlightLine(10f, 5f, 0f);
        set.setColor(Color.BLACK);
        set.setCircleColor(Color.BLACK);
        set.setLineWidth(1f);
        set.setCircleRadius(3f);
        set.setDrawCircleHole(false);
        set.setValueTextSize(9f);
        set.setDrawFilled(true);
        set.setFormLineWidth(1f);
        set.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set.setFormSize(15.f);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
            set.setFillDrawable(drawable);
        }
        else {
            set.setFillColor(Color.BLACK);
        }
        return set;
    }

    @Override
    public void setStockCommonData(StockModel stock) {
        stockName.setText("("+stock.getName()+")");
        stockSymbol.setText(stock.getSymbol());
        stockPrice.setText(stock.getPrice());
        stockChanges.setText(stock.getPercentageChange());
        stockVolume.setText(stock.getVolume());
    }

    @Override
    public void displayNetworkError() {
        // TODO : set empty view here
    }
}
