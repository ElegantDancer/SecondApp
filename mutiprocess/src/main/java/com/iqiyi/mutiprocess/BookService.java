package com.iqiyi.mutiprocess;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenzhen on 2017/2/15.
 */

public class BookService extends Service {

    private List<Book> mList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    private Binder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mList.add(new Book(1, "Android开发艺术探讨"));
            mList.add(new Book(2, "Android群英传"));
            mList.add(new Book(3, "疯狂Android讲义"));
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}
