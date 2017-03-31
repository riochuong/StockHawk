package stockhawk.jd.com.stockhawk.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.MenuItem;


import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import stockhawk.jd.com.stockhawk.R;

public class PrefUtilsModel {

    private Context mContext;

    private static PrefUtilsModel INSTANCE;

    public static final String ACTION_DATA_UPDATED = "com.udacity.stockhawk.ACTION_DATA_UPDATED";

    private  PrefUtilsModel(Context ctx) {
        this.mContext = ctx;
    }

    public static PrefUtilsModel getInstance(Context mContext){
        if (INSTANCE == null){
            INSTANCE = new PrefUtilsModel(mContext);
        }
        return INSTANCE;
    }

    public  Set<String> getStocks() {
        String stocksKey = mContext.getString(R.string.pref_stocks_key);
        String initializedKey = mContext.getString(R.string.pref_stocks_initialized_key);
        String[] defaultStocksList = mContext.getResources().getStringArray(R.array.default_stocks);

        HashSet<String> defaultStocks = new HashSet<>(Arrays.asList(defaultStocksList));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);


        boolean initialized = prefs.getBoolean(initializedKey, false);

        if (!initialized) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(initializedKey, true);
            editor.putStringSet(stocksKey, defaultStocks);
            editor.apply();
            return defaultStocks;
        }
        return prefs.getStringSet(stocksKey, new HashSet<String>());

    }

    private void  editStockPref(String symbol, Boolean add) {
        String key = mContext.getString(R.string.pref_stocks_key);
        Set<String> stocks = getStocks();

        if (add) {
            stocks.add(symbol);
        } else {
            stocks.remove(symbol);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(key, stocks);
        editor.apply();
    }

    public void addStock( String symbol) {
        editStockPref(symbol, true);
    }

    public void removeStock( String symbol) {
        editStockPref(symbol, false);
    }

    public void sendDataUpdateBroadcast(){
        mContext.sendBroadcast(new Intent(ACTION_DATA_UPDATED));
    }

    public  String getDisplayMode() {
        String key = mContext.getString(R.string.pref_display_mode_key);
        String defaultValue = mContext.getString(R.string.pref_display_mode_default);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        return prefs.getString(key, defaultValue);
    }

    public  void toggleDisplayMode() {
        String key = mContext.getString(R.string.pref_display_mode_key);
        String absoluteKey = mContext.getString(R.string.pref_display_mode_absolute_key);
        String percentageKey = mContext.getString(R.string.pref_display_mode_percentage_key);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        String displayMode = getDisplayMode();

        SharedPreferences.Editor editor = prefs.edit();

        if (displayMode.equals(absoluteKey)) {
            editor.putString(key, percentageKey);
        } else {
            editor.putString(key, absoluteKey);
        }

        editor.apply();
    }

    public void setDisplayModeMenuItemIcon(MenuItem item) {
        if (getDisplayMode()
                .equals(mContext.getString(R.string.pref_display_mode_absolute_key))) {
            item.setIcon(R.drawable.ic_percentage);
        } else {
            item.setIcon(R.drawable.ic_dollar);
        }
    }

}
