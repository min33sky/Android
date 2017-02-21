package com.example.heo_mh.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Heo-MH on 2017-02-20.
 * 회원 가입 요청
 */

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://121.137.217.100:3003/auth/register";
    private Map<String, String> parameters;


    public RegisterRequest(String userID, String userPassword, String userGender, String userMajor, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // POST 방식으로 웹서버에 전송
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userGender", userGender);
        parameters.put("userMajor", userMajor);
        parameters.put("userEmail", userEmail);
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}
