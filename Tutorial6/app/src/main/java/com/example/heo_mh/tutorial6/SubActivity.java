package com.example.heo_mh.tutorial6;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Heo-MH on 2017-02-17.
 */

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        TextView nameText = (TextView) findViewById(R.id.nameText);
        Button backBtn = (Button) findViewById(R.id.backButton);

        // 메인 엑티비에서 넘어온 값을 받는다.
        final Intent intent = getIntent();
        nameText.setText(intent.getStringExtra("nameText").toString());

        // 뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 엑티비티를 종료
            }
        });

    }
}
