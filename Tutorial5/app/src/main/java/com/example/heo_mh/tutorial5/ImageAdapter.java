package com.example.heo_mh.tutorial5;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Heo-MH on 2017-02-16.
 */

public class ImageAdapter extends ArrayAdapter<String> {

    // Constructor
    public ImageAdapter(Context context, String[] items) {
        super(context, R.layout.image_layout, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // xml파일을 읽어서 view 객체를 생성
        LayoutInflater imageInflater = LayoutInflater.from(getContext());
        View view = imageInflater.inflate(R.layout.image_layout, parent, false);
        String item = getItem(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        // Set Item
        textView.setText(item);
        imageView.setImageResource(R.mipmap.gukbbong);
        return view;
    }
}
