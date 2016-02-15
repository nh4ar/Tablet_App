package com.uva.inertia.besilite;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ben on 2/10/2016.
 */
public class JsonArrayRequestWithToken extends JsonArrayRequest {

    String token;

    public JsonArrayRequestWithToken(String url, String token, Listener listener, ErrorListener errorListener)
    {
        super(url, listener, errorListener);
        this.token = token;
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();
        headers.put("Authorization", "Token "+token);
        Log.v("DATA",headers.toString());
        return headers;
    }

}
