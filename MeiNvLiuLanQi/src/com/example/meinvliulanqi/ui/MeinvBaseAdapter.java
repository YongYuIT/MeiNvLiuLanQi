package com.example.meinvliulanqi.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MeinvBaseAdapter extends BaseAdapter
{
    private JSONArray meinvs;
    private Context   context;
    private Bitmap[]  imgs;

    public MeinvBaseAdapter(JSONArray _meinv, Bitmap[] _imgs, Context _context)
    {
        meinvs = _meinv;
        context = _context;
        imgs = _imgs;
    }

    @Override
    public int getCount()
    {
        return this.meinvs.length();
    }

    public void setImgs(Bitmap[] _imgs)
    {
        // 数据源发生改变时，通知所有使用本Adapter的对象刷新
        this.imgs = _imgs;
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0)
    {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View view, ViewGroup arg2)
    {

        LayoutInflater inf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inf.inflate(R.layout.item_meinv, null);

        try
        {
            JSONObject meinvObj = meinvs.getJSONObject(arg0);

            ImageView img_hade = (ImageView) view.findViewById(R.id.img_hade);
            if (imgs[arg0] != null)
            {
                img_hade.setImageBitmap(imgs[arg0]);
            } else
            {
                img_hade.setImageResource(R.drawable.app_logo);
            }
            TextView txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_name.setText(txt_name.getText() + meinvObj.getString("name"));

            TextView txt_birthday = (TextView) view
                    .findViewById(R.id.txt_birthday);
            txt_birthday.setText(txt_birthday.getText()
                    + meinvObj.getString("birthday"));

            TextView txt_address = (TextView) view
                    .findViewById(R.id.txt_address);
            txt_address.setText(txt_address.getText()
                    + meinvObj.getString("address"));

            TextView txt_stature = (TextView) view
                    .findViewById(R.id.txt_stature);
            txt_stature.setText(txt_stature.getText()
                    + meinvObj.getString("stature") + "cm");

            TextView txt_weight = (TextView) view.findViewById(R.id.txt_weight);
            txt_weight.setText(txt_weight.getText()
                    + meinvObj.getString("weight") + "kg");

            TextView txt_bust = (TextView) view.findViewById(R.id.txt_bust);
            txt_bust.setText(txt_bust.getText() + meinvObj.getString("bust"));

            TextView txt_waistline = (TextView) view
                    .findViewById(R.id.txt_waistline);
            txt_waistline.setText(txt_waistline.getText()
                    + meinvObj.getString("waistline") + "cm");

            TextView txt_hip = (TextView) view.findViewById(R.id.txt_hip);
            txt_hip.setText(txt_hip.getText() + meinvObj.getString("hip")
                    + "cm");
        } catch (JSONException e)
        {
            Log.e("thinking-------", e.getMessage());
        }
        // TODO Auto-generated method stub
        return view;
    }
}
