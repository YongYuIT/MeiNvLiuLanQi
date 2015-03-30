package com.example.meinvliulanqi.basic_service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class Task_getdata extends AsyncTask<String, Integer, String>
// 第一个泛型参数：启动异步任务（即execute）时的输入参数类型，此输入参数会被doInBackground函数接收，故也是doInBackground的输入参数类型。
// 第二个泛型参数：在doInBackground函数执行过程中可以调用publishProgress函数来更新进度信息，此泛型参数即决定publishProgress函数的输入类型
// 由于此参数会被onProgressUpdate函数接收，故也是onProgressUpdate函数的输入参数类型。
// 第三个类型参数：doInBackground函数的返回值（return）类型，由于此参数会被onPostExecute函数接收，故也是onPostExecute的输入参数类型。
// 注意doInBackground，onProgressUpdate接收的是数组类型，响应的execute，publishProgress传入的也应当是数组类型
{
    private IGetdata igetdata;
    public Task_getdata(IGetdata _igetdata)
    {
        igetdata = _igetdata;
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try
        {
            // 由字符串创建URL
            URL url = new URL(arg0[0]);
            // 由指定的URL创建http连接对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接超时的时间
            conn.setConnectTimeout(5000);
            // 从http连接对象上获取连接代码，如果返回代码是200，则表明连接成功
            if (conn.getResponseCode() == 200)
            {
                // 在连接对象上打开输入流
                InputStream in = conn.getInputStream();
                String jsonStr = "";

                // 对于已知是字符流的流对象，也可以直接这样读取
                // int byteRead;
                // while ((byteRead = in.read()) != -1)
                // {
                // jsonStr += (char) byteRead;
                // }

                // 设置缓冲区
                byte[] buffer = new byte[1024 * 10];
                while (true)
                {
                    // 向缓冲区读入信息
                    int len = in.read(buffer);
                    jsonStr += (new String(buffer, "UTF-8")).trim();
                    // 清空缓冲区
                    buffer = new byte[1024 * 10];
                    if (len == -1)
                    {
                        break;
                    }
                }
                in.close();
                return jsonStr;
            } else
            {
                return "FAIL";
            }
        } catch (Exception e)
        {
            return "FAIL:" + e.getMessage();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {

    }

    @Override
    protected void onPostExecute(String result)
    {
        igetdata.onGetInfoData(result);
    }
}
