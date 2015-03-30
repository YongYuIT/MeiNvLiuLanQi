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
            if (url_str.equals(""))// ���urlΪ��ֱ�ӷ���null
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
                // ������ص���Ӧ����200�����ʾ���ӳɹ���
                if (connection.getResponseCode() == 200)
                {
                    // ��http�����Ͻ���������
                    InputStream inputStream = connection.getInputStream();
                    // ��SD��ָ��λ�ô����ļ����󣬲����ļ������Ͻ��������
                    img_file.createNewFile();
                    FileOutputStream fout = new FileOutputStream(img_file);
                    // �����ֶ���ͼƬ�������������SD����--------------------------
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
                    // Ҳ���Խ�����Bitmap.compress��ͼƬ�������������SD����
                    // ���������ж�ȡBitmap
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    // ��Bitmap��֯��ָ����ʽ���ļ��������������
                    // ���ֻ�ǵ�����ʹ��Bitmap��ΪImageView��ͼƬ��Դ�� ����������Բ���
                    bitmap.compress(CompressFormat.JPEG, 100, fout);
                    inputStream.close();
                    fout.close();
                    return bitmap;

                } else
                {
                    return null;
                }
            } catch (Exception e)// �������س���ʧ�ܵĻ���ֱ�ӷ���null
            {
                Log.e("thinking-------", e.getMessage());
            }
        }
        // ��SD����ָ��λ�ö�ȡ���ݵ�Bitmap��
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
