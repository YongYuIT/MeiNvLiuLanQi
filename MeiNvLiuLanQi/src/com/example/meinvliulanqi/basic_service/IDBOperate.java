package com.example.meinvliulanqi.basic_service;

import android.database.sqlite.SQLiteDatabase;

public interface IDBOperate
{
    public boolean doOperate(SQLiteDatabase db);

    public void onDBOpreaterFinished(Boolean result);
}
