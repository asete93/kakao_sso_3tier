package com.camel.common;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class ApiClient {

    // Methods
    final static String METHOD_GET = "GET";
    final static String METHOD_POST = "POST";
    final static String METHOD_PUT = "PUT";
    final static String METHOD_DELETE = "DELETE";
    // Headers
    final static String APPLICATION_JSON = "application/json";
    // Encoding
    final static String CHARSET_UTF_8 = "UTF-8";


    // CloseableHttpClient 생성
    public CloseableHttpClient createClient() throws Exception {
        CookieStore cookieStore = new BasicCookieStore();
        return createClient(cookieStore);
    }


    public CloseableHttpClient createClient(CookieStore cookieStore) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setDefaultCookieStore(cookieStore) // Set CookieStore
				.build();

        return httpClient;
    }


    /*
    * ****************************************************************************************
    * GET Client
    *
    ******************************************************************************************/
    public CustomMap getClient(String url) throws Exception {
        try(CloseableHttpClient client = createClient()){
            return getClient(client, url, null); // Call Overloaded Method
        }
    }

    public CustomMap getClient(String url, CustomMap headers) throws Exception {
        try(CloseableHttpClient client = createClient()){
            return getClient(client, url, headers); // Call Overloaded Method
        }
    }

    public CustomMap getClient(CloseableHttpClient client, String url) throws Exception {
        return getClient(client, url, null); // Call Overloaded Method
    }

    public CustomMap getClient(CloseableHttpClient client, String url, CustomMap headers) throws Exception {
        HttpGet get = new HttpGet(url);

        // 헤더 비어있는 경우, 기본 헤더 설정하기.
        if(headers == null || headers.isEmpty()){
            headers = createDefaultHeader(headers, METHOD_GET);
        }

        for(String key : headers.keySet()){
            get.setHeader(key, headers.getString(key));
        }

        CloseableHttpResponse response = client.execute(get);

        return parseResponse(response);
    }


    /*
    * ****************************************************************************************
    * POST Client
    *
    ******************************************************************************************/
    public CustomMap postClient(String url, CustomMap param) throws Exception {
        try(CloseableHttpClient client = createClient()){
            return postClient(client, url, param, null);  // Call Overloaded Method
        }
    }

    public CustomMap postClient(String url, CustomMap param, CustomMap headers) throws Exception {
        try(CloseableHttpClient client = createClient()){
            return postClient(client, url, param, headers);  // Call Overloaded Method
        }
    }

    public CustomMap postClient(CloseableHttpClient client, String url, CustomMap param) throws Exception {
        return postClient(client, url, param, null); // Call Overloaded Method
    }

    public CustomMap postClient(CloseableHttpClient client, String url, CustomMap param, CustomMap headers) throws Exception {
        HttpPost post = new HttpPost(url);

        if(param == null){
            param = new CustomMap();
        }

        // 헤더 비어있는 경우, 기본 헤더 설정하기.
        if(headers == null || headers.isEmpty()){
            headers = createDefaultHeader(headers, METHOD_POST);
        }

        for(String key : headers.keySet()){
            post.setHeader(key, headers.getString(key));
        }

        post.setEntity(new StringEntity(param.toJson()));

        CloseableHttpResponse response = client.execute(post);

        return parseResponse(response);
    }


    /*
    * ****************************************************************************************
    * PUT Client
    *
    ******************************************************************************************/
    public CustomMap putClient(String url, CustomMap param) throws Exception {
        try(CloseableHttpClient client = createClient()){
            return putClient(client, url, param, null);  // Call Overloaded Method
        }
    }

    public CustomMap putClient(String url, CustomMap param, CustomMap headers) throws Exception {
        try(CloseableHttpClient client = createClient()){
            return putClient(client, url, param, headers);  // Call Overloaded Method
        }
    }

    public CustomMap putClient(CloseableHttpClient client, String url, CustomMap param) throws Exception {
        return putClient(client, url, param, null); // Call Overloaded Method
    }

    public CustomMap putClient(CloseableHttpClient client, String url, CustomMap param, CustomMap headers) throws Exception {
        HttpPut put = new HttpPut(url);

        if(param == null){
            param = new CustomMap();
        }

        // 헤더 비어있는 경우, 기본 헤더 설정하기.
        if(headers == null || headers.isEmpty()){
            headers = createDefaultHeader(headers, METHOD_PUT);
            
        }

        for(String key : headers.keySet()){
            put.setHeader(key, headers.getString(key));
        }

        put.setEntity(new StringEntity(param.toJson()));

        CloseableHttpResponse response = client.execute(put);

        return parseResponse(response);
    }



    /*
    * ****************************************************************************************
    * DELETE Client
    *
    ******************************************************************************************/
    public CustomMap deleteClient(String url) throws Exception {
        try(CloseableHttpClient client = createClient()){
            return deleteClient(client, url, null);  // Call Overloaded Method
        }
    }

    public CustomMap deleteClient(String url, CustomMap headers) throws Exception {
        try(CloseableHttpClient client = createClient()){
            return deleteClient(client, url, headers);  // Call Overloaded Method
        }
    }

    public CustomMap deleteClient(CloseableHttpClient client, String url) throws Exception {
        return deleteClient(client, url, null); // Call Overloaded Method
    }

    public CustomMap deleteClient(CloseableHttpClient client, String url, CustomMap headers) throws Exception {
        HttpDelete delete = new HttpDelete(url);

        // 헤더 비어있는 경우, 기본 헤더 설정하기.
        if(headers == null || headers.isEmpty()){
            headers = createDefaultHeader(headers, METHOD_DELETE);
            
        }

        for(String key : headers.keySet()){
            delete.setHeader(key, headers.getString(key));
        }

        CloseableHttpResponse response = client.execute(delete);

        return parseResponse(response);
    }




    /*
     * ****************************************************************************************
     * Title : 기본헤더 생성
     * Scope : private
     * Function Name : createDefaultHeader
     * ----------------------------------------------------------------------------------------
     * Description : GET방식을 제외한 나머지 방식의 Client에 대해, 따로 header를 지정하지 않았다면,
     * application/json 헤더를 기본으로 지정한다.
     * 
     ******************************************************************************************/
    private CustomMap createDefaultHeader(CustomMap headers, String type) throws Exception {
        if(headers == null) {
            CustomMap headersMap = new CustomMap();

            if(!type.equals(METHOD_GET)){
                headersMap.put("Content-Type",APPLICATION_JSON);
            }
            
            return headersMap;
        } else {

            if(!type.equals(METHOD_GET)){
                headers.put("Content-Type",APPLICATION_JSON);
            }
            return headers;
        }
    }

    /*
     * ****************************************************************************************
     * Title : 응답 결과 파싱
     * Scope : private
     * Function Name : parseResponse
     * ----------------------------------------------------------------------------------------
     * Description : 응답결과를 파싱하여, CustomMap 객체로 반환
     * 
     ******************************************************************************************/
    private CustomMap parseResponse(CloseableHttpResponse response) throws Exception {
        CustomMap responseMap = new CustomMap();

        responseMap.put("status",response.getStatusLine().getStatusCode());
        responseMap.put("headers",response.getAllHeaders());

        if(response.getEntity() != null){
            responseMap.put("body",EntityUtils.toString(response.getEntity(),CHARSET_UTF_8));
        }

        // Response 자원 반환
        response.close();

        return responseMap;
    }
}