package stockhawk.jd.com.stockhawk.stockportfolio.stockdetail;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by chuondao on 4/2/17.
 */

public class XTimeStampFormat implements IValueFormatter, IAxisValueFormatter {
    private static final String DATE_FORMAT  = "MM-yy";

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        long ts = (long) value;
        return formatStockTimeStamp(ts);

    }

    private String formatStockTimeStamp(long values){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(values);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return (simpleDateFormat.format(cal.getTimeInMillis()));
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return formatStockTimeStamp((long)value);
    }
}
