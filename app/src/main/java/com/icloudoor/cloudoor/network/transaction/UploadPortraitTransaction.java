package com.icloudoor.cloudoor.network.transaction;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.network.http.Entities.FilePart;
import com.icloudoor.cloudoor.network.http.Entities.MultipartEntity;
import com.icloudoor.cloudoor.network.http.Entities.Part;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 上传图片事务
 * Created by Derrick Guan on 7/25/15.
 */
public class UploadPortraitTransaction extends CloudoorBaseTransaction {

    private File file;

    public UploadPortraitTransaction(File file) {
        super(TRANSACTION_UPLOAD_PORTRAIT);
        this.file = file;
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        String portraitUrl = null;
        if (data != null && data instanceof JsonElement) {
            JsonElement json = ((JsonElement) data).getAsJsonObject().get("portraitUrl");
            portraitUrl = json.getAsString();
        }
        if (!TextUtils.isEmpty(portraitUrl)) {
            notifySuccess(portraitUrl);
        } else {
            notifyDataParseError();
        }

    }

    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_API_MODULE, UPLOAD_PORTRAIT);
        CloudoorHttpRequest request = new CloudoorHttpRequest(url, RequestVersion.VERSION_MULTI_PART);

        FilePart filePart = null;
        try {
            filePart = new FilePart("portrait", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Part[] parts = null;
        if (filePart != null) {
            parts = new Part[]{filePart};
        }
        request.setHttpEntity(new MultipartEntity(parts));
        return request;
    }
}
