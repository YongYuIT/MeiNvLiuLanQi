package com.example.meinvliulanqi.basic_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class Task_postdata extends AsyncTask<String, Integer, String>
// ��һ�����Ͳ����������첽���񣨼�execute��ʱ������������ͣ�����������ᱻdoInBackground�������գ���Ҳ��doInBackground������������͡�
// �ڶ������Ͳ�������doInBackground����ִ�й����п��Ե���publishProgress���������½�����Ϣ���˷��Ͳ���������publishProgress��������������
// ���ڴ˲����ᱻonProgressUpdate�������գ���Ҳ��onProgressUpdate����������������͡�
// ���������Ͳ�����doInBackground�����ķ���ֵ��return�����ͣ����ڴ˲����ᱻonPostExecute�������գ���Ҳ��onPostExecute������������͡�
// ע��doInBackground��onProgressUpdate���յ����������ͣ���Ӧ��execute��publishProgress�����ҲӦ������������
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
            
            // ���ַ�������URL
            URL url = new URL(arg0[0]);
            // ��ָ����URL����http���Ӷ���
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // �������ӳ�ʱ��ʱ��
            conn.setConnectTimeout(5000);
            // �������ӵ��������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��http���Ӷ����Ͻ��������
            out = new PrintWriter(conn.getOutputStream());
            // ���������������������������
            out.print(arg0[1]);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ����
            // Ҳ������Task_getdata.java����Ķ�ȡ���������ݵ������ַ���
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
        // ʹ��finally�����ر��������������
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
