package com.example.heo_mh.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Heo-MH on 2017-02-20.
 * 이미 존재하는 회원인지 요청
 */

public class ValidateRequest extends StringRequest {
    final static private String URL = "http://121.137.217.100:3003/auth/registerValidate";
    private Map<String, String> parameters;


    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // POST 방식으로 웹서버에 전송
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}
