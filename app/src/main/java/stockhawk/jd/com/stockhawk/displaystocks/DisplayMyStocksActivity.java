package stockhawk.jd.com.stockhawk.displaystocks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import stockhawk.jd.com.stockhawk.R;

public class DisplayMyStocksActivity extends AppCompatActivity {

    /* status of the view */
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_stocks);
    }
}
