package com.example.jenia.recipe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jenia.recipe.data.ReciContract;
import com.example.jenia.recipe.data.ReciDbHelper;

public class Recipe_view extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    private static final int CM_EDIT_ID = 2;
    private static final int  CM_NEW_ID = 3;

    ListView mList;
    TextView header;
    ReciDbHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    EditText inputSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        header = (TextView) findViewById(R.id.header);
        mList = (ListView) findViewById(R.id.listView);
        inputSearch = (EditText) findViewById(R.id.searchLive);


        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        sqlHelper = new ReciDbHelper(getApplicationContext());

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
        menu.add(0, CM_EDIT_ID, 0, "Редактировать");
        menu.add(0, CM_NEW_ID, 0, "Новое слово");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем инфу о пункте списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();

            db.delete(ReciContract.NewReci.TABLE_NAME, ReciContract.NewReci._ID + " = " + acmi.id, null);
            userCursor.requery();
            userAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"Запись удалена",Toast.LENGTH_SHORT).show();
            return true;

        }
        if (item.getItemId() == CM_EDIT_ID) {
            // получаем инфу о пункте списка
            Toast.makeText(getApplicationContext(),
                    "Удалить", Toast.LENGTH_SHORT).show();
            userAdapter.notifyDataSetChanged();
          /*  return true;*/
        }
        if (item.getItemId() == CM_NEW_ID) {
            // получаем инфу о пункте списка
            Intent intent = new Intent(Recipe_view.this, RecipeAdd.class);
            startActivity(intent);
          /*  return true;*/
        }
        return super.onContextItemSelected(item);

    }




    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = sqlHelper.getReadableDatabase();

        //получаем данные из бд
        userCursor = db.rawQuery("select * from " + ReciContract.NewReci.TABLE_NAME, null);
        String[] headers = new String[]{ReciContract.NewReci.COLUMN_TITLE, ReciContract.NewReci.COLUMN_RECIPE};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        header.setText("Рецептов в книге: " + String.valueOf(userCursor.getCount()));
        mList.setAdapter(userAdapter);
        registerForContextMenu(mList);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // Когда, юзер изменяет текст он работает
                userAdapter.getFilter().filter(cs.toString());

                userAdapter.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {

                if (constraint == null || constraint.length() == 0) {

                    return db.rawQuery("select * from " + ReciContract.NewReci.TABLE_NAME, null);
                }
                else {
                    return db.rawQuery("select * from " + ReciContract.NewReci.TABLE_NAME + " where " +
                            ReciContract.NewReci.COLUMN_TITLE + " like ?", new String[]{"%" + constraint.toString() + "%"});
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключения
        db.close();
        userCursor.close();
    }
}
