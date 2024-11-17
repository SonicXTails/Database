package com.example.db;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    EditText titleEditText, authorEditText;
    Button addBookButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        titleEditText = findViewById(R.id.titleEditText);
        authorEditText = findViewById(R.id.authorEditText);
        addBookButton = findViewById(R.id.addBookButton);

        databaseHelper = new DatabaseHelper(this);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String author = authorEditText.getText().toString();

                if (title.isEmpty() || author.isEmpty()) {
                    Toast.makeText(AddBookActivity.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.insertBook(title, author);
                    Toast.makeText(AddBookActivity.this, "Книга добавлена!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}