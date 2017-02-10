package com.kevindai.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by kevindai on 2017/2/10.
 */
public class HttpClientsUtils {

    /**
     * 发送http请求并获取响应
     * @param url
     * @return
     */
    public static String httpClientSendUrl(String url){
        String result = null;
        try{
            HttpClient httpClient = HttpClients.createDefault();//初始化一个httpClient链接
            HttpGet httpGet = new HttpGet(url);//创建一个get请求
            HttpResponse response = httpClient.execute(httpGet);//获取请求的返回值
            HttpEntity entity = response.getEntity();//得到请求返回值的数据
            result = EntityUtils.toString(entity);//格式化结果
        }catch (Exception e){
            System.out.println("httpClientSend请求异常");
        }
        return result;
    }

}
