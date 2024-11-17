package com.example.db;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addBookButton;
    BookAdapter bookAdapter;
    ArrayList<Book> bookList;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addBookButton = findViewById(R.id.addBookButton);

        databaseHelper = new DatabaseHelper(this);
        bookList = new ArrayList<>();

        loadBooks();

        // Создаем адаптер с обработчиком клика
        bookAdapter = new BookAdapter(this, bookList, new BookAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(Book book) {
                Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
                intent.putExtra("title", book.getTitle());
                intent.putExtra("author", book.getAuthor());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadBooks() {
        Cursor cursor = databaseHelper.getAllBooks();
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1); // Индекс колонки title
                String author = cursor.getString(2); // Индекс колонки author
                bookList.add(new Book(title, author));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookList.clear();
        loadBooks();
        bookAdapter.notifyDataSetChanged();
    }
}