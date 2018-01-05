package com.netease.urs.sms.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http client utils
 * Created by hzliuxin1 on 2016/8/26.
 */
public class HttpClientUtils implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
    private static HttpClient httpClient;
    private int socketTimeout = 1000;
    private int connectTimeout = 1000;
    private int connectionRequestTimeout = 1000;
    private int connectionMaxTotal = 500;
    private int connectionDefaultMaxPerRoute = 10;

    @Override
    public void afterPropertiesSet() {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
                .getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
                .getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory>create().register("http", plainsf)
                .register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                registry);
        cm.setMaxTotal(connectionMaxTotal);
        cm.setDefaultMaxPerRoute(connectionDefaultMaxPerRoute);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * http GET 请求
     *
     * @param url 请求URL
     * @return 请求结果
     * @throws IOException
     */
    public ResponseEntity<String> doGet(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).build());
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            return new ResponseEntity(EntityUtils.toString(httpResponse.getEntity()),
                    HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode()));
        } finally {
            close(httpGet, httpResponse);
        }
    }

    private void close(HttpRequestBase httpRequestBase, HttpResponse httpResponse) {
        try {
            if (httpRequestBase != null) {
                httpRequestBase.releaseConnection();
            }
        } finally {
            if (httpResponse != null) {
                try {
                    try {
                        EntityUtils.consume(httpResponse.getEntity());
                    } finally {
                        if (httpResponse instanceof Closeable) {
                            ((Closeable) httpResponse).close();
                        }
                    }
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
        }

    }

    /**
     * http POST 请求, 请求数据在body内
     *
     * @param url        请求地址
     * @param msgContent 请求数据
     * @return 返回结果
     */
    public ResponseEntity<String> doPost(String url, String msgContent) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom().setConnectTimeout(200).setConnectionRequestTimeout(500).build());
        HttpResponse httpResponse = null;
        try {
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN.toString());
            httpPost.setEntity(new StringEntity(msgContent, Charset.forName("UTF-8")));
            httpResponse = httpClient.execute(httpPost);
            return new ResponseEntity(EntityUtils.toString(httpResponse.getEntity()), HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode()));
        } finally {
            close(httpPost, httpResponse);
        }
    }

    /**
     * http POST 请求, 请求数据为UrlEncodedFormEntity
     *
     * @param url    请求地址
     * @param heads  请求头
     * @param params 请求参数
     * @return 返回结果
     * @throws IOException
     */
    public ResponseEntity<String> doPost(String url, Map<String, String> heads, Map<String, Object> params)
            throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).build());
        HttpResponse httpResponse = null;
        try {
            if (heads != null) {
                for (Map.Entry<String, String> head : heads.entrySet()) {
                    httpPost.addHeader(head.getKey(), head.getValue());
                }
            }
            if (params != null) {
                List<NameValuePair> nvps = new ArrayList<>();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    nvps.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            }
            httpResponse = httpClient.execute(httpPost);
            return new ResponseEntity(EntityUtils.toString(httpResponse.getEntity()),
                    HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode()));
        } finally {
            close(httpPost, httpResponse);
        }
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getConnectionMaxTotal() {
        return connectionMaxTotal;
    }

    public void setConnectionMaxTotal(int connectionMaxTotal) {
        this.connectionMaxTotal = connectionMaxTotal;
    }

    public int getConnectionDefaultMaxPerRoute() {
        return connectionDefaultMaxPerRoute;
    }

    public void setConnectionDefaultMaxPerRoute(int connectionDefaultMaxPerRoute) {
        this.connectionDefaultMaxPerRoute = connectionDefaultMaxPerRoute;
    }
}
