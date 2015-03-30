package com.example.meinvliulanqi.basic_service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
//��Щ����Ҫ�ⲿjar����commons-net-3.1.jar
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import android.os.AsyncTask;
import android.util.Log;

public class Task_get_ftp_file extends AsyncTask<String, Integer, String>
{
    private IGetFtpData igetdata;

    public Task_get_ftp_file(IGetFtpData iget)
    {
        igetdata = iget;
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try
        {
            // ��ȡ����ͷ��Ӧ����ftp
            String reqTitle = arg0[0].substring(0, 3);
            // ��ȡ�����ַ����ftp://���������
            String reqBody = arg0[0].substring(6);
            // ��ȡ�����������ַ
            String host = reqBody.split("/")[0];
            // ��ȡ�ļ��ڷ������µ�·��
            String webFilePath = reqBody.substring(reqBody.indexOf("/"),
                    reqBody.length());
            // ��ȡ�ļ���
            String fileName = reqBody.substring(reqBody.lastIndexOf("/"),
                    reqBody.length());
            // ����ftp�ͻ���
            FTPClient ftpClient = new FTPClient();
            ftpClient.setControlEncoding("UTF-8");
            // �����������Ӧֵ
            int reply;
            // ������������
            ftpClient.connect(host);
            // ��ȡ��Ӧֵ
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply))
            {
                // �Ͽ�����
                ftpClient.disconnect();
                Log.e("thinking-------", "����������ʧ��");
                return null;
            }
            // ��¼��������
            ftpClient.login("ftp_test", "19911214");
            // ��ȡ��Ӧֵ
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply))
            {
                // �Ͽ�����
                Log.e("thinking-------", "����������ʧ��");
                return null;
            } else
            {
                String replyStr = ftpClient.getReplyString();
                Log.i("thinking-------", replyStr);
                // ��ȡ��¼��Ϣ
                FTPClientConfig config = new FTPClientConfig(ftpClient
                        .getSystemType().split(" ")[0]);
                config.setServerLanguageCode("zh");
                ftpClient.configure(config);
                // ʹ�ñ���ģʽ��ΪĬ��
                ftpClient.enterLocalPassiveMode();
                // �������ļ�֧��
                ftpClient
                        .setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
            }

            InputStream inputStream = ftpClient.retrieveFileStream(webFilePath);

            String filePathStr = "/sdcard/meinvliulanqi";
            File filePath = new File(filePathStr);
            if (!filePath.exists())
            {
                filePath.mkdirs();
            }
            File newApkFile = new File(filePathStr, fileName);
            if (!newApkFile.exists())
            {
                newApkFile.createNewFile();
                return filePath + "/" + fileName;
            }
            FileOutputStream fout = new FileOutputStream(newApkFile);

            byte[] buffer = new byte[1024 * 10];
            while (true)
            {
                int len = inputStream.read(buffer);
                if (len == -1)
                {
                    break;
                }
                fout.write(buffer, 0, len);
            }
            inputStream.close();
            fout.close();
            ftpClient.logout();
            ftpClient.disconnect();
            return filePath + "/" + fileName;

        } catch (Exception e0)
        {
            Log.e("thinking-------", "����������ʧ��");
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {

    }

    @Override
    protected void onPostExecute(String result)
    {
        this.igetdata.onGetFtpData(result);
    }
}
