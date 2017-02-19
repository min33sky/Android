package com.example.heo_mh.management;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText ageText = (EditText) findViewById(R.id.ageText);
        Button registerButton = (Button) findViewById(R.id.registerButton);

        // 들록 버튼
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String userID = idText.getText().toString();
                    String userPassword = passwordText.getText().toString();
                    String userName = nameText.getText().toString();
                    int userAge = Integer.parseInt(ageText.getText().toString());

                    Response.Listener<String> resStringListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                // 웹서버로부터 JSON 객체를 받아온다.
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                // 등록 성공시 액티비티 전환
                                if(success){
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                    builder.setMessage("회원 등록에 성공했습니다.")
//                                            .setPositiveButton("확인", null)
//                                            .create()
//                                            .show();

                                    Toast.makeText(RegisterActivity.this, "회원 등록 성공", Toast.LENGTH_SHORT).show();

//                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                    RegisterActivity.this.startActivity(intent);
                                    finish();

                                }else{
                                    // 등록 실패시 다이얼로그 출력
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("회원 등록에 실패했습니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    // 웹서버로 데이터 전송
                    RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, resStringListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);

                }catch(NumberFormatException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("나이에는 숫자를 넣으세요 :)")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                }

            }
        });
    }
}
