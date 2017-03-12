package stockhawk.jd.com.stockhawk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by chuondao on 2/24/17.
 */

public class NetworkUtilsModel {

    private Context mContext;

    private static NetworkUtilsModel INSTANCE;

    public static NetworkUtilsModel getInstance(Context ctx){
        if (INSTANCE == null){
            INSTANCE = new NetworkUtilsModel(ctx);
        }
        return INSTANCE;
    }

    private NetworkUtilsModel(Context mContext) {
        this.mContext = mContext;
    }

    public  boolean networkUp() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
