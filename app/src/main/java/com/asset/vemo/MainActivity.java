package com.asset.vemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    Button scanBtn, viewBtn, btn, recyclerBtn; //SCAN BUTTON & LIST VIEW BUTTON
    TextView textView;
//    private TextView mTextViewResult;
//    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button) findViewById(R.id.scanBtn); //SCAN BUTTON
        viewBtn = (Button) findViewById(R.id.viewBtn); //LIST VIEW BUTTON
        btn = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textView);
//        mTextViewResult = findViewById(R.id.text_view_result);
//        Button buttonParse = findViewById(R.id.button_parse);
//
//        mQueue = Volley.newRequestQueue(this);
//
//        buttonParse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jsonParse();
//            }
//        });
//

        //SCAN BUTTON
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), scanner.class));
            }
        });
//LIST VIEW BUTTON
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, visitor_list.class);
                startActivity(intent);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://services.hanselandpetal.com/feeds/flowers.json";
                new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        String str = new String(responseBody);
                        textView.setText(str);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        textView.setText("Error in calling api");
                    }
                });
            }
        });
    }

//    private void jsonParse() {
//
//        String url = "http://www.json-generator.com/api/json/get/coSAJfKkPm?indent=2";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("employees");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject employee = jsonArray.getJSONObject(i);
//                                String eid = employee.getString("eid");
//                                //String firstname = employee.getString("firstname");
//                                //int age = employee.getInt("age");
//                                //String mail = employee.getString("mail");
//
//                                mTextViewResult.append(eid);
//                                //original code:
//                                //mTextViewResult.append(firstname + ", " + String.valueOf(age) + ", " + mail + "\n\n");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        mQueue.add(request);
//    }
}
