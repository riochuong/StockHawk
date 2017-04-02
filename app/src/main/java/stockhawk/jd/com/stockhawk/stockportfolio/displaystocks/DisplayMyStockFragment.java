package stockhawk.jd.com.stockhawk.stockportfolio.displaystocks;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.stockportfolio.addstocksdialog.AddStockDiaglogFragment;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;
import stockhawk.jd.com.stockhawk.stockportfolio.stockdetail.StockDetailsActivity;
import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;

import static stockhawk.jd.com.stockhawk.util.PrefUtilsModel.ACTION_DATA_UPDATED;

/**
 * This fragment will hold the layout for display stock
 *
 */
public class DisplayMyStockFragment extends Fragment implements DisplayMyStockContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        StockAdapter.StockAdapterOnClickHandler,
        View.OnClickListener{

    @BindView(R.id.recycler_view)
     RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
     SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.error)
     TextView mErrorTextView;

    @BindView(R.id.fab)
     FloatingActionButton addStockFab;

    @BindView(R.id.nasdaq_stock_price)
    TextView nasdaqStockPrice;

    @BindView(R.id.s_n_p_stock_price)
    TextView snptockPrice;

    @BindView(R.id.nasdaq_change)
    TextView nasdaqChange;

    @BindView(R.id.s_n_p_change)
    TextView snpChange;

    @BindView(R.id.card_nasdaq)
    CardView nasdaqCardView;

    @BindView(R.id.card_s_n_p)
    CardView snpCardView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.index_layout)
    LinearLayout mIndexLayout;


    private DisplayMyStockContract.Presenter mPresenter;
    private StockAdapter mAdapter;
    private static final String STOCK_SYMBOL_KEY = "stock_symbol";

    private static final String STOCK_CHANGE_FORMAT = "$%.2f(%.2f%%)";
    private static final String STOCK_PRICE_FORMAT = "$%.2f";

    public DisplayMyStockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DisplayMyStockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayMyStockFragment newInstance() {
        DisplayMyStockFragment fragment = new DisplayMyStockFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.display_my_stock_fragment, container, false);
        ButterKnife.bind(this, view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mAdapter= new StockAdapter(getContext(),this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        addStockFab.setOnClickListener(this);
        this.setHasOptionsMenu(true);

        /* add-on for swiping to remove stocks*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                String symbol = mAdapter.getSymbolAtPosition(viewHolder.getAdapterPosition());
                mPresenter.deleteStockSymbol(symbol);
            }
        }).attachToRecyclerView(mRecyclerView);

        // initialize appbar
        ((DisplayMyStocksActivity)getActivity()).setSupportActionBar(toolbar);
        ((DisplayMyStocksActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        return view;
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

    /**
     * Assign presenter here
     * @param presenter
     */
    @Override
    public void setPresenter(@NonNull DisplayMyStockContract.Presenter presenter) {
            mPresenter = presenter;
    }



    /* ON DATA REFRESHING */
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.refreshStockData();
    }

    /* ON VIEW CLICK */
    @Override
    public void onClick(String symbol) {
        // start new acitivy
        Intent startDetailStockView = new Intent(getContext(), StockDetailsActivity.class);
        startDetailStockView.putExtra(STOCK_SYMBOL_KEY,symbol);
        getContext().startActivity(startDetailStockView);
    }


    @Override
    public void updateStockDataDisplay(List<StockModel> stocks) {

        if (stocks != null && stocks.size() > 0){
            mErrorTextView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            List<StockModel> indices = new ArrayList<>();
            mSwipeRefreshLayout.setRefreshing(false);

        }
        mAdapter.setStockList(stocks);

    }

    @Override
    public void updateInvestmentIndices(List<StockModel> stocks) {
        String NASDAQ_TITLE = getString(R.string.default_stocks_nasdaq);
        String S_N_P_TITLE = getString(R.string.default_stocks_s_n_p);

        for (StockModel model : stocks){
            if (model.getSymbol().equalsIgnoreCase(NASDAQ_TITLE)){

                nasdaqStockPrice.setText(
                        String.format(STOCK_PRICE_FORMAT, Float.parseFloat(model.getPrice()))
                );

                nasdaqChange.setText(
                        String.format(STOCK_CHANGE_FORMAT,
                                    Float.parseFloat(model.getAbsoluteChange()),
                                    Float.parseFloat(model.getPercentageChange())
                ));

                // change color logic
                if (Float.parseFloat(model.getAbsoluteChange()) <= 0){
                    nasdaqCardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.material_red_700));
                } else{
                    nasdaqCardView.setCardBackgroundColor(getContext().getResources().getColor(R.color
                            .material_green_700));
                }
            }
            if (model.getSymbol().equalsIgnoreCase(S_N_P_TITLE)){
                snptockPrice.setText(
                        String.format(STOCK_PRICE_FORMAT, Float.parseFloat(model.getPrice()))
                );

                snpChange.setText(
                        String.format(STOCK_CHANGE_FORMAT,
                                Float.parseFloat(model.getAbsoluteChange()),
                                Float.parseFloat(model.getPercentageChange())
                        ));

                // change color logic
                if (Float.parseFloat(model.getAbsoluteChange()) <= 0){
                    snpCardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.material_red_700));
                } else{
                    snpCardView.setCardBackgroundColor(getContext().getResources().getColor(R.color
                            .material_green_700));
                }
            }

        }

    }

    @Override
    public void displayFailToRefreshDuetoNetworkError() {
        /*TODO:
             1/ Set swipe refresh layout to false

             2/ Display error message
         */
        mSwipeRefreshLayout.setRefreshing(false);

        if (mAdapter.getItemCount() <=0){
            mRecyclerView.setVisibility(View.INVISIBLE);
            mErrorTextView.setVisibility(View.VISIBLE);
            mErrorTextView.setText(getString(R.string.error_no_network));
        }
        else{
            mErrorTextView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(),getString(R.string.network_error_message),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_activity_settings, menu);
        MenuItem item = menu.findItem(R.id.action_change_units);
        PrefUtilsModel.getInstance(getContext()).setDisplayModeMenuItemIcon(item);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_change_units) {
            PrefUtilsModel.getInstance(getContext()).toggleDisplayMode();
            PrefUtilsModel.getInstance(getContext()).setDisplayModeMenuItemIcon(item);
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        new AddStockDiaglogFragment().show(getFragmentManager(),"StockDialogFragment");

    }


}
