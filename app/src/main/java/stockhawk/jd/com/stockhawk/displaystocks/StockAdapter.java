package stockhawk.jd.com.stockhawk.displaystocks;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import stockhawk.jd.com.stockhawk.R;
import stockhawk.jd.com.stockhawk.data.PrefUtils;
import stockhawk.jd.com.stockhawk.displaystocks.model.Stock;

class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private final Context context;
    private final DecimalFormat dollarFormatWithPlus;
    private final DecimalFormat dollarFormat;
    private final DecimalFormat percentageFormat;
    private List<Stock> stockList;
    private final StockAdapterOnClickHandler clickHandler;

    StockAdapter(Context context, StockAdapterOnClickHandler clickHandler) {
        this.context = context;
        this.clickHandler = clickHandler;

        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
    }

    void setStockList(List<Stock> stkList) {
        this.stockList = stkList;
        notifyDataSetChanged();
    }

    String getSymbolAtPosition(int position) {

        if (stockList != null && position < stockList.size()){
            return stockList.get(position).getSymbol();
        }
        return null;
    }

    String getPriceAtPosition(int position) {

        if (stockList != null && position < stockList.size()){
            return stockList.get(position).getPrice();
        }
        return null;
    }

    String getAbsoluteChange(int position) {

        if (stockList != null && position < stockList.size()){
            return stockList.get(position).getPrice();
        }
        return null;
    }

    String getPercentageChange(int position) {

        if (stockList != null && position < stockList.size()){
            return stockList.get(position).getPrice();
        }
        return null;
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(context).inflate(R.layout.list_item_quote, parent, false);

        return new StockViewHolder(item);
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, int position) {

        holder.symbol.setText(getSymbolAtPosition(position));
        holder.price.setText(dollarFormat.format(Float.parseFloat(getPriceAtPosition(position))));


        float rawAbsoluteChange = Float.parseFloat(getAbsoluteChange(position));
        float percentageChange = Float.parseFloat(getPercentageChange(position));

        if (rawAbsoluteChange > 0) {
            holder.change.setBackgroundResource(R.drawable.percent_change_pill_green);
        } else {
            holder.change.setBackgroundResource(R.drawable.percent_change_pill_red);
        }

        String change = dollarFormatWithPlus.format(rawAbsoluteChange);
        String percentage = percentageFormat.format(percentageChange / 100);

        if (PrefUtils.getDisplayMode(context)
                .equals(context.getString(R.string.pref_display_mode_absolute_key))) {
            holder.change.setText(change);
        } else {
            holder.change.setText(percentage);
        }


    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (stockList != null) {
            count = stockList.size();
        }
        return count;
    }


    interface StockAdapterOnClickHandler {
        void onClick(String symbol);
    }

    class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.symbol)
        TextView symbol;

        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.change)
        TextView change;

        StockViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onClick(getSymbolAtPosition(adapterPosition));
        }
    }
}
