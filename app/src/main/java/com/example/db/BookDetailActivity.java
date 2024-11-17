package com.example.db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    TextView titleTextView, authorTextView;
    Button editButton, deleteButton;
    DatabaseHelper databaseHelper;
    Book selectedBook;

    @Override
    protected void onResume() {
        super.onResume();
        String updatedTitle = getIntent().getStringExtra("title");
        String updatedAuthor = getIntent().getStringExtra("author");

        titleTextView.setText(updatedTitle);
        authorTextView.setText(updatedAuthor);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        titleTextView = findViewById(R.id.titleTextView);
        authorTextView = findViewById(R.id.authorTextView);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        databaseHelper = new DatabaseHelper(this);

        // Получение данных из Intent
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        selectedBook = new Book(title, author);

        titleTextView.setText(title);
        authorTextView.setText(author);

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookDetailActivity.this, EditBookActivity.class);
            intent.putExtra("title", selectedBook.getTitle());
            intent.putExtra("author", selectedBook.getAuthor());
            startActivityForResult(intent, 1); // 1 — код запроса
        });


        deleteButton.setOnClickListener(v -> {
            databaseHelper.deleteBook(selectedBook.getTitle());
            Toast.makeText(BookDetailActivity.this, "Книга удалена!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(BookDetailActivity.this, MainActivity.class);
            startActivity(intent); // Переход в MainActivity
            finish(); // Закрываем текущую Activity
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String updatedTitle = data.getStringExtra("title");
            String updatedAuthor = data.getStringExtra("author");

            // Обновляем данные книги
            titleTextView.setText(updatedTitle);
            authorTextView.setText(updatedAuthor);

            selectedBook = new Book(updatedTitle, updatedAuthor);
            Toast.makeText(this, "Книга успешно обновлена!", Toast.LENGTH_SHORT).show();
        }
    }

}