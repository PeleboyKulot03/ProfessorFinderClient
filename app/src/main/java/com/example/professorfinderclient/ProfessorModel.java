package com.example.professorfinderclient;


import android.util.Log;

import java.net.IDN;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProfessorModel {
    private String name;
    private String image;
    private String roomLocation;

    private String time;
    private String comment;
    private String state;
    private String isPresent;
    private String startTime;
    private String endTime;
    //    private Connection connection;
    private String idNumber;
    //    private Statement statement;
    public ProfessorModel(){
//        connection = SqlDb.connection();
//        try {
//            statement = connection.createStatement();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }
    public ProfessorModel(String state, String time){
        this.time = time;
        this.state = state;
    }

    public ProfessorModel(String name, String image, String roomLocation, String isPresent, String idNumber, String startTime, String endTime, String comment) {
        this.name = name;
        this.image = image;
        this.roomLocation = roomLocation;
        this.isPresent = isPresent;
        this.idNumber = idNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;

    }

    public String getComment() {
        return comment;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTime() {
        return time;
    }

    public String getState() {
        return state;
    }
    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public String getIsPresent() {
        return isPresent;
    }


    public ArrayList<ProfessorModel> getData(){
        ArrayList<ProfessorModel> models = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://aws.connect.psdb.cloud/professorfinder", "jemivdj53akpu6jqntjz", "pscale_pw_d3cU2lZ5EJPDYt0yUSA2nqQZ6B47zyyPCQvhVJOnJ0v");
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM quick_information";

            try {
                ResultSet rs = statement.executeQuery(sql);

                // loop through the result set
                while (rs.next()) {
                    String name = rs.getString("name");
                    String roomLocation = rs.getString("roomLocation");
                    String isPresent = rs.getString("isPresent");
                    String idNumber = rs.getString("idNumber");
                    ProfessorModel model = new ProfessorModel(name, "", roomLocation, isPresent, idNumber, "", "", "");
                    models.add(model);
                    Log.i("TAG", "getData: " + model.getData());
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return models;
    }



}
