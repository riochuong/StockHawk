package stockhawk.jd.com.stockhawk.stockportfolio.displaystocks;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.stockportfolio.addstocksdialog.AddStockDiaglogFragment;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;
import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;

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



    private DisplayMyStockContract.Presenter mPresenter;
    private StockAdapter mAdapter;

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

    }


    @Override
    public void updateStockDataDisplay(List<StockModel> stocks) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setStockList(stocks);

    }

    @Override
    public void displayFailToRefreshDuetoNetworkError() {
        /*TODO:
             1/ Set swipe refresh layout to false

             2/ Display error message
         */
        mSwipeRefreshLayout.setRefreshing(false);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(getString(R.string.error_no_network));
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
