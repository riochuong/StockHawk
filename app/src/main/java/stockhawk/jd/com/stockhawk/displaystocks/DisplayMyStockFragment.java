package stockhawk.jd.com.stockhawk.displaystocks;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.displaystocks.model.StockModel;

/**
 * This fragment will hold the layout for display stock
 *
 */
public class DisplayMyStockFragment extends Fragment implements DisplayMyStockContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        StockAdapter.StockAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private DisplayMyStockContract.Presenter mPresenter;
    private StockAdapter mAdapter;

    public DisplayMyStockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayMyStockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayMyStockFragment newInstance(String param1, String param2) {
        DisplayMyStockFragment fragment = new DisplayMyStockFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_my_stock, container, false);
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
        mPresenter.fetchStocksData();
    }

    /* ON VIEW CLICK */
    @Override
    public void onClick(String symbol) {

    }
    @Override
    public void updateStocksData(List<StockModel> stocks) {
        mAdapter.setStockList(stocks);
    }
}
