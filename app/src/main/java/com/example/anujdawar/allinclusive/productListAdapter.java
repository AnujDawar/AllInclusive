package com.example.anujdawar.allinclusive;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.anujdawar.allinclusive.Devices_Activity.dev1Name;
import static com.example.anujdawar.allinclusive.Devices_Activity.dev2Name;
import static com.example.anujdawar.allinclusive.Devices_Activity.dev3Name;
import static com.example.anujdawar.allinclusive.Devices_Activity.dev4Name;
import static com.example.anujdawar.allinclusive.Devices_Activity.dev5Name;
import static com.example.anujdawar.allinclusive.RoomSelectActivity.RoomActive;
import static com.example.anujdawar.allinclusive.SplashScreen.roomDetailsDatabase;

public class productListAdapter extends BaseAdapter {

    private Context mContext;
    private List<product> mProductList;

    protected TextView nameDev1, nameDev2, nameDev3, nameDev4, nameDev5;

    public productListAdapter(Context mContext, List<product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return mProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(mContext, R.layout.text_views, null);

        TextView tvCommand = (TextView) v.findViewById(R.id.commandID);

        nameDev1 = (TextView) v.findViewById(R.id.savedNameDeviceID1);
        nameDev2 = (TextView) v.findViewById(R.id.savedNameDeviceID2);
        nameDev3 = (TextView) v.findViewById(R.id.savedNameDeviceID3);
        nameDev4 = (TextView) v.findViewById(R.id.savedNameDeviceID4);
        nameDev5 = (TextView) v.findViewById(R.id.savedNameDeviceID5);

        TextView tvdev1 = (TextView) v.findViewById(R.id.tv_dev1ID);
        TextView tvdev2 = (TextView) v.findViewById(R.id.tv_dev2ID);
        TextView tvdev3 = (TextView) v.findViewById(R.id.tv_dev3ID);
        TextView tvdev4 = (TextView) v.findViewById(R.id.tv_dev4ID);
        TextView tvdev5 = (TextView) v.findViewById(R.id.tv_dev5ID);

        Cursor res = roomDetailsDatabase.viewAllData();
        res.moveToPosition(RoomActive - 1);

        dev1Name = res.getString(4);
        dev2Name = res.getString(5);
        dev3Name = res.getString(6);
        dev4Name = res.getString(7);
        dev5Name = res.getString(8);

        String a = "Device 1";
        String b = "Device 2";
        String c = "Device 3";
        String d = "Device 4";
        String e = "Device 5";

        if(dev1Name.equals(""))
            nameDev1.setText(a);
        else
            nameDev1.setText(dev1Name);

        if(dev2Name.equals(""))
            nameDev2.setText(b);
        else
            nameDev2.setText(dev2Name);

        if(dev3Name.equals(""))
            nameDev3.setText(c);
        else
            nameDev3.setText(dev3Name);

        if(dev4Name.equals(""))
            nameDev4.setText(d);
        else
            nameDev4.setText(dev4Name);

        if(dev5Name.equals(""))
            nameDev5.setText(e);
        else
            nameDev5.setText(dev5Name);

        tvCommand.setText(mProductList.get(i).getMyCommand());

        tvdev1.setText(String.valueOf(mProductList.get(i).getDev1()));
        tvdev2.setText(String.valueOf(mProductList.get(i).getDev2()));
        tvdev3.setText(String.valueOf(mProductList.get(i).getDev3()));
        tvdev4.setText(String.valueOf(mProductList.get(i).getDev4()));
        tvdev5.setText(String.valueOf(mProductList.get(i).getDev5()));

        v.setTag(mProductList.get(i).getID());

        return v;
    }
}
