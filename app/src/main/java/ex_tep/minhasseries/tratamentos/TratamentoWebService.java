package ex_tep.minhasseries.tratamentos;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Anailson on 16/07/2015.
 */
public class TratamentoWebService {
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT = 3;
    public final static int DELETE = 4;
    public final static int URL = 1;
    public final static int JSON = 2;

    private TratamentoWebService() {}

    public static String makeServiceCall(String url, int method) {
        return makeServiceCall(url, method, null, URL );
    }
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method (GET, POST, PUT or DELETE)
     * @params - http request params
     * @type - type of params to send (URL or JSON)
     * */
    public static String makeServiceCall(String url, int method,
                                  List<NameValuePair> params, int type) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            switch (method) {
                case GET:
                    if (params != null) {
                        if (type == URL) {
                            String paramString = URLEncodedUtils
                                    .format(params, "utf-8");
                            url += "?" + paramString;
                        } else {
                            for (NameValuePair lista : params) {
                                url += "/"+ lista.getValue();
                            }
                        }
                     }
                    HttpGet httpGet = new HttpGet(url);
                    httpResponse = httpClient.execute(httpGet);
                    break;
                case POST:
                    HttpPost httpPost = new HttpPost(url);
                    if (type == URL) {
                        if (params != null) {
                            httpPost.setEntity(new UrlEncodedFormEntity(params));
                        }
                    } else if (type == JSON) {
                        try {
                            JSONObject json = new JSONObject();
                            for (NameValuePair lista : params) {
                                json.put(lista.getName(), lista.getValue());
                            }
                            StringEntity se = new StringEntity(json.toString());
                            httpPost.setEntity(se);
                            httpPost.setHeader("Accept", "application/json");
                            httpPost.setHeader("Content-type", "application/json");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    httpResponse = httpClient.execute(httpPost);
                    break;
                case PUT:
                    HttpPut httpPut = new HttpPut(url);
                    try {
                        JSONObject json = new JSONObject();
                        for (NameValuePair lista : params) {
                            json.put(lista.getName(), lista.getValue());
                        }
                        StringEntity se = new StringEntity(json.toString());
                        httpPut.setEntity(se);
                        httpPut.setHeader("Accept", "application/json");
                        httpPut.setHeader("Content-type", "application/json");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    httpResponse = httpClient.execute(httpPut);
                    break;
                case DELETE:
                    HttpDelete httpDelete = new HttpDelete(url);
                    httpResponse = httpClient.execute(httpDelete);
                    break;
            }


            httpEntity = httpResponse.getEntity();
            if (httpEntity != null)
                response = EntityUtils.toString(httpEntity);
            else
                response = "OK";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}