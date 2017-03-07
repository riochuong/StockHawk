package stockhawk.jd.com.stockhawk.displaystocks;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import stockhawk.jd.com.stockhawk.R;
/**
 * This fragment will hold the layout for display stock
 *
 */
public class DisplayMyStockFragment extends Fragment implements DisplayMyStockContract.View, LoaderManager.LoaderCallbacks<Cursor>,
        SwipeRefreshLayout.OnRefreshListener,
        StockAdapter.StockAdapterOnClickHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DisplayMyStockContract.Presenter mPresenter;


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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    /* LOADER CALL BACKS AND INITIALIZATION */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /* ON DATA REFRESHING */
    @Override
    public void onRefresh() {

    }

    /* ON VIEW CLICK */
    @Override
    public void onClick(String symbol) {

    }
}
