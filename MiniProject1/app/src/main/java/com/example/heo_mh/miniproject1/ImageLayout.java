package com.example.heo_mh.miniproject1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Heo-MH on 2017-02-17.
 */

public class ImageLayout extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.image_main, container, false);
        String[] items = {"오렌지 쥬스", "망고 쥬스", "파인애플 쥬스", "사과 쥬스", "딸기 쥬스"};

        /*
            Fragment에서 context를 가져올 땐 getActivity()를 사용한다.
         */
        ListAdapter adapter = new ImageAdapter(getActivity(), items);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭한 데이터를 가져와서 토스트로 출력
                String item = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
