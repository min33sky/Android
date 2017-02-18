package com.example.heo_mh.tutorial9;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Heo-MH on 2017-02-18.
 */

public class Adapter extends PagerAdapter {

    private int[] images = {R.drawable.four, R.drawable.two, R.drawable.three, R.drawable.sana};
    private LayoutInflater inflater;
    private Context context;

    /*
        생성자
     */
    public Adapter(Context context) {
        this.context = context;
    }

    /*
        이미지의 개수 리턴
    */
    @Override
    public int getCount() {
        return images.length;
    }


    /*
        Object를 LinearLayout으로 변환했을 때 뷰와 같은지 확인
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout)object);
    }

    /*
        각각 아이템들을 인스턴스한다.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        imageView.setImageResource(images[position]);
        textView.setText((position + 1) + "번째 이미지입니다.");
        container.addView(v);
        return v;

    }

    /*
        아이템 객체 파괴 (할당 해제)
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
