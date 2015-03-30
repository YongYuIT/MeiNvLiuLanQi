package com.example.meinvliulanqi.basic_service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MeinvDbHelper extends SQLiteOpenHelper
{
    // ���ݿ�Ļ�������
    public static final String  DB_NAME             = "meinvDB.db";
    // --------------------------------------------------------------
    // ���ݱ�Ļ��������������ͱ��е�����
    public static final String  DB_TABLE_MEINV_INFO = "tab_meinv_info";
    public static final String  KEY_ID              = "id";
    public static final String  KEY_HEAD_PATH       = "head_path";
    public static final String  KEY_NAME            = "name";
    public static final String  KEY_BIRTHDAY        = "birthday";
    public static final String  KEY_ADDRESS         = "address";
    public static final String  KEY_STATURE         = "stature";
    public static final String  KEY_WEIGHT          = "weight";
    public static final String  KEY_BUST            = "bust";
    public static final String  KEY_WAISTLINE       = "waistline";
    public static final String  KEY_HIP             = "hip";
    // --------------------------------------------------------------
    // �������ݿ��SQL���
    private static final String DATABASE_CREATE     = String.format(
                                                            "create table %s (%s integer primary key autoincrement, %s text not null, %s text not null, %s text not null, %s text not null, %s float not null, %s float not null, %s text not null, %s float not null, %s float not null)",
                                                            DB_TABLE_MEINV_INFO,
                                                            KEY_ID,
                                                            KEY_HEAD_PATH,
                                                            KEY_NAME,
                                                            KEY_BIRTHDAY,
                                                            KEY_ADDRESS,
                                                            KEY_STATURE,
                                                            KEY_WEIGHT,
                                                            KEY_BUST,
                                                            KEY_WAISTLINE,
                                                            KEY_HIP);

    // ���幹�캯��
    public MeinvDbHelper(Context context, int version)
    {
        // SQLiteOpenHelper(Context context, String name, CursorFactory factory,
        // int version)
        // ��һ���������������ݿ�������ģ����磬����������com.example.meinvliulanqi.ui.MainActivity��ʵ���������ݿ��ļ����û��ֻ��е�ʵ�ʴ洢λ�ý��ǣ�/data/data/com.example.meinvliulanqi.ui/databases
        // �ڶ��������������������ݿ�����ƣ�����������meinvDB.db����ô�����ݿ�ɹ��������֮��/data/data/com.example.meinvliulanqi.ui/databases·���¾ͻ���meinvDB.db��meinvDB.db-journal�����ļ���һ�������ݿ��ļ�����һ������־�ļ�
        // ���������ĸ�������CursorFactory���ڲ���Cursor����versionΪ�������ݿ�İ汾
        super(context, DB_NAME, null, version);

    }

    // ��дonCreate�������������ϲ��������ݿ�ʱ���𴴽������ݿ�
    @Override
    public void onCreate(SQLiteDatabase arg0)
    {

        arg0.execSQL(DATABASE_CREATE);
    }

    // ��дonUpgrade���������������ݿ�汾��һ��ʱ�����������ϵ����ݿ⵽��ǰ�汾�����ǰ�װ����ܻ�����������
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {

        arg0.execSQL("DROP TABLE IF EXIST " + DB_TABLE_MEINV_INFO);
        onCreate(arg0);
    }

}
