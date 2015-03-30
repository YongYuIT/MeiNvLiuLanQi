package com.example.meinvliulanqi.basic_service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class Task_getdata extends AsyncTask<String, Integer, String>
// ��һ�����Ͳ����������첽���񣨼�execute��ʱ������������ͣ�����������ᱻdoInBackground�������գ���Ҳ��doInBackground������������͡�
// �ڶ������Ͳ�������doInBackground����ִ�й����п��Ե���publishProgress���������½�����Ϣ���˷��Ͳ���������publishProgress��������������
// ���ڴ˲����ᱻonProgressUpdate�������գ���Ҳ��onProgressUpdate����������������͡�
// ���������Ͳ�����doInBackground�����ķ���ֵ��return�����ͣ����ڴ˲����ᱻonPostExecute�������գ���Ҳ��onPostExecute������������͡�
// ע��doInBackground��onProgressUpdate���յ����������ͣ���Ӧ��execute��publishProgress�����ҲӦ������������
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
            // ���ַ�������URL
            URL url = new URL(arg0[0]);
            // ��ָ����URL����http���Ӷ���
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // �������ӳ�ʱ��ʱ��
            conn.setConnectTimeout(5000);
            // ��http���Ӷ����ϻ�ȡ���Ӵ��룬������ش�����200����������ӳɹ�
            if (conn.getResponseCode() == 200)
            {
                // �����Ӷ����ϴ�������
                InputStream in = conn.getInputStream();
                String jsonStr = "";

                // ������֪���ַ�����������Ҳ����ֱ��������ȡ
                // int byteRead;
                // while ((byteRead = in.read()) != -1)
                // {
                // jsonStr += (char) byteRead;
                // }

                // ���û�����
                byte[] buffer = new byte[1024 * 10];
                while (true)
                {
                    // �򻺳���������Ϣ
                    int len = in.read(buffer);
                    jsonStr += (new String(buffer, "UTF-8")).trim();
                    // ��ջ�����
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
