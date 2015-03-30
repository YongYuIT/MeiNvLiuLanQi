package com.example.meinvliulanqi.basic_service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
//这些类需要外部jar包：commons-net-3.1.jar
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
            // 获取请求头，应该是ftp
            String reqTitle = arg0[0].substring(0, 3);
            // 获取请求地址就是ftp://后面的内容
            String reqBody = arg0[0].substring(6);
            // 获取请求的主机地址
            String host = reqBody.split("/")[0];
            // 获取文件在服务器下的路径
            String webFilePath = reqBody.substring(reqBody.indexOf("/"),
                    reqBody.length());
            // 获取文件名
            String fileName = reqBody.substring(reqBody.lastIndexOf("/"),
                    reqBody.length());
            // 建立ftp客户端
            FTPClient ftpClient = new FTPClient();
            ftpClient.setControlEncoding("UTF-8");
            // 定义服务器响应值
            int reply;
            // 连接至服务器
            ftpClient.connect(host);
            // 获取响应值
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply))
            {
                // 断开连接
                ftpClient.disconnect();
                Log.e("thinking-------", "服务器连接失败");
                return null;
            }
            // 登录到服务器
            ftpClient.login("ftp_test", "19911214");
            // 获取响应值
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply))
            {
                // 断开连接
                Log.e("thinking-------", "服务器连接失败");
                return null;
            } else
            {
                String replyStr = ftpClient.getReplyString();
                Log.i("thinking-------", replyStr);
                // 获取登录信息
                FTPClientConfig config = new FTPClientConfig(ftpClient
                        .getSystemType().split(" ")[0]);
                config.setServerLanguageCode("zh");
                ftpClient.configure(config);
                // 使用被动模式设为默认
                ftpClient.enterLocalPassiveMode();
                // 二进制文件支持
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
            Log.e("thinking-------", "服务器连接失败");
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
