package com.example.anujdawar.allinclusive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    public static RoomDetailsDatabase roomDetailsDatabase;
    public static Room1SavedCommands room1SavedCommands;
    public static Room2SavedCommands room2SavedCommands;
    public static Room3SavedCommands room3SavedCommands;
    public static Room4SavedCommands room4SavedCommands;
    public static Room5SavedCommands room5SavedCommands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setTitle("");

        roomDetailsDatabase = new RoomDetailsDatabase(this);
        room1SavedCommands = new Room1SavedCommands(this);
        room2SavedCommands = new Room2SavedCommands(this);
        room3SavedCommands = new Room3SavedCommands(this);
        room4SavedCommands = new Room4SavedCommands(this);
        room5SavedCommands = new Room5SavedCommands(this);

        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);

        String Value1 = shared.getString("Value1", "");
        String Value2 = shared.getString("Value2", "");
        String Value3 = shared.getString("Value3", "");
        String Value4 = shared.getString("Value4", "");
        String Value5 = shared.getString("Value5", "");
        String RoomActiveTemp = shared.getString("RoomActive", "");
        String notiEnable = shared.getString("notificationEnable","");
        String serviceActive = shared.getString("serviceActive","");

        SharedPreferences.Editor editor = shared.edit();

        if(RoomActiveTemp.equals(""))
            editor.putString("RoomActive", "0");

        if(notiEnable.equals(""))
            editor.putString("notificationEnable","0");
        if(serviceActive.equals(""))
            editor.putString("serviceActive","0");
        if(Value1.equals(""))
            editor.putString("Value1", "0");
        if(Value2.equals(""))
            editor.putString("Value2", "0");
        if(Value3.equals(""))
            editor.putString("Value3", "0");
        if(Value4.equals(""))
            editor.putString("Value4", "0");
        if(Value5.equals(""))
            editor.putString("Value5", "0");

        editor.apply();

        Thread kinda = new Thread(new Runnable() {
            @Override
            public void run() {

                Cursor res = roomDetailsDatabase.viewAllData();

                if(res.getCount() != 5)
                {
                    roomDetailsDatabase.insertData("1",               // room number
                            "", "", "",                         // room_name, ip, port
                            "", "", "", "", "",                 // device names
                            "false", "false", "false", "false", "false");   // true/false is checkbox status)

                    roomDetailsDatabase.insertData("2",               // room number
                            "", "", "",                         // room_name, ip, port
                            "", "", "", "", "",                 // device names
                            "false", "false", "false", "false", "false");   // true/false is checkbox status)

                    roomDetailsDatabase.insertData("3",               // room number
                            "", "", "",                         // room_name, ip, port
                            "", "", "", "", "",                 // device names
                            "false", "false", "false", "false", "false");   // true/false is checkbox status)

                    roomDetailsDatabase.insertData("4",               // room number
                            "", "", "",                         // room_name, ip, port
                            "", "", "", "", "",                 // device names
                            "false", "false", "false", "false", "false");   // true/false is checkbox status)

                    roomDetailsDatabase.insertData("5",               // room number
                            "", "", "",                         // room_name, ip, port
                            "", "", "", "", "",                 // device names
                            "false", "false", "false", "false", "false");   // true/false is checkbox status)
                }

                res.close();
            }
        });
        kinda.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);

                if(shared.getString("serviceActive","").equals("1"))
                {
                    Intent devicesIntent = new Intent(SplashScreen.this, Devices_Activity.class);
                    startActivity(devicesIntent);
                }
                else {
                    Intent homeIntent = new Intent(SplashScreen.this, RoomSelectActivity.class);
                    startActivity(homeIntent);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}