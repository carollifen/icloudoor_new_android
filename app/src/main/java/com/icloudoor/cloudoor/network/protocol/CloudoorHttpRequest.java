package com.icloudoor.cloudoor.network.protocol;

import android.text.TextUtils;

import com.icloudoor.cloudoor.PreferMgr.CloudoorPreHelper;
import com.icloudoor.cloudoor.network.http.THttpMethod;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.utils.PlatformUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 云门Http请求
 * Created by Derrick Guan on 7/22/15.
 */
public class CloudoorHttpRequest extends THttpRequest {

    /**
     * HTTP默认的Method为POST, 默认RequestBody以JSON格式传输
     *
     * @param url url
     */
    public CloudoorHttpRequest(String url) {
        this(url, RequestVersion.VERSION_JSON);
    }

    /**
     * HTTP默认的Method为POST
     *
     * @param url            url
     * @param requestVersion requestVersion
     * @see com.icloudoor.cloudoor.network.protocol.RequestVersion
     */
    public CloudoorHttpRequest(String url, RequestVersion requestVersion) {
        this(url, THttpMethod.POST, requestVersion);
    }

    /**
     * @param url            url
     * @param method         method
     * @param requestVersion requestVersion
     * @see com.icloudoor.cloudoor.network.protocol.RequestVersion
     */
    public CloudoorHttpRequest(String url, THttpMethod method, RequestVersion requestVersion) {
        super(url, method);

        // 添加sid参数
        String sid = CloudoorPreHelper.getUserSid();
        if (!TextUtils.isEmpty(sid)) {
            addParameter("sid", sid);
        }
        // 添加ver参数
        addParameter("ver", PlatformUtil.getMetaData("APP_VER"));
        // 添加imei参数
        addParameter("imei", PlatformUtil.getPhoneIMEI());

        init(requestVersion);
    }


    private void init(RequestVersion requestVersion) {
        // 可以在此方法中添加Header相关信息
        switch (requestVersion) {
            case VERSION_URL_ENCODED:
                addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                break;
            case VERSION_JSON:
                addHeader("Content-Type", "application/json");
                break;
            case VERSION_MULTI_PART:
                // 如果是以Content-Type : multipart/form-data
                // 形式上传的接口，不需要addHeader
                break;
        }
    }

    public byte[] getBody(Map<String, String> params) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), "utf-8"));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), "utf-8"));
                encodedParams.append('&');
            }
            if (encodedParams.length() > 0) {
                encodedParams.substring(0, encodedParams.length());
            }
            return encodedParams.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + "utf-8", uee);
        }
    }

}
