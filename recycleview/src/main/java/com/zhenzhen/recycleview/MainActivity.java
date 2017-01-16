package com.zhenzhen.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhenzhen.recycleview.Bean.Book;
import com.zhenzhen.recycleview.adapter.MyRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Book> list = new ArrayList<>();
        Book book;

        for(int i = 0; i< 20; i++){

            book = new Book();
            book.setImgId(R.mipmap.ic_launcher);
            book.setDes("title" + String.valueOf(i));
            book.setName("des" + String.valueOf(i));
            list.add(book);
        }

        rec = (RecyclerView) findViewById(R.id.rec);

        rec.setLayoutManager(new GridLayoutManager(this, 4));
        rec.setAdapter(new MyRecycleAdapter(this, list));
    }
}
