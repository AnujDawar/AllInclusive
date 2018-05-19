package com.example.anujdawar.allinclusive;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.anujdawar.allinclusive.SplashScreen.room1SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room2SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room3SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room4SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room5SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.roomDetailsDatabase;
import static com.example.anujdawar.allinclusive.saved_commands_activity.EditFlag;
import static com.example.anujdawar.allinclusive.saved_commands_activity.tempCommand;
import static com.example.anujdawar.allinclusive.saved_commands_activity.tempDev1;
import static com.example.anujdawar.allinclusive.saved_commands_activity.tempDev2;
import static com.example.anujdawar.allinclusive.saved_commands_activity.tempDev3;
import static com.example.anujdawar.allinclusive.saved_commands_activity.tempDev4;
import static com.example.anujdawar.allinclusive.saved_commands_activity.tempDev5;
import static com.example.anujdawar.allinclusive.saved_commands_activity.tempID;

public class addCommandIntentClass extends AppCompatActivity {

    protected static boolean SaveSuccessError = false;
    protected static ArrayAdapter<CharSequence> adapter, adapter2;
    protected EditText customInputCommandText;
    protected TextView device1CustomName, device2CustomName, device3CustomName, device4CustomName, device5CustomName;
    protected Spinner customSpinner1, customSpinner2, customSpinner3, customSpinner4, customSpinner5;
    protected ImageButton customMicButton;
    protected static String inputText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_command_menu_file, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.viewSavedCommands_id:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent homeIntent = new Intent(getApplicationContext(), saved_commands_activity.class);
                        startActivity(homeIntent);
                        finish();
                    }
                }, 0);
                break;

            case R.id.settings_id:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }
                }, 0);
                break;

            case R.id.saveCommand_id:

                if (customInputCommandText.getText().toString().equals(""))
                    Toast.makeText(this, "Custom Command Cannot Be Empty", Toast.LENGTH_SHORT).show();

                else if (EditFlag) {

                    boolean isUpdate = false;
                    inputText = customInputCommandText.getText().toString();

                    SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
                    int RoomActive = Integer.parseInt(shared.getString("RoomActive", ""));

                    switch (RoomActive) {
                        case 1:
                            isUpdate = room1SavedCommands.updateData(tempID, inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString());

                            break;

                        case 2:
                            isUpdate = room2SavedCommands.updateData(tempID, inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString());

                            break;

                        case 3:
                            isUpdate = room3SavedCommands.updateData(tempID, inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString());

                            break;

                        case 4:
                            isUpdate = room4SavedCommands.updateData(tempID, inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString());

                            break;

                        case 5:
                            isUpdate = room5SavedCommands.updateData(tempID, inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString());

                            break;
                    }

                    if (isUpdate)
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Not Updated", Toast.LENGTH_SHORT).show();

                    customInputCommandText.setText("");
                    customSpinner1.setSelection(0);
                    customSpinner2.setSelection(0);
                    customSpinner3.setSelection(0);
                    customSpinner4.setSelection(0);
                    customSpinner5.setSelection(0);
                } else {

                    inputText = customInputCommandText.getText().toString();
                    boolean ifInserted = false;

                    SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
                    int RoomActive = Integer.parseInt(shared.getString("RoomActive", ""));

                    switch (RoomActive) {
                        case 1:
                            ifInserted = room1SavedCommands.insertData(inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString()
                            );
                            break;

                        case 2:
                            ifInserted = room2SavedCommands.insertData(inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString()
                            );
                            break;

                        case 3:
                            ifInserted = room3SavedCommands.insertData(inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString()
                            );
                            break;

                        case 4:
                            ifInserted = room4SavedCommands.insertData(inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString()
                            );
                            break;

                        case 5:
                            ifInserted = room5SavedCommands.insertData(inputText,
                                    customSpinner1.getSelectedItem().toString(),
                                    customSpinner2.getSelectedItem().toString(),
                                    customSpinner3.getSelectedItem().toString(),
                                    customSpinner4.getSelectedItem().toString(),
                                    customSpinner5.getSelectedItem().toString()
                            );
                            break;
                    }

                    if (ifInserted)
                        Toast.makeText(this, "Command Inserted", Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(this, "Command Not Inserted", Toast.LENGTH_SHORT).show();

                    customInputCommandText.setText("");
                    customSpinner1.setSelection(0);
                    customSpinner2.setSelection(0);
                    customSpinner3.setSelection(0);
                    customSpinner4.setSelection(0);
                    customSpinner5.setSelection(0);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_command);

        setTitle(R.string.addCommandActivity);

        Cursor res = roomDetailsDatabase.viewAllData();

        customInputCommandText = (EditText) findViewById(R.id.customCommandTyped);
        device1CustomName = (TextView) findViewById(R.id.addCommandDevice1);
        device2CustomName = (TextView) findViewById(R.id.addCommandDevice2);
        device3CustomName = (TextView) findViewById(R.id.addCommandDevice3);
        device4CustomName = (TextView) findViewById(R.id.addCommandDevice4);
        device5CustomName = (TextView) findViewById(R.id.addCommandDevice5);

        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
        int RoomActive = Integer.parseInt(shared.getString("RoomActive", ""));

        res.moveToPosition(RoomActive - 1);

        device1CustomName.setText(res.getString(4));
        device2CustomName.setText(res.getString(5));
        device3CustomName.setText(res.getString(6));
        device4CustomName.setText(res.getString(7));
        device5CustomName.setText(res.getString(8));

        String a = "Device1";
        String b = "Device2";
        String c = "Device3";
        String d = "Device4";
        String e = "Device5";

        String dev1Name, dev2Name, dev3Name, dev4Name, dev5Name;
        res.moveToPosition(RoomActive - 1);

        dev1Name = res.getString(4);
        dev2Name = res.getString(5);
        dev3Name = res.getString(6);
        dev4Name = res.getString(7);
        dev5Name = res.getString(8);

        if (dev1Name.equals(""))
            device1CustomName.setText(a);
        else
            device1CustomName.setText(dev1Name);

        if (dev2Name.equals(""))
            device2CustomName.setText(b);
        else
            device2CustomName.setText(dev2Name);

        if (dev3Name.equals(""))
            device3CustomName.setText(c);
        else
            device3CustomName.setText(dev3Name);

        if (dev4Name.equals(""))
            device4CustomName.setText(d);
        else
            device4CustomName.setText(dev4Name);

        if (dev5Name.equals(""))
            device5CustomName.setText(e);
        else
            device5CustomName.setText(dev5Name);

        customSpinner1 = (Spinner) findViewById(R.id.spinner1);
        customSpinner2 = (Spinner) findViewById(R.id.spinner2);
        customSpinner3 = (Spinner) findViewById(R.id.spinner3);
        customSpinner4 = (Spinner) findViewById(R.id.spinner4);
        customSpinner5 = (Spinner) findViewById(R.id.spinner5);

        customInputCommandText.setText("");
        customSpinner1.setSelection(0);
        customSpinner2.setSelection(0);
        customSpinner3.setSelection(0);
        customSpinner4.setSelection(0);
        customSpinner5.setSelection(0);

        adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.status_of_devices_spinner));

        adapter2 = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.status_of_devices_spinner2));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(shared.getString("Type1", "").contains("true"))
            customSpinner1.setAdapter(adapter2);
        else
            customSpinner1.setAdapter(adapter);

        if(shared.getString("Type2", "").contains("true"))
            customSpinner2.setAdapter(adapter2);
        else
            customSpinner2.setAdapter(adapter);

        if(shared.getString("Type3", "").contains("true"))
            customSpinner3.setAdapter(adapter2);
        else
            customSpinner3.setAdapter(adapter);

        if(shared.getString("Type4", "").contains("true"))
            customSpinner4.setAdapter(adapter2);
        else
            customSpinner4.setAdapter(adapter);

        if(shared.getString("Type5", "").contains("true"))
            customSpinner5.setAdapter(adapter2);
        else
            customSpinner5.setAdapter(adapter);

        res.close();

        if (EditFlag) {
            customInputCommandText.setText(tempCommand);

            int myPos = 0;

            switch (tempDev1) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
                case "0":
                    myPos = 1;
                    break;
                case "1":
                    myPos = 2;
                    break;
                case "2":
                    myPos = 3;
                    break;
                case "3":
                    myPos = 4;
                    break;
                case "4":
                    myPos = 5;
                    break;
            }

            customSpinner1.setSelection(myPos);

            switch (tempDev2) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
                case "0":
                    myPos = 1;
                    break;
                case "1":
                    myPos = 2;
                    break;
                case "2":
                    myPos = 3;
                    break;
                case "3":
                    myPos = 4;
                    break;
                case "4":
                    myPos = 5;
                    break;
            }

            customSpinner2.setSelection(myPos);

            switch (tempDev3) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
                case "0":
                    myPos = 1;
                    break;
                case "1":
                    myPos = 2;
                    break;
                case "2":
                    myPos = 3;
                    break;
                case "3":
                    myPos = 4;
                    break;
                case "4":
                    myPos = 5;
                    break;
            }

            customSpinner3.setSelection(myPos);

            switch (tempDev4) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
                case "0":
                    myPos = 1;
                    break;
                case "1":
                    myPos = 2;
                    break;
                case "2":
                    myPos = 3;
                    break;
                case "3":
                    myPos = 4;
                    break;
                case "4":
                    myPos = 5;
                    break;
            }

            customSpinner4.setSelection(myPos);

            switch (tempDev5) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
                case "0":
                    myPos = 1;
                    break;
                case "1":
                    myPos = 2;
                    break;
                case "2":
                    myPos = 3;
                    break;
                case "3":
                    myPos = 4;
                    break;
                case "4":
                    myPos = 5;
                    break;
            }

            customSpinner5.setSelection(myPos);
        }

        else {
            customInputCommandText.setText("");
            customSpinner1.setSelection(0);
            customSpinner2.setSelection(0);
            customSpinner3.setSelection(0);
            customSpinner4.setSelection(0);
            customSpinner5.setSelection(0);
        }

        customMicButton = (ImageButton) findViewById(R.id.micButtonIDcommandinput);
        micButtonOnClickListener();
    }

    public void micButtonOnClickListener() {

        customMicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeech();
            }
        });
    }

    public void promptSpeech() {

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

    public void onActivityResult(int request_code, int result_code, Intent intent) {
        super.onActivityResult(request_code, result_code, intent);

        switch (request_code) {
            case 100:
                if (result_code == RESULT_OK && intent != null) {
                    ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String textToFill = (result.get(0));
                    textToFill = textToFill.toLowerCase();

                    customInputCommandText.setText(textToFill);
                }
        }
    }
}