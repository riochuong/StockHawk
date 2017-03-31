package stockhawk.jd.com.stockhawk.stockportfolio.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.data.sync.QuoteIntentService;
import stockhawk.jd.com.stockhawk.stockportfolio.displaystocks.DisplayMyStocksActivity;
import stockhawk.jd.com.stockhawk.stockportfolio.stockdetail.StockDetailsActivity;

/**
 * Created by chuondao on 3/23/17.
 */

public class MyStockWidgetProvider extends AppWidgetProvider  {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds){
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_stock_detail_layout);

            // set up the collection to launch the detail view
            Intent clickIntentTemplate = new Intent(context, StockDetailsActivity.class);
            Intent launchMainActivityTemplate = new Intent(context, DisplayMyStocksActivity.class);
            PendingIntent launchMainActivityIntentTemplate = PendingIntent.getActivity(context,0,
                    launchMainActivityTemplate,PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_stock_list_view,clickPendingIntentTemplate);
            views.setRemoteAdapter(R.id.widget_stock_list_view, new Intent(context,MyStockWidgetRemoteService.class));
            views.setOnClickPendingIntent(R.id.widget_title, launchMainActivityIntentTemplate);

            // tell appwidgetmanager to perform update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        // time to update our data
        if (QuoteIntentService.ACTION_DATA_UPDATED.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_stock_list_view);
        }
    }


}
