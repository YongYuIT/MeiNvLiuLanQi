package com.example.meinvliulanqi.basic_service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class Task_operate_db extends AsyncTask<Void, Integer, Boolean>
{

    private Context    context;
    private int        db_version;
    private IDBOperate operate;

    public Task_operate_db(Context _con, int _ver, IDBOperate _ope)
    {
        context = _con;
        db_version = _ver;
        operate = _ope;
    }

    @Override
    protected Boolean doInBackground(Void... para)
    {
        MeinvDbHelper helper = new MeinvDbHelper(context, db_version);
        SQLiteDatabase db = null;
        try
        {
            db = helper.getWritableDatabase();
            return operate.doOperate(db);
        } catch (Exception e)
        {
            Log.e("thinking-------", e.getMessage());
            return false;
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {

    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        this.operate.onDBOpreaterFinished(result);
    }

}
