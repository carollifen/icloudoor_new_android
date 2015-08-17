package com.icloudoor.cloudoor.network.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.icloudoor.cloudoor.app.CloudoorConstants;
import com.icloudoor.cloudoor.network.bean.meta.AbstractBanner;
import com.icloudoor.cloudoor.network.bean.meta.AnnouncementBanner;
import com.icloudoor.cloudoor.network.bean.meta.LinkBanner;
import com.icloudoor.cloudoor.network.http.THttpRequest;
import com.icloudoor.cloudoor.network.protocol.CloudoorHttpRequest;
import com.icloudoor.cloudoor.network.protocol.RequestVersion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取Banner事务
 * Created by Derrick Guan on 7/29/15.
 */
public class GetBannersTransaction extends CloudoorBaseTransaction {

    public GetBannersTransaction() {
        super(TRANSACTION_GET_BANNERS);
    }

    @Override
    protected void onCloudoorTransactionSuccess(Object data) {
        List<AbstractBanner> banners = new ArrayList<AbstractBanner>();
        if (data != null && data instanceof JsonElement) {
            Gson gson = new Gson();
            JsonArray jsonArray = ((JsonElement) data).getAsJsonArray();
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonElement json = (JsonElement) it.next();
                String type = (json.getAsJsonObject().get("type")).getAsString();
                if (type.equalsIgnoreCase(CloudoorConstants.BannerType.Announcement)) {
                    AnnouncementBanner banner = gson.fromJson(json, AnnouncementBanner.class);
                    banners.add(banner);
                } else if (type.equalsIgnoreCase(CloudoorConstants.BannerType.Link)) {
                    LinkBanner banner = gson.fromJson(json, LinkBanner.class);
                    banners.add(banner);
                }
            }
            notifySuccess(banners);
        } else {
            notifyDataParseError();
        }
    }

    /**
     * 获取Banner
     * <br>wiki文档上定义此方法为get，但get的情况下会报错<br/>
     * <br>所以目前使用post方法<br/>
     *
     * @return THttpRequest
     */
    @Override
    public THttpRequest createRequest() {
        String url = getRequestUrl(USER_PROP_ZONE_MODULE, GET_BANNERS);
        return new CloudoorHttpRequest(url, RequestVersion.VERSION_URL_ENCODED);
    }
}
