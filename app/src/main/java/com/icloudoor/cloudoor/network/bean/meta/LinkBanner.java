package com.icloudoor.cloudoor.network.bean.meta;

/**
 * 链接类Banner
 * Created by Derrick Guan on 7/29/15.
 */
public class LinkBanner extends AbstractBanner {

    // 背景图片url
    private String photoUrl;
    // 跳转的链接
    private String link;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
