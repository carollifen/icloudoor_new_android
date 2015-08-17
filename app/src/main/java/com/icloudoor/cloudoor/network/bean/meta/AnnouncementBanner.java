package com.icloudoor.cloudoor.network.bean.meta;

/**
 * 公告类Banner对象
 * Created by Derrick Guan on 7/29/15.
 */
public class AnnouncementBanner extends AbstractBanner {

    // 标题
    private String title;
    // 内容
    private String content;
    // 物业公司
    private String propertyCompany;
    // 日期 yyyy-MM-dd
    private String createDate;
    // 背景颜色
    private String bgColor;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPropertyCompany() {
        return propertyCompany;
    }

    public void setPropertyCompany(String propertyCompany) {
        this.propertyCompany = propertyCompany;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
