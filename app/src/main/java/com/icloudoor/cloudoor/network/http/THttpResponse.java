package com.icloudoor.cloudoor.network.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Derrick Guan on 7/22/15.
 */

public interface THttpResponse {

    public int getResponseCode() throws IOException;

    /**
     * @return
     */
    public String getFirstHeader(String key);

    /**
     * 获取内容长度
     *
     * @return
     */
    public long getContentLength();

    /**
     * 获取内容类型
     *
     * @return
     */
    public String getContentType();

    /**
     * @return 如果原始数据是GZIP的stream，返回解压后的stream
     * @throws IOException
     */
    public InputStream getResponseStream() throws IOException;


    public void close();

}
