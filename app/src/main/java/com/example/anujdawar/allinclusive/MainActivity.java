package com.example.anujdawar.allinclusive;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.annotation.BoolRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Locale;

import static com.example.anujdawar.allinclusive.SplashScreen.roomDetailsDatabase;
import static com.example.anujdawar.allinclusive.saved_commands_activity.EditFlag;

public class MainActivity extends AppCompatActivity
{
    Handler h1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {

            res = 1;
            CarLockTone.start();
            resultString += msg.obj.toString();

            if (resultString.contains("SET:")) {

                int myindex = resultString.indexOf(":");

                if (resultString.length() == myindex + 6)
                {
                    val1 = String.valueOf(resultString.charAt(myindex + 1));
                    val2 = String.valueOf(resultString.charAt(myindex + 2));
                    val3 = String.valueOf(resultString.charAt(myindex + 3));
                    val4 = String.valueOf(resultString.charAt(myindex + 4));
                    val5 = String.valueOf(resultString.charAt(myindex + 5));

                    resultString = "";

                    setSharedPref();

                    try {
                        dataIn.close();
                        dataOut.close();
                        s.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent homeIntent = new Intent(MainActivity.this, Devices_Activity.class);
                            startActivity(homeIntent);
                            finish();
                        }
                    }, 0);
                }
            }
        }
    };

    MediaPlayer CarLockTone, Bleats, Trash;
    private static int DeleteFlag = 0, threadStartFlag = 0;
    private static String resultString;
    private int checkInputFlag = 0;
    public EditText ipAddress, port, Device1Name, Device2Name, Device3Name, Device4Name, Device5Name;
    public TextView ResultBox;
    private static int res;
    private CheckBox check1, check2, check3, check4, check5;
    protected static int result;
    public EditText RoomNameEditText;
    String temproomname, tempport, tempip, tempname1, tempname2, tempname3,tempname4;
    String tempname5, temptype1, temptype2, temptype3, temptype4, temptype5;
    protected Socket s;
    protected DataInputStream dataIn;
    protected DataOutputStream dataOut;
    public static SharedPreferences roomDetailsPreferences;
        String val1,val2,val3,val4,val5;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            dataIn.close();
            dataOut.close();
            s.close();
        }catch (Exception e)
        {   }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_all_inclusive_white_24dp);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("  All Inclusive");

        CarLockTone = MediaPlayer.create(this, R.raw.carock);
        Bleats = MediaPlayer.create(this, R.raw.bleatsounds);
        Trash = MediaPlayer.create(this, R.raw.whip);

        RoomNameEditText = (EditText) findViewById(R.id.roomNameID);
        ipAddress = (EditText) findViewById(R.id.ipAddressTextBox);
        port = (EditText) findViewById(R.id.portNumberTextBox);
        ResultBox = (TextView) findViewById(R.id.resultTextBoxMain);

        Device1Name = (EditText) findViewById(R.id.Device1TextBox);
        Device2Name = (EditText) findViewById(R.id.Device2TextBox);
        Device3Name = (EditText) findViewById(R.id.Device3TextBox);
        Device4Name = (EditText) findViewById(R.id.Device4TextBox);
        Device5Name = (EditText) findViewById(R.id.Device5TextBox);

        check1 = (CheckBox) findViewById(R.id.checkBox1);
        check2 = (CheckBox) findViewById(R.id.checkBox2);
        check3 = (CheckBox) findViewById(R.id.checkBox3);
        check4 = (CheckBox) findViewById(R.id.checkBox4);
        check5 = (CheckBox) findViewById(R.id.checkBox5);

        setDefaultParametersFromDatabase();
        port.clearFocus();
        RoomNameEditText.requestFocus();
    }

    private void setDefaultParametersFromDatabase() {

        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
        int RoomActive = Integer.parseInt(shared.getString("RoomActive", ""));

        Cursor res = roomDetailsDatabase.viewAllData();

        switch (RoomActive)
        {
            case 1:

                res.moveToPosition(0);

                RoomNameEditText.setText(res.getString(1));
                ipAddress.setText(res.getString(2));
                port.setText(res.getString(3));
                Device1Name.setText(res.getString(4));
                Device2Name.setText(res.getString(5));
                Device3Name.setText(res.getString(6));
                Device4Name.setText(res.getString(7));
                Device5Name.setText(res.getString(8));
                check1.setChecked(Boolean.parseBoolean(res.getString(9)));
                check2.setChecked(Boolean.parseBoolean(res.getString(10)));
                check3.setChecked(Boolean.parseBoolean(res.getString(11)));
                check4.setChecked(Boolean.parseBoolean(res.getString(12)));
                check5.setChecked(Boolean.parseBoolean(res.getString(13)));

                break;

            case 2:

                res.moveToPosition(1);

                RoomNameEditText.setText(res.getString(1));
                ipAddress.setText(res.getString(2));
                port.setText(res.getString(3));
                Device1Name.setText(res.getString(4));
                Device2Name.setText(res.getString(5));
                Device3Name.setText(res.getString(6));
                Device4Name.setText(res.getString(7));
                Device5Name.setText(res.getString(8));
                check1.setChecked(Boolean.parseBoolean(res.getString(9)));
                check2.setChecked(Boolean.parseBoolean(res.getString(10)));
                check3.setChecked(Boolean.parseBoolean(res.getString(11)));
                check4.setChecked(Boolean.parseBoolean(res.getString(12)));
                check5.setChecked(Boolean.parseBoolean(res.getString(13)));

                break;

            case 3:

                res.moveToPosition(2);

                RoomNameEditText.setText(res.getString(1));
                ipAddress.setText(res.getString(2));
                port.setText(res.getString(3));
                Device1Name.setText(res.getString(4));
                Device2Name.setText(res.getString(5));
                Device3Name.setText(res.getString(6));
                Device4Name.setText(res.getString(7));
                Device5Name.setText(res.getString(8));
                check1.setChecked(Boolean.parseBoolean(res.getString(9)));
                check2.setChecked(Boolean.parseBoolean(res.getString(10)));
                check3.setChecked(Boolean.parseBoolean(res.getString(11)));
                check4.setChecked(Boolean.parseBoolean(res.getString(12)));
                check5.setChecked(Boolean.parseBoolean(res.getString(13)));

                break;

            case 4:

                res.moveToPosition(3);

                RoomNameEditText.setText(res.getString(1));
                ipAddress.setText(res.getString(2));
                port.setText(res.getString(3));
                Device1Name.setText(res.getString(4));
                Device2Name.setText(res.getString(5));
                Device3Name.setText(res.getString(6));
                Device4Name.setText(res.getString(7));
                Device5Name.setText(res.getString(8));
                check1.setChecked(Boolean.parseBoolean(res.getString(9)));
                check2.setChecked(Boolean.parseBoolean(res.getString(10)));
                check3.setChecked(Boolean.parseBoolean(res.getString(11)));
                check4.setChecked(Boolean.parseBoolean(res.getString(12)));
                check5.setChecked(Boolean.parseBoolean(res.getString(13)));

                break;

            case 5:

                res.moveToPosition(4);

                RoomNameEditText.setText(res.getString(1));
                ipAddress.setText(res.getString(2));
                port.setText(res.getString(3));
                Device1Name.setText(res.getString(4));
                Device2Name.setText(res.getString(5));
                Device3Name.setText(res.getString(6));
                Device4Name.setText(res.getString(7));
                Device5Name.setText(res.getString(8));
                check1.setChecked(Boolean.parseBoolean(res.getString(9)));
                check2.setChecked(Boolean.parseBoolean(res.getString(10)));
                check3.setChecked(Boolean.parseBoolean(res.getString(11)));
                check4.setChecked(Boolean.parseBoolean(res.getString(12)));
                check5.setChecked(Boolean.parseBoolean(res.getString(13)));

                break;
        }

        res.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.info_id:

                EditFlag = false;

                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                a_builder.setMessage("This App is Created By : \n\t\t\t\tAnuj Dawar\nFor Suggestions, " +
                        "Contact : \n\t\t\t\tanujdawar95@gmail.com\n\t\t\t\tVersion 4.0.1").setCancelable(false)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = a_builder.create();
                alert.setTitle("About Developer");
                alert.show();

                break;

            case R.id.Clear_id:

                Intent intent = new Intent(this, TheService.class);
                stopService(intent);

                EditFlag = false;

                if (threadStartFlag == 0) {

                    RoomNameEditText.setText("");
                    ipAddress.setText("");
                    port.setText("");
                    Device1Name.setText("");
                    Device2Name.setText("");
                    Device3Name.setText("");
                    Device4Name.setText("");
                    Device5Name.setText("");
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    check5.setChecked(false);

                    Trash.start();
                }

                break;

            case R.id.next_id:

                ConnectButtonCall();

                break;

            case R.id.addCommands_id:

                EditFlag = false;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        roomDetailsPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = roomDetailsPreferences.edit();

                        editor.putString("Type1", String.valueOf(Boolean.parseBoolean(String.valueOf(check1.isChecked()))));
                        editor.putString("Type2", String.valueOf(Boolean.parseBoolean(String.valueOf(check2.isChecked()))));
                        editor.putString("Type3", String.valueOf(Boolean.parseBoolean(String.valueOf(check3.isChecked()))));
                        editor.putString("Type4", String.valueOf(Boolean.parseBoolean(String.valueOf(check4.isChecked()))));
                        editor.putString("Type5", String.valueOf(Boolean.parseBoolean(String.valueOf(check5.isChecked()))));

                        editor.commit();

                        Intent homeIntent = new Intent(getApplicationContext(), addCommandIntentClass.class);
                        startActivity(homeIntent);
                            finish();
                    }
                }, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ConnectButtonCall()
        {
            if(ipAddress.getText().toString().equals(""))
            {
                checkInputFlag = 1;
                Toast.makeText(MainActivity.this, "IP Address Field Cannot be Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if(port.getText().toString().equals(""))
            {
                checkInputFlag = 2;
                Toast.makeText(MainActivity.this, "Port Number Field Cannot be Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if(RoomNameEditText.getText().toString().equals(""))
            {
                checkInputFlag = 4;
                Toast.makeText(this, "Please fill Room's Name to continue", Toast.LENGTH_SHORT).show();
                return;
            }

            if(Device1Name.getText().toString().equals("") &&
                    Device2Name.getText().toString().equals("") &&
                    Device3Name.getText().toString().equals("") &&
                    Device4Name.getText().toString().equals("") &&
                    Device5Name.getText().toString().equals(""))
            {
                Toast.makeText(MainActivity.this, "Name At Least 1 Device", Toast.LENGTH_SHORT).show();
                checkInputFlag = 3;
                return;
            }

            temproomname = RoomNameEditText.getText().toString();
            tempip = ipAddress.getText().toString();
            tempport = port.getText().toString();
            tempname1 = Device1Name.getText().toString();
            tempname2 = Device2Name.getText().toString();
            tempname3 = Device3Name.getText().toString();
            tempname4 = Device4Name.getText().toString();
            tempname5 = Device5Name.getText().toString();
            temptype1 = String.valueOf(check1.isChecked());
            temptype2 = String.valueOf(check2.isChecked());
            temptype3 = String.valueOf(check3.isChecked());
            temptype4 = String.valueOf(check4.isChecked());
            temptype5 = String.valueOf(check5.isChecked());

            SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
            int RoomActive = Integer.parseInt(shared.getString("RoomActive", ""));

            switch (RoomActive)
            {
                case 1:

                    roomDetailsDatabase.updateData("1", temproomname, tempip, tempport,
                            tempname1, tempname2, tempname3, tempname4, tempname5,
                            temptype1, temptype2, temptype3, temptype4, temptype5);

                    break;

                case 2:

                    roomDetailsDatabase.updateData("2", temproomname, tempip, tempport,
                            tempname1, tempname2, tempname3, tempname4, tempname5,
                            temptype1, temptype2, temptype3, temptype4, temptype5);

                    break;

                case 3:

                    roomDetailsDatabase.updateData("3", temproomname, tempip, tempport,
                            tempname1, tempname2, tempname3, tempname4, tempname5,
                            temptype1, temptype2, temptype3, temptype4, temptype5);

                    break;

                case 4:

                    roomDetailsDatabase.updateData("4", temproomname, tempip, tempport,
                            tempname1, tempname2, tempname3, tempname4, tempname5,
                            temptype1, temptype2, temptype3, temptype4, temptype5);

                    break;

                case 5:

                    roomDetailsDatabase.updateData("5", temproomname, tempip, tempport,
                            tempname1, tempname2, tempname3, tempname4, tempname5,
                            temptype1, temptype2, temptype3, temptype4, temptype5);

                    break;
            }

            Thread connectThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        s = new Socket(tempip,Integer.parseInt(tempport));
                        dataIn = new DataInputStream(s.getInputStream());
                        dataOut = new DataOutputStream(s.getOutputStream());

                        while(true)
                        {
                            byte temp = dataIn.readByte();
                            char convert = (char) temp;
                            Message msg = Message.obtain();
                            msg.obj = convert;
                            h1.sendMessage(msg);
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            connectThread.start();
        }

    public void setSharedPref()
    {
        roomDetailsPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = roomDetailsPreferences.edit();

        editor.putString("RoomName", temproomname);
        editor.putString("IP_Address", tempip);
        editor.putString("Port", tempport);
        editor.putString("Device1Name", tempname1);
        editor.putString("Device2Name", tempname2);
        editor.putString("Device3Name", tempname3);
        editor.putString("Device4Name", tempname4);
        editor.putString("Device5Name", tempname5);
        editor.putString("Type1", temptype1);
        editor.putString("Type2", temptype2);
        editor.putString("Type3", temptype3);
        editor.putString("Type4", temptype4);
        editor.putString("Type5", temptype5);
        editor.putString("Value1", val1);
        editor.putString("Value2", val2);
        editor.putString("Value3", val3);
        editor.putString("Value4", val4);
        editor.putString("Value5", val5);

        editor.apply();
    }
 }