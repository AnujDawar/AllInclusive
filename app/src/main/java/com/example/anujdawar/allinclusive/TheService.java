package com.example.anujdawar.allinclusive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TheService extends Service{

    protected Socket s;
    protected DataOutputStream dataOut;
    protected DataInputStream dataIn;

    protected static String RoomName, IP_Address, Port,
            Device1Name, Device2Name, Device3Name, Device4Name, Device5Name,
            Type1, Type2, Type3, Type4, Type5,
            Value1, Value2, Value3, Value4, Value5;

    protected String state1, state2, state3, state4, state5, notiEnabled;

    String resultString = "";
    String a = "", b = "", c = "", d = "", e = "";
    int connectCounter = 0;
    boolean connectedFlag = false, handlerCalledFlag = false;
    Thread counterThread;

    Handler HandlerDisconnected = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();

            editor.putString("serviceActive", "0");
            editor.commit();

            if(shared.getString("notificationEnable","").equals("0"))
            {
                Intent i = new Intent();
                i.setClass(TheService.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Devices_Activity.fa.finish();
//                System.exit(0);
                startActivity(i);
                stopSelf();

            }

            else
                NotificationFunction(1);
        }
    };

    Handler h1 = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            resultString += msg.obj.toString();

            if(resultString.equals("c"))
            {
                connectCounter++;
                resultString = "";

                if (!connectedFlag) {
                    connectedFlag = true;
                    checkCounter();
                }
            }

            if (resultString.contains("SET:")) {

                int myindex = resultString.indexOf(":");

                if (resultString.length() == myindex + 6) {

                    if(Type1.contains("true"))
                        Value1 = String.valueOf(resultString.charAt(myindex + 1));

                    else if (resultString.charAt(myindex + 1) == '0')
                    {
                        Value1 = "0";
                        state1 = "OFF";
                    }

                    else
                    {
                        Value1 = "4";
                        state1 = "ON";
                    }

                    if(Type2.contains("true"))
                        Value2 = String.valueOf(resultString.charAt(myindex + 2));

                    else if (resultString.charAt(myindex + 2) == '0')
                    {
                        Value2 = "0";
                        state2 = "OFF";
                    }

                    else
                    {
                        Value2 = "4";
                        state2 = "ON";
                    }

                    if(Type3.contains("true"))
                        Value3 = String.valueOf(resultString.charAt(myindex + 3));

                    else if (resultString.charAt(myindex + 3) == '0')
                    {
                        Value3 = "0";
                        state3 = "OFF";
                    }

                    else
                    {
                        Value3 = "4";
                        state3 = "ON";
                    }

                    if(Type4.contains("true"))
                        Value4 = String.valueOf(resultString.charAt(myindex + 4));

                    else if (resultString.charAt(myindex + 4) == '0')
                    {
                        Value4 = "0";
                        state4 = "OFF";
                    }

                    else
                    {
                        Value4 = "4";
                        state4 = "ON";
                    }

                    if(Type5.contains("true"))
                        Value5 = String.valueOf(resultString.charAt(myindex + 5));

                    else if (resultString.charAt(myindex + 5) == '0')
                    {
                        Value5 = "0";
                        state5 = "OFF";
                    }

                    else
                    {
                        Value5 = "4";
                        state5 = "ON";
                    }

                    SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();

                    editor.putString("Value1", Value1);
                    editor.putString("Value2", Value2);
                    editor.putString("Value3", Value3);
                    editor.putString("Value4", Value4);
                    editor.putString("Value5", Value5);

                    editor.apply();
                    resultString = "";

                    notiEnabled = shared.getString("notificationEnable","");

                    if(notiEnabled.equals("1"))
                        NotificationFunction(0);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {

//        Toast.makeText(this, "Service Started here", Toast.LENGTH_SHORT).show();

        Thread initThread = new Thread(new Runnable() {
            @Override
            public void run() {

                SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);

                SharedPreferences.Editor editor = shared.edit();
                editor.putString("serviceActive","1");
                editor.apply();

                RoomName = shared.getString("RoomName", "");
                IP_Address = shared.getString("IP_Address", "");
                Port = shared.getString("Port", "");
                Device1Name = shared.getString("Device1Name", "");
                Device2Name = shared.getString("Device2Name", "");
                Device3Name = shared.getString("Device3Name", "");
                Device4Name = shared.getString("Device4Name", "");
                Device5Name = shared.getString("Device5Name", "");
                Type1 = shared.getString("Type1", "");
                Type2 = shared.getString("Type2", "");
                Type3 = shared.getString("Type3", "");
                Type4 = shared.getString("Type4", "");
                Type5 = shared.getString("Type5", "");
                Value1 = shared.getString("Value1", "");
                Value2 = shared.getString("Value2", "");
                Value3 = shared.getString("Value3", "");
                Value4 = shared.getString("Value4", "");
                Value5 = shared.getString("Value5", "");

                connectedFlag = false;
                handlerCalledFlag = false;
                connectCounter = 0;
            }
        });
        initThread.start();
        try {
            initThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        startManagingThread();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

//        Toast.makeText(TheService.this, "Service Stopped", Toast.LENGTH_SHORT).show();
        SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("serviceActive","0");
        editor.commit();

        try {
            dataIn.close();
            dataOut.close();
            s.close();
            counterThread.interrupt();
            super.onDestroy();
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void NotificationFunction(int disconnected)
    {
        Intent notificationIntent = new Intent(getApplicationContext(), SplashScreen.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        Notification notification = null;

        if(disconnected == 0) {

            String notiContent = "";

            if (!Device1Name.equals("")) {
                notiContent += "\n" + Device1Name + " : ";
                if (Type1.contains("true"))
                    if (!Value1.equals("0"))
                        notiContent += "Level " + Value1;
                    else
                        notiContent += "OFF";
                else
                    notiContent += state1;
            }

            if (!Device2Name.equals("")) {
                notiContent += "\n" + Device2Name + " : ";
                if (Type2.contains("true"))
                    if (!Value2.equals("0"))
                        notiContent += "Level " + Value2;
                    else
                        notiContent += "OFF";
                else
                    notiContent += state2;
            }

            if (!Device3Name.equals("")) {
                notiContent += "\n" + Device3Name + " : ";
                if (Type3.contains("true"))
                    if (!Value3.equals("0"))
                        notiContent += "Level " + Value3;
                    else
                        notiContent += "OFF";
                else
                    notiContent += state3;
            }

            if (!Device4Name.equals("")) {
                notiContent += "\n" + Device4Name + " : ";
                if (Type4.contains("true"))
                    if (!Value4.equals("0"))
                        notiContent += "Level " + Value4;
                    else
                        notiContent += "OFF";
                else
                    notiContent += state4;
            }

            if (!Device5Name.equals("")) {
                notiContent += "\n" + Device5Name + " : ";
                if (Type5.contains("true"))
                    if (!Value5.equals("0"))
                        notiContent += "Level " + Value5;
                    else
                        notiContent += "OFF";
                else
                    notiContent += state5;
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                notification = new Notification.Builder(getApplicationContext())
                        .setTicker("TickerTitle")
                        .setContentTitle(RoomName)
                        .setStyle(new Notification.BigTextStyle().bigText(notiContent))
                        .setSmallIcon(R.mipmap.ic_all_inclusive_black_48dp)
                        .setContentIntent(pIntent).getNotification();
            }

            if (notification != null) {
                notification.flags = Notification.FLAG_AUTO_CANCEL;
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
        }

        else {
            SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);
//            if (shared.getString("serviceActive", "").equals("1") && shared.getString("notificationEnable", "").equals("1")) {

                String notiContent = "\nDISCONNECTED";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    notification = new Notification.Builder(getApplicationContext())
                            .setTicker("TickerTitle")
                            .setContentTitle(RoomName)
                            .setStyle(new Notification.BigTextStyle().bigText(notiContent))
                            .setSmallIcon(R.mipmap.ic_all_inclusive_black_48dp)
                            .setContentIntent(pIntent).getNotification();
                }

                if (notification != null) {
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                }

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, notification);

//                Toast.makeText(TheService.this, "Service Stopped", Toast.LENGTH_SHORT).show();
                shared = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("serviceActive","0");
                editor.commit();

                try {
                    dataIn.close();
                    dataOut.close();
                    s.close();
                    counterThread.interrupt();
//                    super.onDestroy();
                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                stopSelf();
            }
        }
//    }

    private void startManagingThread()
    {
        final Thread tryt = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(500);

                    s = new Socket(IP_Address, Integer.parseInt(Port));
                    dataIn = new DataInputStream(s.getInputStream());
                    dataOut = new DataOutputStream(s.getOutputStream());
                    ConnectButton();
                }catch(Exception e){
                }
            }
        });
        tryt.start();

        Thread sendingStatesThread = new Thread(new Runnable() {
            @Override
            public void run() {

                SharedPreferences shared = getSharedPreferences("MyPref", MODE_PRIVATE);

                while(true)
                {
                    a = shared.getString("Value1", "");
                    b = shared.getString("Value2", "");
                    c = shared.getString("Value3", "");
                    d = shared.getString("Value4", "");
                    e = shared.getString("Value5", "");

                    click(a + b + c + d + e);

                    try {
                        Thread.sleep(1750);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
    });
        sendingStatesThread.start();
    }

    public void click(final String s)
    {
        Thread sendTo = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    dataOut.writeBytes(s);
                    dataOut.flush();
                    dataOut.flush();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        sendTo.start();
    }

    public void ConnectButton()
    {
        Thread connectThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
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

    private void checkCounter()
    {
        counterThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while(!handlerCalledFlag && !counterThread.isInterrupted()) {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (connectCounter != 0)
                        connectCounter = 0;
                    else
                    {
                        handlerCalledFlag = true;
                        HandlerDisconnected.sendEmptyMessage(0);
                    }
                }
            }
        });
        counterThread.start();
    }
}