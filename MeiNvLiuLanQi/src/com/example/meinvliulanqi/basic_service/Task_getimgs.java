package com.example.meinvliulanqi.basic_service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class Task_getimgs extends AsyncTask<String[], Bitmap, Bitmap[]>
{
    private IGetdata igetdata;

    public Task_getimgs(IGetdata iget)
    {
        igetdata = iget;
    }

    private Bitmap getimg(String url_str, String meinv_id)
    {
        Bitmap bitmap = null;
        String img_path = "/sdcard/meinvliulanqi";
        File filePath = new File(img_path);
        if (!filePath.exists())
        {
            filePath.mkdirs();

        }

        String fileName = meinv_id + ".jpg";
        File img_file = new File(img_path, fileName);

        if (!img_file.exists())
        {
            if (url_str.equals(""))// 如果url为空直接返回null
            {
                return null;
            }
            try
            {
                URL url = new URL(url_str);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(5000);
                connection.connect();
                // 如果返回的响应码是200，则表示连接成功！
                if (connection.getResponseCode() == 200)
                {
                    // 在http连接上建立输入流
                    InputStream inputStream = connection.getInputStream();
                    // 在SD卡指定位置创建文件对象，并在文件对象上建立输出流
                    img_file.createNewFile();
                    FileOutputStream fout = new FileOutputStream(img_file);
                    // 可以手动将图片借助输出流存入SD卡中--------------------------
                    // byte[] buffer = new byte[1024 * 10];
                    // while (true)
                    // {
                    // int len = inputStream.read(buffer);
                    // if (len == -1)
                    // break;
                    // fout.write(buffer, 0, len);
                    // }
                    // inputStream.close();
                    // fout.close();
                    // -----------------------------------------------------
                    // 也可以借助于Bitmap.compress将图片借助输出流存入SD卡中
                    // 从输入流中读取Bitmap
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    // 将Bitmap组织成指定格式的文件并存入输出流，
                    // 如果只是单纯想使用Bitmap作为ImageView的图片来源， 这个动作可以不做
                    bitmap.compress(CompressFormat.JPEG, 100, fout);
                    inputStream.close();
                    fout.close();
                    return bitmap;

                } else
                {
                    return null;
                }
            } catch (Exception e)// 联网下载尝试失败的话，直接返回null
            {
                Log.e("thinking-------", e.getMessage());
            }
        }
        // 从SD卡的指定位置读取数据到Bitmap中
        bitmap = BitmapFactory.decodeFile(img_path + "/" + fileName);
        return bitmap;

    }

    @Override
    protected Bitmap[] doInBackground(String[]... arg0)
    {
        Bitmap[] imgs = new Bitmap[arg0.length];
        for (int i = 0; i < imgs.length; i++)
        {
            imgs[i] = getimg(arg0[i][0], arg0[i][1]);
            publishProgress(imgs);
        }
        return imgs;
    }

    @Override
    protected void onProgressUpdate(Bitmap... values)
    {
        igetdata.onGetImgData(values);
    }

}
