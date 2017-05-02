package com.example.jenia.recipe;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jenia.recipe.data.ReciDbHelper;
import com.example.jenia.recipe.data.ReciContract;



public class RecipeAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);
    }


    public void onClickSave(View view) {
        insertWords();
        // Закрываем активность
        finish();
    }

    private void insertWords() {
        // Считываем данные из текстовых полей
        EditText mWordEditText = (EditText) findViewById(R.id.editTextTitle);
        EditText mTansEditText = (EditText) findViewById(R.id.editTextRecipe);
        EditText mUnitEditText = (EditText) findViewById(R.id.tvCategory);
        String title = mWordEditText.getText().toString().trim();
        String recipe = mTansEditText.getText().toString().trim();
        String category = mUnitEditText.getText().toString().trim();

        ReciDbHelper mDbHelper = new ReciDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ReciContract.NewReci.COLUMN_TITLE, title);
        values.put(ReciContract.NewReci.COLUMN_RECIPE, recipe);
        values.put(ReciContract.NewReci.COLUMN_CATEGORY, category);



        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        long newRowId = db.insert(ReciContract.NewReci.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при заведении слова", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Слово заведено под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }


}


