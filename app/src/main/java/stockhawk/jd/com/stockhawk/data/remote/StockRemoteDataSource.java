package stockhawk.jd.com.stockhawk.data.remote;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.List;

import stockhawk.jd.com.stockhawk.data.StockDataSource;
import stockhawk.jd.com.stockhawk.data.sync.QuoteIntentService;
import stockhawk.jd.com.stockhawk.data.sync.QuoteJobService;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;
import timber.log.Timber;

/**
 * Created by chuondao on 3/11/17.
 */

public class StockRemoteDataSource implements StockDataSource {

    private static final int ONE_OFF_ID = 2;
    private static final int PERIOD = 300000;
    private static final int INITIAL_BACKOFF = 10000;
    private static final int PERIODIC_ID = 1;

    private static StockRemoteDataSource INSTANCE ;

    private StockRemoteDataSource(Context context) {
        this.mContext = context;
    }

    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void schedulePeriodic() {
        Timber.d("Scheduling a periodic task");

        JobInfo.Builder builder = new JobInfo.Builder(PERIODIC_ID, new ComponentName(mContext, QuoteJobService.class));


        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(PERIOD)
                .setBackoffCriteria(INITIAL_BACKOFF, JobInfo.BACKOFF_POLICY_EXPONENTIAL);


        JobScheduler scheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        scheduler.schedule(builder.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void syncImmediately() {

        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            Intent nowIntent = new Intent(mContext, QuoteIntentService.class);
            mContext.startService(nowIntent);
        } else {

            JobInfo.Builder builder = new JobInfo.Builder(ONE_OFF_ID, new ComponentName(mContext, QuoteJobService.class));


            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setBackoffCriteria(INITIAL_BACKOFF, JobInfo.BACKOFF_POLICY_EXPONENTIAL);


            JobScheduler scheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);

            scheduler.schedule(builder.build());

        }
    }


    public static StockRemoteDataSource getInstance(Context mContext){
        if (INSTANCE == null){
            INSTANCE = new StockRemoteDataSource(mContext);
        }
        return INSTANCE;
    }

    @Override
    public void insertStocks(@NonNull List<StockModel> stocks, InsertStocksCallBacks callBacks) {
            // NO-OP ..will use local data source
    }

    @Override
    public void deleteStock(@NonNull String symbol, DeleteStockCallBacks callBacks) {
        // No-op
    }

    @Override
    public void insertSingleStock(String symbol) {
        //NO-OP
    }

    @Override
    public void refreshStocks() {
        syncImmediately();
    }

    @Override
    public void shedulePeriodicSync() {
        schedulePeriodic();
    }
}
