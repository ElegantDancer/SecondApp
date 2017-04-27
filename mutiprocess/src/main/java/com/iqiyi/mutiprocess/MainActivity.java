package com.iqiyi.mutiprocess;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    public static String TAG = "MainActivity";
    @BindView(R.id.text)
    TextView mTextview;
    private IBookManager iBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        findViewById(R.id.click_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.NUMBER = 2;
                TAG = "secondActivity";
                Intent intent = new Intent(MainActivity.this, BookService.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            Message message = handler.obtainMessage();
            message.obj = bookManager;
            handler.sendMessage(message);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            iBookManager = (IBookManager) msg.obj;
            try {
                iBookManager.addBook(new Book(3, "MainActivity"));
                List<Book> list = iBookManager.getBookList();
                Book book = list.get(0);
                mTextview.setText(book.getBookName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
}
