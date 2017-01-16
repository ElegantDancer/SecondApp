package com.zhenzhen.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhenzhen.recycleview.Bean.Book;
import com.zhenzhen.recycleview.R;

import java.util.List;

/**
 * Created by zhenzhen on 2017/1/13.
 */

public class MyRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public MyRecycleAdapter(Context context, List<Book> mList) {
        this.mList = mList;
        this.mContext = context;
    }

    private List<Book> mList;
    private Context mContext;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.imageView.setImageResource(mList.get(position).getImgId());
        viewHolder.titleTex.setText(mList.get(position).getName());
        viewHolder.desTex.setText(mList.get(position).getDes());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView titleTex;
        private TextView desTex;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.rec_img);
            titleTex = (TextView) itemView.findViewById(R.id.linear_1);
            desTex = (TextView) itemView.findViewById(R.id.linear_2);
        }
    }
}
