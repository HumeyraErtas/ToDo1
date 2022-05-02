package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv = findViewById(R.id.apiData);
        if (isOnline()) {
            getUserList task = new getUserList();
            task.execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText username = findViewById(R.id.editUsername);
        EditText password = findViewById(R.id.editPassword);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("p369963") && password.getText().toString().equals("123123")){
                    Log.d("***MODE","Ebeveyn mod on !");
                    startActivity(new Intent(LoginActivity.this, Main2Activity.class));
                    finish();
                }
                else if (username.getText().toString().equals("c11") && password.getText().toString().equals("111"))
                {
                    Log.d("***MODE","Çocuk mod on !");
                    startActivity(new Intent(LoginActivity.this, Main3Activity.class));
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "BÖYLE BİR KULLANICI YOK !", Toast.LENGTH_LONG).show();
            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { username.setText(null);}
        });
    }

    private class getUserList extends AsyncTask<Void, Void, String> {
        org.json.JSONObject jsonO;
        org.json.JSONObject jObj;

        @Override
        protected String doInBackground(Void... voids) {
            ClassJSONParser jParser = new ClassJSONParser();
            String FinalURL = "http://dummy.restapiexample.com/api/v1/employees";
            //String FinalURL = "http://127.0.0.1:5000/users";
            jsonO = jParser.getJSONFromUrl(FinalURL);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(jsonO == null)
                Log.d("***Null value", "json0 is null !");
            else
            {
                Log.d("***JsonFromMethod", jsonO.toString());
                try {
                    JSONArray jsonArray = jsonO.getJSONArray("data");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String data ="NAME : " + jsonObject.getString("employee_name") + " - AGE : " + jsonObject.getString("employee_age");
                    tv.setText(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }

}