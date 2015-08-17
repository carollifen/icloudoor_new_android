package com.icloudoor.cloudoor.network.protocol;

/**
 * Created by Derrick Guan on 7/22/15.
 */
public enum RequestVersion {
    /**
     * 1.0 & 1.2版本的请求
     */
    VERSION_URL_ENCODED,

    /**
     * 1.3版本及之后的请求
     */
    VERSION_JSON,

    /**
     * 上传头像等
     * Content-Type: multipart/form-data
     */

    VERSION_MULTI_PART
}
