package com.example.meinvliulanqi.ui;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.meinvliulanqi.basic_service.IGetFtpData;
import com.example.meinvliulanqi.basic_service.IGetdata;
import com.example.meinvliulanqi.basic_service.Task_get_ftp_file;
import com.example.meinvliulanqi.basic_service.Task_getdata;
import com.example.meinvliulanqi.basic_service.Task_getimgs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity implements IGetdata, IGetFtpData
{

    private MeinvBaseAdapter ada;
    private GridView         gid_meinv;
    private JSONArray        meinvs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Task_getdata task = new Task_getdata(this);
        task.execute(new String[] { "http://192.168.10.111:8011/MeinvInfo.ashx?method=get_meinv&&check_num=1001" });
    }

    @Override
    public void onGetInfoData(String info)
    {
        try
        {
            meinvs = new JSONArray(info);
            initUIInfo();

        } catch (JSONException e)
        {
            Log.e("thinking-------", e.getMessage());
        }

    }

    private void initUIInfo()
    {
        gid_meinv = (GridView) findViewById(R.id.gid_meinv);
        ada = new MeinvBaseAdapter(meinvs, new Bitmap[meinvs.length()], this);
        gid_meinv.setAdapter(ada);

        String[][] img_infos = new String[meinvs.length()][2];
        for (int i = 0; i < img_infos.length; i++)
        {
            try
            {
                img_infos[i][0] = meinvs.getJSONObject(i)
                        .getString("photoPath");
            } catch (JSONException e)
            {
                img_infos[i][0] = "";
                Log.e("thinking-------", e.getMessage());
            }
            img_infos[i][1] = i + "";
        }

        Task_getimgs task = new Task_getimgs(this);
        task.execute(img_infos);

        gid_meinv.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3)
            {
                Task_get_ftp_file task = new Task_get_ftp_file(
                        MainActivity.this);
                task.execute(new String[] { "ftp://192.168.10.111/MeiNv_Liulanqi.apk" });

            }

        });
    }

    @Override
    public void onGetImgData(Bitmap[] img)
    {
        ada.setImgs(img);
    }

    @Override
    public void onGetFtpData(String info)
    {
        Toast t = Toast.makeText(this, info, 10000);
        t.show();
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(Uri.fromFile(new File(info)),
                "application/vnd.android.package-archive");
        this.startActivityForResult(install, 1111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1111)
        {
            Toast t = Toast.makeText(this, "°²×°Íê±Ï", 10000);
            t.show();
        }
    }
}
