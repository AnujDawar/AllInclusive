package com.example.anujdawar.allinclusive;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.anujdawar.allinclusive.SplashScreen.room1SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room2SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room3SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room4SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room5SavedCommands;
import static com.example.anujdawar.allinclusive.saved_commands_activity.EditFlag;

public class Devices_Activity extends AppCompatActivity {

    Handler h1 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            resultString += msg.obj.toString();

            if (resultString.contains("SET:")) {

                int myindex = resultString.indexOf(":");

                if (resultString.length() == myindex + 6) {

                    setFlag = 0;

                    if(type1.contains("true"))
                        seek1.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 1))));

                    else if (String.valueOf(resultString.charAt(myindex + 1)).equals("0"))
                        device1.setChecked(false);
                    else
                        device1.setChecked(true);

                    if(type2.contains("true"))
                        seek2.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 2))));
                    else if (String.valueOf(resultString.charAt(myindex + 2)).equals("0"))
                        device2.setChecked(false);
                    else
                        device2.setChecked(true);

                    if(type3.contains("true"))
                        seek3.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 3))));
                    else if (String.valueOf(resultString.charAt(myindex + 3)).equals("0"))
                        device3.setChecked(false);
                    else
                        device3.setChecked(true);

                    if(type4.contains("true"))
                        seek4.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 4))));
                    else if (String.valueOf(resultString.charAt(myindex + 4)).equals("0"))
                        device4.setChecked(false);
                    else
                        device4.setChecked(true);

                    if(type5.contains("true"))
                        seek5.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 5))));
                    else if (String.valueOf(resultString.charAt(myindex + 5)).equals("0"))
                        device5.setChecked(false);
                    else
                        device5.setChecked(true);

                    state1 = Integer.parseInt(String.valueOf(resultString.charAt(myindex + 1)));
                    state2 = Integer.parseInt(String.valueOf(resultString.charAt(myindex + 2)));
                    state3 = Integer.parseInt(String.valueOf(resultString.charAt(myindex + 3)));
                    state4 = Integer.parseInt(String.valueOf(resultString.charAt(myindex + 4)));
                    state5 = Integer.parseInt(String.valueOf(resultString.charAt(myindex + 5)));

                    resultString = "";
                    setFlag = 1;
                }
            }
        }
    };

    Handler setButtonStatesHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            String a = shared.getString("Value1", "");
            String b = shared.getString("Value2", "");
            String c = shared.getString("Value3", "");
            String d = shared.getString("Value4", "");
            String e = shared.getString("Value5", "");

            state1 = Integer.parseInt(a);
            state2 = Integer.parseInt(b);
            state3 = Integer.parseInt(c);
            state4 = Integer.parseInt(d);
            state5 = Integer.parseInt(e);

            if(type1.contains("true"))
                seek1.setProgress(Integer.parseInt(a));
            else if (a.equals("0"))
                device1.setChecked(false);
            else
                device1.setChecked(true);

            if(type2.contains("true"))
                seek2.setProgress(Integer.parseInt(b));
            else if (b.equals("0"))
                device2.setChecked(false);
            else
                device2.setChecked(true);

            if(type3.contains("true"))
                seek3.setProgress(Integer.parseInt(c));
            else if (c.equals("0"))
                device3.setChecked(false);
            else
                device3.setChecked(true);

            if(type4.contains("true"))
                seek4.setProgress(Integer.parseInt(d));
            else if (d.equals("0"))
                device4.setChecked(false);
            else
                device4.setChecked(true);

            if(type5.contains("true"))
                seek5.setProgress(Integer.parseInt(e));
            else if (e.equals("0"))
                device5.setChecked(false);
            else
                device5.setChecked(true);
        }
    };

    Handler tempH = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(Devices_Activity.this, "exception", Toast.LENGTH_SHORT).show();
        }
    };

    private String RoomName = "";
    private static String resultString;
    private static int setFlag = 1;
    private SeekBar seek1, seek2, seek3, seek4, seek5;
    private TextView nameSeek1, nameSeek2, nameSeek3, nameSeek4, nameSeek5;
    protected static Switch device1, device2, device3, device4, device5;
    private static String ipAddress;
    private static String port;
    public static String dev1Name, type1;
    public static String dev2Name, type2;
    public static String dev3Name, type3;
    public static String dev4Name, type4;
    public static String dev5Name, type5;
    static SharedPreferences shared;
    int state1, state2, state3, state4, state5;

    private TextView myTextResult;
    protected MediaPlayer Settings_mediaPLayer;
    private static String textToConvert = "", convertedText = "";
    public static Activity fa;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.devices_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settings_id) {
            EditFlag = false;

            Settings_mediaPLayer = MediaPlayer.create(this, R.raw.slip);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        Intent intent1 = new Intent(Devices_Activity.this, TheService.class);
                        stopService(intent1);
                        Intent homeIntent = new Intent(Devices_Activity.this, MainActivity.class);
                        startActivity(homeIntent);
                    }catch (Exception e)
                    {
                        Toast.makeText(Devices_Activity.this, "Exception caught here", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }, 0);
        } else if (item.getItemId() == R.id.mic_id) {
             promptSpeechRecognition();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fa = this;

        setContentView(R.layout.activity_devices_);

        android.support.v7.app.ActionBar devicesActionBar = getSupportActionBar();
        if (devicesActionBar != null) {
            devicesActionBar.setDisplayUseLogoEnabled(true);
        }
        if (devicesActionBar != null) {
            devicesActionBar.setDisplayShowHomeEnabled(true);
        }
        assert devicesActionBar != null;

        Settings_mediaPLayer = MediaPlayer.create(this, R.raw.slip);

        defineIDs();
        setNamesOfDevices();
        alignDevices();
        setTitle(RoomName);

        setListenersOnDevices();

        shared = getSharedPreferences("MyPref", MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();
        editor.putString("notificationEnable","0");
        editor.commit();

        setButtonStatesHandler.sendEmptyMessage(0);

        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);

        if(shared.getString("serviceActive", "").equals("0")) {

            Thread servicestartthread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Intent intent1 = new Intent(Devices_Activity.this, TheService.class);
                        startService(intent1);
                    } catch (Exception e) {
                        Toast.makeText(Devices_Activity.this, "exception caught here", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            servicestartthread.start();
        }

        Thread syncButtonsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    setButtonStatesHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        syncButtonsThread.start();
    }

    public void alignDevices() {

        device1.setText(dev1Name);
        device2.setText(dev2Name);
        device3.setText(dev3Name);
        device4.setText(dev4Name);
        device5.setText(dev5Name);

        nameSeek1.setText(dev1Name);
        nameSeek2.setText(dev2Name);
        nameSeek3.setText(dev3Name);
        nameSeek4.setText(dev4Name);
        nameSeek5.setText(dev5Name);

        device1.setEnabled(false);
        device2.setEnabled(false);
        device3.setEnabled(false);
        device4.setEnabled(false);
        device5.setEnabled(false);

        seek1.setEnabled(false);
        seek2.setEnabled(false);
        seek3.setEnabled(false);
        seek4.setEnabled(false);
        seek5.setEnabled(false);

        seek1.setVisibility(View.INVISIBLE);
        seek2.setVisibility(View.INVISIBLE);
        seek3.setVisibility(View.INVISIBLE);
        seek4.setVisibility(View.INVISIBLE);
        seek5.setVisibility(View.INVISIBLE);

        nameSeek1.setVisibility(View.INVISIBLE);
        nameSeek2.setVisibility(View.INVISIBLE);
        nameSeek3.setVisibility(View.INVISIBLE);
        nameSeek4.setVisibility(View.INVISIBLE);
        nameSeek5.setVisibility(View.INVISIBLE);

        device1.setVisibility(View.INVISIBLE);
        device2.setVisibility(View.INVISIBLE);
        device3.setVisibility(View.INVISIBLE);
        device4.setVisibility(View.INVISIBLE);
        device5.setVisibility(View.INVISIBLE);

        if (dev1Name.equals("")) {
            device1.setVisibility(View.GONE);
            seek1.setVisibility(View.GONE);
            nameSeek1.setVisibility(View.GONE);
        }

        else if(type1.contains("true"))
        {
            seek1.setVisibility(View.VISIBLE);
            nameSeek1.setVisibility(View.VISIBLE);
            seek1.setEnabled(true);
        }

        else
        {
            device1.setVisibility(View.VISIBLE);
            device1.setEnabled(true);
        }

        if (dev2Name.equals("")) {
            device2.setVisibility(View.GONE);
            seek2.setVisibility(View.GONE);
            nameSeek2.setVisibility(View.GONE);
        }

        else if(type2.contains("true"))
        {
            seek2.setVisibility(View.VISIBLE);
            nameSeek2.setVisibility(View.VISIBLE);
            seek2.setEnabled(true);
        }
        else
        {
            device2.setVisibility(View.VISIBLE);
            device2.setEnabled(true);
        }

        if (dev3Name.equals("")) {
            device3.setVisibility(View.GONE);
            seek3.setVisibility(View.GONE);
            nameSeek3.setVisibility(View.GONE);
        }

        else if(type3.contains("true"))
        {
            seek3.setVisibility(View.VISIBLE);
            nameSeek3.setVisibility(View.VISIBLE);
            seek3.setEnabled(true);
        }
        else
        {
            device3.setVisibility(View.VISIBLE);
            device3.setEnabled(true);
        }

        if (dev4Name.equals("")) {
            device4.setVisibility(View.GONE);
            seek4.setVisibility(View.GONE);
            nameSeek4.setVisibility(View.GONE);
        }

        else if(type4.contains("true"))
        {
            seek4.setVisibility(View.VISIBLE);
            nameSeek4.setVisibility(View.VISIBLE);
            seek4.setEnabled(true);
        }
        else
        {
            device4.setVisibility(View.VISIBLE);
            device4.setEnabled(true);
        }

        if (dev5Name.equals("")) {
            device5.setVisibility(View.GONE);
            seek5.setVisibility(View.GONE);
            nameSeek5.setVisibility(View.GONE);
        }

        else if(type5.contains("true"))
        {
            seek5.setVisibility(View.VISIBLE);
            nameSeek5.setVisibility(View.VISIBLE);
            seek5.setEnabled(true);
        }
        else
        {
            device5.setVisibility(View.VISIBLE);
            device5.setEnabled(true);
        }
    }

    public void defineIDs() {

        device1 = (Switch) findViewById(R.id.switch1);
        device2 = (Switch) findViewById(R.id.switch2);
        device3 = (Switch) findViewById(R.id.switch3);
        device4 = (Switch) findViewById(R.id.switch4);
        device5 = (Switch) findViewById(R.id.switch5);

        seek1 = (SeekBar) findViewById(R.id.seekBar1);
        seek2 = (SeekBar) findViewById(R.id.seekBar2);
        seek3 = (SeekBar) findViewById(R.id.seekBar3);
        seek4 = (SeekBar) findViewById(R.id.seekBar4);
        seek5 = (SeekBar) findViewById(R.id.seekBar5);

        nameSeek1 = (TextView) findViewById(R.id.seekName1);
        nameSeek2 = (TextView) findViewById(R.id.seekName2);
        nameSeek3 = (TextView) findViewById(R.id.seekName3);
        nameSeek4 = (TextView) findViewById(R.id.seekName4);
        nameSeek5 = (TextView) findViewById(R.id.seekName5);

        myTextResult = (TextView) findViewById(R.id.myResult);
    }

    public void setListenersOnDevices() {

        device1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (setFlag == 1) {
                    if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    rightStatesToPref(String.valueOf(state1), String.valueOf(state2), String.valueOf(state3), String.valueOf(state4), String.valueOf(state5));
                }
            }
        });

        device2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    rightStatesToPref(String.valueOf(state1), String.valueOf(state2), String.valueOf(state3), String.valueOf(state4), String.valueOf(state5));
            }
        });

        device3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    rightStatesToPref(String.valueOf(state1), String.valueOf(state2), String.valueOf(state3), String.valueOf(state4), String.valueOf(state5));
            }
        });

        device4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (device4.isChecked())
                    state4 = 4;
                else
                    state4 = 0;

                    rightStatesToPref(String.valueOf(state1), String.valueOf(state2), String.valueOf(state3), String.valueOf(state4), String.valueOf(state5));
            }
        });

        device5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (device5.isChecked())
                    state5 = 4;
                else
                    state5 = 0;

                rightStatesToPref(String.valueOf(state1), String.valueOf(state2), String.valueOf(state3), String.valueOf(state4), String.valueOf(state5));

            }
        });

        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                state1 = i;
                rightStatesToPref(String.valueOf(state1), String.valueOf(state2), String.valueOf(state3), String.valueOf(state4), String.valueOf(state5));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                state2 = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                }

        });

        seek3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                state3 = i;
                rightStatesToPref(String.valueOf(state1), String.valueOf(state2), String.valueOf(state3), String.valueOf(state4), String.valueOf(state5));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seek4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                state4 = i;
                rightStatesToPref(String.valueOf(state1), String.valueOf(state2), String.valueOf(state3), String.valueOf(state4), String.valueOf(state5));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seek5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int yostate;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                state5 = i;
                rightStatesToPref(String.valueOf(state1), String.valueOf(state2), String.valueOf(state3), String.valueOf(state4), String.valueOf(state5));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void setNamesOfDevices() {

        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);

        RoomName = shared.getString("RoomName", "");
        ipAddress = shared.getString("IP_Address", "");
        port = shared.getString("Port", "");
        dev1Name = shared.getString("Device1Name", "");
        dev2Name = shared.getString("Device2Name", "");
        dev3Name = shared.getString("Device3Name", "");
        dev4Name = shared.getString("Device4Name", "");
        dev5Name = shared.getString("Device5Name", "");
        type1 = shared.getString("Type1", "");
        type2 = shared.getString("Type2", "");
        type3 = shared.getString("Type3", "");
        type4 = shared.getString("Type4", "");
        type5 = shared.getString("Type5", "");


