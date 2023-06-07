package com.example.professorfinderclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new InfoAsyncTask().execute();
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String name = "";
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://aws.connect.psdb.cloud/professorfinder", "7h1h6gwlesio1mhr2dwv", "pscale_pw_GjGHKmif5lnUFb4Q2SvRopAOJJ75ve4UePzIViJsxjc")) {
                String sql = "SELECT * FROM user_cred WHERE username = '" + username.getText().toString() + "' AND password = '" + password.getText().toString() + "'";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    name = resultSet.getString("name");
                }
                resultSet.close();
                return name;
                

            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading school information", e);
            }

            return name;
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("")){
                Intent intent = new Intent(getApplicationContext(), page.class);
                intent.putExtra("name", result);
                startActivity(intent);
                return;
            }
            Toast.makeText(LoginActivity.this, "Login Failed, Please try again!", Toast.LENGTH_SHORT).show();
        }
    }
}