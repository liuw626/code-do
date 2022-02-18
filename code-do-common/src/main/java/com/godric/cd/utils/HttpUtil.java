package com.godric.cd.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpUtil {

    public static String doGet(String url, Map<String, String> params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
            // 请求
            HttpGet request = new HttpGet(uriBuilder.build());
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(request);
                log.info("HttpUtil.doGet request:{}, response:{}", JSON.toJSONString(request), JSON.toJSONString(response));
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (Exception e) {
                log.error("HttpUtil.doGet error, url: " + url, e);
            } finally {
                // 释放连接
                if (response != null) {
                    try {
                        response.close();
                        httpClient.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        } catch (Exception e) {
            log.error("HttpUtil.doGet error, url: " + url, e);
        }
        return null;
    }

    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, Object> params) {
        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = HttpClientBuilder.create().build();
            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));

            //设置参数
            List<NameValuePair> nvps = new ArrayList<>();
            for (String name : params.keySet()) {
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));

            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                // 请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(), StandardCharsets.UTF_8));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line).append(NL);
                }

                in.close();

                log.info("doPost request:{}, response:{}", JSON.toJSONString(request), JSON.toJSONString(response));
                return sb.toString();
            } else {    //
                log.error("HttpUtil.doPost():状态码：" + code);
                return null;
            }
        } catch (Exception e) {
            log.error("HttpUtil.doPost():出错了", e);
            return null;
        }
    }

}