//        Cursor cursor = roomDetailsDatabase.viewAllData();
//
//        switch (RoomActive) {
//
//            case 1:
//
//                cursor.moveToPosition(0);
//
//                RoomName = cursor.getString(1);
//                ipAddress = cursor.getString(2);
//                port = cursor.getString(3);
//                dev1Name = cursor.getString(4);
//                dev2Name = cursor.getString(5);
//                dev3Name = cursor.getString(6);
//                dev4Name = cursor.getString(7);
//                dev5Name = cursor.getString(8);
//                type1 = cursor.getString(9);
//                type2 = cursor.getString(10);
//                type3 = cursor.getString(11);
//                type4 = cursor.getString(12);
//                type5 = cursor.getString(13);
//
//                cursor.close();
//
//                break;
//
//            case 2:
//
//                cursor.moveToPosition(1);
//
//                RoomName = cursor.getString(1);
//                ipAddress = cursor.getString(2);
//                port = cursor.getString(3);
//                dev1Name = cursor.getString(4);
//                dev2Name = cursor.getString(5);
//                dev3Name = cursor.getString(6);
//                dev4Name = cursor.getString(7);
//                dev5Name = cursor.getString(8);
//                type1 = cursor.getString(9);
//                type2 = cursor.getString(10);
//                type3 = cursor.getString(11);
//                type4 = cursor.getString(12);
//                type5 = cursor.getString(13);
//
//                cursor.close();
//
//                break;
//
//            case 3:
//
//                cursor.moveToPosition(2);
//
//                RoomName = cursor.getString(1);
//                ipAddress = cursor.getString(2);
//                port = cursor.getString(3);
//                dev1Name = cursor.getString(4);
//                dev2Name = cursor.getString(5);
//                dev3Name = cursor.getString(6);
//                dev4Name = cursor.getString(7);
//                dev5Name = cursor.getString(8);
//                type1 = cursor.getString(9);
//                type2 = cursor.getString(10);
//                type3 = cursor.getString(11);
//                type4 = cursor.getString(12);
//                type5 = cursor.getString(13);
//
//                cursor.close();
//
//                break;
//
//            case 4:
//
//                cursor.moveToPosition(3);
//
//                RoomName = cursor.getString(1);
//                ipAddress = cursor.getString(2);
//                port = cursor.getString(3);
//                dev1Name = cursor.getString(4);
//                dev2Name = cursor.getString(5);
//                dev3Name = cursor.getString(6);
//                dev4Name = cursor.getString(7);
//                dev5Name = cursor.getString(8);
//                type1 = cursor.getString(9);
//                type2 = cursor.getString(10);
//                type3 = cursor.getString(11);
//                type4 = cursor.getString(12);
//                type5 = cursor.getString(13);
//
//                cursor.close();
//
//                break;
//
//            case 5:
//
//                cursor.moveToPosition(4);
//
//                RoomName = cursor.getString(1);
//                ipAddress = cursor.getString(2);
//                port = cursor.getString(3);
//                dev1Name = cursor.getString(4);
//                dev2Name = cursor.getString(5);
//                dev3Name = cursor.getString(6);
//                dev4Name = cursor.getString(7);
//                dev5Name = cursor.getString(8);
//                type1 = cursor.getString(9);
//                type2 = cursor.getString(10);
//                type3 = cursor.getString(11);
//                type4 = cursor.getString(12);
//                type5 = cursor.getString(13);
//
//                cursor.close();
//
//                break;
//        }
    }

    @Override
    protected void onResume() {
        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("notificationEnable","0");
        editor.commit();
        super.onResume();
    }

    @Override
    protected void onDestroy() {

        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("notificationEnable","1");
        editor.commit();
        super.onDestroy();
    }

    public void rightStatesToPref(String val1, String val2, String val3, String val4, String val5) {
        SharedPreferences.Editor editor = shared.edit();

        editor.putString("Value1", val1);
        editor.putString("Value2", val2);
        editor.putString("Value3", val3);
        editor.putString("Value4", val4);
        editor.putString("Value5", val5);

        editor.apply();
    }

    public void promptSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something?");

        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Your Device Doesn't Support This Language yo", Toast.LENGTH_SHORT).show();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//             myTextResult.setText(textToConvert);
        }
    };

    Handler clearScreen = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myTextResult.setText("");
        }
    };

    public void onActivityResult(int request_code, int result_code, Intent intent) {super.onActivityResult(request_code, result_code, intent);

        switch (request_code) {
            case 100:
                if (result_code == RESULT_OK && intent != null) {
                    ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textToConvert = (result.get(0));

                    textToConvert = textToConvert.toLowerCase();
                    handler.sendEmptyMessage(0);

                    Cursor myCursor = null;

                    SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
                    int RoomActive = Integer.parseInt(shared.getString("RoomActive", ""));

                    switch(RoomActive) {

                        case 1:
                            myCursor = room1SavedCommands.viewAllData();
                            break;
                        case 2:
                            myCursor = room2SavedCommands.viewAllData();
                            break;
                        case 3:
                            myCursor = room3SavedCommands.viewAllData();
                            break;
                        case 4:
                            myCursor = room4SavedCommands.viewAllData();
                            break;
                        case 5:
                            myCursor = room5SavedCommands.viewAllData();
                            break;
                    }

                    boolean milGyaDatabaseMeFlag = false;

                    if (myCursor != null) {
                        while (myCursor.moveToNext()) {
                            if (textToConvert.equals(String.valueOf(myCursor.getString(1)))) {
                                String device1State = myCursor.getString(2);
                                String device2State = myCursor.getString(3);
                                String device3State = myCursor.getString(4);
                                String device4State = myCursor.getString(5);
                                String device5State = myCursor.getString(6);

                                // also check for 0,1,2,3,4   coz seek-bars

                                String type1 = shared.getString("Type1", "");
                                String type2 = shared.getString("Type2", "");
                                String type3 = shared.getString("Type3", "");
                                String type4 = shared.getString("Type4", "");
                                String type5 = shared.getString("Type5", "");

                                switch (device1State) {
                                    case "ON":
                                        if (!device1.isChecked())
                                            device1.setChecked(true);
                                        break;
                                    case "OFF":
                                        if (device1.isChecked())
                                            device1.setChecked(false);
                                        break;
                                    case "0":
                                        if (type1.contains("true"))
                                            seek1.setProgress(0);
                                        break;
                                    case "1":
                                        if (type1.contains("true"))
                                            seek1.setProgress(1);
                                        break;
                                    case "2":
                                        if (type1.contains("true"))
                                            seek1.setProgress(2);
                                        break;
                                    case "3":
                                        if (type1.contains("true"))
                                            seek1.setProgress(3);
                                        break;
                                    case "4":
                                        if (type1.contains("true"))
                                            seek1.setProgress(4);
                                        break;
                                }

                                switch (device2State) {
                                    case "ON":
                                        if (!device2.isChecked())
                                            device2.setChecked(true);
                                        break;
                                    case "OFF":
                                        if (device2.isChecked())
                                            device2.setChecked(false);
                                        break;
                                    case "0":
                                        if (type2.contains("true"))
                                            seek2.setProgress(0);
                                        break;
                                    case "1":
                                        if (type2.contains("true"))
                                            seek2.setProgress(1);
                                        break;
                                    case "2":
                                        if (type2.contains("true"))
                                            seek2.setProgress(2);
                                        break;
                                    case "3":
                                        if (type2.contains("true"))
                                            seek2.setProgress(3);
                                        break;
                                    case "4":
                                        if (type2.contains("true"))
                                            seek2.setProgress(4);
                                        break;
                                }

                                switch (device3State) {
                                    case "ON":
                                        if (!device3.isChecked())
                                            device3.setChecked(true);
                                        break;
                                    case "OFF":
                                        if (device3.isChecked())
                                            device3.setChecked(false);
                                        break;
                                    case "0":
                                        if (type3.contains("true"))
                                            seek3.setProgress(0);
                                        break;
                                    case "1":
                                        if (type3.contains("true"))
                                            seek3.setProgress(1);
                                        break;
                                    case "2":
                                        if (type3.contains("true"))
                                            seek3.setProgress(2);
                                        break;
                                    case "3":
                                        if (type3.contains("true"))
                                            seek3.setProgress(3);
                                        break;
                                    case "4":
                                        if (type3.contains("true"))
                                            seek3.setProgress(4);
                                        break;
                                }

                                switch (device4State) {
                                    case "ON":
                                        if (!device4.isChecked())
                                            device4.setChecked(true);
                                        break;
                                    case "OFF":
                                        if (device4.isChecked())
                                            device4.setChecked(false);
                                        break;
                                    case "0":
                                        if (type4.contains("true"))
                                            seek4.setProgress(0);
                                        break;
                                    case "1":
                                        if (type4.contains("true"))
                                            seek4.setProgress(1);
                                        break;
                                    case "2":
                                        if (type4.contains("true"))
                                            seek4.setProgress(2);
                                        break;
                                    case "3":
                                        if (type4.contains("true"))
                                            seek4.setProgress(3);
                                        break;
                                    case "4":
                                        if (type4.contains("true"))
                                            seek4.setProgress(4);
                                        break;
                                }

                                switch (device5State) {
                                    case "ON":
                                        if (!device5.isChecked())
                                            device5.setChecked(true);
                                        break;
                                    case "OFF":
                                        if (device5.isChecked())
                                            device5.setChecked(false);
                                        break;
                                    case "0":
                                        if (type5.contains("true"))
                                            seek5.setProgress(0);
                                        break;
                                    case "1":
                                        if (type5.contains("true"))
                                            seek5.setProgress(1);
                                        break;
                                    case "2":
                                        if (type5.contains("true"))
                                            seek5.setProgress(2);
                                        break;
                                    case "3":
                                        if (type5.contains("true"))
                                            seek5.setProgress(3);
                                        break;
                                    case "4":
                                        if (type5.contains("true"))
                                            seek5.setProgress(4);
                                        break;
                                }

                                milGyaDatabaseMeFlag = true;
                                break;
                            }
                        }
                    }

                    if (!milGyaDatabaseMeFlag) {

                        String type1 = shared.getString("Type1", "");
                        String type2 = shared.getString("Type2", "");
                        String type3 = shared.getString("Type3", "");
                        String type4 = shared.getString("Type4", "");
                        String type5 = shared.getString("Type5", "");

//                        if(type1.contains("false"))
//                        {
                            if (
                                    textToConvert.contains("device") && textToConvert.contains("one") && textToConvert.contains(" on") ||
                                            (textToConvert.contains("debice") && textToConvert.contains("one") && textToConvert.contains(" on")) ||
                                            (textToConvert.contains("device") && textToConvert.contains("1") && textToConvert.contains(" on")) ||
                                            (textToConvert.contains("debice") && textToConvert.contains("1") && textToConvert.contains(" on")) ||
                                            (textToConvert.contains("ice") && textToConvert.contains(" on") && textToConvert.contains("erst")) ||
                                            (textToConvert.contains("ice") && textToConvert.contains(" on") && textToConvert.contains("isrt")) ||
                                            (textToConvert.contains("ice") && textToConvert.contains(" on") && textToConvert.contains("st")) ||
                                            (textToConvert.contains(dev1Name + " ") && textToConvert.contains(" on"))
                                    )

//                        if (setFlag == 1)
                                device1.setChecked(true);

                            else if (textToConvert.contains("ice") && textToConvert.contains("one") && textToConvert.contains(" of") ||
                                    (textToConvert.contains("ice") && textToConvert.contains("1") && textToConvert.contains(" of")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains(" of")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains("one") && textToConvert.contains("off")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains("1") && textToConvert.contains("off")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains("off")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains(" off") && textToConvert.contains("st")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains(" off") && textToConvert.contains("erst")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains(" off") && textToConvert.contains("irst")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains(" of") && textToConvert.contains("st")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains(" of") && textToConvert.contains("erst")) ||
                                    (textToConvert.contains("ice") && textToConvert.contains(" of") && textToConvert.contains("irst")) ||
                                    (textToConvert.contains(dev1Name + " ") && textToConvert.contains("off")) ||
                                    (textToConvert.contains(dev1Name + " ") && textToConvert.contains("of"))
                                    )

                                device1.setChecked(false);
//                        }

//                        else
//                        {
//                            if ((textToConvert.contains("ice") && textToConvert.contains("one") && textToConvert.contains("zero")) ||
//                                    (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains("zero")) ||
//                                            (textToConvert.contains("ice") && textToConvert.contains("1") && textToConvert.contains("zero") )||
//                                    (textToConvert.contains("ice") && textToConvert.contains("one") && textToConvert.contains("0")) ||
//                                    (textToConvert.contains("ice") && textToConvert.contains("1") && textToConvert.contains("0")) ||
//                                    (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains("0")) ||
//                                    (textToConvert.contains("ice") && textToConvert.contains("one") && textToConvert.contains("level")) ||
//                                    (textToConvert.contains("ice") && textToConvert.contains("1") && textToConvert.contains("level")) ||
//                                    (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains("level")) ||
//                                    (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains("level")) ||
//                                            (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains("level")) ||
//                                                    (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains("level")) ||
//                                                    (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains("level")))
//
//                            {}
//                        }

                        else if (textToConvert.contains("ice") && textToConvert.contains("two") && textToConvert.contains(" on") ||
                                (textToConvert.contains("ice") && textToConvert.contains("to") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("2") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("nd") && textToConvert.contains(" on")) ||
                                (textToConvert.contains(dev2Name + " ") && textToConvert.contains(" on"))
                                )

                            device2.setChecked(true);

                        else if (textToConvert.contains("ice") && textToConvert.contains("two") && textToConvert.contains(" off") ||
                                (textToConvert.contains("ice") && textToConvert.contains("two") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("to") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("to") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("2") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("2") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("nd") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("nd") && textToConvert.contains(" of")) ||
                                (textToConvert.contains(dev2Name + " ") && textToConvert.contains(" off")) ||
                                (textToConvert.contains(dev2Name + " ") && textToConvert.contains(" of"))
                                )

                            device2.setChecked(false);

                        else if (textToConvert.contains("ice") && textToConvert.contains("three") && textToConvert.contains(" on") ||
                                (textToConvert.contains("ice") && textToConvert.contains("thre") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("thri") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("tree") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("3") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("3") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rd") && textToConvert.contains(" on")) ||
                                (textToConvert.contains(dev3Name +" ") && textToConvert.contains(" on"))
                                )

                            device3.setChecked(true);

                        else if (textToConvert.contains("ice") && textToConvert.contains("three") && textToConvert.contains(" off") ||
                                (textToConvert.contains("ice") && textToConvert.contains("thre") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("thri") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("tree") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("3") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("three") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("thre") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("thri") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("tree") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("3") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rd") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rd") && textToConvert.contains(" of")) ||
                                (textToConvert.contains(dev3Name + " ") && textToConvert.contains(" off")) ||
                                (textToConvert.contains(dev3Name + " ") && textToConvert.contains(" of"))

                                )

                            device3.setChecked(false);

                        else if (textToConvert.contains("ice") && textToConvert.contains("four") && textToConvert.contains(" on") ||
                                (textToConvert.contains("ice") && textToConvert.contains("for") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("fur") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("4") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("phore") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rth") && textToConvert.contains(" on")) ||
                                (textToConvert.contains(dev4Name + " ") && textToConvert.contains(" on"))
                                )

                            device4.setChecked(true);

                        else if (textToConvert.contains("ice") && textToConvert.contains("four") && textToConvert.contains(" off") ||
                                (textToConvert.contains("ice") && textToConvert.contains("for") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("fur") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("4") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("phore") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("four") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("for") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("fur") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("4") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("phore") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rth") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rth") && textToConvert.contains(" of")) ||
                                (textToConvert.contains(dev4Name + " ") && textToConvert.contains(" off")) ||
                                (textToConvert.contains(dev4Name + " ") && textToConvert.contains(" of"))
                                )

                            device4.setChecked(false);

                        else if (type5.contains("false"))

                            {
                                if (textToConvert.contains("ice") && textToConvert.contains("five") && textToConvert.contains(" on") ||
                                        (textToConvert.contains("ice") && textToConvert.contains("5") && textToConvert.contains(" on")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("phive") && textToConvert.contains(" on")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("fice") && textToConvert.contains(" on")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("phibe") && textToConvert.contains(" on")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("fibe") && textToConvert.contains(" on")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("fifth") && textToConvert.contains(" on")) ||
                                        (textToConvert.contains(dev5Name + " ") && textToConvert.contains(" on"))
                                )

                                device5.setChecked(true);

                                else if (textToConvert.contains("ice") && textToConvert.contains("five") && textToConvert.contains(" off") ||
                                        (textToConvert.contains("ice") && textToConvert.contains("phive") && textToConvert.contains(" off")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("fibe") && textToConvert.contains(" off")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("5") && textToConvert.contains(" off")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("phibe") && textToConvert.contains(" off")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("five") && textToConvert.contains(" of")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("phive") && textToConvert.contains(" of")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("fibe") && textToConvert.contains(" of")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("5") && textToConvert.contains(" of")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("phibe") && textToConvert.contains(" of")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("fifth") && textToConvert.contains(" off")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains("fifth") && textToConvert.contains(" of")) ||
                                        (textToConvert.contains(dev5Name + " ") && textToConvert.contains(" off")) ||
                                        (textToConvert.contains(dev5Name + " ") && textToConvert.contains(" of"))
                                        )
                                    device5.setChecked(false);
                            }

                            else if(type5.contains("true"))
                            {
                                if(textToConvert.contains("ice") && textToConvert.contains(" 5") && textToConvert.contains("level") && textToConvert.contains("0"))
                                    seek5.setProgress(0);
                                if(textToConvert.contains("ice") && textToConvert.contains(" 5") && textToConvert.contains("level") && textToConvert.contains("1"))
                                    seek5.setProgress(1);
                                if(textToConvert.contains("ice") && textToConvert.contains(" 5") && textToConvert.contains("level") && textToConvert.contains("2"))
                                    seek5.setProgress(2);
                                if(textToConvert.contains("ice") && textToConvert.contains(" 5") && textToConvert.contains("level") && textToConvert.contains("3"))
                                    seek5.setProgress(3);
                                if(textToConvert.contains("ice") && textToConvert.contains(" 5") && textToConvert.contains("level") && textToConvert.contains("4"))
                                    seek5.setProgress(4);
                            }
                    }
                    break;
                }
        }
    }
}
