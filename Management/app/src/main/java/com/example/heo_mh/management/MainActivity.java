package com.example.heo_mh.management;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView idText = (TextView) findViewById(R.id.idText);
        TextView passwordText = (TextView) findViewById(R.id.passwordText);
        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        Button managementButton = (Button) findViewById(R.id.managementButton);

        /*
            인텐트로 넘어온 값을 받아온다.
         */
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");
        String message = "환엽합니다. " + userID + "님!";

        /*
            값 세팅 후 출력
         */
        idText.setText(userID);
        passwordText.setText(userPassword);
        welcomeMessage.setText(message);

        /*
           ID가 admin이 아니면 버튼 비활성화
        */
        if(!userID.equals("admin")){
//            managementButton.setEnabled(false); // 버튼을 클릭 못하게 함
            managementButton.setVisibility(View.GONE); // 버튼을 안보이게 함
        }

        /*
            management버튼 클릭시 서버에서 데이터를 가져온다.
         */
        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
    }

    /*
        회원 리스트 가져오기
    */
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        /*
            초기화
         */
        @Override
        protected void onPreExecute() {
            target = "http://121.137.217.100:3003/auth/list";
        }

        /*
            Background에서 데이터 읽어오기
         */
        @Override
        protected String doInBackground(Void... params) {
            try{
                URL url = new URL(target);
                /*
                    웹 파싱
                    : BufferedReader를 이용해서 한 줄씩 읽어온 후 StringBuilder를 이용해 합친다.
                 */
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /*
            Background에서 읽어온 데이터를 가져와서 활용
         */
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
            intent.putExtra("userList", result);
            MainActivity.this.startActivity(intent);
        }


    }
}
