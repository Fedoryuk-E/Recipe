package com.example.jenia.recipe.data;

import android.provider.BaseColumns;

/**
 * Created by Jenia on 02.05.2017.
 */

public final class ReciContract {
    private ReciContract(){
    };
    public static final class NewReci implements BaseColumns {
        public final static String TABLE_NAME = "recipes";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE= "title";
        public final static String COLUMN_CATEGORY = "category";
        public final static String COLUMN_RECIPE = "recipe";


    }
}
