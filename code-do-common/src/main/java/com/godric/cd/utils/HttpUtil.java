package com.godric.cd.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class HttpUtil {

    public static String doGet(String url, Map<String, String> params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 设置请求
            URIBuilder uriBuilder = new URIBuilder(url);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // 请求
            CloseableHttpResponse httpResponse = null;
            try {
//            System.out.println("exec start");
//            System.out.println(new Date());
                httpResponse = httpClient.execute(httpGet);
//            System.out.println("exec end");
//            System.out.println(new Date());
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                }
            } catch (Exception e) {
                log.error("doGet error, url = " + url, e);
            } finally {
                // 释放连接
                if (null != httpResponse) {
                    try {
                        httpResponse.close();
                        httpClient.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

}
