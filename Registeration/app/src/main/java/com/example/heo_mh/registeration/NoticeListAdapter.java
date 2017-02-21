package com.example.heo_mh.registeration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Heo-MH on 2017-02-21.
 * 공지 사항 리스트 어댑터
 */

public class NoticeListAdapter extends BaseAdapter{

    private Context context;
    private List<Notice> noticeList;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.notice, null);

        TextView noticeText = (TextView) v.findViewById(R.id.noticeText);
        TextView nameText = (TextView) v.findViewById(R.id.nameText);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);

        // 뷰에 값들을 삽입
        dateText.setText(noticeList.get(position).getDate());
        noticeText.setText(noticeList.get(position).getNotice());
        nameText.setText(noticeList.get(position).getName());
        // 태그 입력
        v.setTag(noticeList.get(position).getNotice());
        // 뷰 리턴
        return  v;
    }
}
