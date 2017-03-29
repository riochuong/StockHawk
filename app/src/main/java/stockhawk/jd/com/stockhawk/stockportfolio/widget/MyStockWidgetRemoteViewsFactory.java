package stockhawk.jd.com.stockhawk.stockportfolio.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.data.StockDataRepository;
import stockhawk.jd.com.stockhawk.data.local.StockLocalDataSource;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;

import static stockhawk.jd.com.stockhawk.stockportfolio.stockdetail.StockDetailsActivity.STOCK_SYMBOL;

/**
 * Created by chuondao on 3/27/17.
 */

public class MyStockWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, MyStockWidgetContract.View{


    MyStockWidgetContract.Presenter mPresenter;
    List<StockModel> stocks;
    Context mContext;

    public MyStockWidgetRemoteViewsFactory(Context mContext) {
        this.mPresenter = null;
        this.stocks = null;
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // clear calling identity to force calling through our apps for content provider
        final long identityToken = Binder.clearCallingIdentity();
        mPresenter.getStocksData();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (stocks != null){
            return stocks.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (stocks == null || position == AdapterView.INVALID_POSITION){
            return null;
        }

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.stock_widget_list_item_layout);
        StockModel stock = stocks.get(position);
        views.setTextViewText(R.id.widget_stock_symbol, stock.getSymbol());
        views.setTextViewText(R.id.widget_stock_price, stock.getPrice());
        views.setTextViewText(R.id.change, stock.getAbsoluteChange());
        // set filling intent
        final Intent fillingIntent = new Intent();
        fillingIntent.putExtra(STOCK_SYMBOL, stock.getSymbol());
        views.setOnClickFillInIntent(R.id.widget_stock_layout, fillingIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.stock_widget_list_item_layout);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (stocks != null){
            return position;
        }
        return -1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void setPresenter(MyStockWidgetContract.Presenter presenter) {
            this.mPresenter = presenter;
    }

    @Override
    public void updateStockData(List<StockModel> mstocks) {
        stocks  = mstocks;
    }
}
