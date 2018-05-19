package com.example.anujdawar.allinclusive;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.anujdawar.allinclusive.SplashScreen.room1SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room2SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room3SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room4SavedCommands;
import static com.example.anujdawar.allinclusive.SplashScreen.room5SavedCommands;

public class saved_commands_activity extends AppCompatActivity {

    Handler sizeHandle1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(saved_commands_activity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    };

    Handler sizeHandle2 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(saved_commands_activity.this, "Not Deleted", Toast.LENGTH_SHORT).show();
        }
    };

    protected ListView lvProduct;
    protected productListAdapter adapter;
    protected static List<product> mProductList;

    protected static boolean EditFlag = false;
    protected static String tempID, tempDev1, tempDev2, tempDev3, tempDev4, tempDev5, tempCommand;

    protected static int positionOfCommandSelected;
    protected static int EditOrDeleteSelected;
    protected static TextView noDataFoundTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_commands);

        setTitle(R.string.savedCommandTitle);

        lvProduct = (ListView) findViewById(R.id.myListID);
        mProductList = new ArrayList<>();

        noDataFoundTV = (TextView) findViewById(R.id.noDataFoundID);

        this.registerForContextMenu(lvProduct);

        ViewDataAll();
    }

    public void ViewDataAll()
    {

        Cursor res = room1SavedCommands.viewAllData();

        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
        int RoomActive = Integer.parseInt(shared.getString("RoomActive", ""));

        switch(RoomActive)
        {
            case 1:
                res =  room1SavedCommands.viewAllData();
                break;

            case 2:
                res =  room2SavedCommands.viewAllData();
                break;

            case 3:
                res =  room3SavedCommands.viewAllData();
                break;

            case 4:
                res =  room4SavedCommands.viewAllData();
                break;

            case 5:
                res =  room5SavedCommands.viewAllData();
                break;
        }

        if(res.getCount() == 0)
        {
            String temp = "No Data Found";
            lvProduct.setVisibility(View.INVISIBLE);
        }

        else
            lvProduct.setVisibility(View.VISIBLE);

        mProductList.clear();

        while(res.moveToNext())
        {
            mProductList.add(new product(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6)));
        }

        adapter = new productListAdapter(getApplicationContext(), mProductList);
        lvProduct.setAdapter(adapter);

        res.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.saved_command_menu_file,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.eraseId:

                EditFlag = false;

                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("Delete All Saved Commands ?").setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
                    int RoomActive = Integer.parseInt(shared.getString("RoomActive", ""));

                    switch (RoomActive)
                    {
                        case 1:
                            room1SavedCommands.deleteAll();
                            break;
                        case 2:
                            room2SavedCommands.deleteAll();
                            break;
                        case 3:
                            room3SavedCommands.deleteAll();
                            break;
                        case 4:
                            room4SavedCommands.deleteAll();
                            break;
                        case 5:
                            room5SavedCommands.deleteAll();
                            break;
                    }

                    ViewDataAll();
                    dialogInterface.cancel();
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                AlertDialog alert = a_builder.create();
                alert.setTitle("ALERT !!!");
                alert.show();

                break;

            case R.id.settings_id:
                EditFlag = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }
                }, 0);
                break;

            case R.id.addCommands_id:
                EditFlag = false;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent homeIntent = new Intent(getApplicationContext(), addCommandIntentClass.class);
                        startActivity(homeIntent);
                finish();
                    }
                }, 0);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if(v.getId() == R.id.myListID)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

            CharSequence temp;
            temp = mProductList.get(info.position).getMyCommand();

            menu.setHeaderTitle(temp);

            positionOfCommandSelected = info.position;

            String[] menuItems = {"Edit","Delete"};

            for(int i = 0; i < menuItems.length; i++)
                menu.add(Menu.NONE, i, i, menuItems[i]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        Cursor res = null;

        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
        int RoomActive = Integer.parseInt(shared.getString("RoomActive", ""));

        switch(RoomActive)
        {
            case 1:
                res =  room1SavedCommands.viewAllData();
                break;

            case 2:
                res =  room2SavedCommands.viewAllData();
                break;

            case 3:
                res =  room3SavedCommands.viewAllData();
                break;

            case 4:
                res =  room4SavedCommands.viewAllData();
                break;

            case 5:
                res =  room5SavedCommands.viewAllData();
                break;
        }

        res.moveToPosition(positionOfCommandSelected);

        if (menuItemIndex == 0)
        {
            EditFlag = true;

            tempID = res.getString(0);
            tempCommand = res.getString(1);
            tempDev1 = res.getString(2);
            tempDev2 = res.getString(3);
            tempDev3 = res.getString(4);
            tempDev4 = res.getString(5);
            tempDev5 = res.getString(6);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(getApplicationContext(), addCommandIntentClass.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, 0);

        }

        else {

            Integer MyResult = 0;

            switch (RoomActive)
            {
                case 1:
                    MyResult = room1SavedCommands.deleteData(String.valueOf(res.getString(0)));
                    break;

                case 2:
                    MyResult = room2SavedCommands.deleteData(String.valueOf(res.getString(0)));
                    break;

                case 3:
                    MyResult = room3SavedCommands.deleteData(String.valueOf(res.getString(0)));
                    break;

                case 4:
                    MyResult = room4SavedCommands.deleteData(String.valueOf(res.getString(0)));
                    break;

                case 5:
                    MyResult = room5SavedCommands.deleteData(String.valueOf(res.getString(0)));
                    break;
            }

            if(MyResult > 0)
                sizeHandle1.sendEmptyMessage(0);

            else
                sizeHandle2.sendEmptyMessage(0);

            ViewDataAll();
            return true;
        }

        return true;
    }
}
