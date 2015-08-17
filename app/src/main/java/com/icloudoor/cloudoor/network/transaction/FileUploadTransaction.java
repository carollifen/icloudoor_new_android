package com.icloudoor.cloudoor.network.transaction;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.frame.TransTypeCode;
import com.icloudoor.cloudoor.network.http.Entities.FilePart;
import com.icloudoor.cloudoor.network.http.Entities.MultipartEntity;
import com.icloudoor.cloudoor.network.http.Entities.Part;
import com.icloudoor.cloudoor.network.http.Entities.StringPart;
import com.icloudoor.cloudoor.network.http.THttpMethod;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传事务
 * <br>此事务分为2步：<br/>
 * <br>1. 先通过后端接口获取上传到又拍云的相关参数;<br/>
 * <br>2. 通过上一步获取得到的参数，将File上传到又拍云服务器<br/>
 * <br>上传成功后，又拍云会返回文件的HTTP地址，将该地址返回给上层调用者<br/>
 * Created by Derrick Guan on 7/31/15.
 */
public class FileUploadTransaction extends CloudoorBaseTransaction {


    private String type;

    private String ext;

    private File file;

    /**
     * 获取文件上传策略和签名
     *
     * @param type 上传类型: 1. 物业报修 ; 31. 用户头像
     * @param ext  文件扩展名: png/jpg/jpeg
     * @param file 待上传的文件
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.UploadFileType
     * @see com.icloudoor.cloudoor.app.CloudoorConstants.FileExtension
     */
    public FileUploadTransaction(String type, String ext, File file) {
        super(TRANSACTION_FILE_UPLOAD);
        this.type = type;
        this.ext = ext;
        this.file = file;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        if (data != null && data instanceof JsonElement) {
            JsonElement json = (JsonElement) data;
            // 又拍云参数
            String policy = json.getAsJsonObject().get("policy").getAsString();
            // 又拍云参数
            String signature = json.getAsJsonObject().get("signature").getAsString();
            // 又拍云提交地址
            String submitUrl = json.getAsJsonObject().get("submitUrl").getAsString();
            // 上传图片成功后指向的HTTP地址
            String photoUrl = json.getAsJsonObject().get("photoUrl").getAsString();

            // 如果返回的参数都不是空串，则将文件上传到又拍云
            if (!TextUtils.isEmpty(policy)
                    && !TextUtils.isEmpty(signature)
                    && !TextUtils.isEmpty(submitUrl)
                    && !TextUtils.isEmpty(photoUrl)) {
                if (doUploadToUPaiyun(policy, signature, submitUrl)) {
                    // 上传文件成功
                    notifySuccess(photoUrl);
                } else {
                    // 上传文件失败, notifyError
                    notifyError(TransTypeCode.ERR_HTTP, null);
                }
            } else {
                // 有参数解析不出，notifyError
                notifyDataParseError();
            }

        } else {
            notifyDataParseError();
        }
    }


    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_FILE_MODULE, GET_SIGNATURE_AND_POLICY);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, THttpMethod.GET, RequestVersion.VERSION_URL_ENCODED);
        Map<String, String> params = new HashMap<String, String>();
        // TODO 此请求需要通过addParameter的方法进行添加参数，否则会报错，原因需要与后端确认
//        params.put("type", type);
//        params.put("ext", ext);
        request.addParameter("type", type);
        request.addParameter("ext", ext);
        request.setHttpEntity(new ByteArrayEntity(request.getBody(params)));
        return request;
    }

    private boolean doUploadToUPaiyun(String policy, String signature, String submitUrl) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost request = new HttpPost(submitUrl);

        Part[] parts;
        FilePart photoPart;

        try {
            photoPart = new FilePart("file", file);
            StringPart policyPart = new StringPart("policy", policy);
            StringPart signaturePart = new StringPart("signature", signature);

            parts = new Part[]{policyPart, signaturePart, photoPart};

        } catch (FileNotFoundException e) {
            return false;
        }

        request.setEntity(new MultipartEntity(parts));
        HttpResponse response;

        try {
            response = httpClient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            // 如果responseCode == 200 返回true 否则false
            return responseCode == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
