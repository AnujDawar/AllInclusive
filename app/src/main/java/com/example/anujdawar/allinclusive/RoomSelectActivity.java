package com.example.anujdawar.allinclusive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Toast;

public class RoomSelectActivity extends AppCompatActivity {

    public RadioButton room1RadioButton, room2RadioButton, room3RadioButton, room4RadioButton, room5RadioButton;
    public static int RoomActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_select);

        setTitle("Room Select");
        setRadioButton();
    }

    private void setRadioButton() {

        room1RadioButton = (RadioButton) findViewById(R.id.radioButton1);
        room2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        room3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        room4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        room5RadioButton = (RadioButton) findViewById(R.id.radioButton5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.room_selected_start_service_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.next_id:

                if(room1RadioButton.isChecked())
                    RoomActive = 1;
                else if(room2RadioButton.isChecked())
                    RoomActive = 2;
                else if(room3RadioButton.isChecked())
                    RoomActive = 3;
                else if(room4RadioButton.isChecked())
                    RoomActive = 4;
                else if(room5RadioButton.isChecked())
                    RoomActive = 5;

                if(RoomActive == 0)
                    Toast.makeText(this, "Select A Room First", Toast.LENGTH_SHORT).show();

                else
                {
                    SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("RoomActive", String.valueOf(RoomActive));
                    editor.commit();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(homeIntent);
                            finish();
                        }
                    }, 0);
                }
                break;
        }
        return true;
    }
}
