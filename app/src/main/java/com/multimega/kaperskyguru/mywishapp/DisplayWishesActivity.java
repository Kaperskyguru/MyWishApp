package com.multimega.kaperskyguru.mywishapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.MyWish;

public class DisplayWishesActivity extends AppCompatActivity {
    private DatabaseHandler db = new DatabaseHandler(getApplicationContext());
    ;
    private ArrayList<MyWish> dbWishes = new ArrayList<>();
    private WishAdapter wishAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wishes);

        listView = (ListView) findViewById(R.id.list);

        refreshData();
    }

    private void refreshData() {
        //dbWishes.clear();


        ArrayList<MyWish> wishesFromDB = db.get_all_wishes();

        for (int i = 0; i < wishesFromDB.size(); i++) {
            String title = wishesFromDB.get(i).getTitle();
            String date = wishesFromDB.get(i).getRecordDate();
            String content = wishesFromDB.get(i).getContent();

            MyWish wish = new MyWish();
            wish.setTitle(title);
            wish.setContent(content);
            wish.setRecordDate(date);

            dbWishes.add(wish);

        }
        db.close();

        // setup Adapter

        wishAdapter = new WishAdapter(DisplayWishesActivity.this, R.layout.wish_row, dbWishes);
        listView.setAdapter(wishAdapter);
        wishAdapter.notifyDataSetChanged();
    }


    public class WishAdapter extends ArrayAdapter<MyWish> {
        Activity activity;
        int layoutresurce;
        MyWish wish;
        ArrayList<MyWish> mdata = new ArrayList<>();

        public WishAdapter(Activity act, int resource, ArrayList<MyWish> data) {
            super(act, resource, data);
            layoutresurce = resource;
            mdata = data;
            activity = act;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return mdata.size();
        }

        @Override
        public MyWish getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public int getPosition(MyWish item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            viewHolder holder = null;

            if (row == null || (row.getTag() == null)) {
                LayoutInflater inflater = LayoutInflater.from(activity);
                row = inflater.inflate(layoutresurce, null);
                holder = new viewHolder();

                holder.mTitle = (TextView) row.findViewById(R.id.myTitle);
                holder.mDate = (TextView) row.findViewById(R.id.dateText);

                row.setTag(holder);
            } else {
                holder = (viewHolder) row.getTag();
            }
            holder.myWish = getItem(position);
            holder.mTitle.setText(holder.myWish.getTitle());
            holder.mDate.setText(holder.myWish.getRecordDate());

            return row;
        }

        class viewHolder {
            MyWish myWish;
            TextView mTitle;
            TextView mContent;
            TextView mId;
            TextView mDate;
        }
    }
}