package com.example.meinvliulanqi.ui;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.meinvliulanqi.basic_service.IDBOperate;
import com.example.meinvliulanqi.basic_service.IGetdata;
import com.example.meinvliulanqi.basic_service.MeinvDbHelper;
import com.example.meinvliulanqi.basic_service.Task_getimgs;
import com.example.meinvliulanqi.basic_service.Task_operate_db;
import com.example.meinvliulanqi.basic_service.Task_postdata;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class PostActivity extends Activity implements IGetdata, IDBOperate
{
    private MeinvBaseAdapter ada;
    private GridView         gid_meinv;
    private JSONArray        meinvs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Task_postdata task = new Task_postdata(this);
        task.execute(new String[] {
                "http://192.168.10.111:8011/MeinvInfo.ashx",
                "{\"method\":\"get_meinv\",\"check_num\":1001}" });
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
        gid_meinv = (GridView) findViewById(R.id.gid_meinv_post);
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

        Task_operate_db task_db = new Task_operate_db(this, 1, this);
        task_db.execute();
    }
    
    @Override
    public void onGetImgData(Bitmap[] img)
    {
        ada.setImgs(img);
    }

    @Override
    public boolean doOperate(SQLiteDatabase db)
    {
        try
        {
            ContentValues newValues = new ContentValues();
            for (int i = 0; i < meinvs.length(); i++)
            {
                newValues.put(MeinvDbHelper.KEY_NAME, meinvs.getJSONObject(i)
                        .getString("name"));
                newValues.put(MeinvDbHelper.KEY_ADDRESS, meinvs
                        .getJSONObject(i).getString("address"));
                newValues.put(MeinvDbHelper.KEY_HEAD_PATH,
                        "/sdcard/meinvliulanqi/" + i + ".jpg");
                newValues.put(MeinvDbHelper.KEY_BUST, meinvs.getJSONObject(i)
                        .getString("bust"));
                newValues.put(MeinvDbHelper.KEY_HIP, meinvs.getJSONObject(i)
                        .getDouble("hip"));
                newValues.put(MeinvDbHelper.KEY_BIRTHDAY,
                        meinvs.getJSONObject(i).getString("birthday"));
                newValues.put(MeinvDbHelper.KEY_STATURE, meinvs
                        .getJSONObject(i).getDouble("stature"));
                newValues.put(MeinvDbHelper.KEY_WEIGHT, meinvs.getJSONObject(i)
                        .getDouble("weight"));
                newValues.put(MeinvDbHelper.KEY_WAISTLINE, meinvs
                        .getJSONObject(i).getDouble("waistline"));
                db.insert(MeinvDbHelper.DB_TABLE_MEINV_INFO, null, newValues);
            }

            return true;
        } catch (Exception e)
        {
            Log.e("thinking-------", e.getMessage());
            return false;
        }

    }

    @Override
    public void onDBOpreaterFinished(Boolean result)
    {
        // TODO Auto-generated method stub

    }

}
