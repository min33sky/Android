package com.example.heo_mh.miniproject1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Heo-MH on 2017-02-17.
 * 이미지 어댑터
 */

public class ImageAdapter extends ArrayAdapter<String> {

    public ImageAdapter(Context context, String[] items) {
        super(context, R.layout.image_layout, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflater를 이용해서 뷰를 생성
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.image_layout, parent, false);

        String item = getItem(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView2);

        textView.setText(item);
        imageView.setImageResource(R.mipmap.chul9);

        return view;
    }
}
