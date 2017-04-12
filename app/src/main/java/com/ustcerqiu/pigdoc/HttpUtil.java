package com.ustcerqiu.pigdoc;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by kkwang on 3/11/2017.
 * 网络请求共用方法类
 * 1、sendHttpRequest 通过HttpURLConnection发送网络请求;
 * 2、sendOkHttpRequest 通过 OkHttp发送请求
 */

public class HttpUtil {

    //------------ 1 -------------------
    //通过HttpURLConnection发送网络请求
    static public void sendHttpRequest(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    connection.setRequestProperty("Charset", "utf-8");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null) {
                        response.append(line);
                    }
                    if(listener!=null)
                    {
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if(listener!=null)
                    {
                        listener.onError(e);
                    }
                }finally {
                    if(reader != null){
                        try{
                            reader.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection !=null)
                    {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    } //sendHttpRequest


    //------------ 2 -------------------
    //通过OkHttp发送网络请求
    static public void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    } //sendOkHttpRequest




}
