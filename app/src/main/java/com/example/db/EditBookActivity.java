package com.example.db;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;


import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {

    EditText titleEditText, authorEditText;
    Button saveButton;
    DatabaseHelper databaseHelper;
    String oldTitle;

    private void updateBook(String oldTitle, String newTitle, String newAuthor) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", newTitle);
        values.put("author", newAuthor);
        db.update("books", values, "title=?", new String[]{oldTitle});
        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        titleEditText = findViewById(R.id.titleEditText);
        authorEditText = findViewById(R.id.authorEditText);
        saveButton = findViewById(R.id.saveButton);

        databaseHelper = new DatabaseHelper(this);

        // Получение данных из Intent
        oldTitle = getIntent().getStringExtra("title");
        String oldAuthor = getIntent().getStringExtra("author");

        titleEditText.setText(oldTitle);
        authorEditText.setText(oldAuthor);

        saveButton.setOnClickListener(v -> {
            String newTitle = titleEditText.getText().toString();
            String newAuthor = authorEditText.getText().toString();

            if (newTitle.isEmpty() || newAuthor.isEmpty()) {
                Toast.makeText(EditBookActivity.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            } else {
                updateBook(oldTitle, newTitle, newAuthor);

                Toast.makeText(EditBookActivity.this, "Книга обновлена!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditBookActivity.this, MainActivity.class);
                startActivity(intent); // Переход в MainActivity
                finish(); // Закрываем EditBookActivity
            }
        });
    }
}