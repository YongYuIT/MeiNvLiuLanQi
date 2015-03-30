package com.example.meinvliulanqi.basic_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class Task_postdata extends AsyncTask<String, Integer, String>
// 第一个泛型参数：启动异步任务（即execute）时的输入参数类型，此输入参数会被doInBackground函数接收，故也是doInBackground的输入参数类型。
// 第二个泛型参数：在doInBackground函数执行过程中可以调用publishProgress函数来更新进度信息，此泛型参数即决定publishProgress函数的输入类型
// 由于此参数会被onProgressUpdate函数接收，故也是onProgressUpdate函数的输入参数类型。
// 第三个类型参数：doInBackground函数的返回值（return）类型，由于此参数会被onPostExecute函数接收，故也是onPostExecute的输入参数类型。
// 注意doInBackground，onProgressUpdate接收的是数组类型，响应的execute，publishProgress传入的也应当是数组类型
{
    private IGetdata igetdata;

    public Task_postdata(IGetdata _igetdata)
    {
        igetdata = _igetdata;
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try
        {
            
            // 由字符串创建URL
            URL url = new URL(arg0[0]);
            // 由指定的URL创建http连接对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接超时的时间
            conn.setConnectTimeout(5000);
            // 设置连接的相关属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 在http连接对象上建立输出流
            out = new PrintWriter(conn.getOutputStream());
            // 借助输出流向服务器发送请求参数
            out.print(arg0[1]);
            // flush输出流的缓冲
            out.flush();
            // 建立BufferedReader输入流来读取URL的响应数据
            // 也可以用Task_getdata.java里面的读取输入流数据的那两种方法
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        } catch (Exception e)
        {
            return "FAIL:" + e.getMessage();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            } catch (IOException ex)
            {
                return "FAIL:" + ex.getMessage();
            }
        }
        return result;

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
