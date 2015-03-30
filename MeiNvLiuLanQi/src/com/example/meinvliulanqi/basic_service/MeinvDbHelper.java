package com.example.meinvliulanqi.basic_service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MeinvDbHelper extends SQLiteOpenHelper
{
    // 数据库的基本参数
    public static final String  DB_NAME             = "meinvDB.db";
    // --------------------------------------------------------------
    // 数据表的基本描述，表名和表中的列名
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
    // 创建数据库的SQL语句
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

    // 定义构造函数
    public MeinvDbHelper(Context context, int version)
    {
        // SQLiteOpenHelper(Context context, String name, CursorFactory factory,
        // int version)
        // 第一个参数：创建数据库的上下文，比如，如果传入的是com.example.meinvliulanqi.ui.MainActivity的实例，那数据库文件在用户手机中的实际存储位置将是：/data/data/com.example.meinvliulanqi.ui/databases
        // 第二个参数：欲创建的数据库的名称，比如名称是meinvDB.db，那么在数据库成功创建完成之后/data/data/com.example.meinvliulanqi.ui/databases路径下就会有meinvDB.db、meinvDB.db-journal两个文件，一个是数据库文件，另一个是日志文件
        // 第三、第四个参数：CursorFactory用于产生Cursor对象，version为创建数据库的版本
        super(context, DB_NAME, null, version);

    }

    // 重写onCreate函数，当磁盘上不存在数据库时负责创建新数据库
    @Override
    public void onCreate(SQLiteDatabase arg0)
    {

        arg0.execSQL(DATABASE_CREATE);
    }

    // 重写onUpgrade函数，当存在数据库版本不一致时，升级磁盘上的数据库到当前版本，覆盖安装后可能会出现这种情况
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {

        arg0.execSQL("DROP TABLE IF EXIST " + DB_TABLE_MEINV_INFO);
        onCreate(arg0);
    }

}
