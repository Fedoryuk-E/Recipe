package com.example.jenia.recipe.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.jenia.recipe.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;


import static com.example.jenia.recipe.data.ReciContract.NewReci.TABLE_NAME;

/**
 * Created by Jenia on 02.05.2017.
 */

public class ReciDbHelper extends SQLiteOpenHelper {
    private final Context fContext;
    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 1;


    public ReciDbHelper (Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        fContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + ReciContract.NewReci._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ReciContract.NewReci.COLUMN_TITLE + " TEXT NOT NULL, "
                + ReciContract.NewReci.COLUMN_CATEGORY + " TEXT NOT NULL, "
                + ReciContract.NewReci.COLUMN_RECIPE + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_RECIPES_TABLE);
// Добавляем записи в таблицу
        ContentValues values = new ContentValues();

        // Получим файл из ресурсов
        Resources res = fContext.getResources();

        // Открываем xml-файл
        XmlResourceParser _xml = res.getXml(R.xml.recipes_insert);
        try {
            // Ищем конец документа
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Ищем теги record
                if ((eventType == XmlPullParser.START_TAG)
                        && (_xml.getName().equals("record"))) {
                    // Тег Record найден, теперь получим его атрибуты и
                    // вставляем в таблицу
                    String title = _xml.getAttributeValue(0);
                    String category = _xml.getAttributeValue(1);
                    String recipe = _xml.getAttributeValue(2);
                    values.put("title", title);
                    values.put("category", category);
                    values.put("recipe", recipe);
                    db.insert(TABLE_NAME, null, values);
                }
                eventType = _xml.next();
            }
        }
        // Catch errors
        catch (XmlPullParserException e) {
            Log.e("Test", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("Test", e.getMessage(), e);

        } finally {
            // Close the xml file
            _xml.close();
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
