package com.example.heo_mh.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Heo-MH on 2017-02-21.
 * 로그인 요청
 */

public class LoginRequest extends StringRequest {
    final static private String URL = "http://121.137.217.100:3003/auth/login";
    private Map<String, String> parameters;


    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // POST 방식으로 웹서버에 전송
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}
