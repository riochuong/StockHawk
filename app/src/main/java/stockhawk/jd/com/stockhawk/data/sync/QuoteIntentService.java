package stockhawk.jd.com.stockhawk.data.sync;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import stockhawk.jd.com.stockhawk.util.PrefUtilsModel;
import stockhawk.jd.com.stockhawk.data.StockDataSource;
import stockhawk.jd.com.stockhawk.data.local.StockContract;
import stockhawk.jd.com.stockhawk.data.local.StockLocalDataSource;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockModel;
import timber.log.Timber;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockQuote;


public class QuoteIntentService extends IntentService {

    public static final String ACTION_DATA_UPDATED = "com.udacity.stockhawk.ACTION_DATA_UPDATED";
    private static final int YEARS_OF_HISTORY = 2;


    public QuoteIntentService() {
        super(QuoteIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("Intent handled");
        // try to get the quotes here
        List<StockModel> mListStockModel = getQuotes(this);
        StockLocalDataSource localDataSource = StockLocalDataSource.getInstance(getContentResolver());
        // update stocks and broadcast the good news
        localDataSource.insertStocks(mListStockModel, new StockDataSource.InsertStocksCallBacks() {
            @Override
            public void onStocksInserted() {
                Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
                PrefUtilsModel.getInstance(QuoteIntentService.this).sendDataUpdateBroadcast();
            }

            @Override
            public void onInsertError() {
                Timber.e("Failed to insert stocks to DB");
            }
        });

    }


    static List<StockModel> getQuotes(Context context) {

        Timber.d("Running sync job");

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -YEARS_OF_HISTORY);
        ArrayList<StockModel> quoteStocks = new ArrayList<>();

        try {

            Set<String> stockCopy = new HashSet<>();
            Set<String> stockPref = PrefUtilsModel.getInstance(context).getStocks();
            stockCopy.addAll(stockPref);
            String[] stockArray = stockPref.toArray(new String[stockPref.size()]);

            Timber.d(stockCopy.toString());

            if (stockArray.length == 0) {
                return null;
            }

            Map<String, Stock> quotes = YahooFinance.get(stockArray);
            Iterator<String> iterator = stockCopy.iterator();

            Timber.d(quotes.toString());



            while (iterator.hasNext()) {
                String symbol = iterator.next();
                Stock stock = quotes.get(symbol);

                // avoid bad quote got added -- stock with no price should be invalid
                if (stock == null) {
                    continue;
                }
                if (stock.getQuote() == null) {
                    continue;
                }
                if (stock.getQuote().getPrice() == null) {
                    continue;
                }


                StockQuote quote = stock.getQuote();
                float price = quote.getPrice().floatValue();
                float change = quote.getChange().floatValue();
                float percentChange = quote.getChangeInPercent().floatValue();
                // just in case investment index does not have this values
                long volumeAvg = (quote.getAvgVolume() != null) ? quote.getAvgVolume() : -1;
                String name = stock.getName();

                // WARNING! Don't request historical data for a stock that doesn't exist!
                // The request will hang forever X_x
                List<HistoricalQuote> history = stock.getHistory(from, to, Interval.WEEKLY);

                StringBuilder historyBuilder = new StringBuilder();

                for (HistoricalQuote it : history) {
                    historyBuilder.append(it.getDate().getTimeInMillis());
                    historyBuilder.append(", ");
                    historyBuilder.append(it.getClose());
                    historyBuilder.append("\n");
                }

                ContentValues quoteCV = new ContentValues();
                quoteCV.put(StockContract.Quote.COLUMN_SYMBOL, symbol);
                quoteCV.put(StockContract.Quote.COLUMN_PRICE, price);
                quoteCV.put(StockContract.Quote.COLUMN_PERCENTAGE_CHANGE, percentChange);
                quoteCV.put(StockContract.Quote.COLUMN_ABSOLUTE_CHANGE, change);
                quoteCV.put(StockContract.Quote.COLUMN_VOLUME_AVG, volumeAvg);
                quoteCV.put(StockContract.Quote.COLUMN_NAME, name);


                quoteCV.put(StockContract.Quote.COLUMN_HISTORY, historyBuilder.toString());

                quoteStocks.add(StockModel.from(quoteCV));
            }

            return quoteStocks;

        } catch (IOException exception) {
            Timber.e(exception, "Error fetching stock quotes");
        }finally {
            return quoteStocks;
        }
    }
}
