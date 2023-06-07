package com.example.professorfinderclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private LinearLayout emptyState;
    private TextView name, roomNo, time, remarks, comment;
    private ImageView send;
    private EditText addComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        name = findViewById(R.id.professorName);
//        roomNo = findViewById(R.id.roomNo);
//        time = findViewById(R.id.time);
//        remarks = findViewById(R.id.remarks);
//        comment = findViewById(R.id.comment);
//        send = findViewById(R.id.send);
//        addComment = findViewById(R.id.addComment);
//
//
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (addComment.getText().toString().length() < 10){
//                    Toast.makeText(getApplicationContext(), "add more comment please!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (addComment.getText().toString().length() > 100){
//                    Toast.makeText(getApplicationContext(), "can't add more than 100 characters", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                new send().execute();
//                send.setEnabled(false);
//                send.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
//                comment.setText(addComment.getText().toString());
//            }
//        });
//        new InfoAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncTask extends AsyncTask<Void, Void, ProfessorModel> {
        @Override
        protected ProfessorModel doInBackground(Void... voids) {
            ProfessorModel model = null;
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://aws.connect.psdb.cloud/professorfinder", "7h1h6gwlesio1mhr2dwv", "pscale_pw_GjGHKmif5lnUFb4Q2SvRopAOJJ75ve4UePzIViJsxjc")) {
                String sql = "SELECT * FROM quick_information WHERE name = 'Arjo R. Ladia' AND roomLocation != 'Vacant';";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    String roomLocation = resultSet.getString("roomLocation");
                    String isPresent = resultSet.getString("isPresent");
                    String idNumber = resultSet.getString("idNumber");
                    String startTime = resultSet.getString("startTime");
                    String endTime = resultSet.getString("endTime");
                    String comment = resultSet.getString("comment");
                    model = new ProfessorModel(name, "", roomLocation, isPresent, idNumber, startTime, endTime, comment);
                }
                resultSet.close();
                return model;

            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading school information", e);
            }

            return new ProfessorModel();
        }

        @Override
        protected void onPostExecute(ProfessorModel result) {
            if (result != null && !result.getName().isEmpty()){
//                progressBar.setVisibility(View.GONE);
//                emptyState.setVisibility(View.INVISIBLE);
                name.setText(result.getName());
                remarks.setText(result.getIsPresent());
                String timeText = result.getStartTime() + " - " + result.getEndTime();
                time.setText(timeText);
                comment.setText(result.getComment());
                roomNo.setText(result.getRoomLocation());
                if (!result.getComment().equals("No Comment Yet")){
                    send.setEnabled(false);
                    send.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
            }
//            emptyState.setVisibility(View.VISIBLE);
//            progressBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class send extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://aws.connect.psdb.cloud/professorfinder", "jemivdj53akpu6jqntjz", "pscale_pw_d3cU2lZ5EJPDYt0yUSA2nqQZ6B47zyyPCQvhVJOnJ0v")) {
                String sql = "UPDATE quick_information SET comment = ? WHERE name = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, addComment.getText().toString());
                statement.setString(2, "Arjo R. Ladia");
                statement.executeUpdate();
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading school information", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(), "Adding comments Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}