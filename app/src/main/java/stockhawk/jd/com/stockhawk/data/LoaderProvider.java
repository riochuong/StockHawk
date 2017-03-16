/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stockhawk.jd.com.stockhawk.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

/**
 * This class returns the correct cursor loader based on the Filter given from the
 * Presenter
 */

import java.net.URI;

import stockhawk.jd.com.stockhawk.data.local.StockContract;
import stockhawk.jd.com.stockhawk.stockportfolio.model.StockFilter;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoaderProvider {

    @NonNull
    private final Context mContext;

    public LoaderProvider(@NonNull Context context) {
        mContext = context;
    }

    /*create Loader based on given filter */
    public Loader<Cursor> createFilteredTasksLoader(StockFilter taskFilter) {
        String selection = null;
        String[] selectionArgs = null;
        Uri uri = null;
        String symbol = taskFilter.getSymbol();
        switch (taskFilter.getStockFilterType()) {
            case ALL_PERSONAL_STOCKS:
                selection = null;
                selectionArgs = null;
                uri = StockContract.Quote.URI;
                break;

            case ONE_SPECIFIC_STOCK:
                if (symbol == null){
                    throw new IllegalArgumentException("symbol must be a valid value");
                }
                uri = StockContract.Quote.URI.buildUpon().appendPath(symbol).build();
                break;
        }
        return new CursorLoader(
                mContext,
                uri,
                StockContract.Quote.QUOTE_COLUMNS.toArray(new String[]{}),selection, selectionArgs, null
        );

    }

}
