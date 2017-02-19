package com.example.heo_mh.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Heo-MH on 2017-02-19.
 */

public class DeleteRequest extends StringRequest{

    final static private String URL = "http://121.137.217.100:3003/auth/delete";
    private Map<String, String> parameters;

    public DeleteRequest(String userID, Response.Listener<String> listener) {
        // POST방식, 웹서버 Path, Resonse LIstener, Error Listener
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}
