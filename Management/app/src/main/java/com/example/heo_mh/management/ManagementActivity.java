package com.example.heo_mh.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;    // 원본 리스트
    private List<User> saveList;    // 사본 리스트 : 검색 기능을 위해 원본의 값을 저장해 놓는 용도


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        // LoginActivity에서 값을 받아오기 위한 준비
        Intent intent = getIntent();

        // 초기화
        listView = (ListView) findViewById(R.id.listView);
        userList = new ArrayList<User>();
        saveList = new ArrayList<User>();
        adapter = new UserListAdapter(getApplicationContext(), userList, this, saveList);
        listView.setAdapter(adapter);

        /*
            회원 목록을 출력
         */
        try{
            // string을 JSON Object로 파싱
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            // JSON 배열 형태값을 받아옴
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID, userPassword, userName, userAge;

            while(count < jsonArray.length()){
                // JSON 배열에서 Json 객체 하나를 가져온다.
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userPassword = object.getString("userPassword");
                userName = object.getString("userName");
                userAge = object.getString("userAge");
                /*
                    관리자는 목록에서 보여주지 않는다.
                 */
                if(!userID.equals("admin")){
                    userList.add( new User(userID, userPassword, userName, userAge));
                    saveList.add( new User(userID, userPassword, userName, userAge));
                }
                count++;
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        /*
            검색 기능 설정
         */
        final EditText search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // 텍스트가 바뀔 때 마다 함수 실행
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /*
        원본 리스트를 다 삭제 후
        사본 리스트에서 검색 조건과 맞는 값을
        원본 리스트에 추가한다.
     */
    public void searchUser(String search){
        userList.clear();
        for(int i=0; i<saveList.size(); i++) {
            if (saveList.get(i).getUserID().contains(search)) {
                userList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged(); // ListView Refresh
    }
}
