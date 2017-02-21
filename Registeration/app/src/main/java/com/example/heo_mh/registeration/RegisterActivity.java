package com.example.heo_mh.registeration;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
/**
    등록 액티비티
 */
public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID;
    private String userPassword;
    private String userGender;
    private String userMajor;
    private String userEmail;
    private AlertDialog dialog;
    private boolean validate = false;   // 중복 아이디


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 스피너 초기화
        spinner = (Spinner) findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // 입력값 변수 초기화
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        // 성별 데이터 얻어오기 (RadioGroup)
        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int genderGroupID =  genderGroup.getCheckedRadioButtonId(); // 해당 라디오 버튼의 ID 값
        userGender = ((RadioButton)findViewById(genderGroupID)).getText().toString();

        // 성별 체크마다 데이터 가져오는 리스너
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 체크할 때 마다 RadioButton ID값을 가져와서 입력값을 얻어온다.
                RadioButton genderButton = (RadioButton) findViewById(checkedId);
                userGender = genderButton.getText().toString();
            }
        });

        // 중복 아이디 확인
        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();

                // 등록할 수 있는 ID(서버에 같은 ID가 없다)일 경우 함수 종료
                if(validate){
                    return;
                }

                // 아이디를 입력하지 않았을 경우 다이얼로그 출력
                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("ID를 입력해 주세요!!!")
                                    .setPositiveButton("확인", null)
                                    .create();
                    dialog.show();
                    return;
                }

                // 중복 체크 리스너
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            // 서버로부터 JSON 문자열을 받아서 JSON 객체로 변환
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            // 사용 가능한 아이디일 경우
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                // 아이디값을 수정할 수 없도록 한다.
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();

                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                // 서버에 중복 아이디인지 확인 요청
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        // 등록 버튼
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();

                // 중복 체크가 되어 있지 않다면 함수 종료
                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요!!")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                // 각 칸이 빈 칸일 경우에도 함수 종료
                if(userID.equals("") || userPassword.equals("") || userMajor.equals("") || userEmail.equals("") || userGender.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요!!")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                // 등록 리스너
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            // 사용 가능한 아이디일 경우
                            if(success){
//                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                dialog = builder.setMessage("회원 등록에 성공했습니다.")
//                                        .setPositiveButton("확인", null)
//                                        .create();
//                                dialog.show();
                                Toast.makeText(getApplicationContext(), "회원 등록 성공 :)", Toast.LENGTH_SHORT).show();
                                finish();   // 회원 가입 창을 닫는다.

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();

                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                // 서버에 등록을 요청
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userMajor, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    /*
        회원 등록창이 끝나면 실행
     */
    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}
